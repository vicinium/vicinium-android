package io.evilolive.vicinium;

import android.location.Location;

public class RoomCoordinates {
    public final int latitude ;
    public final int longitude;

    RoomCoordinates(Location location) {
        this.latitude = (int) Math.floor(location.getLatitude() * 1000);
        this.longitude =  (int) Math.floor(location.getLongitude() * 1000);
    }



}
