package com.example.examapp;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterTable extends BaseAdapter {
    Context context;
    ArrayList<TableGame> tableGames;
    LayoutInflater inflater;

    public AdapterTable(Context context, ArrayList<TableGame> tableGames) {
        this.context = context;
        this.tableGames = tableGames;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    TableGame getGame(int pos){return (TableGame) getItem(pos);}

    @Override
    public int getCount() {
        return tableGames.size();
    }

    @Override
    public Object getItem(int i) {
        return tableGames.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public Bitmap getImageBitmap(String image){
        if(image != null){
            byte[] bytes = Base64.decode(image,Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        } else {
            return null;
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = inflater.inflate(R.layout.item_grid,viewGroup,false);
        }
        TableGame tableGame = getGame(i);
        ((TextView) view.findViewById(R.id.title_title)).setText(tableGame.title);
        ((TextView) view.findViewById(R.id.cost_title)).setText(tableGame.cost+"");
        ((TextView) view.findViewById(R.id.stock_title)).setText(tableGame.stock+"");
        ((TextView) view.findViewById(R.id.store_title)).setText(tableGame.thestore+"");
        ((TextView) view.findViewById(R.id.desc_title)).setText(tableGame.descpritoin);
        ((TextView) view.findViewById(R.id.rewiews_title)).setText(tableGame.rewies);
        if(tableGame.image != null) {
            ((ImageView) view.findViewById(R.id.image_title)).setImageBitmap(getImageBitmap(tableGame.image));
        }
        else{
            ((ImageView) view.findViewById(R.id.image_title)).setImageResource(R.drawable.baseline_image_24);
        }
        return view;
    }
}
