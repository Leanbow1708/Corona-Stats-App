package com.example.coronanews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

public class FrequentlyAskedActivity extends AppCompatActivity {


    ArrayList<HelpModel> list;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frequently_asked);





        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.parseColor("#ff9a4025"));
        }



        list = new ArrayList<>();
        recyclerView = findViewById(R.id.helpRecycler);

    HelpModel m1 = new HelpModel();
    m1.setQ("Q)    How can i get information of specific state?");
    m1.setA("Ans)  You can click on the name of state under the India pane.");

        HelpModel m2 = new HelpModel();
        m2.setQ("Q)    From where all these informations are fetched?");
        m2.setA("Ans)  I have used  several APIs to get real time data");

        HelpModel m3 = new HelpModel();
        m3.setQ("Q)    Are all these stats official?");
        m3.setA("Ans)  All these stats are scrapped from COVID-19 official website via an API,here i am using free version which takes an hour or two to get refresh");

        HelpModel m4 = new HelpModel();
        m4.setQ("Q)    How can i get the APIs of this app to try make one from scratch?");
        m4.setA("Ans)  To get APIs jsut mail or text me on the credentials given in About me section");

        HelpModel m5 = new HelpModel();
        m5.setQ("Q)    What is the future scope of this app?");
        m5.setA("Ans)  This app is just a prototype for what i am thinking to develop(A full fledged news app).Rightnow i get limited data and that to with noticeable delay from these free APIs so if u all support me then i can go on for the paid resources.");


    list.add(m1);
    list.add(m2);
    list.add(m3);
    list.add(m4);
    list.add(m5);

    HelpAdapter adapter = new HelpAdapter(list, this);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));




    }
}
