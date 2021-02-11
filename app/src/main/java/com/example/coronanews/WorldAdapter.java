package com.example.coronanews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class WorldAdapter extends RecyclerView.Adapter<WorldAdapter.MyAdapter> implements Filterable {

    ArrayList<WorldModel> list;
    ArrayList<WorldModel> listFull;

    Context context;

    public WorldAdapter(ArrayList<WorldModel> adapters, Context context) {
        this.list = adapters;
        this.context = context;
        listFull = new ArrayList<>(adapters);
    }

    @NonNull
    @Override
    public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(context).inflate(R.layout.row_world_recycler, parent,false);

        return new MyAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter holder, final int position) {


        holder.cityCase.setText(list.get(position).getCases()+"");
        holder.txtCountry.setText(list.get(position).getName());


        holder.txtCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context,IndividualWorldActivity.class);
               intent.putExtra("a", list.get(position).getA()+"");
               intent.putExtra("c", list.get(position).getC()+"");
               intent.putExtra("r", list.get(position).getR()+"");
               intent.putExtra("d", list.get(position).getD()+"");
               intent.putExtra("name", list.get(position).getName());

                context.startActivity(intent);

            }
        });

        Picasso.get().load(list.get(position).getFlag()).into(holder.flag);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }





    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<WorldModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (WorldModel item : listFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };









    public class MyAdapter extends RecyclerView.ViewHolder{

        TextView txtCountry;
        ImageView flag;
        TextView cityCase;
        public MyAdapter(@NonNull View itemView) {
            super(itemView);

            txtCountry = itemView.findViewById(R.id.cityName);
            flag = itemView.findViewById(R.id.flag);
            cityCase = itemView.findViewById(R.id.cityCase);





        }
    }

}
