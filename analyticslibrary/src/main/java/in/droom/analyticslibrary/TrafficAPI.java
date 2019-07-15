package in.droom.analyticslibrary;

import android.util.Log;

import java.util.Random;

public class TrafficAPI {
    public static int gettraffic(){
        Random rand = new Random();

        // Generate random integers in range 0 to 999
        int traffic = rand.nextInt(10);

        Log.d("Traffic class : ", ">>>> traffic : " + traffic);

        return traffic;
    }
}
