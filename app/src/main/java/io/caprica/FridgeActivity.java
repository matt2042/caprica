package io.caprica;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.graphics.drawable.ArgbEvaluator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mbukowski on 28.04.18.
 */

public class FridgeActivity extends AppCompatActivity {

    protected static final int MAX_DAY = 5;

    protected static final String[] COLOR_PALETTE = {
            "#228B22",
            "#368B1F",
            "#4A8B1C",
            "#5E8B19",
            "#728B16",
            "#868B13",
            "#9B8C0F",
            "#AF8C0C",
            "#C38C09",
            "#D78C06",
            "#EB8C03",
            "#FF8C00",
            "#FC8105",
            "#F9760B",
            "#F56B10",
            "#F26016",
            "#EF551B",
            "#EC4B21",
            "#E94026",
            "#E6352C",
            "#E22A31",
            "#DF1F37",
            "#DC143C"
    };

    protected int day = 0;

    protected int[] quality1 = {100, 90, 85, 80, 60, 80, 70, 40, 30, 35};
    protected int[] quantity1 = {2, 2, 2, 2, 2, 1, 1, 1, 1, 1};

    protected int[] quality2 = {80, 79, 78, 77, 76, 75, 74, 73, 72, 71};
    protected int[] quantity2 = {5, 4, 4, 3, 3, 2, 4, 4, 3, 2};

    protected int[] quality3 = {40, 35, 33, 0, 54, 27, 80,  77, 37, 75};
    protected int[] quantity3 = {3, 1, 2, 4, 2, 3, 5, 2, 1, 5};

    protected TextView textViewDay;

    protected TextView textViewQA1;
    protected TextView textViewQA2;
    protected TextView textViewQA3;

    protected TextView textViewQN1;
    protected TextView textViewQN2;
    protected TextView textViewQN3;

    @SuppressLint("RestrictedApi")
    protected ArgbEvaluator argb = new ArgbEvaluator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fridge);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        textViewDay = findViewById(R.id.simpleTextViewDay);

        textViewQA1 = findViewById(R.id.quality1);
        textViewQA2 = findViewById(R.id.quality2);
        textViewQA3 = findViewById(R.id.quality3);

        textViewQN1 = findViewById(R.id.quantity1);
        textViewQN2 = findViewById(R.id.quantity2);
        textViewQN3 = findViewById(R.id.quantity3);

        updateValues();
    }


    protected void updateValues() {
        textViewQA1.setText("" + quality1[day] + "%");
        textViewQA1.setTextColor(Color.parseColor(colourCode(quality1[day])));
        textViewQN1.setText("" + quantity1[day]);

        textViewQA2.setText("" + quality2[day] + "%");
        textViewQA2.setTextColor(Color.parseColor(colourCode(quality2[day])));
        textViewQN2.setText("" + quantity2[day]);

        textViewQA3.setText("" + quality3[day] + "%");
        textViewQA3.setTextColor(Color.parseColor(colourCode(quality3[day])));
        textViewQN3.setText("" + quantity3[day]);

        textViewDay.setText("DAY " + (day + 1));

        MyAsync myAsync = new MyAsync();
        myAsync.execute();
    }

    public void nextDay(View view) {
        day = (day + 1) % MAX_DAY;
        updateValues();
    }

    protected String colourCode(int value) {
        double ratio = (100 - value) / 100.00;
        ratio = Math.min(ratio, 0.9999);
        int id = (int) (ratio * COLOR_PALETTE.length);

        return COLOR_PALETTE[id];
    }


    protected String getData() {
        String result = "";

        try {
            URL url = new URL("http://caprica451.herokuapp.com/day/" + (day + 1));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            result = inputStreamToString(in);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private String inputStreamToString(InputStream is) {
        String rLine = "";
        StringBuilder answer = new StringBuilder();

        InputStreamReader isr = new InputStreamReader(is);

        BufferedReader rd = new BufferedReader(isr);

        try {
            while ((rLine = rd.readLine()) != null) {
                answer.append(rLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer.toString();
    }

    class MyAsync extends AsyncTask<Void, Void, String> {

        protected String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = "FOO";
            try {
                URL url = new URL("http://caprica451.herokuapp.com/day/" + (day + 1));
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                result = inputStreamToString(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

//            textViewDay.setText("DAY " + s);
//            progressDialog.dismiss();
//            try {
//                JSONObject jsonObject = new JSONObject(s);
//                JSONArray jsonArray = jsonObject.getJSONArray("contacts");
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    String id = jsonArray.getJSONObject(i).getString("id");
//                    String name = jsonArray.getJSONObject(i).getString("name");
//                    String email = jsonArray.getJSONObject(i).getString("email");
//
//                    String mobile = jsonArray.getJSONObject(i).getJSONObject("phone").getString("mobile");
//
//                    textView.append(id + "\n");
//                    textView.append(name + "\n");
//                    textView.append(email + "\n");
//                    textView.append(mobile + "\n\n");
//
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

        }
    }


}
