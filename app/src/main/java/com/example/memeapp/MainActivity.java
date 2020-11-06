package com.example.memeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private customadapter y;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            ListView x = (ListView) findViewById(R.id.abc);
            y = new customadapter(this, 0, new ArrayList<customize>());
            x.setAdapter(y);
        TextView z=(TextView)findViewById(R.id.ab);
        z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText r=(EditText)findViewById(R.id.ok);
                EditText y=(EditText)findViewById(R.id.ok1);
                if(TextUtils.isDigitsOnly(r.getText().toString())&&TextUtils.isDigitsOnly(y.getText().toString())) {
                    if (r.getText().toString().equalsIgnoreCase("") || y.getText().toString().equalsIgnoreCase("") || Integer.parseInt(y.getText().toString()) < Integer.parseInt(r.getText().toString()) || Integer.parseInt(r.getText().toString()) < 1 || Integer.parseInt(y.getText().toString()) > 10) {
                        Toast.makeText(getApplicationContext(), "Invalid range", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"proceeding ur request of"+r.getText().toString()+"and"+y.getText().toString(),Toast.LENGTH_SHORT).show();
                        final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=" + r.getText().toString() + "&limit=" + y.getText().toString();
                        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
                        task.execute(USGS_REQUEST_URL);
                        r.setText("");
                        y.setText("");
                    }
                }
                else
                    Toast.makeText(getApplicationContext(),"PLEASE ENTER DIGITS ONLY",Toast.LENGTH_SHORT).show();
            }
        });

            x.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // Find the current earthquake that was clicked on
                    customize currentEarthquake = (customize) y.getItem(position);

                    // Convert the String URL into a URI object (to pass into the Intent constructor)
                    Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                    // Create a new intent to view the earthquake URI
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                    // Send the intent to launch a new activity
                    startActivity(websiteIntent);
                }
            });
        }
        class EarthquakeAsyncTask extends AsyncTask<String, Void, List<customize>> {

            /**
             * This method runs on a background thread and performs the network request.
             * We should not update the UI from a background thread, so we return a list of
             * {@link customize}s as the result.
             */
            @Override
            protected List<customize> doInBackground(String... urls) {
                // Don't perform the request if there are no URLs, or the first URL is null.
                if (urls.length < 1 || urls[0] == null) {
                    return null;
                }

                List<customize> result = QueryUtils.fetchEarthquakeData(urls[0]);
                return result;
            }

            /**
             * This method runs on the main UI thread after the background work has been
             * completed. This method receives as input, the return value from the doInBackground()
             * method. First we clear out the adapter, to get rid of earthquake data from a previous
             * query to USGS. Then we update the adapter with the new list of earthquakes,
             * which will trigger the ListView to re-populate its list items.
             */
            @Override
            protected void onPostExecute(List<customize> data) {
                // Clear the adapter of previous earthquake data
                y.clear();

                // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
                // data set. This will trigger the ListView to update.
                if (data != null && !data.isEmpty()) {
                    y.addAll(data);
                }
            }
        }
    }