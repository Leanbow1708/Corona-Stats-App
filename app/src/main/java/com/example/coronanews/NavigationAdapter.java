package com.example.coronanews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.MyAdapter> {


    ArrayList<String> list;
    Context context;

    public NavigationAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_navigation_recycler, parent,false);

        return new MyAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter holder, final int position) {

        holder.txtText.setText(list.get(position));

        holder.txtText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(position==0)
                {
                    Intent intent = new Intent(context,StateWiseActivity.class);
                    context.startActivity(intent);
                }
                else if(position==1){

                    Intent intent = new Intent(context,WorldActivity.class);
                    context.startActivity(intent);
                }
                else if(position==2){
                    Intent intent = new Intent(context,NewsActivity.class);
                    context.startActivity(intent);

                }
                else if(position==3)
                {
                    Intent intent = new Intent(context,HelpLineActivity.class);
                    context.startActivity(intent);
                }
                else if(position==4){
                    Intent intent = new Intent(context,FrequentlyAskedActivity.class);
                    context.startActivity(intent);
                }
                else if(position==5){
                    Intent intent = new Intent(context,ImportantLinkActivity.class);
                    context.startActivity(intent);
                }
                else if(position==6)
                {

                    Intent intent = new Intent(context,AboutMeActivity.class);
                    context.startActivity(intent);

                }
                else {

                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(context,LoginActivity.class);
                    context.startActivity(intent);
                    ((Activity)context).finish();

                }



            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyAdapter extends RecyclerView.ViewHolder{

        TextView txtText;

        public MyAdapter(@NonNull View itemView) {
            super(itemView);

            txtText = itemView.findViewById(R.id.txtNav);





        }
    }
}
