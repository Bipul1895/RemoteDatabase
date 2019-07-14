package in.droom.analyticslibrary;

import android.content.Context;
import android.util.Log;

public class SingletonClass  {

    private static  SingletonClass ourInstance;
    private SingletonClass() {

    }

    static CreateDatabase helper;

    public static SingletonClass getInstance(){
        return ourInstance;
    }

    public static synchronized void initialize(Context context){
        if(ourInstance==null){
            //Create Instance
            ourInstance=new SingletonClass();
            helper= new CreateDatabase(context);
        }
    }

    public static void InsertData(String eventname, String eventtype, String timestamp, String addinfo){
        //Insert Data into SQLite database
        DatabaseMethods insertobj=new DatabaseMethods();
        insertobj.InsertData(eventname,eventtype,timestamp,addinfo);
    }

    public static String ShowData(Context context){
        // Show data present in the database
        DatabaseMethods showobj=new DatabaseMethods();
        String data = showobj.ShowData();
        return data;
    }

    public static void PushData(){
        //Push data to the server
        DatabaseMethods pushobj=new DatabaseMethods();
        pushobj.PushData();
    }

    public static void UpdateData(int id){
        //Update Sync Flag of those fields that have been pushed to the server
        DatabaseMethods updobj=new DatabaseMethods();
        updobj.UpdateData(id);
    }

    public static void DeleteData(){
        //Delete those data from SQLite which have been pushed to the server
        DatabaseMethods delobj=new DatabaseMethods();
        delobj.DeleteData();
    }

    public static void DeleteSyncedData(){
        DatabaseMethods delobj=new DatabaseMethods();
        delobj.DeleteSyncedData();
    }

}

