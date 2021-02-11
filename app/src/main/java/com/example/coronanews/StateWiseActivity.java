package com.example.coronanews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class StateWiseActivity extends AppCompatActivity {



    StateAdapter adapter;
    ImageView imgCancel;
    EditText edtSearch;
    TextView title;
    ImageView imageView;



    Retrofit retrofit;
    String TAG = "StateWiseActivity";
    ProgressDialog nDialog;
    ArrayList<StateModel> dataList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_wise);






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


        nDialog = new ProgressDialog(StateWiseActivity.this);
        nDialog.setMessage("Loading..");
        nDialog.setTitle("Getting Data");
        nDialog.setIcon(R.drawable.swirl);
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(false);
        nDialog.show();

        recyclerView = findViewById(R.id.stateRecycler);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.rootnet.in/covid19-in/unofficial/covid19india.org/")

                .addConverterFactory(ScalarsConverterFactory.create())

                .build();
        dataList = new ArrayList<>();
        getInformation();




    }



    private void getInformation() {






        final JsonApiHolder jsonApiHolder = retrofit.create(JsonApiHolder.class);
        Call<String> call = jsonApiHolder.getLatest();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.isSuccessful()) {

//                            Toast.makeText(MainActivity.this, response.body().toString(), Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onResponse: " + response.body().toString());

                    JsonParser parser = new JsonParser();
                    JsonElement json = parser.parse(response.body());

                    JsonObject jsonObject1 = json.getAsJsonObject();
                    JsonObject jsonObject2 = (JsonObject) jsonObject1.get("data");
//                    Log.d("Maya", "onResponse: "+jsonObject2.toString());
//                    JsonObject jsonObject3 = (JsonObject) jsonObject2.getAsJsonObject("total");


                    JsonArray jsonArray1 = jsonObject2.getAsJsonArray("statewise");

                    Log.d(TAG, "onResponse: " + jsonArray1.size());


                    for (int i = 0; i < jsonArray1.size(); i++)
                    {


                        JsonObject jsonObject3 = jsonArray1.get(i).getAsJsonObject();
                        String name = jsonObject3.get("state").toString();
                        int cases = jsonObject3.get("confirmed").getAsInt();
                        int c =  jsonObject3.get("confirmed").getAsInt();
                        int r =  jsonObject3.get("recovered").getAsInt();
                        int a =  jsonObject3.get("active").getAsInt();
                        int d =  jsonObject3.get("deaths").getAsInt();

                        StateModel s1 = new StateModel();
                        s1.setName(name);
                        s1.setTotal(cases);
                        s1.setA(a);
                        s1.setC(c);
                        s1.setD(d);
                        s1.setR(r);
                        dataList.add(s1);



                    }
                    fillTheActivity();




                    Log.d(TAG, "onResponse: "+dataList);



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

            private void fillTheActivity() {


                nDialog.dismiss();
                 adapter = new StateAdapter(dataList, StateWiseActivity.this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(StateWiseActivity.this));






            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });













    }



}
