import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.net.*;
import java.io.*;
import java.net.URLConnection;

public class Main {
    private static final String rootURL = "http://localhost:5000";
    private static final String registerURL = rootURL + "/register";
    private static final String home = rootURL + "/home";
    private static final String data = "";


    public static void main(String[] args) throws Exception {
        URL oracle = new URL(registerURL);
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        String inputLine = in.readLine();

//        System.out.println(inputLine);

        try {
            FileWriter myWriter = new FileWriter("register.json");
            myWriter.write(inputLine);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        Object obj = new JSONParser().parse(new FileReader("register.json"));
        JSONObject jo = (JSONObject) obj;
        String accessToken = (String) jo.get("access_token");
        String link = (String) jo.get("link");

        System.out.println("Access token: " + accessToken);
        System.out.println("Route: " + link);


    }
}