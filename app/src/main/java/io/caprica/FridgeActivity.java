package io.caprica;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.graphics.drawable.ArgbEvaluator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

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

    @SuppressLint("RestrictedApi")
    protected ArgbEvaluator argb = new ArgbEvaluator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fridge);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        updateValues();
    }


    protected void updateValues() {
        TextView textViewQA1 = findViewById(R.id.quality1);
        textViewQA1.setText("" + quality1[day] + "%");
        textViewQA1.setTextColor(Color.parseColor(colourCode(quality1[day])));

        TextView textViewQN1 = findViewById(R.id.quantity1);
        textViewQN1.setText("" + quantity1[day]);

        TextView textViewQA2 = findViewById(R.id.quality2);
        textViewQA2.setText("" + quality2[day] + "%");
        textViewQA2.setTextColor(Color.parseColor(colourCode(quality2[day])));

        TextView textViewQN2 = findViewById(R.id.quantity2);
        textViewQN2.setText("" + quantity2[day]);

        TextView textViewQA3 = findViewById(R.id.quality3);
        textViewQA3.setText("" + quality3[day] + "%");
        textViewQA3.setTextColor(Color.parseColor(colourCode(quality3[day])));

        TextView textViewQN3 = findViewById(R.id.quantity3);
        textViewQN3.setText("" + quantity3[day]);

        TextView textViewDay = findViewById(R.id.simpleTextViewDay);
        textViewDay.setText("DAY " + (day + 1));
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



}
