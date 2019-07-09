package in.droom.newapplication;

import android.app.Application;
import android.util.Log;
import in.droom.analyticslibrary.SingletonClass;

public class MyApplication extends Application {
    public void onCreate(){
        super.onCreate();
        Log.d("MyApplication : ", "Application started!");

        SingletonClass.initialize(this);

    }



}
