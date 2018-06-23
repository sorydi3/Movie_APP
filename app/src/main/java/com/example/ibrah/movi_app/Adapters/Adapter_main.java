package com.example.ibrah.movi_app.Adapters;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ibrah.movi_app.R;
import com.example.ibrah.movi_app.Utils.Movie;
import com.squareup.picasso.Picasso;
import java.util.List;

public class Adapter_main extends RecyclerView.Adapter<Adapter_main.ViewHolder> {
    private Listener linstener;
    private Context contxt;
    class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView ItemView;
        private ViewHolder(CardView itemView) {
            super(itemView);
            ItemView =itemView;
        }
    }

    private final LayoutInflater mInflater;
    private List<Movie> mMovie; // Cached copy of words
    public static String TAG="Adapter_main";

    public Adapter_main(Context context) {
        mInflater = LayoutInflater.from(context);
        contxt = context;
    }

    @Override
    public Adapter_main.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView c = (CardView) mInflater.inflate(R.layout.card_view_item_main, parent, false);
        return new Adapter_main.ViewHolder(c);
    }


    @Override
    public void onBindViewHolder(Adapter_main.ViewHolder holder, final int position) {
        CardView cardView=holder.ItemView;
        TextView textView=(TextView)cardView.findViewById(R.id.textView2) ;
        if (mMovie != null) {
            Movie current= mMovie.get(position);
            textView.setText(current.getTitle());
            // Set Image if available
            ImageView imageView = (ImageView) cardView.findViewById(R.id.image_v);
            String image = current.getThumbnailLink();
            if (image != null && image.length() > 0) {
                //TODO fix picaso copying the last one that i used from sandwich app
                    Picasso.get().load("https://image.tmdb.org/t/p/w300/"+image).into(imageView);
                Log.e(TAG, "onBindViewHolder:---> https://image.tmdb.org/t/p/w185/"+image);
            }
        } else {
            // Covers the case of data not being ready yet.
           textView.setText(R.string.no_name_novie);
        }
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(linstener!=null)linstener.onClick(position);
            }
        });
    }

    public void setWords(List<Movie> movies) {
        mMovie = movies;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if (mMovie != null)
            return mMovie.size();
        else return 0;
    }

    public interface Listener {
        void onClick(int position);
    }

    public void SetLister(Listener listener){
        this.linstener=listener;
    }


}
