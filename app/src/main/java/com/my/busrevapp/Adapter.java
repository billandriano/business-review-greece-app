package com.my.busrevapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    List<String> titles;
    List<Integer> images;
    LayoutInflater inflater;
    private Context mContext;


    public Adapter(Context ctx, List<String> titles, List<Integer> images){
        this.titles = titles;
        this.images = images;
        this.inflater = LayoutInflater.from(ctx);
        mContext = ctx;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cat_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(titles.get(position));
        holder.gridIcon.setImageResource(images.get(position));


    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView gridIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cat_title);
            gridIcon = itemView.findViewById(R.id.cat_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String cat_potition="";
                    if (getAdapterPosition()==0){
                        cat_potition="4";
                    }
                    else if (getAdapterPosition()==1){
                        cat_potition="5";
                    }
                    else if (getAdapterPosition()==2){
                        cat_potition="6";
                    }
                    else if (getAdapterPosition()==3){
                        cat_potition="7";
                    }
                    else if (getAdapterPosition()==4){
                        cat_potition="8";
                    }
                    else if (getAdapterPosition()==5){
                        cat_potition="314";
                    }
                    else if (getAdapterPosition()==6){
                        cat_potition="49";
                    }
                    else if (getAdapterPosition()==7){
                        cat_potition="48";
                    }
                    else if (getAdapterPosition()==8){
                        cat_potition="11";
                    }
                    else if (getAdapterPosition()==9){
                        cat_potition="10";
                    }
                    else if (getAdapterPosition()==10){
                        cat_potition="12";
                    }
                    else if (getAdapterPosition()==11){
                        cat_potition="13";
                    }
                    else if (getAdapterPosition()==12) {
                        cat_potition="50";
                    }

                    Intent intent = new Intent(mContext, AllActivity.class);
                    intent.putExtra("cat_id", cat_potition);
                    intent.putExtra("key", "categories");
                    mContext.startActivity(intent);
                }
            });
        }
    }
}