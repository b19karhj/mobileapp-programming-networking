package com.example.networking;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    public ArrayList<Mountain> mountainArrayList=new ArrayList(); //Skapar en ny lista med namnet mountainArrayList. Värden av typen mountain som är skapad.

    public ArrayAdapter<Mountain>adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter=new ArrayAdapter<Mountain>(this, R.layout.list_item_textview,R.id.text_T,mountainArrayList); //Adapter pekar på layouten och sedan på id:et och tar in värdet av mountainArrayList. Adapter kopplar samman alla.
        ListView my_listview=(ListView) findViewById(R.id.list_V);  //Hämtar list view
        my_listview.setAdapter(adapter);
        my_listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick (AdapterView<?> parent, View V, int poistion, long id) {
                Toast.makeText(getApplicationContext(), mountainArrayList.get(poistion).info(),Toast.LENGTH_SHORT).show(); //Skriver ut värdet i mountainArraylist.

            }
        });

        Button finalButton = findViewById(R.id.button1); //laggt in knappen i java filen.
        finalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonTask().execute("https://wwwlab.iit.his.se/brom/kurser/mobilprog/dbservice/admin/getdataasjson.php?type=brom");
            }
        });


    }

    @SuppressLint("StaticFieldLeak")
    private class JsonTask extends AsyncTask<String, String, String> {

        private HttpURLConnection connection = null;
        private BufferedReader reader = null;

        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null && !isCancelled()) {
                    builder.append(line).append("\n");
                }
                return builder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String json) {
            try {
                mountainArrayList.clear();
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String name = jsonObject.getString("name");
                    String location = jsonObject.getString("location");
                    int height = jsonObject.getInt("size");

                    Mountain mountain = new Mountain(name, location, height);
                    mountainArrayList.add(mountain);
                }
                adapter.notifyDataSetChanged();

                }

                catch (JSONException e) {
                    Log.d("JSON", "Could not parse: " + json + "\n due to expectation:" + e);
                }
            }
        }
    }

