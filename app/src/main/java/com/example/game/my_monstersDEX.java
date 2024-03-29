package com.example.game;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.example.game.databases.MonsterDex;
import com.example.game.graphics.Sprite;
import com.example.game.graphics.SpriteSheet;
import com.example.game.screens.MainMenu;

import java.util.ArrayList;
import java.util.List;

public class my_monstersDEX extends Fragment {
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

        View v = inflater.inflate(R.layout.fragment_my_monsterdex, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.monsters);

        setMonsters();

        recycler_view_adapter adapter = new recycler_view_adapter(((MainActivity)getActivity()), monster);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(((MainActivity)getActivity())));




        Toolbar toolbar = v.findViewById(R.id.toolbar_home);
        toolbar.inflateMenu(R.menu.menu_test);
        Menu menu = toolbar.getMenu();
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {

                case R.id.back:
                    // Navigate to settings screen

                    MainMenu menuback = new MainMenu();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, menuback).commit();
                    return true;

                default:
                    return false;
            }
        });


        return v;
    }

    private void setMonsters() {
        List<MonsterDex> monsters = viewModel.getDatabase().monsterDexDao().getAllMonsters();


        for (int i=0;i<monsters.size();i++){
            MonsterDex m = monsters.get(i);
            Bitmap bitmap = BitmapFactory.decodeByteArray(m.bArray, 0, m.bArray.length);
            monster.add(new monster_class(bitmap, m.name));
        }
    }
}
