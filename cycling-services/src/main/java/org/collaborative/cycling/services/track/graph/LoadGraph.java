package org.collaborative.cycling.services.track.graph;

import org.collaborative.cycling.models.RoadType;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class LoadGraph {
    private final String dirs[] = {
            "test.data"
//            "Ro-AtoB-Gravel.tracks.data",
//            "Ro-AtoB-Paved.tracks.data",
//            "Ro-AtoB-Unpaved.tracks.data",
//            "Ro-MTB-Gravel.tracks.data",
//            "Ro-MTB-Paved.tracks.data",
//            "Ro-MTB-Unpaved.tracks.data",
//            "Ro-Racing-Gravel.tracks.data",
//            "Ro-Racing-Paved.tracks.data",
//            "Ro-Racing-Unpaved.tracks.data"
    };
    private final String BASE_PATH = "/Users/baduna/personal/android-cycling/cycling-services/src/main/resources/";

    public LoadGraph() {
        for (String dir : dirs) {
//            System.out.println(dir);
            File folder = new File(BASE_PATH + dir);
            File[] allFiles = folder.listFiles();

            for (final File file : allFiles) {
                if (!file.getName().endsWith(".graph")) {
                    continue;
                }

                String trackId = file.getName().replace(".graph", "");

                RoadType roadType = RoadType.PAVED;
                for (RoadType type : RoadType.values()) {
                    if (dir.toLowerCase().contains(type.name().toLowerCase())) {
                        roadType = type;
                        break;
                    }
                }

                Graph.loadGraph(file.getPath(), trackId, roadType);
            }
        }
    }
}
