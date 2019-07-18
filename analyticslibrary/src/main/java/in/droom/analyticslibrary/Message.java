package in.droom.analyticslibrary;

import android.content.Context;
import android.widget.Toast;

//Not required
public class Message {
    public static void message(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
