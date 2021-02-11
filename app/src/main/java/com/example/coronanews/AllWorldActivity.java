package com.example.coronanews;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class AllWorldActivity extends AppCompatActivity {

    ProgressDialog nDialog;

    ImageView imgCancel;
    EditText edtSearch;
    TextView title;
    ImageView imageView;


    RecyclerView recyclerView;
    ArrayList<WorldModel> list;
    Retrofit retrofit;

    WorldAdapter adapter;
    String TAG="AllWorldActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_world);


        title = findViewById(R.id.title);


        final View includedSearchBar = findViewById(R.id.includeSearch);
        imgCancel = includedSearchBar.findViewById(R.id.imgCancel);
        edtSearch = includedSearchBar.findViewById(R.id.edtSearch);



        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                adapter.getFilter().filter(editable.toString());

            }
        });



        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageView.setVisibility(View.GONE);
                title.setVisibility(View.GONE);

                includedSearchBar.setVisibility(View.VISIBLE);


            }
        });

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageView.setVisibility(View.VISIBLE);
                title.setVisibility(View.VISIBLE);

                includedSearchBar.setVisibility(View.GONE);



                adapter.getFilter().filter(null);

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

        recyclerView = findViewById(R.id.countryRecycler);
        list = new ArrayList<>();

        nDialog = new ProgressDialog(AllWorldActivity.this);
        nDialog.setMessage("Loading..");
        nDialog.setTitle("Getting Data");
        nDialog.setIcon(R.drawable.swirl);
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(false);
        nDialog.show();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://corona.lmao.ninja/")

                .addConverterFactory(ScalarsConverterFactory.create())

                .build();



        getInformation();

    }



    private void getInformation() {






        final JsonApiHolder jsonApiHolder = retrofit.create(JsonApiHolder.class);
        Call<String> call = jsonApiHolder.getAllCountry();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.isSuccessful()){

//                            Toast.makeText(MainActivity.this, response.body().toString(), Toast.LENGTH_LONG).show();


                    JsonParser parser = new JsonParser();
                    JsonElement json =  parser.parse(response.body());

                    JsonArray array = json.getAsJsonArray();



                    Log.d(TAG, "onResponse: "+array.size());

                    for(int i = 0;i<array.size();i++){

                        JsonObject object = array.get(i).getAsJsonObject();


                        int r = object.get("recovered").getAsInt();
                        int a = object.get("active").getAsInt();
                        int d = object.get("deaths").getAsInt();
                        int c = object.get("cases").getAsInt();

                        WorldModel w = new WorldModel();
                        String name = object.get("country").toString();
                        int cases = object.get("cases").getAsInt();
                        JsonObject object1 = object.getAsJsonObject("countryInfo");
                        String flag = object1.get("flag").toString();
                        w.setName(name.replace("\"",""));
                        w.setCases(cases);
                        w.setFlag(flag.replace("\"",""));
                        w.setC(c);
                        w.setA(a);
                        w.setD(d);
                        w.setR(r);


                        list.add(w);




                    }


                    fillTheView();


//                    JsonObject jsonObject1  = json.getAsJsonObject();
//                    JsonObject jsonObject2 = (JsonObject) jsonObject1.get("data");
//                    Log.d("Maya", "onResponse: "+jsonObject2.toString());
//                    JsonObject jsonObject3 = (JsonObject) jsonObject2.getAsJsonObject("total");

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



            @Override
            public void onFailure(Call<String> call, Throwable t) {

                nDialog.dismiss();
                Log.d(TAG, "onFailure: "+t.getMessage());

            }
        });













    }






    private void fillTheView() {


         adapter = new WorldAdapter(list,AllWorldActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AllWorldActivity.this));
        nDialog.dismiss();



    }

}
