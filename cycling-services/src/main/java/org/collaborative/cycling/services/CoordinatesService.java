package org.collaborative.cycling.services;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import org.collaborative.cycling.models.Coordinates;
import org.springframework.stereotype.Service;

@Service
public class CoordinatesService {

    public double computeDistance(Coordinates coordinates1, Coordinates coordinates2) {
        LatLng latLng1 = new LatLng(coordinates1.getLatitude(), coordinates1.getLongitude());
        LatLng latLng2 = new LatLng(coordinates2.getLatitude(), coordinates2.getLongitude());

        return LatLngTool.distance(latLng1, latLng2, LengthUnit.METER);
    }
}
