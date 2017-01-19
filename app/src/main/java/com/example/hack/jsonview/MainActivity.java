package com.example.hack.jsonview;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    ArrayList<ListRow> ListRowV;

    ListRowAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListRowV = new ArrayList<ListRow>();
        new JSONAsyncTask().execute("http://hitechwebdesign.net/yeasin/jsondata/all_jsondata.php");

        ListView listview = (ListView)findViewById(R.id.list);
        adapter = new ListRowAdapter(getApplicationContext(), R.layout.list_row,ListRowV);

        listview.setAdapter(adapter);




        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,long id) {
                // TODO Auto-generated method stub;
/*=---===================Dialog==========================*/
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.singleinfo);
                dialog.setTitle("Time:"+ListRowV.get(position).getTime());

               ImageView p_image = (ImageView) dialog.findViewById(R.id.imageDialog);
                String simage = ListRowV.get(position).getSin_image();

                try {
                    byte[] rawImage = Base64.decode(simage, Base64.DEFAULT);
                    Bitmap bmp = BitmapFactory.decodeByteArray(rawImage, 0, rawImage.length);

                    p_image.setImageBitmap(bmp);

                } catch (Exception e) {
                    e.getMessage();

                }

                // set the custom dialog components - text, image and button
                TextView title = (TextView) dialog.findViewById(R.id.textDialog);
                title.setText(ListRowV.get(position).getTitle());
                TextView des = (TextView) dialog.findViewById(R.id.tvdes);
                des.setText(ListRowV.get(position).getDes());

                dialog.show();

                Button declineButton = (Button) dialog.findViewById(R.id.declineButton);
                // if button is clicked, close the custom dialog
                declineButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


/*=---==================Dialog===========================*/
               // Toast.makeText(getApplicationContext(), ListRowV.get(position).getTitle()+ ListRowV.get(position).getTime(), Toast.LENGTH_LONG).show();
            }
        });



    }


    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Loading, please wait...");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                //------------------>>
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);


                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("user");

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        ListRow list = new ListRow();

                        list.setTitle(object.getString("title"));
                        list.setTime(object.getString("time"));
                        list.setDes(object.getString("des"));
                        list.setImage(object.getString("image"));
                        list.setSin_image(object.getString("sin_image"));

                        ListRowV.add(list);
                    }
                    return true;
                }

                //------------------>>

           /* }  catch (ParseException e11) {
               // e1.printStackTrace();*/
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            dialog.cancel();
            adapter.notifyDataSetChanged();
            if(result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }
}
