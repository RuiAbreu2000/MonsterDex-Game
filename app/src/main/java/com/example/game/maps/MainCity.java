package com.example.game.maps;

import static com.example.game.SharedViewModel.NUMBER_OF_MAP_COLUMNS;
import static com.example.game.SharedViewModel.TILESIZE;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.game.city.BattleCOOPboss;
import com.example.game.city.BattlePVP;
import com.example.game.screens.MainMenu;
import com.example.game.R;
import com.example.game.SharedViewModel;
import com.example.game.TradingMon;
import com.example.game.threads.Task_Manager;

public class MainCity extends Fragment implements View.OnTouchListener{

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
        viewModel.setLevelTo_1();
        // Build map
        viewModel.getMap("home");

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
        int touchX = (int) event.getRawX();
        int touchY = (int) event.getRawY();
        int imageX = touchX - posXY[0]; // posXY[0] is the X coordinate
        int imageY = touchY - posXY[1]; // posXY[1] is the y coordinate
        int idxRow = (int)( imageY/TILESIZE);
        int idxCol = (int)( imageX/TILESIZE);
        float tile = idxRow*NUMBER_OF_MAP_COLUMNS+idxCol;


        if( tile == 0){               // Co-Op Boss Battle
            Log.w("texto", "Co-Op");
            BattleCOOPboss battleCOOPboss = new BattleCOOPboss();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, battleCOOPboss).commit();
        }else if( tile == 1 ){      // Shop
            Log.w("texto", "Shop");


        }else if( tile == 2 ){      // Arena
            BattlePVP battlePVP = new BattlePVP();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, battlePVP).commit();

        }else if( tile == 3 ) {      // Trade
            TradingMon tradingMon = new TradingMon();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, tradingMon).commit();

        }else if( tile == 8 ){      // Go Left
            ZoneSelection_1 zone_1 = new ZoneSelection_1();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, zone_1).commit();

        }else if( tile == 18 ){      // Hospital
            // Dialog box to Heal Monsters
            AlertDialog.Builder alertDialogBuilder  = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setMessage("Heal Monsters?");
            alertDialogBuilder.setPositiveButton("Heal", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // Heal

                }
            });
            alertDialogBuilder.setNegativeButton("Leave", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Leave
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }
        //else if( tile == 14 ){      // Go Left
        //    Log.w("texto", "Right Map");

        //}

        return false;
    }

    public void createTask(int op){
        Task_Manager taskManager = new Task_Manager();
        taskManager.executeAsync(viewModel.getDatabase(), op);
    }
}