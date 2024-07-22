package com.my.busrevapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private Calendar time = Calendar.getInstance();
    private String hours;
    private String hours2;
    private String month;
    private Context context;
    private ArrayList<HashMap<String, Object>> _data;
    private OnItemClickListener onItemClickListener;
    int size;

    public MainAdapter(Context context, ArrayList<HashMap<String, Object>> _data) {
        this.context=context;
        this._data=_data;
        size=_data.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new MainAdapter.ViewHolder(view, onItemClickListener);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int _position) {
        final ViewHolder holders= holder;
        time = Calendar.getInstance();
        if (new SimpleDateFormat("yyyy-MM-dd").format(time.getTime()).equals(_data.get((int) _position).get("date").toString().replace("T", " ").substring((int) (0), (int) (10)))) {
            if (new SimpleDateFormat("H").format(time.getTime()).equals(_data.get((int) _position).get("date").toString().replace("T", " ").substring((int) (11), (int) (13)))) {
                hours = "Πριν από ";
                hours2 = " λεπτά";
                holder.date.setText(hours.concat(String.valueOf((long) (Double.parseDouble(new SimpleDateFormat("mm").format(time.getTime())) - Double.parseDouble(_data.get((int) _position).get("date").toString().replace("T", " ").substring((int) (14), (int) (16))))).concat(hours2)));
            } else {
                if ((Double.parseDouble(new SimpleDateFormat("H").format(time.getTime())) > Double.parseDouble(_data.get((int) _position).get("date").toString().replace("T", " ").substring((int) (11), (int) (13)))) || (Double.parseDouble(new SimpleDateFormat("H").format(time.getTime())) == Double.parseDouble(_data.get((int) _position).get("date").toString().replace("T", " ").substring((int) (11), (int) (13))))) {
                    hours = "Πριν από ";
                    if (String.valueOf((long) (Double.parseDouble(new SimpleDateFormat("H").format(time.getTime())) - Double.parseDouble(_data.get((int) _position).get("date").toString().replace("T", " ").substring((int) (11), (int) (13))))).equals("1")) {
                        hours2 = " ώρα";
                    } else {
                        hours2 = " ώρες";
                    }
                    holder.date.setText(hours.concat(String.valueOf((long) (Double.parseDouble(new SimpleDateFormat("H").format(time.getTime())) - Double.parseDouble(_data.get((int) _position).get("date").toString().replace("T", " ").substring((int) (11), (int) (13))))).concat(hours2)));
                }
            }
        } else {
            if (_data.get((int) _position).get("date").toString().replace("T", " ").substring((int) (0), (int) (10)).substring((int) (5), (int) (7)).equals("01")) {
                month = " Ιανουαρίου ";
            }
            if (_data.get((int) _position).get("date").toString().replace("T", " ").substring((int) (0), (int) (10)).substring((int) (5), (int) (7)).equals("02")) {
                month = " Φεβρουαρίου ";
            }
            if (_data.get((int) _position).get("date").toString().replace("T", " ").substring((int) (0), (int) (10)).substring((int) (5), (int) (7)).equals("03")) {
                month = " Μαρτίου ";
            }
            if (_data.get((int) _position).get("date").toString().replace("T", " ").substring((int) (0), (int) (10)).substring((int) (5), (int) (7)).equals("04")) {
                month = " Απριλίου ";
            }
            if (_data.get((int) _position).get("date").toString().replace("T", " ").substring((int) (0), (int) (10)).substring((int) (5), (int) (7)).equals("05")) {
                month = " Μαΐου ";
            }
            if (_data.get((int) _position).get("date").toString().replace("T", " ").substring((int) (0), (int) (10)).substring((int) (5), (int) (7)).equals("06")) {
                month = " Ιουνίου ";
            }
            if (_data.get((int) _position).get("date").toString().replace("T", " ").substring((int) (0), (int) (10)).substring((int) (5), (int) (7)).equals("07")) {
                month = " Ιουλίου ";
            }
            if (_data.get((int) _position).get("date").toString().replace("T", " ").substring((int) (0), (int) (10)).substring((int) (5), (int) (7)).equals("08")) {
                month = " Αυγούστου ";
            }
            if (_data.get((int) _position).get("date").toString().replace("T", " ").substring((int) (0), (int) (10)).substring((int) (5), (int) (7)).equals("09")) {
                month = " Σεπτεμβρίου ";
            }
            if (_data.get((int) _position).get("date").toString().replace("T", " ").substring((int) (0), (int) (10)).substring((int) (5), (int) (7)).equals("10")) {
                month = " Οκτωβρίου ";
            }
            if (_data.get((int) _position).get("date").toString().replace("T", " ").substring((int) (0), (int) (10)).substring((int) (5), (int) (7)).equals("11")) {
                month = " Νοεμβρίου ";
            }
            if (_data.get((int) _position).get("date").toString().replace("T", " ").substring((int) (0), (int) (10)).substring((int) (5), (int) (7)).equals("12")) {
                month = " Δεκεμβρίου ";
            }
            holder.date.setText(_data.get((int) _position).get("date").toString().replace("T", " ").substring((int) (0), (int) (10)).substring((int) (8), (int) (10)).concat(month.concat(_data.get((int) _position).get("date").toString().replace("T", " ").substring((int) (0), (int) (10)).substring((int) (0), (int) (4)))));
        }
        String content=_data.get((int) _position).get("content").toString().replace("&#8211;", "-").replace("&#8217;", "'").replace("&#8221;", "\"").replace("&#8220;", "\"").replace("<p>", "").replace("}", "").replace("{", "").replace("rendered=", "").replace("</p>", "").replace(", protected=false", "").replace("&amp;", "&").replace("&#8230;", "...");
        float read0=((content.length()*4)/93)/60;
        int read=Math.round(read0);
        if (read<1){holder.read.setText("·Λιγότερο από 1 λεπτό διάβασμα·");
        }
        else if(read==1){holder.read.setText("·1 λεπτό διάβασμα·");}
        else{
            String finalread="·"+read+" λεπτά διάβασμα·";
            holder.read.setText(finalread);
        }

        holder.title.setText(_data.get((int) _position).get("title").toString().replace("&#8221", "\"").replace("&#8220;", "\"").replace("&#8217;", "'").replace("&#8211;", "-").replace("{", "").replace("}", "").replace("rendered=", "").replace("&#8230;", "...").replace("&#8216;", "'"));
        holder.desc.setText(_data.get((int) _position).get("excerpt").toString().replace("&#8211;", "-").replace("&#8217;", "'").replace("&#8221;", "\"").replace("&#8220;", "\"").replace("<p>", "").replace("}", "").replace("{", "").replace("rendered=", "").replace("</p>", "").replace(", protected=false", "").replace("&amp;", "&").replace("&#8230;", "..."));
        Glide.with(context).load(Uri.parse(_data.get((int)_position).get("jetpack_featured_media_url").toString())).listener(new RequestListener<Uri, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.imageview1);
    }

    @Override
    public int getItemCount() {
        return _data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title,desc,date,read;
        ImageView imageview1;
        ProgressBar progressBar;
        OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            read=itemView.findViewById(R.id.readtime);
            title = itemView.findViewById(R.id.mtitle);
            imageview1 = itemView.findViewById(R.id.mimg);
            desc = itemView.findViewById(R.id.mdesc);
            date = itemView.findViewById(R.id.mdate);
            progressBar=itemView.findViewById(R.id.prograss_load_photo);
            this.onItemClickListener= onItemClickListener;
        }
        public void onClick(View view){
            onItemClickListener.onItemClick(view, getAdapterPosition());

        }
    }
}