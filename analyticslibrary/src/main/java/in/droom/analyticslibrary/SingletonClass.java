package in.droom.analyticslibrary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import static android.database.sqlite.SQLiteDatabase.OPEN_READWRITE;


public class SingletonClass  {

    private static  SingletonClass ourInstance;
    private SingletonClass() {

    }

    static CreateDatabase helper;

    public static SingletonClass getInstance(Context context){
        if(ourInstance==null){
            ourInstance=new SingletonClass();
        }
        if(helper==null){

            helper= new CreateDatabase(context);
        }

        return ourInstance;
    }

    public static SingletonClass getInstance(){
        if(ourInstance==null){
            ourInstance=new SingletonClass();
        }

        return ourInstance;
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

    public static void UpdateData(int id, String eventname, String eventtype, String timestamp, String addinfo){
        DatabaseMethods updobj=new DatabaseMethods();
        updobj.UpdateData(id, eventname, eventtype, timestamp, addinfo);
    }

    public static void DeleteData(){
        DatabaseMethods delobj=new DatabaseMethods();
        delobj.DeleteData();
    }

}

