package com.example.coronanews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.MyAdapter> implements Filterable {



    ArrayList<StateModel> list;
    ArrayList<StateModel> listFull;
    Context context;

    public StateAdapter(ArrayList<StateModel> list, Context context) {
        this.list = list;
        this.context = context;
        listFull =  new ArrayList<>(list);
    }



    @NonNull
    @Override
    public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(context).inflate(R.layout.row_india_city, parent,false);

        return new MyAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter holder, final int position) {

        holder.txtNo.setText((position+1)+")");
      if(list.get(position).getTotal()==0){

          holder.txtCases.setText("-");

      }
      else
      {

          holder.txtCases.setText(list.get(position).getTotal()+"");
      }
        holder.txtCity.setText(list.get(position).getName().replace("\"",""));

        holder.txtCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,StateSpecificActivity.class);


                String name = list.get(position).getName();
                int c = list.get(position).getC();
                int r = list.get(position).getR();
                int a = list.get(position).getA();
                int d = list.get(position).getD();

                intent.putExtra("name", name);
                intent.putExtra("c", c+"");
                intent.putExtra("r", r+"");
                intent.putExtra("d", d+"");
                intent.putExtra("a", a+"");



                context.startActivity(intent);
            }
        });




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
            List<StateModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (StateModel item : listFull) {
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

        TextView txtCity;
        TextView txtNo;
        TextView txtCases;
        public MyAdapter(@NonNull View itemView) {
            super(itemView);

            txtCity = itemView.findViewById(R.id.cityName);
            txtCases = itemView.findViewById(R.id.cityCase);
            txtNo = itemView.findViewById(R.id.row_no);





        }
    }
}
