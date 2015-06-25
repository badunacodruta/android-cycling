package scripting;

import java.io.File;

public class Neighbors {


    public static final String dirs[] = {
            "Ro-AtoB-Gravel.tracks.data",
            "Ro-AtoB-Paved.tracks.data",
            "Ro-AtoB-Unpaved.tracks.data",
            "Ro-MTB-Gravel.tracks.data",
            "Ro-MTB-Paved.tracks.data",
            "Ro-MTB-Unpaved.tracks.data",
            "Ro-Racing-Gravel.tracks.data",
            "Ro-Racing-Paved.tracks.data",
            "Ro-Racing-Unpaved.tracks.data"
    };
    public static final String BASE_PATH = "/Users/mciorobe/work/mihai/mihigh/cycling-app/bikemap/data/trackManyPoints/";

    public static void main(String[] args) {
        for (String dir : dirs) {
            dir = "Ro-AtoB-Gravel.tracks.data";
            File folder = new File(BASE_PATH + dir);
            File[] allFiles = folder.listFiles();


            for (File file : allFiles) {
                if (!file.getName().endsWith(".points")) {
                    continue;
                }

                String trackId = file.getName().replace(".points", "");
                ScriptingCreateGraph.run(file.getPath(), trackId);
            }


            break;
        }

    }
}
