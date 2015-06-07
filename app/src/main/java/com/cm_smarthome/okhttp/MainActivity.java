package com.cm_smarthome.okhttp;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    OkHttpClient client = new OkHttpClient();
    private String strStatusID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myAsyncTask task = new myAsyncTask();
        task.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class myAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            RequestBody formBody = new FormEncodingBuilder()
                    .add("sID", "55021744")
                    .build();
            Request request = new Request.Builder()
                    .url("http://www.cm-smarthome.com/reg/testCount.php")
                    .post(formBody)
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
                String result = response.body().string();

                JSONObject c;
                try {
                    c = new JSONObject(result);
                    strStatusID = c.getString("CountQR");
                    System.out.println(strStatusID);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!response.isSuccessful()) try {
                throw new IOException("Unexpected code " + response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
