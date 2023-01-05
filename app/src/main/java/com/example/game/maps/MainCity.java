package com.example.game.maps;

import static com.example.game.SharedViewModel.NUMBER_OF_MAP_COLUMNS;
import static com.example.game.SharedViewModel.NUMBER_OF_MAP_ROWS;
import static com.example.game.SharedViewModel.TILESIZE;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.game.Arena;
import com.example.game.Battle;
import com.example.game.Loja;
import com.example.game.MainActivity;
import com.example.game.R;
import com.example.game.SharedViewModel;
import com.example.game.maps.Map1;
import com.example.game.maps.Map2;

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

        // Build map
        viewModel.getMap("home");

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
        int touchX = (int) event.getRawX();
        int touchY = (int) event.getRawY();
        int imageX = touchX - posXY[0]; // posXY[0] is the X coordinate
        int imageY = touchY - posXY[1]; // posXY[1] is the y coordinate
        int idxRow = (int)( imageY/TILESIZE);
        int idxCol = (int)( imageX/TILESIZE);
        float tile = idxRow*NUMBER_OF_MAP_COLUMNS+idxCol;


        if( tile == 0){               // Co-Op Boss Battle
            Log.w("texto", "Co-Op");

        }else if( tile == 1 ){      // Go Left
            Log.w("texto", "Shop");

        }else if( tile == 2 ){      // Go Left
            Log.w("texto", "Arena");

        }else if( tile == 3 ) {      // Go Left
            Log.w("texto", "Trade");

        }else if( tile == 8 ){      // Go Left
            Log.w("texto", "Left Map");
            ZoneSelection_1 zone_1 = new ZoneSelection_1();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, zone_1).commit();

        }else if( tile == 18 ){      // Go Left
            Log.w("texto", "Hospital");

        }
        //else if( tile == 14 ){      // Go Left
        //    Log.w("texto", "Right Map");

        //}

        return false;
    }
}