import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    private static final String registerURL = "/register";
    private static final String homeURL = "/home";
    private static final String data = "";


    public static void main(String[] args) throws Exception {


        String string = (String) getAccessToken.getToken(registerURL, data);
//        System.out.println("Register JSON: " + string);

        try {
            FileWriter myWriter = new FileWriter("register.json");
            myWriter.write(string);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        Object obj = new JSONParser().parse(new FileReader("register.json"));
        JSONObject jo = (JSONObject) obj;
        String accessToken = (String) jo.get("access_token");
        String link = (String) jo.get("link");

        System.out.println("Register:");
        System.out.println("Access token: " + accessToken);
        System.out.println("Route: " + link);

        String homeLink = (String) getAccessToken.getToken(homeURL, accessToken);
        System.out.println("\nHome: " + homeLink);

        try {
            FileWriter myWriter1 = new FileWriter("home.json");
            myWriter1.write(homeLink);
            myWriter1.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }
//        Object obj1 = new JSONParser().parse(new FileReader("home.json"));
//        JSONObject jo1 = (JSONObject) obj1;
//        String message = (String) jo1.get("msg");
//        String link1 = (String) jo1.get("link");
//
//
//        System.out.println("\nHome:");
//        System.out.println("Message: " + message);
//        System.out.println("Links: " + link1);
}