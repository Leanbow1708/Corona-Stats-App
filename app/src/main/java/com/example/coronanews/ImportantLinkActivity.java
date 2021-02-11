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

public class ImportantLinkActivity extends AppCompatActivity {


    ArrayList<ImportantLinkModel> list;
    RecyclerView linkRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important_link);



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
        linkRecycler = findViewById(R.id.linkRecycler);

            ImportantLinkModel o1 = new ImportantLinkModel();
            o1.setName("WHO");
            o1.setContent("Get updated with latest news about coronavirus");
            o1.setImage(R.drawable.who);

        ImportantLinkModel o2 = new ImportantLinkModel();
        o2.setName("UNICEF");
        o2.setContent("Important FAQs and facts about Coronavirus");
        o2.setImage(R.drawable.unicef);

        ImportantLinkModel o3 = new ImportantLinkModel();
        o3.setName("OnHealth");
        o3.setContent("Get important food tips for boosting immunity");
        o3.setImage(R.drawable.wheat);

        ImportantLinkModel o4 = new ImportantLinkModel();
        o4.setName("Art Of Living");
        o4.setContent("Learn to do Yoga to boost immunity");
        o4.setImage(R.drawable.yoga);

        ImportantLinkModel o5 = new ImportantLinkModel();
        o5.setName("COVID-19");
        o5.setContent("Latest statistics about Covid-19 spread around the globe");
        o5.setImage(R.drawable.covid19);

        list.add(o1); list.add(o2); list.add(o3); list.add(o4); list.add(o5);

        ImportantLinkAdapter adapter = new ImportantLinkAdapter(list, ImportantLinkActivity.this);
        linkRecycler.setAdapter(adapter);
        linkRecycler.setLayoutManager(new LinearLayoutManager(ImportantLinkActivity.this));





    }
}
