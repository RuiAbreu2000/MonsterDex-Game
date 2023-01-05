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

import com.example.game.maps.TestMap;

public class MainMenu extends Fragment {

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
                NewGame fragment = new NewGame();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });

        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestMap testMap = new TestMap();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, testMap).commit();
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