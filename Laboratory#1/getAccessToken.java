import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class GetAccessToken {
    private static final String rootURL = "http://localhost:5000";
    private static HttpURLConnection connection;

    public static Object getAccessToken(String link, String header) {
        try {
            URL url = new URL(rootURL + link);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Access-Token", header);
//            connection.setReadTimeout(30000);
            connection.setUseCaches(false);
            connection.setAllowUserInteraction(false);
            connection.connect();

            int responseCode = connection.getResponseCode();
//            System.out.println("Response code: " + responseCode);

            if (responseCode == 200 || responseCode == 201) {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String inputLine = bufferedReader.readLine();
//                System.out.println(inputLine);
                stringBuilder.append(inputLine);

                return stringBuilder.toString();

            } else {
                System.out.println("Error reading web page");
                return null;
            }


            } catch (ProtocolException protocolException) {
            protocolException.printStackTrace();
        } catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }
}