package com.example.coronanews;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {



    String sharedConstant = "MayankDeveloper1708";


    SharedPreferences sharedPreferences;

    ImageView imgRefresh;
    TextView followInstagram;
    ImageView navIcon;


    DrawerLayout drawerLayout;
    RecyclerView navRecycler;

    int iIndian;
    TextView txtForeigner;
    int iForeigner;
    TextView txtRecovered;
    int iRecovered;
    TextView txtDeath;
    int iDeath;
    int totalCase = 0;

    SharedPreferences.Editor myEdit;
    ArrayList<String> arrayList;

    TextView txtTotalCases;

    Retrofit retrofit;
    String TAG = "MainActivity";
    ProgressDialog nDialog;
    ArrayList<DataModel> dataList;
    RecyclerView recyclerView;

        ArrayList<String> navList;


    public static final String NOTIFICATION_CHANNEL_ID = "2000" ;
    private final static String default_notification_channel_id = "default" ;







    private Notification getNotification (String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder( this, default_notification_channel_id ) ;
        builder.setContentTitle( "India COVID-19" ) ;
        builder.setContentText(content) ;
        builder.setSmallIcon(android.R.drawable.ic_menu_help ) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        return builder.build() ;
    }

    private void scheduleNotification (Notification notification , int delay) {
        Intent notificationIntent = new Intent( this, MyNotificationPublisher. class ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION_ID , 1 ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION , notification) ;
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( this, 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        long futureInMillis = SystemClock. elapsedRealtime () + delay ;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , futureInMillis , pendingIntent) ;
    }








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtDeath = findViewById(R.id.txtDeath);




         sharedPreferences
                = getSharedPreferences(sharedConstant,
                MODE_PRIVATE);


         myEdit
                = sharedPreferences.edit();





        imgRefresh = findViewById(R.id.imgRefresh);


        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
        followInstagram = findViewById(R.id.followInstagram);

        followInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Uri uri = Uri.parse("http://instagram.com/_u/leanbow__");


                Intent i= new Intent(Intent.ACTION_VIEW,uri);

                i.setPackage("com.instagram.android");

                try {
                    startActivity(i);
                } catch (ActivityNotFoundException e) {

                    startActivity(new Intent(Intent.ACTION_VIEW,
                           uri));
                }


            }
        });



        drawerLayout = findViewById(R.id.drawerLayout);



        navIcon = findViewById(R.id.navIcon);

        navIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawerLayout.openDrawer(Gravity.LEFT);


            }
        });

        navRecycler = findViewById(R.id.navigationRecycler);
        navList = new ArrayList<>();

//        Toast.makeText(this, checkResolution()+"", Toast.LENGTH_LONG).show();

        txtForeigner = findViewById(R.id.txtForeigner);
        txtRecovered = findViewById(R.id.txtRecovered);
        recyclerView = findViewById(R.id.mainRecycler);

        dataList = new ArrayList<>();

        nDialog = new ProgressDialog(MainActivity.this);
        nDialog.setMessage("Loading..");
        nDialog.setTitle("Getting Data");
        nDialog.setIcon(R.drawable.swirl);
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(false);
        nDialog.show();
        txtTotalCases = findViewById(R.id.txtTotalCases);



