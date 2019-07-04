package in.droom.analyticslibrary;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

//    public String ShowMeData(String name){
//        StringBuffer str=new StringBuffer();
//        SQLiteDatabase db=helper.getWritableDatabase();
//        String[] columns={CreateDatabase.getUID(),CreateDatabase.getNAME(),CreateDatabase.getPASSWORD()};
//        String[] selectionargs={name};
//        Cursor cursor=db.query(CreateDatabase.getTableName(),columns,CreateDatabase+"=?" ,selectionargs,null,null,null);
//        while(cursor.moveToNext()){
//            int index1=cursor.getColumnIndex(helper.UID);
//            int index2=cursor.getColumnIndex(helper.NAME);
//            int index3=cursor.getColumnIndex(helper.PASSWORD);
//            int myid=cursor.getInt(index1);
//            String myname=cursor.getString(index2);
//            String mypass=cursor.getString(index3);
//            Log.d("TAG", "ShowMeData: "+myname);
//            //int tell=name.compareTo(myname);
//            str.append(myid+ " " + name+" "+ mypass+"\n");
//        }
//        //String mystr=str.toString();
//        //Log.d("Mydatabaseadapter : ",mystr);
//        return str.toString();
//    }
//
    public int UpdateData(int id){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        Cursor cursor=db.query(CreateDatabase.getTableName(),null,CreateDatabase.getUID()+"="+id,null,null,null,null);
//        values.put(CreateDatabase.getEventname(), eventname);
//        values.put(CreateDatabase.getEventtype(), eventtype);
//        values.put(CreateDatabase.getTimestamp(), timestamp);
//        values.put(CreateDatabase.getAddinfo(), addinfo);
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
//
    public int DeleteData(){
        SQLiteDatabase db=helper.getWritableDatabase();
        int num=db.delete(CreateDatabase.getTableName(),null,null);
        return num;
    }

}
