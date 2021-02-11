package com.example.coronanews;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class IndividualNewActivity extends AppCompatActivity {

    Button btnNews;
    TextView txtSource;

    ProgressDialog nDialog;

    String newsUrl;
    ImageView imgNews;
    String imageUrl;
    String content;
    String TAG = "Individual";
    String source;
    Uri uri;
    TextView txtContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_new);



        nDialog = new ProgressDialog(this);
        nDialog.setMessage("Loading..");
        nDialog.setTitle("Getting Data");
        nDialog.setIcon(R.drawable.swirl);
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(false);
        nDialog.show();


        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.parseColor("#FDC391"));
        }

        btnNews = findViewById(R.id.btnNews);

        txtContent = findViewById(R.id.txtContent);
        txtSource = findViewById(R.id.txtSource);
        imgNews = findViewById(R.id.imgNews);


        newsUrl = getIntent().getStringExtra("newsUrl");

        imageUrl = getIntent().getStringExtra("url");
        content = getIntent().getStringExtra("content");
        source = getIntent().getStringExtra("source");
        txtContent.setText(content);
        txtSource.setText(source);



        Picasso.get().load(imageUrl).into(imgNews,new Callback.EmptyCallback(){
            @Override
            public void onSuccess() {
                super.onSuccess();

                nDialog.dismiss();
            }
        });




        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uri = Uri.parse(newsUrl);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);startActivity(intent);

            }
        });



    }
}
