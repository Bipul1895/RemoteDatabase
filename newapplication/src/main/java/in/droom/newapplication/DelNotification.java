package in.droom.newapplication;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import in.droom.analyticslibrary.SingletonClass;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class DelNotification extends BroadcastReceiver {

    SingletonClass obj;

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Notification Ignored", Toast.LENGTH_LONG).show();

        TimeZone tz=TimeZone.getTimeZone("Asia/Calcutta");
        Calendar calendar=Calendar.getInstance(tz);

        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        final String time=format.format(calendar.getTime());

        obj=SingletonClass.getInstance();

        obj.InsertData("Notification Ignored", "Swipe", time, "");

//        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.cancel(MainActivity.NOTIFICATION_ID );

    }
}
