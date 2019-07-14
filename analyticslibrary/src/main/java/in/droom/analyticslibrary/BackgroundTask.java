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
import java.util.ArrayList;

import static in.droom.analyticslibrary.SingletonClass.helper;


public class BackgroundTask extends AsyncTask<String,Void,Void> {

    String eventname,eventtype,timestamp,addinfo;
    int id,syncflag;
    @Override
    protected Void doInBackground(String... strings) {
        String push_url="http://192.168.43.150/webapp/droom.php";
        Log.d("BackgroundTask : ","Async task called");

        JSONObject parent=new JSONObject();

        JSONArray jsonArray=new JSONArray();

        SQLiteDatabase db = helper.getWritableDatabase();
        int value=0;
        Cursor cursor=db.query(CreateDatabase.getTableName(),null,CreateDatabase.getFLAG()+"= '"+ value +"'" ,null,null,null,null);
        while(cursor.moveToNext()){
            JSONObject jsonObject=new JSONObject();

            id=cursor.getInt(0);
            eventname=cursor.getString(1);
            eventtype=cursor.getString(2);
            timestamp= cursor.getString(3);
            addinfo=cursor.getString(4);
            syncflag=cursor.getInt(5);

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


//            str.append("\nID : "+id+ "\nEventName : " + eventname+"\nEventType : "+ eventtype+ "\nTimeStamp : "+timestamp+"\nAdditionalInfo : "+addinfo+"\n");
        }

        try {

            String data=parent.toString();

            Log.d("BackgroundTask : ", "JSON data : "+data);

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

            Log.d("BAckground Task : ", ">>>> : "+response);

            JSONArray jArray = new JSONArray(response);

            ArrayList<Integer> User_List = new ArrayList<Integer>();

            for (int i = 0; i < jArray.length(); i++)
            {
                int json_data = jArray.getInt(i);
                User_List.add(json_data);
            }

            System.out.println(">>>>" + User_List.size());

            for(int i=0;i<User_List.size();i++){
                System.out.println(">>>> : " + User_List.get(i));
                SingletonClass.UpdateData(User_List.get(i));
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d("Back", "Error 1");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Back", "Error 2");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
