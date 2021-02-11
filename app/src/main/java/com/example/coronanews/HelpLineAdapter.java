package com.example.coronanews;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HelpLineAdapter extends RecyclerView.Adapter<HelpLineAdapter.MyAdapter> implements  ActivityCompat.OnRequestPermissionsResultCallback{


    Intent callIntent;
    ArrayList<HelpLineModel> list;

    Context context;

    public HelpLineAdapter(ArrayList<HelpLineModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_helpline_numbers, parent, false);
        return new MyAdapter(view);
    }




    @Override
    public void onBindViewHolder(@NonNull MyAdapter holder, final int position) {

        holder.stateName.setText(list.get(position).getName());
        holder.phoneNo.setText(list.get(position).getNumber());


        holder.imgCall.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + list.get(position).getNumber()));//change the number
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (context.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                        Log.d("pp", "onClick: "+"No Permission");

                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{Manifest.permission.CALL_PHONE},
                                100);
                       // context.startActivity(callIntent);

                        // TODO: Consider calling
                        //    Activity#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for Activity#requestPermissions for more details.
                        return;
                    }
                    else
                    {
                        context.startActivity(callIntent);

                    }
                }
                else
                {
                    context.startActivity(callIntent);

                }


            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){

            case 100:{

                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    context.startActivity(callIntent);
                }
                else
                {
                    Toast.makeText(context, "Calling permissions denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

    public class MyAdapter extends RecyclerView.ViewHolder{

        TextView stateName;
      TextView phoneNo;
      TextView txtCall;
      ImageView imgCall;
        public MyAdapter(@NonNull View itemView) {
            super(itemView);

            stateName = itemView.findViewById(R.id.stateName);
            phoneNo = itemView.findViewById(R.id.phoneNo);
            txtCall = itemView.findViewById(R.id.txtCall);
            imgCall = itemView.findViewById(R.id.imgCall);




        }
    }
}
