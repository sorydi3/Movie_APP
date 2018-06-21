package com.example.ibrah.movi_app.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ibrah.movi_app.DatabaBase.Favorite;
import com.example.ibrah.movi_app.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView ItemView;

            private ViewHolder(View itemView) {
                super(itemView);
                ItemView = itemView.findViewById(R.id.textView);
            }
        }

        private final LayoutInflater mInflater;
        private List<Favorite> mWords; // Cached copy of words

    public Adapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = mInflater.inflate(R.layout.item_recicler_view, parent, false);
            return new ViewHolder(itemView);
        }


    @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (mWords != null) {
                Favorite current= mWords.get(position);
                holder.ItemView.setText(current.getmNom());
            } else {
                // Covers the case of data not being ready yet.
                holder.ItemView.setText("No Word");
            }
        }

    public void setWords(List<Favorite> words) {
            mWords = words;
            notifyDataSetChanged();
        }
        @Override
        public int getItemCount() {
            if (mWords != null)
                return mWords.size();
            else return 0;
        }
}
