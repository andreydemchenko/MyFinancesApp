package ru.turbopro.coursework;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.StatisticsViewHolder> {

    List<Card_Statistics> stat;

    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class StatisticsViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView name;
        TextView price;

        StatisticsViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.cardview);
            name = (TextView)itemView.findViewById(R.id.namecard);
            price = (TextView)itemView.findViewById(R.id.pricecard);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }



    RVAdapter(List<Card_Statistics> stat){
        this.stat = stat;
    }

    @Override
    public int getItemCount() {
        return stat.size();
    }

    @Override
    public StatisticsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        CardView cv = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview, viewGroup, false);
        StatisticsViewHolder pvh = new StatisticsViewHolder(cv, mListener);
        return pvh;
    }

    @Override
    public void onBindViewHolder(StatisticsViewHolder personViewHolder, int i) {
        personViewHolder.name.setText(stat.get(i).name);
        personViewHolder.price.setText(stat.get(i).price);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
