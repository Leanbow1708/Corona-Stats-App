package com.example.coronanews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class IndividualWorldActivity extends AppCompatActivity {



    TextView txtA;
    TextView txtC;
    TextView txtR;
    TextView txtD;
    TextView txtName;
    PieChart pieChart;
    Typeface custom_font;
    String c,r,d,a,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_world);


        pieChart = findViewById(R.id.pieChart);
        ArrayList NoOfEmp = new ArrayList();
        txtC = findViewById(R.id.txtConfirmed);
        txtA = findViewById(R.id.txtActive);
        txtR = findViewById(R.id.txtRecovered);
        txtD = findViewById(R.id.txtDeath);

        c = getIntent().getStringExtra("c");
        r = getIntent().getStringExtra("r");
        a = getIntent().getStringExtra("a");
        d = getIntent().getStringExtra("d");
        name = getIntent().getStringExtra("name");


        txtA.setText(a+"");txtC.setText(c+"");txtD.setText(d+"");txtR.setText(r+"");

        txtName = findViewById(R.id.txtState);
        txtName.setText(name.replace("\"",""));

        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.parseColor("#ff9a4025"));
        }


        custom_font = ResourcesCompat.getFont(this, R.font.montserrat_light);

        float mortalityRate = (Float.parseFloat(d)/Float.parseFloat(c)*100);

        pieChart.setCenterTextTypeface(custom_font);
        pieChart.setCenterTextSize(20f);
        pieChart.setCenterText(String.format("%.2f", mortalityRate)+"%\nMortality");
        pieChart.showContextMenu();

        NoOfEmp.add(new Entry(Float.parseFloat(r), 1));
        NoOfEmp.add(new Entry(Float.parseFloat(a), 2));
        NoOfEmp.add(new Entry(Float.parseFloat(d), 3));

        PieDataSet dataSet = new PieDataSet(NoOfEmp, "");

        ArrayList year = new ArrayList();


        year.add("recovered");
        year.add("active");
        year.add("death");

        PieData data = new PieData(year, dataSet);
        data.setDrawValues(false);
        data.setValueTextSize(15f);
        data.setValueTypeface(custom_font);
        pieChart.setData(data);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.animateXY(3000, 3000);
    }
}
