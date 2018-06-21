package com.example.ibrah.movi_app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ibrah.movi_app.DatabaBase.Favorite;
import com.example.ibrah.movi_app.R;
import com.example.ibrah.movi_app.Utils.Trailer;

import java.util.ArrayList;
import java.util.List;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

    private List<Trailer> data;
    Context context;

    public HorizontalAdapter(Context contex) {
        this.context = contex;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_single_row, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.title.setText(data.get(position).getName());
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playVideo(data.get(position).getKey());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (data != null)
            return data.size();
        else return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            image = (ImageView) itemView.findViewById(R.id.image_view);
        }
    }

    public void setWords(List<Trailer> words) {
        data = words;
        notifyDataSetChanged();
    }

    public void playVideo(String key) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
        if (intent.resolveActivity(context.getPackageManager()) == null) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?=" + key));
        }
        context.startActivity(intent);
    }
}
