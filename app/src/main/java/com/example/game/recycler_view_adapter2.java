package com.example.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recycler_view_adapter2 extends RecyclerView.Adapter<recycler_view_adapter2.MyViewHolder> {
    Context context;
    Bitmap[] monsters;



    public recycler_view_adapter2(Context context, Bitmap[] monsters){
        this.context = context;
        this.monsters = monsters;

    }

    @NonNull
    @Override
    public recycler_view_adapter2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycle_view_row2, parent, false);
        return new recycler_view_adapter2.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recycler_view_adapter2.MyViewHolder holder, int position) {
        holder.imageView.setImageBitmap(monsters[position]);
        //Drawable d = new BitmapDrawable(context.getResourcrecycler_view_adapteres(), monsters.get(position).getImage());
        //holder.imageView.setImageDrawable(d);
    }

    @Override
    public int getItemCount() {
        return monsters.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }
}
