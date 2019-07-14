package in.droom.newapplication;

import android.app.NotificationManager;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import in.droom.analyticslibrary.SingletonClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class LandingActivity extends AppCompatActivity {

    //NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

    SingletonClass obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        Toast.makeText(this, "Landing Activity is active!", Toast.LENGTH_LONG).show();

        TimeZone tz=TimeZone.getTimeZone("Asia/Calcutta");
        Calendar calendar=Calendar.getInstance(tz);
//                final String date= DateFormat.getDateInstance().format(calendar.getTime());

        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        final String time=format.format(calendar.getTime());

        obj=SingletonClass.getInstance();

        obj.InsertData("Notification On click","ActionButton" ,time ,"Notification Actions" );

        //notificationManager.cancel(MainActivity.NOTIFICATION_ID);

    }
}
