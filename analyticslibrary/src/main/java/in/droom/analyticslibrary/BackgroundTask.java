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
        String push_url="http://172.20.4.129/webapp/droom.php";
        Log.d("BackgroundTask : ","Async task called");

        JSONObject parent=new JSONObject();

        JSONArray jsonArray=new JSONArray();

        SQLiteDatabase db = helper.getWritableDatabase();
        int value=0;

        //Select only those data from database which have sync flag = 0
        //One by one retrieve data from database and put in into a JSONArray named jsonArray
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

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        //Put jsonArray into parent jsonObject named "parent"
        try {
            parent.put("data", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {

            String data=parent.toString();

            Log.d("BackgroundTask : ", "JSON data : "+data);


            //Establish connection with server program
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

            //Receive a JSON array from server program
            //This json array is an array of ID of those elements which have been synced to the server
            //The ID of these elements will be used to mark sync flag of these elements as 1

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

                //Update data method of class "Singleton Class" is called to mark sync flag as 1
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
