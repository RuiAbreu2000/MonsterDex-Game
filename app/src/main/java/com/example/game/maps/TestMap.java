package com.example.game.maps;

import static com.example.game.SharedViewModel.NUMBER_OF_MAP_COLUMNS;
import static com.example.game.SharedViewModel.NUMBER_OF_MAP_ROWS;
import static com.example.game.SharedViewModel.TILESIZE;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.game.R;
import com.example.game.SharedViewModel;
import com.example.game.graphics.Sprite;

import androidx.lifecycle.ViewModelProviders;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TestMap#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestMap extends Fragment implements View.OnTouchListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Variables
    private SharedViewModel viewModel;
    private Canvas mapCanvas;
    private ImageView image;
    int[] posXY;
    private int[] tileIndexArray;
    private Bitmap bitmap;
    private int[][] monsterArray;

    public TestMap() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestMap.
     */
    // TODO: Rename and change types and number of parameters
    public static TestMap newInstance(String param1, String param2) {
        TestMap fragment = new TestMap();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_test_map, container, false);
        image = v.findViewById(R.id.MapHolder);

        // Build map
        viewModel.getMap("grass");
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
        int idxRow = imageY/TILESIZE;
        int idxCol = imageX/TILESIZE;
        int tile = idxRow*NUMBER_OF_MAP_COLUMNS+idxCol;
        Log.w("a", String.valueOf(tile));
        if(monsterArray[idxRow][idxCol] > 0){
            // BATTLE SCREEN TO CAPTURE MONSTER
            Log.w("a", "BATTLE TIME");
        }




        return false;
    }
}