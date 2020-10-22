import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;

public class JSONConverter {
    public static String toJson(String type, String data) throws IOException {
        String json = "";
        ObjectMapper jsonMapper = new ObjectMapper();
        switch (type) {
            case "application/json": {
                return data;
            }
            case "application/xml": {
                XmlMapper xmlMapper = new XmlMapper();
                JsonNode node = xmlMapper.readTree(data.getBytes());
                json = jsonMapper.writeValueAsString(node);
                break;
            }
            case "text/csv": {
                json = CSVtoJSON.csvToJson(data);
                break;
            }
            case "application/x-yaml": {
                YAMLMapper yamlMapper = new YAMLMapper();
                JsonNode node = yamlMapper.readTree(data.getBytes());
                json = jsonMapper.writeValueAsString(node);
                break;
            }
            default: {
                System.out.println("Unknown data type: " + type);
                break;
            }
        }

        return json;
    }
}
