package com.example.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.game.databases.Monster;
import com.example.game.databases.MonsterDex;
import com.example.game.graphics.Sprite;
import com.example.game.graphics.SpriteSheet;

import java.util.ArrayList;
import java.util.List;

public class my_monsters extends Fragment {
    ArrayList<monster_class> monster = new ArrayList<>();
    private SharedViewModel viewModel;
    private Sprite monsterSprite;
    private SpriteSheet waterMonsterSpriteSheet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
    }


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
        List<Monster> monsters = viewModel.getDatabase().monsterDao().getAllMonsters();


        for (int i=0;i<monsters.size();i++){
            Monster m = monsters.get(i);
            Bitmap bitmap = BitmapFactory.decodeByteArray(m.bArray, 0, m.bArray.length);
            monster.add(new monster_class(bitmap, m.name));
        }
    }
}
