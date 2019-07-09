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

public class NoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no);

        getSupportActionBar().setTitle("No Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toast.makeText(this, "No Activity is active!", Toast.LENGTH_LONG).show();

        TimeZone tz=TimeZone.getTimeZone("Asia/Calcutta");
        Calendar calendar=Calendar.getInstance(tz);
//                final String date= DateFormat.getDateInstance().format(calendar.getTime());

        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        final String time=format.format(calendar.getTime());

        SingletonClass.InsertData("No","ActionButton" ,time ,"Notification Actions" );



    }
}
