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

public class HelpLineActivity extends AppCompatActivity {


    ProgressDialog nDialog;
    Retrofit retrofit;
    RecyclerView recyclerView;
    ArrayList<HelpLineModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_line);




        Window window = getWindow();

        nDialog = new ProgressDialog(HelpLineActivity.this);
        nDialog.setMessage("Loading..");
        nDialog.setTitle("Getting Data");
        nDialog.setIcon(R.drawable.swirl);
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(false);
        nDialog.show();


// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.parseColor("#FDC391"));
        }



        recyclerView = findViewById(R.id.contactRecycler);

        list = new ArrayList<>();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.rootnet.in/covid19-in/")

                .addConverterFactory(ScalarsConverterFactory.create())

                .build();
        getInformation();

    }
    private void fillTheView() {


        nDialog.dismiss();
        HelpLineAdapter adapter = new HelpLineAdapter(list, HelpLineActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(HelpLineActivity.this));

    }



    private void getInformation() {






        final JsonApiHolder jsonApiHolder = retrofit.create(JsonApiHolder.class);
        Call<String> call = jsonApiHolder.getContacts();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.isSuccessful()){


                    JsonParser parser = new JsonParser();
                    JsonElement json =  parser.parse(response.body());

                    JsonObject jsonObject1  = json.getAsJsonObject();
                    JsonObject object = jsonObject1.getAsJsonObject("data");
                    JsonObject contactJson = object.getAsJsonObject("contacts");
                    JsonArray array = contactJson.getAsJsonArray("regional");
                    Log.d("Conatct", "onResponse: "+array.size());

                    for(int i = 0;i<array.size();i++)
                    {
                        JsonObject object1 = array.get(i).getAsJsonObject();
                        String name = object1.get("loc").toString();
                        String number = object1.get("number").toString();
                        HelpLineModel h = new HelpLineModel();
                        h.setName(name.replace("\"",""));
                        h.setNumber(number.replace("\"",""));
                        list.add(h);


                    }



                    fillTheView();




                }



            }



            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });













    }

}
