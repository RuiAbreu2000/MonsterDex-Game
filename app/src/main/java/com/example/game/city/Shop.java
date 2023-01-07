package com.example.game.city;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.game.R;
import com.example.game.maps.MainCity;
import com.example.game.screens.MainMenu;

public class Shop extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shop, container, false);

        TextView comingSoonText = v.findViewById(R.id.coming_soon_text);
        comingSoonText.setText("Coming soon");

        Toolbar toolbar = v.findViewById(R.id.toolbar2);
        toolbar.inflateMenu(R.menu.menu_test);
        Menu menu = toolbar.getMenu();
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {

                case R.id.back:
                    // Navigate to settings screen

                    MainCity mainCity = new MainCity();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mainCity).commit();
                    return true;

                default:
                    return false;
            }
        });

        return v;
    }
}