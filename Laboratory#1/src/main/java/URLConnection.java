import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class URLConnection {
    public static List<String> dataList = new ArrayList<>();
    private static Client httpClient;
    private static List<String> linkList = new ArrayList<>();
    private static ExecutorService exe;
    private static DataReader dataReader;
    private static Integer y = 0;

    URLConnection() {
        httpClient = new Client();
        exe = Executors.newCachedThreadPool();
        dataReader = new DataReader();
    }


    public boolean initiate() {
        String registerURL = "/register";
        exe.submit(new Thread(registerURL));
        long startTime = System.currentTimeMillis();
        while (true) {
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            if (totalTime >= 20000) {
                exe.shutdown();
                return true;
            }
        }
    }

    static class Thread implements Runnable {
        private String registerURL;

        Thread(String registerURL) {
            this.registerURL = registerURL;
            System.out.println(registerURL);
        }

        @Override
        public void run() {
            try {
                String result = httpClient.get(registerURL);
                httpClient.searchJsonKey("link", JsonParser.parseString(result), linkList);
                String data = httpClient.searchKeyValue("data", result);
                if (data != null) {
                    String type = httpClient.searchKeyValue("mime_type", result);
                    if (type == null) {
                        type = "application/json";
                    }
                    System.out.println("\n-------------------------------------------------------------------------");
                    System.out.println("ROUTE: " + registerURL + "\nTYPE: " + type);
                    System.out.println("-------------------------------------------------------------------------");
                    System.out.println(JSONConverter.toJson(type, data));
                    dataList.add(JSONConverter.toJson(type, data));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (int i = y; i < linkList.size(); i++) {
                exe.submit(new Thread(linkList.get(i)));
                y++;

            }
        }
    }
}
