package com.example.game.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.game.R;
import com.example.game.SharedViewModel;
import com.example.game.my_monsters;
import com.example.game.my_monstersDEX;

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

                NewGame fragment = new NewGame();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });

        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_monsters monsters = new my_monsters();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, monsters).commit();
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