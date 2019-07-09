package in.droom.analyticslibrary;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Calendar;
import java.util.Date;

public class CreateDatabase extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "MyDatabase";
    private static final String TABLE_NAME = "MyTable";
    private static final int DATABASE_VERSION = 3;
    private static final String UID = "_id";
    private static final String EVENTNAME = "Event_Name";
    private static final String EVENTTYPE = "Event_Type";
    private static final String TIMESTAMP="Time_Stamp";
    private static final String ADDINFO="Add_info";
    private static final String SYNCFLAG="Sync_Flag";
    private static final String CREATE_TABLE = " CREATE TABLE " + TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + EVENTNAME + " VARCHAR(255), " + EVENTTYPE + " VARCHAR(255), "+ TIMESTAMP +" VARCHAR(255), "+ ADDINFO +" VARCHAR(1000),"+ SYNCFLAG +" INTEGER); ";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + " ";

    public CreateDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static String getUID() {
        return UID;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static String getEventname() {
        return EVENTNAME;
    }

    public static String getEventtype() {
        return EVENTTYPE;
    }

    public static String getTimestamp(){
        return TIMESTAMP;
    }

    public static String getAddinfo(){
        return ADDINFO;
    }

    public static String getFLAG() {
        return SYNCFLAG;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE);
            onCreate(db);
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
    }

}
