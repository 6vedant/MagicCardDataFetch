package com.example.recyclerviewpractice.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewpractice.R;
import com.example.recyclerviewpractice.model.CardItem;
import com.example.recyclerviewpractice.ui.DetailActivity;

import java.util.List;

public class MagicCardRecyclerAdapter extends RecyclerView.Adapter<MagicCardRecyclerAdapter.ItemViewHolder> {

    private List<CardItem> mItems;
    private Context context;
    private ListItemClickListener mListItemClickListener;

    public MagicCardRecyclerAdapter(Context context, List<CardItem> mItems) {
        this.context = context;
        this.mItems = mItems;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.list_item, parent, false);


        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("card_detail", mItems.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mItems == null) ? 0 : mItems.size();
    }

    public void setOnListItemClickListener(ListItemClickListener listItemClickListener) {
        mListItemClickListener = listItemClickListener;
    }

    public void swapData(List<CardItem> p) {
        mItems = p;
        notifyDataSetChanged();
    }

    interface ListItemClickListener {
        void onListItemClick(CardItem item);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvName;
        private TextView tvType;
        private LinearLayout llItem;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvType = itemView.findViewById(R.id.tv_type);
            llItem = itemView.findViewById(R.id.ll_item);
            itemView.setOnClickListener(this);


        }

        public void bind(int position) {
            tvName.setText(mItems.get(position).getName());
            tvType.setText(mItems.get(position).getType());

        }

        @Override
        public void onClick(View v) {
            if (mListItemClickListener != null) {
                int clickedIndex = getAdapterPosition();
                CardItem cardItem = mItems.get(clickedIndex);
                mListItemClickListener.onListItemClick(cardItem);
            }

        }
    }
}
