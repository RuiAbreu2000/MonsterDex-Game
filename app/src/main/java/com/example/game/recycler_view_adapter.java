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

public class recycler_view_adapter extends RecyclerView.Adapter<recycler_view_adapter.MyViewHolder> {
    Context context;
    ArrayList<monster_class> monsters;

    private static OnItemClickListener listener;

    public recycler_view_adapter(Context context, ArrayList<monster_class> monsters){
        this.context = context;
        this.monsters = monsters;

    }

    // In the adapter class
    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public recycler_view_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycle_view_row, parent, false);
        return new recycler_view_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recycler_view_adapter.MyViewHolder holder, int position) {
        holder.nameView.setText(monsters.get(position).getName());
        holder.imageView.setImageBitmap(monsters.get(position).getImage());
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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            nameView = itemView.findViewById(R.id.textView2);

            // In the MyViewHolder class
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
