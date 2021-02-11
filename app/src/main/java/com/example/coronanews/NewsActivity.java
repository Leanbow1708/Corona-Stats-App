package com.example.coronanews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NewsActivity extends AppCompatActivity {

    ProgressDialog nDialog;

    String newsUrl;
    String imageUrl;
    String newsContent;
    String newsSource;
    Retrofit retrofit;
    String TAG = "NewsActivity";
    ArrayList<NewsModel> list;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);



        nDialog = new ProgressDialog(NewsActivity.this);
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

        recyclerView = findViewById(R.id.newsRecycler);
        list = new ArrayList<>();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://newsapi.org/v2/")

                .addConverterFactory(ScalarsConverterFactory.create())

                .build();

        getInformation();


    }




    private void getInformation() {






        final JsonApiHolder jsonApiHolder = retrofit.create(JsonApiHolder.class);
        Call<String> call = jsonApiHolder.getNews("2e6d6184cb2e4e02bb7bfcc9abeede06","in");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.isSuccessful()){

//                            Toast.makeText(MainActivity.this, response.body().toString(), Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onResponse: "+response.body().toString());

                    JsonParser parser = new JsonParser();
                    JsonElement json =  parser.parse(response.body());









                    JsonObject jsonObject1  = json.getAsJsonObject();

                    JsonArray jsonArray = jsonObject1.getAsJsonArray("articles");

                    Log.d(TAG, "onResponse: "+jsonArray.size());


                    for(int i = 0;i<jsonArray.size();i++){


                        JsonObject object = jsonArray.get(i).getAsJsonObject();

                        JsonObject object1 = (JsonObject) object.get("source");

                        String source = object1.get("name").toString();


                        String desc = object.get("title").toString();

                        String url = object.get("url").toString();

                        if (desc.length()<5)
                        {
                            desc = object.get("description").toString();

                        }


                        newsSource = source;
                        newsContent = object.get("content").toString();
                        imageUrl = object.get("urlToImage").toString();
                        NewsModel n = new NewsModel();
                        n.setDesc(desc.replace("\"",""));
                        n.setContent(newsContent.replace("\"",""));
                        n.setImageUrl(imageUrl.replace("\"",""));
                        n.setSource(source.replace("\"",""));
                        n.setNewsUrl(url.replace("\"",""));

                        list.add(n);






                    }

                    fillTheView();


//                    Log.d(TAG, "onResponse: "+list.get(2).getSource());




//                    JsonObject jsonObject2 = (JsonObject) jsonObject1.get("data");
//                    Log.d("Maya", "onResponse: "+jsonObject2.toString());
//                    JsonObject jsonObject3 = (JsonObject) jsonObject2.getAsJsonObject("total");
//
//                    iDeath = Integer.parseInt(jsonObject3.get("deaths").toString());
//                    iForeigner = Integer.parseInt(jsonObject3.get("active").toString());
//                    iIndian = Integer.parseInt(jsonObject3.get("confirmed").toString());
//                    iRecovered = Integer.parseInt(jsonObject3.get("recovered").toString());
//
//                    totalCase = Integer.parseInt(jsonObject3.get("confirmed").toString());
//                    Log.d("MayaTotal", "onResponse: "+totalCase+"   "+iRecovered+"   "+iForeigner+"  "+iDeath);
//
//                    mainCounter();
//                    nDialog.dismiss();


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

            private void fillTheView() {


                nDialog.dismiss();
                NewsAdapter adapter = new NewsAdapter(list, NewsActivity.this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(NewsActivity.this));

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());

            }
        });













    }

}
