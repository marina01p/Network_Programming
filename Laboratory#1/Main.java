import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    private static final String rootURL = "http://localhost:5000";
    private static final String registerURL = "/register";
    private static final String homeURL = "/home";
    private static final String data = "";

    public static void main(String[] args) throws Exception {
        ThreadJoining t1 = new ThreadJoining();
        ThreadJoining t2 = new ThreadJoining();
        ThreadJoining t3 = new ThreadJoining();
        ThreadJoining t4 = new ThreadJoining();

        String string = (String) GetAccessToken.getAccessToken(registerURL, data);
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

        String homeLink = (String) GetAccessToken.getAccessToken(homeURL, accessToken);
        System.out.println("\nHome:");
//
//        try {
//            FileWriter myWriter1 = new FileWriter("home.json");
//            myWriter1.write(homeLink);
//            myWriter1.close();
//        } catch (IOException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }

//        URL jsonUrl = new URL(rootURL + homeURL);
        User user = null;

        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        user = mapper.readValue(homeLink, User.class);
        System.out.println("Message: " + user.getMsg());

        user = mapper.readValue(homeLink, User.class);
        System.out.println("\nRoute_1: " + user.getLink().getRoute_1());
        t1.start();

        try {
            System.out.println("Current Thread: "
                    + Thread.currentThread().getName());
            t1.join();
        } catch(Exception ex) {
            System.out.println("Exception has " +
                    "been caught" + ex);
        }

        String route_1 = (String) GetAccessToken.getAccessToken(user.getLink().getRoute_1(), accessToken);
        System.out.println(route_1);

        try {
            FileWriter myWriter1 = new FileWriter("route1.txt");
            myWriter1.write(route_1);
            myWriter1.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        user = mapper.readValue(homeLink, User.class);
        System.out.println("\nRoute_2: " + user.getLink().getRoute_2());
        t2.start();

        try {
            System.out.println("Current Thread: "
                    + Thread.currentThread().getName());
            t2.join();
        } catch(Exception ex) {
            System.out.println("Exception has been" +
                    " caught" + ex);
        }

        String route_2 = (String) GetAccessToken.getAccessToken(user.getLink().getRoute_2(), accessToken);
        System.out.println(route_2);

        user = mapper.readValue(homeLink, User.class);
        System.out.println("\nRoute_3: " + user.getLink().getRoute_3());
        t3.start();

        try {
            System.out.println("Current Thread: "
                    + Thread.currentThread().getName());
            t3.join();
        } catch(Exception ex)
        {
            System.out.println("Exception has been" +
                    " caught" + ex);
        }

        String route_3 = (String) GetAccessToken.getAccessToken(user.getLink().getRoute_3(), accessToken);
        System.out.println(route_3);

        user = mapper.readValue(homeLink, User.class);
        System.out.println("\nRoute_4: " + user.getLink().getRoute_4());
        t4.start();
//        try {
//            System.out.println("Current Thread: "
//                    + Thread.currentThread().getName());
//            t4.join();
//        } catch(Exception ex)
//        {
//            System.out.println("Exception has been" +
//                    " caught" + ex);
//        }
        String route_4 = (String) GetAccessToken.getAccessToken(user.getLink().getRoute_4(), accessToken);
        System.out.println(route_4);



    }


}