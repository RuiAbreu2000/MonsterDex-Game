package com.example.game;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class my_monsters extends Fragment {
    ArrayList<monster_class> monster = new ArrayList<>();
    int [] monstersImage = {R.drawable.water, R.drawable.water, R.drawable.water, R.drawable.water, R.drawable.water, R.drawable.water, R.drawable.water, R.drawable.water, R.drawable.water, R.drawable.water};
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_my_monster, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.monsters);

        setMonsters();

        recycler_view_adapter adapter = new recycler_view_adapter(((MainActivity)getActivity()), monster);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(((MainActivity)getActivity())));


        return v;
    }

    private void setMonsters() {
        for (int i=0;i<10;i++){
            monster.add(new monster_class(monstersImage[i], Integer.toString(i)));
        }
    }
}
