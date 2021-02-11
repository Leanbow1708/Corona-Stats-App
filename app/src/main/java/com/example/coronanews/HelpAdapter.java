package com.example.coronanews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.MyAdapter>{


    ArrayList<HelpModel> list;
    Context context;

    public HelpAdapter(ArrayList<HelpModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_faq_recycler, parent,false);
        return new MyAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter holder, int position) {

        holder.txtA.setText(list.get(position).getA());
        holder.txtQ.setText(list.get(position).getQ());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyAdapter extends RecyclerView.ViewHolder{

       TextView txtQ;
       TextView txtA;
        public MyAdapter(@NonNull View item) {
            super(item);

            txtQ = item.findViewById(R.id.txtQ);
            txtA = item.findViewById(R.id.txtA);





        }
    }

}
