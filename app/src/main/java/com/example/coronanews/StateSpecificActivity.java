package com.example.coronanews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class StateSpecificActivity extends AppCompatActivity {

    TextView txtA;
    TextView txtC;
    TextView txtR;
    TextView txtD;
    TextView txtName;
    PieChart pieChart;
    Typeface custom_font;
    String c,r,d,a,name;
    int ia;
    int ic;
    int ir;
    int iD;
    String TAG = "StateSpecificActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_specific);
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
        Log.d(TAG, "onCreate: "+c+" "+r+" "+a+" "+d);

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

        ic = Integer.parseInt(c);
        ir = Integer.parseInt(r);
        ia = Integer.parseInt(a);
        iD = Integer.parseInt(d);
        NoOfEmp.add(new Entry(ic, 0));
        NoOfEmp.add(new Entry(ir, 1));
        NoOfEmp.add(new Entry(ia, 2));
        NoOfEmp.add(new Entry(iD, 3));

        PieDataSet dataSet = new PieDataSet(NoOfEmp, "");

        ArrayList year = new ArrayList();

        year.add("confirmed");
        year.add("recovered");
        year.add("active");
        year.add("death");

        PieData data = new PieData(year, dataSet);
        data.setValueTextSize(15f);
        data.setValueTypeface(custom_font);
        pieChart.setData(data);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.animateXY(3000, 3000);
    }
}
