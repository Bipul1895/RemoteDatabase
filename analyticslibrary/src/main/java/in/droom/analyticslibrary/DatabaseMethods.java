package in.droom.analyticslibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import static android.database.sqlite.SQLiteDatabase.OPEN_READWRITE;
import static in.droom.analyticslibrary.SingletonClass.helper;

public class DatabaseMethods {

    public long InsertData(String eventname, String eventtype, String timestamp, String addinfo) {

        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.getEventname(), eventname);
        values.put(CreateDatabase.getEventtype(), eventtype);
        values.put (CreateDatabase.getTimestamp(), timestamp);
        values.put(CreateDatabase.getAddinfo(), addinfo);
        values.put(CreateDatabase.getFLAG(), 0);
        long id = db.insert(CreateDatabase.getTableName(), null, values);

        return id;
    }

    public String ShowData(){
        StringBuffer str=new StringBuffer();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor=db.query(CreateDatabase.getTableName(),null,null,null,null,null,null);
        while(cursor.moveToNext()){
            // to get column index use :
            int id=cursor.getInt(0);
            String eventname=cursor.getString(1);
            String eventtype=cursor.getString(2);
            String timestamp= cursor.getString(3);
            String addinfo=cursor.getString(4);
            int flag=cursor.getInt(5);
            str.append("\nID : "+id+ "\nEventName : " + eventname+"\nEventType : "+ eventtype+ "\nTimeStamp : "+timestamp+"\nAdditionalInfo : "+addinfo+"\nFlag :"+flag+"\n");
        }


        return str.toString();
    }

    public void PushData(){

        BackgroundTask backgroundTask=new BackgroundTask();
        backgroundTask.execute();

    }

//    public void callAsynchronousTask() {
//        final Handler handler = new Handler();
//        Timer timer = new Timer();
//        TimerTask doAsynchronousTask = new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(new Runnable() {
//                    public void run() {
//                        try {
//                            BackgroundTask performBackgroundTask = new BackgroundTask();
//                            // PerformBackgroundTask this class is the class that extends AsynchTask
//                            performBackgroundTask.execute();
//                        } catch (Exception e) {
//                            // TODO Auto-generated catch block
//                            System.out.println("Error " + e.getMessage());
//                        }
//                    }
//                });
//            }
//        };
//        timer.schedule(doAsynchronousTask, 0, 200000); //execute in every 5000 ms
//    }


    public int UpdateData(int id){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        Cursor cursor=db.query(CreateDatabase.getTableName(),null,CreateDatabase.getUID()+"="+id,null,null,null,null);

        while (cursor.moveToNext()) {
            String eventname = cursor.getString(1);
            String eventtype = cursor.getString(2);
            String timestamp = cursor.getString(3);
            String addinfo = cursor.getString(4);

            values.put(CreateDatabase.getEventname(), eventname);
            values.put(CreateDatabase.getEventtype(), eventtype);
            values.put(CreateDatabase.getTimestamp(), timestamp);
            values.put(CreateDatabase.getAddinfo(), addinfo);
            values.put(CreateDatabase.getFLAG(),1);

        }

        int count=db.update(CreateDatabase.getTableName(),values,CreateDatabase.getUID()+"="+id,null);
        return count;
    }

    public int DeleteData(){
        SQLiteDatabase db=helper.getWritableDatabase();
        int num=db.delete(CreateDatabase.getTableName(),null,null);
        return num;
    }

    public int DeleteSyncedData(){
        SQLiteDatabase db=helper.getWritableDatabase();
        int num=db.delete(CreateDatabase.getTableName(),CreateDatabase.getFLAG()+"="+1,null);
        return num;
    }

}
