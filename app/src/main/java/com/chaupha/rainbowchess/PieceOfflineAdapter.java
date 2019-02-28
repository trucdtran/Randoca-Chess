package com.chaupha.rainbowchess;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class PieceOfflineAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private BoardOffline board;


    public PieceOfflineAdapter(Context context, BoardOffline board) {
        layoutInflater = LayoutInflater.from(context.getApplicationContext());
        this.board = board;
    }


    @Override
    public int getCount() {
        return BoardOffline.SIZE * BoardOffline.SIZE;
    }


    @Override
    public Object getItem(int i) {
        return null;
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_board, null);
        }

        ImageView backgroundView = view.findViewById(R.id.itemView);
        ImageView pieceView = view.findViewById(R.id.pieceView);

        int x = i / BoardOffline.SIZE;
        int y = i % BoardOffline.SIZE;

        boolean itemHover = board.getItemHover(x,y);
        if(!itemHover) {
            backgroundView.setImageResource(R.drawable.normal_item);
        }
        else {
            backgroundView.setImageResource(R.drawable.selected_item);
        }

        BoardOffline.itemType item = board.getItem(x,y);
        if (item == BoardOffline.itemType.RED) {
            pieceView.setImageResource(R.drawable.red_piece);
        }
        else if (item == BoardOffline.itemType.BLUE) {
            pieceView.setImageResource(R.drawable.blue_piece);
        }
        else if (item == BoardOffline.itemType.REDSTAR) {
            pieceView.setImageResource(R.drawable.red_king);
        }
        else if (item == BoardOffline.itemType.BLUESTAR) {
            pieceView.setImageResource(R.drawable.blue_king);
        }
        else {
            pieceView.setImageBitmap(null);
        }

        return view;
    }

}
