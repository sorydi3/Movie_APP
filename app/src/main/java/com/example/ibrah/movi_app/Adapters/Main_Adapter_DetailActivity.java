//package com.example.ibrah.movi_app.Adapters;
//
//import android.content.Context;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.example.ibrah.movi_app.DetailsActivity;
//import com.example.ibrah.movi_app.R;
//import com.example.ibrah.movi_app.Utils.Movie;
//import com.example.ibrah.movi_app.Utils.Review;
//
//import java.util.ArrayList;
//
//
//public class Main_Adapter_DetailActivity extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//    private Context context;
//    private ArrayList<Object> items;
//    private final int VERTICAL = 1;
//    private final int HORIZONTAL = 2;
//
//
//    public Main_Adapter_DetailActivity(Context context, ArrayList<Object> items) {
//        this.context = context;
//        this.items = items;
//
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View view;
//        RecyclerView.ViewHolder holder;
//        switch (viewType) {
//            case VERTICAL:
//                view = inflater.inflate(R.layout.vertical, parent, false);
//                holder = new VerticalViewHolder(view);
//                break;
//            case HORIZONTAL:
//                view = inflater.inflate(R.layout.horizontal, parent, false);
//                holder = new HorizontalViewHolder(view);
//                break;
//
//            default:
//                view = inflater.inflate(R.layout.horizontal, parent, false);
//                holder = new HorizontalViewHolder(view);
//                break;
//        }
//
//
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (holder.getItemViewType() == VERTICAL)
//            verticalView((VerticalViewHolder) holder);
//        else if (holder.getItemViewType() == HORIZONTAL)
//            horizontalView((HorizontalViewHolder) holder);
//    }
//
//    private void verticalView(VerticalViewHolder holder) {
//
//        VerticalAdapter adapter1 = new VerticalAdapter();
//        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
//        holder.recyclerView.setAdapter(adapter1);
//    }
//
//
//    private void horizontalView(HorizontalViewHolder holder) {
//        HorizontalAdapter adapter = new HorizontalAdapter(this);
//        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
//        holder.recyclerView.setAdapter(adapter);
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return items.size();
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (items.get(position) instanceof Movie)
//            return VERTICAL;
//        if (items.get(position) instanceof Review)
//            return HORIZONTAL;
//        return -1;
//    }
//
//    public class HorizontalViewHolder extends RecyclerView.ViewHolder {
//
//        RecyclerView recyclerView;
//
//        HorizontalViewHolder(View itemView) {
//            super(itemView);
//            recyclerView = itemView.findViewById(R.id.inner_recyclerView);
//        }
//    }
//
//    public class VerticalViewHolder extends RecyclerView.ViewHolder {
//        RecyclerView recyclerView;
//
//        VerticalViewHolder(View itemView) {
//            super(itemView);
//            recyclerView = itemView.findViewById(R.id.inner_recyclerView);
//        }
//    }
//}
