package com.example.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.example.game.databases.Monster;
import com.example.game.graphics.Sprite;
import com.example.game.graphics.SpriteSheet;
import com.example.game.screens.MainMenu;

import java.util.ArrayList;
import java.util.List;

public class Backpack extends Fragment implements RecyclerViewInterface3{
    private ArrayList<monster_class> monster = new ArrayList<>();
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

        View v = inflater.inflate(R.layout.fragment_backpack, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.monsters);

        setMonsters();

        recycler_view_adapter3 adapter = new recycler_view_adapter3(((MainActivity)getActivity()), monster, this::onItemClick);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(((MainActivity)getActivity())));

        Toolbar toolbar = v.findViewById(R.id.toolbar);
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
        List<Monster> monsters = viewModel.getDatabase().monsterDao().getAllMonsters(); // fazer em thread
        Log.w("texto", String.valueOf(monsters));

        for (int i=0;i<monsters.size();i++){
            Monster m = monsters.get(i);
            Bitmap bitmap = BitmapFactory.decodeByteArray(m.bArray, 0, m.bArray.length);
            monster.add(new monster_class(m.id, bitmap, m.name, String.valueOf(m.level), m.type));
        }
    }

    @Override
    public void onItemClick(int position) {
        //Log.w("texto", "CREATING ");
        //Log.w("texto", String.valueOf(monster.get(position).getId()));

        viewModel.setCurrentMonster2(monster.get(position).getId());
        viewModel.changeMonsterChange();
        // Go Back
        //getActivity().getSupportFragmentManager().beginTransaction().detach(this).attach(this).commit();
        Fragment goBack = viewModel.getLastFragment();
        goBack.onResume();
        //TestMap fragment = new TestMap();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, goBack, "BATTLETAG").commit();
    }
}