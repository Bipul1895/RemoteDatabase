package in.droom.analyticslibrary;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static in.droom.analyticslibrary.SingletonClass.helper;


public class BackgroundTask extends AsyncTask<String,Void,Void> {

    @Override
    protected Void doInBackground(String... strings) {
        String push_url="http://172.20.4.222/webapp/droom.php";
        Log.d("BackgroundTask : ","Async task called");

        SingletonClass obj;

        JSONObject parent=new JSONObject();

        JSONArray jsonArray=new JSONArray();

        SQLiteDatabase db = helper.getWritableDatabase();
        int value=0;
        Cursor cursor=db.query(CreateDatabase.getTableName(),null,CreateDatabase.getFLAG()+"= '"+ value +"'" ,null,null,null,null);
        while(cursor.moveToNext()){
            JSONObject jsonObject=new JSONObject();
            int id=cursor.getInt(0);
            String eventname=cursor.getString(1);
            String eventtype=cursor.getString(2);
            String timestamp= cursor.getString(3);
            String addinfo=cursor.getString(4);
            int syncflag=cursor.getInt(5);

            try {
                jsonObject.put("Id", id);
                jsonObject.put("Eventname", eventname);
                jsonObject.put("Eventtype", eventtype);
                jsonObject.put("Timestamp", timestamp);
                jsonObject.put("AdditionalInfo", addinfo);
                jsonObject.put("Syncflag", syncflag);

                jsonArray.put(jsonObject);

                parent.put("data", jsonArray);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            obj=SingletonClass.getInstance();

            obj.UpdateData(id, eventname, eventtype, timestamp, addinfo);

//            str.append("\nID : "+id+ "\nEventName : " + eventname+"\nEventType : "+ eventtype+ "\nTimeStamp : "+timestamp+"\nAdditionalInfo : "+addinfo+"\n");
        }

        try {

            String data=parent.toString();

           // Log.d("BackgroundTask : ", ">>>> : "+data);

            URL url=new URL(push_url);

            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            OutputStream outputStream=httpURLConnection.getOutputStream();

            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));


            bufferedWriter.write(data);

            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream=httpURLConnection.getInputStream();

            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String response="";
            String line="";
            line=bufferedReader.readLine();
            while (line!=null){
                response+=line;
                line=bufferedReader.readLine();
            }

            bufferedReader.close();
            inputStream.close();

            System.out.println(">>>>"+response);
            System.out.println("::::");


        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d("Back", "Error 1");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Back", "Error 2");
        }



        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
