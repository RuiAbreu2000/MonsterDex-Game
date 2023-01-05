package com.example.game;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.game.databases.Monster;
import com.example.game.maps.MainCity;
import com.example.game.maps.TestMap;

import java.util.List;

public class MainMenu extends Fragment {
    private SharedViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_main_menu, container, false);

        Button startGameButton = v.findViewById(R.id.button_start_game);
        Button button_continue = v.findViewById(R.id.button_continue);
        Button button_options = v.findViewById(R.id.button_options);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Monster> monsters = viewModel.getDatabase().monsterDao().getAllMonsters();

                if (monsters.size()==0){
                    NewGame fragment = new NewGame();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                }else{
                    MainCity mainCity = new MainCity();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mainCity).commit();
                }

            }
        });

        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_monsters my_monsters = new my_monsters();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, my_monsters).commit();
            }
        });

        button_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_monstersDEX my_monsters = new my_monstersDEX();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, my_monsters).commit();
            }
        });

        return v;
    }
}