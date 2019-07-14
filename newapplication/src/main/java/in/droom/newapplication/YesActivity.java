package in.droom.newapplication;

import android.app.NotificationManager;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import in.droom.analyticslibrary.DisplayDatabase;
import in.droom.analyticslibrary.SingletonClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class YesActivity extends AppCompatActivity {

    SingletonClass obj;

    Button buy,sell,addtocart,show_database,push_to_server,delete_synced_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yes);

        getSupportActionBar().setTitle("Yes Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toast.makeText(this, "Yes Activity is active!", Toast.LENGTH_LONG).show();

        TimeZone tz=TimeZone.getTimeZone("Asia/Calcutta");
        final Calendar calendar=Calendar.getInstance(tz);

        final SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        final String time=format.format(calendar.getTime());

        obj=SingletonClass.getInstance();
        obj.InsertData("Yes","ActionButton" ,time ,"" );

        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(MainActivity.NOTIFICATION_ID );


        buy=findViewById(R.id.btn_buy);
        final String buy_name=buy.getText().toString();
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String buy_click_timestamp=format.format(calendar.getTime());
                obj.InsertData(buy_name, "Button", buy_click_timestamp, "");
            }
        });

        sell=findViewById(R.id.btn_sell);
        final String sell_name=sell.getText().toString();
        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String sell_click_timestamp=format.format(calendar.getTime());
                obj.InsertData(sell_name, "Button", sell_click_timestamp, "");
            }
        });

        addtocart=findViewById(R.id.btn_addcart);
        final String addcartname=addtocart.getText().toString();

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String cart_click_timestamp=format.format(calendar.getTime());
                obj.InsertData(addcartname,"Button" ,cart_click_timestamp ,"" );
            }
        });

        show_database=findViewById(R.id.btn_yes_show);
        show_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(YesActivity.this, DisplayDatabase.class);
                startActivity(intent);
            }
        });

        push_to_server=findViewById(R.id.btn_push);
        push_to_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obj.PushData();
            }
        });

        delete_synced_data=findViewById(R.id.del_synced_yes);
        delete_synced_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obj.DeleteSyncedData();
            }
        });

    }
}
