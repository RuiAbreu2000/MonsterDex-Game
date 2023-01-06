package com.example.game;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recycler_view_adapter3 extends RecyclerView.Adapter<recycler_view_adapter3.MyViewHolder> {
    Context context;
    ArrayList<monster_class> monsters;



    public recycler_view_adapter3(Context context, ArrayList<monster_class> monsters){
        this.context = context;
        this.monsters = monsters;

    }

    @NonNull
    @Override
    public recycler_view_adapter3.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycle_view_row3, parent, false);
        return new recycler_view_adapter3.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recycler_view_adapter3.MyViewHolder holder, int position) {
        holder.nameView.setText(monsters.get(position).getName());
        holder.imageView.setImageBitmap(monsters.get(position).getImage());
        holder.lvlView.setText(monsters.get(position).getLevel());
        holder.typeView.setText(monsters.get(position).getType());
        //Drawable d = new BitmapDrawable(context.getResourcrecycler_view_adapteres(), monsters.get(position).getImage());
        //holder.imageView.setImageDrawable(d);
    }

    @Override
    public int getItemCount() {
        return monsters.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView nameView;
        TextView lvlView;
        TextView typeView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            nameView = itemView.findViewById(R.id.textView2);
            lvlView = itemView.findViewById(R.id.textViewLvl);
            typeView = itemView.findViewById(R.id.textViewType);
        }
    }
}
