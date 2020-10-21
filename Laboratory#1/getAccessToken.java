import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class getAccessToken{
    private static final String rootURL = "http://localhost:5000";
    private static HttpURLConnection connection;

    public static String getToken(String link, String header) {
        try {
            URL url = new URL(rootURL + link);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Access-Token", header);
            connection.setUseCaches(false);
            connection.setAllowUserInteraction(false);
            connection.connect();
            int statusResponse = connection.getResponseCode();

            switch (statusResponse) {
                case 200:
                case 201:

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String inputLine = bufferedReader.readLine();
//                    System.out.println(inputLine);
                    stringBuilder.append(inputLine);
                    return stringBuilder.toString();

            }

        } catch(Exception e) {
            System.out.println("Something went wrong. Try again!");
        }
        return null;
    }
}
