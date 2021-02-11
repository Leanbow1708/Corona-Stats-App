package com.example.coronanews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyAdapter> {

    ArrayList<DataModel> list;
    Context context;

    public MainAdapter(ArrayList<DataModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(context).inflate(R.layout.row_main_recycler, parent,false);

        return new MyAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter holder, final int position) {


        holder.imgImage.setImageResource(list.get(position).getDrawable());
        holder.txtText.setText(list.get(position).getText());


        holder.imgImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(position==0)
                {

                    Intent intent = new Intent(context,StateWiseActivity.class);
                    context.startActivity(intent);

                }
                else if(position == 3){
                    Intent intent = new Intent(context,NewsActivity.class);
                    context.startActivity(intent);

                }
                else if(position == 1){

                    Intent intent = new Intent(context,WorldActivity.class);
                    context.startActivity(intent);

                }
                else {

                    Intent intent = new Intent(context,HelpLineActivity.class);
                    context.startActivity(intent);

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
        ImageView imgImage;
        public MyAdapter(@NonNull View itemView) {
            super(itemView);

            txtText = itemView.findViewById(R.id.txtText);
            imgImage = itemView.findViewById(R.id.imgImage);





        }
    }
}
