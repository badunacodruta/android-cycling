package scripting;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScriptingCreateGraph {

    public static final List<NodeScripting> DB_TRACKS = new ArrayList<NodeScripting>();
    public static final Map<String, List<NodeScripting>> DB_MAP = new HashMap<String, List<NodeScripting>>();


    public static final double maxDistanceBetweenPoints = 0.0001;
    public static final String segment_size = ".5";

    public static void run(String path, String trackId) {
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(path));
            JSONArray jsonObject = (JSONArray) obj;

            for (Object aJsonObject : jsonObject) {
                JSONObject node = (JSONObject) aJsonObject;

                JSONArray gpsLocation = (JSONArray) node.get("data");
                gpsLocation.get(0);
                gpsLocation.get(1);

            }
        } catch (Exception e) {
            System.err.println(path);
            e.printStackTrace();
        }

    }


}
