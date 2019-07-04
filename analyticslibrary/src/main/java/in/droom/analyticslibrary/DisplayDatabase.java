package in.droom.analyticslibrary;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class DisplayDatabase extends AppCompatActivity {
    private TextView Txtinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_database);

        Txtinfo=findViewById(R.id.txt_display_info);

        String info=SingletonClass.ShowData(DisplayDatabase.this);

        Txtinfo.setText(info);

    }
}
