package com.chaupha.rainbowchess;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Card> cards;
    private int mode;

    public static final int MODE_SHOP = 0;
    public static final int MODE_DESC = 1;

    public CardAdapter(Context context, ArrayList<Card> cards, int mode) {
        this.context = context;
        this.cards = cards;
        this.mode = mode;
    }


    @NonNull
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(cardView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Card card = cards.get(position);
        holder.image.setImageResource(card.getCardResource());
        if(card.getCount() == 0) {
            holder.image.setAlpha(0.3f);
            holder.text.setAlpha(0.3f);
        }
        else {
            holder.image.setAlpha(1.0f);
            holder.text.setAlpha(1.0f);
            if(mode == MODE_DESC) {
                holder.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for(int i = 0; i < DescActivity.desc.length; i++) {
                            if(DescActivity.desc[i] == 0) {
                                MediaPlayer.create(context, R.raw.sound_touch).start();
                                DescActivity.desc[i] = card.getCardID();
                                card.setCount(card.getCount() - 1);
                                String buffer = context.getResources().getString(R.string.profile_desc) + ": " +
                                        DescActivity.getDescPower();
                                DescActivity.textDescPower.setText(buffer);
                                switch (i) {
                                    case 0: {DescActivity.image1.setImageResource(card.getCardResource()); break;}
                                    case 1: {DescActivity.image2.setImageResource(card.getCardResource()); break;}
                                    case 2: {DescActivity.image3.setImageResource(card.getCardResource()); break;}
                                    case 3: {DescActivity.image4.setImageResource(card.getCardResource()); break;}
                                    case 4: {DescActivity.image5.setImageResource(card.getCardResource()); break;}
                                    case 5: {DescActivity.image6.setImageResource(card.getCardResource()); break;}
                                    case 6: {DescActivity.image7.setImageResource(card.getCardResource()); break;}
                                    case 7: {DescActivity.image8.setImageResource(card.getCardResource()); break;}
                                    case 8: {DescActivity.image9.setImageResource(card.getCardResource()); break;}
                                    case 9: {DescActivity.image10.setImageResource(card.getCardResource()); break;}
                                    case 10: {DescActivity.image11.setImageResource(card.getCardResource()); break;}
                                    case 11: {DescActivity.image12.setImageResource(card.getCardResource()); break;}
                                    case 12: {DescActivity.image13.setImageResource(card.getCardResource()); break;}
                                }
                                DescActivity.sortCards();
                                DescActivity.cardAdapter.notifyDataSetChanged();
                                return;
                            }
                        }
                    }
                });
            }
        }
        String buffer = context.getResources().getString(R.string.shop_count) + " " + card.getCount();
        holder.text.setText(buffer);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.card_image);
            text = itemView.findViewById(R.id.card_text);
        }
    }
}
