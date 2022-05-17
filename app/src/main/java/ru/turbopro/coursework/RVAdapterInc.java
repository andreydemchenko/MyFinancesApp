package ru.turbopro.coursework;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RVAdapterInc extends RecyclerView.Adapter<RVAdapterInc.StatisticsViewHolderInc> {

    List<Card_Statistics> cardInc;

    private RVAdapterInc.OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(RVAdapterInc.OnItemClickListener listener){
        mListener = listener;
    }

    public static class StatisticsViewHolderInc extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView name;
        TextView price;

        StatisticsViewHolderInc(View itemView, final RVAdapterInc.OnItemClickListener listener) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.cardviewincome);
            name = (TextView)itemView.findViewById(R.id.namecardincome);
            price = (TextView)itemView.findViewById(R.id.pricecardincome);

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



    RVAdapterInc(List<Card_Statistics> cardInc){
        this.cardInc = cardInc;
    }

    @Override
    public int getItemCount() {
        return cardInc.size();
    }

    @Override
    public RVAdapterInc.StatisticsViewHolderInc onCreateViewHolder(ViewGroup viewGroup, int i) {
        CardView cv = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardviewincome, viewGroup, false);
        RVAdapterInc.StatisticsViewHolderInc pvh = new RVAdapterInc.StatisticsViewHolderInc(cv, mListener);
        return pvh;
    }

    @Override
    public void onBindViewHolder(RVAdapterInc.StatisticsViewHolderInc personViewHolder, int i) {
        personViewHolder.name.setText(cardInc.get(i).name);
        personViewHolder.price.setText(cardInc.get(i).price);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

