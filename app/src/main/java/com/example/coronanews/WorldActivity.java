package com.example.coronanews;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class WorldActivity extends AppCompatActivity {




    Button btnWorld;
    ProgressDialog nDialog;
    PieChart pieChart;
    Typeface custom_font;
    TextView txtDeath;
    TextView txtRecovered;
    TextView txtTotalCases;
    Retrofit retrofit;
    int c;
    int r;
    int d;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world);




        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.parseColor("#ff9a4025"));
        }


        btnWorld = findViewById(R.id.button);

        btnWorld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(WorldActivity.this,AllWorldActivity.class);
                startActivity(intent);
            }
        });

        nDialog = new ProgressDialog(WorldActivity.this);
        nDialog.setMessage("Loading..");
        nDialog.setTitle("Getting Data");
        nDialog.setIcon(R.drawable.swirl);
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);
        nDialog.show();
        txtDeath = findViewById(R.id.txtDeath);

        txtRecovered = findViewById(R.id.txtRecovered);
        txtTotalCases = findViewById(R.id.txtTotalCases);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://corona.lmao.ninja/")

                .addConverterFactory(ScalarsConverterFactory.create())

                .build();

        getInformation();

    }

    private void fillTheView() {


        nDialog.dismiss();

        txtTotalCases.setText(c+"");
        txtRecovered.setText(r +"");
        txtDeath.setText(d +"");

        int a = (c-r-d);


        custom_font = ResourcesCompat.getFont(this, R.font.montserrat_light);
        pieChart = findViewById(R.id.pieChart);
        ArrayList NoOfEmp = new ArrayList();

        float mortalityRate = (Float.parseFloat(String.valueOf(d))/Float.parseFloat(String.valueOf(c))*100);


        pieChart.getLegend().setEnabled(false);
       pieChart.setDescription(null);

        pieChart.setCenterTextTypeface(custom_font);
        pieChart.setCenterTextSize(20f);
        pieChart.setCenterText(String.format("%.2f", mortalityRate)+"%\nMortality");
        pieChart.showContextMenu();
        NoOfEmp.add(new Entry(a, 0));
        NoOfEmp.add(new Entry(r, 1));
        NoOfEmp.add(new Entry(d, 2));


        PieDataSet dataSet = new PieDataSet(NoOfEmp, "");

        ArrayList year = new ArrayList();

        year.add("active");
        year.add("recovered");

        year.add("death");


        PieData data = new PieData(year, dataSet);
        data.setValueTextSize(15f);
        data.setValueTypeface(custom_font);
        pieChart.setData(data);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.animateXY(3000, 3000);




    }



    private void getInformation() {


        Log.d("Ma", "getInformation: ");
        final JsonApiHolder jsonApiHolder = retrofit.create(JsonApiHolder.class);
        Call<String> call = jsonApiHolder.getAllStats();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.isSuccessful()){

                    JsonParser parser = new JsonParser();
                    JsonElement json =  parser.parse(response.body());
                    JsonObject jsonObject = json.getAsJsonObject();
                    c = jsonObject.get("cases").getAsInt();
                    r = jsonObject.get("recovered").getAsInt();
                    d = jsonObject.get("deaths").getAsInt();

                    Log.d("Ma", "onResponse: "+d+" "+r+" "+c);
                    fillTheView();






                }



            }



            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.d("Ma", "onFailure: "+t.getMessage());

            }
        });













    }
}
