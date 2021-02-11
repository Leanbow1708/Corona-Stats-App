package com.example.coronanews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class AboutMeActivity extends AppCompatActivity {

    ImageView insta;
    ImageView gmail;
    ImageView whatsapp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);




        whatsapp = findViewById(R.id.whatsapp);
        insta = findViewById(R.id.insta);
        gmail = findViewById(R.id.gmail);


        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try{



                    Intent intent=new Intent(Intent.ACTION_SEND);
                    String[] recipients={"mayank.singh101522@marwadiuniversity.ac.in"};
                    intent.putExtra(Intent.EXTRA_EMAIL, recipients);

                    intent.setType("text/html");
                    intent.setPackage("com.google.android.gm");
                    startActivity(Intent.createChooser(intent, "Send mail"));






//                    Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "mayank.singh101522@marwadiuniversity.ac.in"));
//                    intent.putExtra(Intent.EXTRA_SUBJECT, "");
//                    intent.putExtra(Intent.EXTRA_TEXT, "");
//                    startActivity(intent);
                }catch(ActivityNotFoundException e){
                    //TODO smth
                }


            }
        });



        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String url = "https://api.whatsapp.com/send?phone="+"+918469514210";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });


        insta.setOnClickListener(new View.OnClickListener() {
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



        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.parseColor("#ff9a4025"));
        }
    }
}
