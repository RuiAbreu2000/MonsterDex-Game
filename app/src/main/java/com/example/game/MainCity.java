package com.example.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.game.maps.Map1;
import com.example.game.maps.Map2;

public class MainCity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_main_city, container, false);

        Button leftButton = v.findViewById(R.id.button_left);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the previous map
                ((MainActivity)getActivity()).showFragment(new Map1());
            }
        });

        Button rightButton = v.findViewById(R.id.button_right);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the next map
                ((MainActivity)getActivity()).showFragment(new Map2());
            }
        });

        Button arena = v.findViewById(R.id.button_arena);
        arena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the previous map
                ((MainActivity)getActivity()).showFragment(new Arena());
            }
        });

        Button loja = v.findViewById(R.id.button_loja);
        loja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the next map
                ((MainActivity)getActivity()).showFragment(new Loja());
            }
        });

        return v;
    }
}