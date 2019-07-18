package in.droom.analyticslibrary;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

//The object of this class will be created in the "original app"
//All the methods have been defined here

/**
 * To know which function us responsible for what operation, go to "DatabaseMethods" class
 * This class is only calling the methods
 */
public class SingletonClass  {

    private static  SingletonClass ourInstance;
    private SingletonClass() {

    }

    static CreateDatabase helper;

    public static SingletonClass getInstance(){
        return ourInstance;
    }

    //Very important, it provides context to "helper" object
    //This will initialize our table
    public static synchronized void initialize(Context context){
        if(ourInstance==null){
            //Create Instance
            ourInstance=new SingletonClass();
            helper= new CreateDatabase(context);
        }
    }

    /**
     * Insert Data into SQLite database
     *
     * @param eventname - This function will insert data into the local database
     * @param eventtype - Type of event, e.g. Button
     * @param timestamp - Time at which the notification action occured
     * @param addinfo -
     */
    public static void InsertData(String eventname, String eventtype, String timestamp, String addinfo){
        //
        DatabaseMethods insertobj=new DatabaseMethods();
        insertobj.InsertData(eventname,eventtype,timestamp,addinfo);
    }


    //Not required, Used to show database contents to the user
    //For debugging purpose
    public static String ShowData(Context context){
        // Show data present in the database
        DatabaseMethods showobj=new DatabaseMethods();
        String data = showobj.ShowData();
        return data;
    }

    /**
     * Used to push data to server
     */
    public static void PushData(){

        TrafficAPI trafficAPI=new TrafficAPI();
        int traffic=trafficAPI.gettraffic();
        //Threshold traffic = 5;
        if(traffic <= 5) {
            DatabaseMethods pushobj = new DatabaseMethods();
            pushobj.PushData();
        }
        else{
            Log.d("Singleton Class: ", "traffic : "+traffic);
        }
    }


    /**
     *
     * @param id - ID of those elements whose sync flag has to be updated to 1
     */
    public static void UpdateData(int id){
        //Update Sync Flag of those fields that have been pushed to the server
        DatabaseMethods updobj=new DatabaseMethods();
        updobj.UpdateData(id);
    }



    public static void DeleteSyncedData(){
        //Delete those data from mobile device which have been synced to the server
        DatabaseMethods delobj=new DatabaseMethods();
        delobj.DeleteSyncedData();
    }


    //Not required
    public static void DeleteData(){

        DatabaseMethods delobj=new DatabaseMethods();
        delobj.DeleteData();
    }



}

