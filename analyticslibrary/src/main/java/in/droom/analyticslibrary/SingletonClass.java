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
            ourInstance=new SingletonClass();
            helper= new CreateDatabase(context);
        }

    }


    public static void InsertData(String eventname, String eventtype, String timestamp, String addinfo){
        DatabaseMethods insertobj=new DatabaseMethods();
        insertobj.InsertData(eventname,eventtype,timestamp,addinfo);
    }

    public static String ShowData(Context context){
        DatabaseMethods showobj=new DatabaseMethods();
        String data = showobj.ShowData();
        //Message.message(context, data);
        return data;
    }

    public static void PushData(){
        DatabaseMethods pushobj=new DatabaseMethods();
        pushobj.PushData();
    }

    public static void UpdateData(int id){
        DatabaseMethods updobj=new DatabaseMethods();
        updobj.UpdateData(id);
    }

    public static void DeleteData(){
        DatabaseMethods delobj=new DatabaseMethods();
        delobj.DeleteData();
    }

}

