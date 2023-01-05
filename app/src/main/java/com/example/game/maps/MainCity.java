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
    private float[] displaymatrix = new float[9];
    private int actW, actH;

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

        // Get Display Image Settings
        image.getImageMatrix().getValues(displaymatrix);
        // Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)
        final float scaleX = displaymatrix[Matrix.MSCALE_X];
        final float scaleY = displaymatrix[Matrix.MSCALE_Y];
        // Calculate the actual dimensions
        actW = Math.round(NUMBER_OF_MAP_COLUMNS*TILESIZE * scaleX);
        actH = Math.round(NUMBER_OF_MAP_COLUMNS*TILESIZE * scaleY);

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
        float screenTileSizeW = actW/NUMBER_OF_MAP_ROWS;
        float screenTileSizeH = actH/NUMBER_OF_MAP_ROWS;
        int idxRow = (int) (imageY/screenTileSizeH);
        int idxCol = (int) (imageX/screenTileSizeW);
        float tile = idxRow*NUMBER_OF_MAP_COLUMNS+idxCol;

        // Print Coords
        Log.w("texto", "Coords");
        Log.w("texto", String.valueOf(idxRow));
        Log.w("texto", String.valueOf(idxCol));
        Log.w("texto", String.valueOf(tile));


        if( tile == 0){               // Co-Op Boss Battle
            Log.w("texto", "Co-Op");

        } else if( tile == 10 ){      // Go Left
            Log.w("texto", "Left Map");

        }else if( tile == 22 ){      // Go Left
            Log.w("texto", "Hospital");

        }
        else if( tile == 14 ){      // Go Left
            Log.w("texto", "Right Map");

        }

        return false;
    }
}