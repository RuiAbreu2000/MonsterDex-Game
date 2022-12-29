package com.example.game.maps;

import static com.example.game.SharedViewModel.NUMBER_OF_MAP_COLUMNS;
import static com.example.game.SharedViewModel.NUMBER_OF_MAP_ROWS;
import static com.example.game.SharedViewModel.TILESIZE;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.game.R;
import com.example.game.SharedViewModel;
import com.example.game.TileRelated.Sprite;

import androidx.lifecycle.ViewModelProviders;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TestMap#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestMap extends Fragment {

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
        image = (ImageView)v.findViewById(R.id.MapHolder);
        // Get Map Bitmap
        int current_x = 0;
        int current_y = 0;
        Sprite[][] mapSprite = viewModel.getTestMap();

        // Create bitmap and canvas to draw on it
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        Bitmap bitmap = Bitmap.createBitmap(NUMBER_OF_MAP_ROWS*TILESIZE, NUMBER_OF_MAP_COLUMNS*TILESIZE, conf); // this creates a MUTABLE bitmap
        mapCanvas = new Canvas(bitmap);

        // Add all sprite bitmaps to canvas
        for (int iRow = 0; iRow < NUMBER_OF_MAP_ROWS; iRow++) {
            for (int iCol = 0; iCol < NUMBER_OF_MAP_COLUMNS; iCol++) {
                mapCanvas.drawBitmap(mapSprite[iRow][iCol].getSpriteBitmap(), current_x, current_y, null);
                current_x += current_x;
            }
            current_y += TILESIZE;
            current_x = 0;
        }

        image.setImageBitmap(bitmap);


        // Inflate the layout for this fragment
        return v;
    }
}