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

    //This function will insert data into the local database

    /**
     *
     * @param eventname - Name of the event, e.g. Name of the action button
     * @param eventtype - Type of event, e.g. Button
     * @param timestamp - Time at which the notification action occured
     * @param addinfo
     * @return
     */
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


    //Not required, it was only for demo purpose
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

    /**
     * @description This function will be called to push data to server
     *     This function will call class named "Background Task"
     *     "Background Task" extends "Async Task"
     */
    public void PushData(){

        BackgroundTask backgroundTask=new BackgroundTask();
        backgroundTask.execute();

    }

    /**
     * @description - This function will update sync flag from 0 to 1.
     *                This will be called for those data which have been synced to the server.
     * @param id - ID of the table row
     * @return int -
     */
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

    //Not required, To delete all data present in the local database
    public int DeleteData(){
        SQLiteDatabase db=helper.getWritableDatabase();
        int num=db.delete(CreateDatabase.getTableName(),null,null);
        return num;
    }

    /**
     * To delete those data from local database that have flag (or syncflag) = 1
     * These are those data which have been synced to the server
     *
     * @return number of rows that were deleted
     */
    public int DeleteSyncedData(){
        SQLiteDatabase db=helper.getWritableDatabase();
        int num=db.delete(CreateDatabase.getTableName(),CreateDatabase.getFLAG()+"="+1,null);
        return num;
    }

}
