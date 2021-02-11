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

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyAdapter>{


    ArrayList<NewsModel> list;
    Context context;

    public NewsAdapter(ArrayList<NewsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_news_feed, parent,false);

        return new MyAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter holder, final int position) {

        holder.txtNo.setText((position+1)+")");
        holder.txtSource.setText(list.get(position).getSource());
        holder.txtDesc.setText(list.get(position).getDesc());

        holder.imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,IndividualNewActivity.class);
                intent.putExtra("content", list.get(position).getContent());
                intent.putExtra("url", list.get(position).getImageUrl());
                intent.putExtra("source", list.get(position).getSource());
                intent.putExtra("newsUrl", list.get(position).getNewsUrl());
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyAdapter extends RecyclerView.ViewHolder{

        TextView txtNo;
        TextView txtSource;
        TextView txtDesc;
        ImageView imgNext;
        public MyAdapter(@NonNull View itemView) {
            super(itemView);

            imgNext = itemView.findViewById(R.id.imgNext);
            txtNo = itemView.findViewById(R.id.txtNo);
            txtDesc = itemView.findViewById(R.id.txtDesc);
            txtSource = itemView.findViewById(R.id.txtSource);






        }
    }


}