//        mainCounter();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.rootnet.in/covid19-in/unofficial/covid19india.org/")

                .addConverterFactory(ScalarsConverterFactory.create())

                .build();
        arrayList = new ArrayList<>();



        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.parseColor("#ff9a4025"));
        }
        getInformation();
        setNav();


              try{




              }
              catch (Exception e)
              {
                  txtTotalCases.setText("000");


              }






        DataModel d1 = new DataModel();
        d1.setDrawable(R.drawable.india);
        d1.setText("India");
        DataModel d2 = new DataModel();
        d2.setDrawable(R.drawable.world);
        d2.setText("World");
        DataModel d3 = new DataModel();
        d3.setDrawable(R.drawable.helpline);
        d3.setText("Helplines");
        DataModel d4 = new DataModel();
        d4.setDrawable(R.drawable.news);
        d4.setText("News");


        dataList.add(d1);dataList.add(d2);dataList.add(d3);dataList.add(d4);

        MainAdapter adapter = new MainAdapter(dataList, MainActivity.this);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));














    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        System.exit(0);


    }

    private void setNav() {


         navList.add("India");
         navList.add("World");
         navList.add("News");
         navList.add("Helplines");
         navList.add("F.A.Q");
         navList.add("W.H.O.");
         navList.add("About me");
         navList.add("Logout");


         NavigationAdapter adapter = new NavigationAdapter(navList, this);

        navRecycler.addItemDecoration(new SimpleDividerItemDecoration(this));
         navRecycler.setLayoutManager(new LinearLayoutManager(this));
        navRecycler.setAdapter(adapter);



    }


    private void getInformation() {






                final JsonApiHolder jsonApiHolder = retrofit.create(JsonApiHolder.class);
                Call<String> call = jsonApiHolder.getLatest();
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        if(response.isSuccessful()){

//                            Toast.makeText(MainActivity.this, response.body().toString(), Toast.LENGTH_LONG).show();
                            Log.d(TAG, "onResponse: "+response.body().toString());

                            JsonParser parser = new JsonParser();
                            JsonElement json =  parser.parse(response.body());

                            JsonObject jsonObject1  = json.getAsJsonObject();
                            JsonObject jsonObject2 = (JsonObject) jsonObject1.get("data");
                            Log.d("Maya", "onResponse: "+jsonObject2.toString());
                            JsonObject jsonObject3 = (JsonObject) jsonObject2.getAsJsonObject("total");

                            iDeath = Integer.parseInt(jsonObject3.get("deaths").toString());
                            iForeigner = Integer.parseInt(jsonObject3.get("active").toString());
                            iIndian = Integer.parseInt(jsonObject3.get("confirmed").toString());
                            iRecovered = Integer.parseInt(jsonObject3.get("recovered").toString());

                            totalCase = Integer.parseInt(jsonObject3.get("confirmed").toString());
                            Log.d("MayaTotal", "onResponse: "+totalCase+"   "+iRecovered+"   "+iForeigner+"  "+iDeath);



                            String info;
                            String data = sharedPreferences.getString("current", "hello");

                            if(data=="hello")
                            {

                                info = "Welcome to the app";

                            }
                            else
                            {
                                int curr_date = totalCase-Integer.parseInt(data);
                                if(curr_date==0)
                                {
                                    info = "No new case found since,\nyou last opened the app";
                                }
                                else
                                {
                                    info = curr_date+" more people found positive for COVID-19since,\nyou last opened the app";

                                }
                            }




                            myEdit.putString("current",totalCase+"");

                            myEdit.commit();
                            scheduleNotification(getNotification( info ) , 0000 ) ;



                            txtTotalCases.setText(totalCase+"");
                            txtForeigner.setText(iForeigner+"");
                            txtRecovered.setText(iRecovered+"");
                            txtDeath.setText(iDeath+"");


                            nDialog.dismiss();


//                            JsonArray jsonArray1 = jsonObject2.getAsJsonArray("regional");

//                            Log.d("Size", "onResponse: "+jsonArray1.size());
//                            for(int i = 0;i<jsonArray1.size();i++){
//
//                                JsonObject jsonObject3 = jsonArray1.get(i).getAsJsonObject();
//                                Log.d("M1", "onResponse: "+jsonObject3.toString());
//
//                                String name = jsonObject3.get("loc").toString();
//                                Integer c = jsonObject3.get("confirmedCasesIndian").getAsInt();
////                               State s = new State();
////                               s.setCases(c);
////                               s.setName(name);
//                                arrayList.add(name+" "+c+"\n");
//
//
//
//                            }
//                            Log.d("Maya", "onCreate: "+arrayList);
//                            txtAll.setText(arrayList.toString());
//
//
//
//
//                            Log.d("Array", "onResponse: "+jsonArray1.toString());

                        }



                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });













    }
}
