package com.example.game.maps;

import static com.example.game.SharedViewModel.NUMBER_OF_MAP_COLUMNS;
import static com.example.game.SharedViewModel.TILESIZE;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.game.city.Battle;
import com.example.game.R;
import com.example.game.SharedViewModel;


public class FireDungeon extends Fragment implements View.OnTouchListener{

    // Variables
    private SharedViewModel viewModel;
    private ImageView image;
    int[] posXY;
    private Bitmap bitmap;
    private String[] typeArray;
    private int[][] monsterArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_water_dungeon, container, false);
        image = v.findViewById(R.id.MapHolder);

        // Build map
        viewModel.getMap("fireDungeon");
        // Get Monster Matrix
        monsterArray = viewModel.getMonsterArray().clone();

        // Get Bitmap
        bitmap = viewModel.getBitmap();

        // Save on screen map coordinates and set listener
        image.setImageBitmap(bitmap);
        posXY = new int[2];
        image.getLocationOnScreen(posXY);
        image.setOnTouchListener(this);


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

        Log.w("texto", "ROWS AND COLUMNS");
        Log.w("texto", String.valueOf(idxRow));
        Log.w("texto", String.valueOf(idxCol));
        Log.w("texto", String.valueOf(tile));

        if(tile == 17){     // Go Home
            MainCity maincity = new MainCity();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, maincity).commit();
        }else if(tile == 1){      // Go Forward
            if(viewModel.getLevel() == 20) {
                MainCity maincity = new MainCity();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, maincity).commit();
            }else {
                viewModel.incrementLevel();
                // Save Frament
                FireDungeon fireDungeon = new FireDungeon();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fireDungeon).commit();
            }
        }else if(monsterArray[idxRow][idxCol] > 0){
            // Save Fragment and Go to Battle Screen
            viewModel.setCurrentType((int) tile);
            Battle battle = new Battle();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, battle,"BATTLETAG").addToBackStack(null).commit();
        }


        return false;
    }
}