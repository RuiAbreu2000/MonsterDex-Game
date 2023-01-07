package com.example.game.maps;

import static com.example.game.SharedViewModel.NUMBER_OF_MAP_COLUMNS;
import static com.example.game.SharedViewModel.TILESIZE;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.game.R;
import com.example.game.SharedViewModel;
import com.example.game.screens.MainMenu;


public class ZoneSelection_2 extends Fragment implements View.OnTouchListener{

    // Variables
    private SharedViewModel viewModel;
    private ImageView image;
    int[] posXY;
    private Bitmap bitmap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_main_city, container, false);
        image = v.findViewById(R.id.MapHolder);

        // Build map
        viewModel.getMap("zoneSelection_2");

        // Get Bitmap
        bitmap = viewModel.getBitmap();

        // Save on screen map coordinates and set listener
        image.setImageBitmap(bitmap);
        posXY = new int[2];
        image.getLocationOnScreen(posXY);
        image.setOnTouchListener(this);


        Toolbar toolbar = v.findViewById(R.id.toolbar2);
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


        // Inflate the layout for this fragment
        return v;
    }

    // Image Touch Screen and calculates which Tile was clicked
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int touchX = (int) event.getX();
        int touchY = (int) event.getY();
        int imageX = touchX - posXY[0]; // posXY[0] is the X coordinate
        int imageY = touchY - posXY[1]; // posXY[1] is the y coordinate
        int idxRow = (int) (imageY/TILESIZE);
        int idxCol = (int) (imageX/TILESIZE);
        float tile = idxRow*NUMBER_OF_MAP_COLUMNS+idxCol;


        if( tile == 11 ){      // Right Arrow
            //Log.w("texto", "Right Arrow");
            ZoneSelection_1 zone_1 = new ZoneSelection_1();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, zone_1).commit();

        }else if( tile == 17 ) {      // Random Dungeon
            //Log.w("texto", "Random Dungeon");
            RandomDungeon randomDungeon = new RandomDungeon();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, randomDungeon).commit();
        }else if( tile == 8 ) {      // Go Left
            //Log.w("texto", "Left Arrow");
            ZoneSelection_3 zone_3 = new ZoneSelection_3();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, zone_3).commit();
        }
        return false;
    }


}