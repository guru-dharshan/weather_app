package com.example.jsonexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    EditText txt;
    TextView message;
    class down extends AsyncTask<String,Void,String>{
        URL url;
        HttpURLConnection urlConnection=null;
        @Override
        protected String doInBackground(String... urls) {
            String result=" ";
            URL url;
            HttpURLConnection urlConnection=null;
            try{
                url =new URL(urls[0]);
                urlConnection=(HttpURLConnection)url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader =new InputStreamReader(in);
                int data = reader.read();
                while(data!=-1){
                    char current=(char) data;
                    result+=current;
                    data=reader.read();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {

                Log.i("json", s);
                JSONObject json;
                json = new JSONObject(s);
                String weather = json.getString("weather");
                JSONArray arr = new JSONArray(weather);

                String main = "--";
                String description = "--";
                int temp=0;
                int hum=0;
                for (int i = 0; i < arr.length( ); i++) {
                    JSONObject J = arr.getJSONObject(i);
                    main = J.getString("main");
                    description = J.getString("description");

                }
                String temperature = json.getString("main");
                Log.i("main",temperature);
               // JSONArray arr1 =new JSONArray(temperature);
                 //for (int j = 0; j < arr1.length( ); j++)
                    JSONObject K = new JSONObject(temperature);
                    temp = K.getInt("temp");
                    hum = K.getInt("humidity");



                message.setText( main + ":" + description+"\n\n"+"Temperature"+temp+"c"+"\n\n"+"Humidity :"+hum+"%");

            } catch (JSONException e) {
                e.printStackTrace( );
            }

        }
    }
    public void check(View view){
        down a = new down();
        a.execute("https://openweathermap.org/data/2.5/weather?q=" + txt.getText( ).toString( ) + "&appid=439d4b804bc8187953eb36d2a8c26a02");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = (EditText)findViewById(R.id.editText);
        message= (TextView)findViewById(R.id.textView3);
    }
}
