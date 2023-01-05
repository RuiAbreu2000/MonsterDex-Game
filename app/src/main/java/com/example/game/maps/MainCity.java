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
    private Canvas mapCanvas;
    private ImageView image;
    int[] posXY;
    private int[] tileIndexArray;
    private Bitmap bitmap;
    private int[][] monsterArray;
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
        // Get Tile Matrix
        tileIndexArray = new int[NUMBER_OF_MAP_COLUMNS*NUMBER_OF_MAP_ROWS];
        tileIndexArray = viewModel.getTileMatrix().clone();
        // Get Monster Matrix
        monsterArray = viewModel.getMonsterArray().clone();
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
        float screenTileSize = actW/NUMBER_OF_MAP_ROWS;
        int idxRow = (int) (imageY/screenTileSize);
        int idxCol = (int) (imageX/screenTileSize);
        float tile = idxRow*NUMBER_OF_MAP_COLUMNS+idxCol;


        if( imageX > 0 && imageX < 256 ){        // First Column
            if( imageY > 0 && imageY < 256){               // Co-Op Boss Battle
                Log.w("texto", "Co-Op");

            } else if( imageY > 512 && imageY < 768 ){      // Go Left
                Log.w("texto", "Left Map");

            }
        }else if( imageX > 256 && imageX < 512 ){   // Second Column
            if( imageY > 0 && imageY < 256){               // Shop
                Log.w("texto", "Shop");

            }
        }
        else if( imageX > 512 && imageX < 768 ){   // Third Column
            if( imageY > 0 && imageY < 256){               // Arena (Versus)
                Log.w("texto", "Arena");

            } else if( imageY > 512 && imageY < 1280 ){      // Hospital
                Log.w("texto", "Hospital");

            }
        }else if( imageX > 1024 && imageX < 1280 ) {   // Fifth Column
            if (imageY > 0 && imageY < 256) {               // Trade
                Log.w("texto", "Trade");

            } else if (imageY > 512 && imageY < 768) {      // Go Right
                Log.w("texto", "Go Right");
            }
        }
        return false;
    }
}