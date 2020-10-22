import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DataReader {

    DataReader() {

    }

    public String readStreamFromReader(Reader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        int ch;
        while ((ch = reader.read()) != -1) {
            sb.append((char) ch);
        }

        return sb.toString();
    }


    public ArrayList<String> getValuesOfKey(String key, List<String> jsonList) {
        ArrayList<String> values = new ArrayList<>();
        for (String json : jsonList) {
            new Client().searchJsonKey(key, JsonParser.parseString(json), values);
        }
        return values;
    }

    public ArrayList<String> getKeyAndValue(String key, String value, List<String> jsonList) {
        ArrayList<String> objects = new ArrayList<>();
        for (String obj : jsonList) {
            JsonElement jsonElement = JsonParser.parseString(obj);
            if (jsonElement.isJsonArray()) {
                for (JsonElement jsonElement1 : jsonElement.getAsJsonArray()) {
                    if (jsonElement1.isJsonObject()) {
                        {
                            if (jsonElement1.getAsJsonObject().get(key) != null) {
                                String val = jsonElement1.getAsJsonObject().get(key).getAsString();
                                if (val.equals(value)) {
                                    objects.add(jsonElement1.toString());
                                }
                            }
                        }
                    }
                }
            } else {
                if (jsonElement.isJsonObject()) {
                    if (jsonElement.getAsJsonObject().get(key) != null) {
                        String val = jsonElement.getAsJsonObject().get(key).getAsString();
                        if (val.equals(value)) {
                            objects.add(jsonElement.toString());
                        }
                    }
                }
            }
        }

        return objects;
    }

    public String getProperty(String property) {
        String data = "";
        try {
            InputStream inputStream = DataReader.class.getResourceAsStream("/config.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            data = properties.getProperty(property);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


}
