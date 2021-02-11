package com.example.coronanews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ImportantLinkAdapter extends RecyclerView.Adapter<ImportantLinkAdapter.MyAdapter>{

    Uri newsUrl;
    ArrayList<ImportantLinkModel> list;
    Context context;

    public ImportantLinkAdapter(ArrayList<ImportantLinkModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_important_links, parent,false);

        return new MyAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter holder, final int position) {

        holder.imgImage.setImageResource(list.get(position).getImage());
        holder.txtContent.setText(list.get(position).getContent());
        holder.txtName.setText(list.get(position).getName());

        holder.btnVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(position==0)
                {

                    newsUrl = Uri.parse("https://www.who.int/health-topics/coronavirus#tab=tab_1");
                    Uri uri;
                    uri = Uri.parse(String.valueOf(newsUrl));

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    context.startActivity(intent);



                }
                else if(position==1)
                {
                    newsUrl = Uri.parse("https://www.unicef.org/stories/novel-coronavirus-outbreak-what-parents-should-know");
                    Uri uri;
                    uri = Uri.parse(String.valueOf(newsUrl));

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    context.startActivity(intent);



                }
                else if(position==2)
                {
                    newsUrl = Uri.parse("https://www.onhealth.com/content/1/top_diet_weight_loss");
                    Uri uri;
                    uri = Uri.parse(String.valueOf(newsUrl));

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    context.startActivity(intent);



                }
                else if(position==3)
                {
                    newsUrl = Uri.parse("https://www.artofliving.org/in-en/yoga-%E2%80%93-natural-immunity-booster");
                    Uri uri;
                    uri = Uri.parse(String.valueOf(newsUrl));

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    context.startActivity(intent);



                }

                else if(position==4)
                {
                    newsUrl = Uri.parse("https://www.covid19india.org/");
                    Uri uri;
                    uri = Uri.parse(String.valueOf(newsUrl));

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(uri);
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

        TextView txtName;
        TextView txtContent;
        ImageView imgImage;
        TextView btnVisit;
        public MyAdapter(@NonNull View item) {
            super(item);

            txtName = item.findViewById(R.id.txtName);
            txtContent = item.findViewById(R.id.txtContent);
            imgImage = item.findViewById(R.id.imgImage);
            btnVisit = item.findViewById(R.id.btnVisit);







        }
    }
}
