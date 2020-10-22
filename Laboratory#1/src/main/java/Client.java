import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.*;

public class Client {

    private String accessToken;
    private String rootURL;
    private DataReader dataReader;


    Client() {
        this.dataReader = new DataReader();
        this.rootURL = "http://localhost:5000";
    }

    public String get(String uri) {
        try {
            org.apache.http.client.HttpClient client = HttpClientBuilder.create().build();
            String text = "";
            HttpGet request = new HttpGet(this.rootURL + uri);
            request.setHeader("X-Access-Token", accessToken);
            HttpResponse responseCode = client.execute(request);


            if (responseCode.getStatusLine().getStatusCode() == 200 || responseCode.getStatusLine().getStatusCode() == 201) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(responseCode.getEntity().getContent()));
                text = dataReader.readStreamFromReader(reader);
                if (uri.equals("/register")) {
                    searchJsonKey("access_token", JsonParser.parseString(text), new ArrayList<>());
                }
            } else {
                System.out.println("Error reading web page.");
                return null;
            }
            return text;
        } catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }


    public String searchKeyValue(String key, String text) {
        String value = "";
        List<String> list = new ArrayList<>();
        searchJsonKey(key, JsonParser.parseString(text), list);
        if (list.isEmpty()) {
            return null;
        } else {
            value = list.get(0);
        }
        return value;
    }

    public List<String> searchJsonKey(String key, JsonElement jsonElement, List<String> list) {

        if (jsonElement.isJsonArray()) {
            for (JsonElement jElement : jsonElement.getAsJsonArray()) {
                searchJsonKey(key, jElement, list);
            }
        } else {
            if (jsonElement.isJsonObject()) {
                Set<Map.Entry<String, JsonElement>> entrySet = jsonElement
                        .getAsJsonObject().entrySet();
                for (Map.Entry<String, JsonElement> entry : entrySet) {
                    String thisKey = entry.getKey();
                    if (thisKey.equals(key)) {
                        String value = entry.getValue().toString();
                        try {
                            JSONObject jsonKeyObj = new JSONObject(value);
                            Iterator<String> keySet = jsonKeyObj.keys();
                            while (keySet.hasNext()) {
                                list.add(jsonKeyObj.get(keySet.next()).toString());
                            }
                        } catch (JSONException e) {
                            e.getMessage();
                            if (key.equals("access_token")) {
                                accessToken = entry.getValue().getAsString();
                                System.out.println("Access Token: " + accessToken);
                            } else {
                                list.add(entry.getValue().getAsString());
                            }

                        }

                    }
                    searchJsonKey(key, entry.getValue(), list);
                }
            } else {
                if (jsonElement.toString().equals(key)) {
                    list.add(jsonElement.toString());
                }
            }
        }

        return list;
    }


}
