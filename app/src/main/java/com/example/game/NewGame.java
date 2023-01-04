package com.example.game;

import static com.example.game.SharedViewModel.NUMBER_OF_MAP_COLUMNS;
import static com.example.game.SharedViewModel.NUMBER_OF_MAP_ROWS;
import static com.example.game.SharedViewModel.TILESIZE;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.game.databases.MonsterDex;
import com.example.game.graphics.Sprite;
import com.example.game.graphics.SpriteSheet;
import com.example.game.maps.TestMap;

import java.io.ByteArrayOutputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewGame#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewGame extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Variables
    private SharedViewModel viewModel;
    private Button button;
    private Sprite[][] monsterSprite;
    private SpriteSheet waterMonsterSpriteSheet;
    private String[] monsterNames = {
            "Crab", "Angry Crab", "Monster Crab",
            "Ocean Bear", "Ocean Bearsaur", "Oceanzilla",
            "Ocean Mutant", "Giant Ocean Mutant", "Mega Mutant",
            "Shark", "Shark Monster", "Shark Monstrosity",
    };

    public NewGame() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewGame.
     */
    // TODO: Rename and change types and number of parameters
    public static NewGame newInstance(String param1, String param2) {
        NewGame fragment = new NewGame();
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
        View v = inflater.inflate(R.layout.fragment_new_game, container, false);


        button = v.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {        // Load Monsters to MonsterDex Database

                // Build SpriteSheet and Sprite Array
                waterMonsterSpriteSheet = viewModel.getWaterMonsterSpriteSheet();
                monsterSprite = new Sprite[4][3];       // Numero de Water Monsters Atualmente
                buildSpriteArray(4, 3);

                // Build Bitmaps
                Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
                String evolvesFrom = null;
                for (int iRow = 0; iRow < 4; iRow++) {
                    for (int iCol = 0; iCol < 3; iCol++) {
                        Bitmap bitmap = Bitmap.createBitmap(TILESIZE, TILESIZE, conf); // this creates a MUTABLE bitmap
                        Canvas mapCanvas = new Canvas(bitmap);
                        mapCanvas.drawBitmap(monsterSprite[iRow][iCol].getSpriteBitmap(), null, new Rect(0,0,256,256), null);
                        // Transform to bit Array
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                        // Create new Monster and Insert into Database
                        MonsterDex newMonster = new MonsterDex();
                        newMonster.name = monsterNames[iRow*3+iCol];
                        newMonster.type = "water";
                        newMonster.health = 100;
                        newMonster.attack = 100;
                        newMonster.defense = 100;
                        newMonster.isBoss = false;
                        newMonster.bArray = bos.toByteArray();
                        newMonster.rarity = "common";
                        newMonster.evolution = iCol+1;
                        if(iCol == 0){                              // Se for o primeiro da linha é evolução 1
                            newMonster.evolvesFrom = "base";
                        }else{
                            newMonster.evolvesFrom = evolvesFrom;   // Adiciona o monstro do qual evolui
                        }
                        viewModel.createNewMonster(newMonster);
                        evolvesFrom = monsterNames[iRow+iCol];
                    }
                }


                TestMap fragment = new TestMap();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

                //my_monsters fragment = new my_monsters();
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

    public void buildSpriteArray(int numberOfRows, int numberOfColumns){

        // Build Map with Sprites
        for (int iRow = 0; iRow < numberOfRows; iRow++) {
            for (int iCol = 0; iCol < numberOfColumns; iCol++) {
                // get corresponding sprite
                //if (iRow == 0){
                    monsterSprite[iRow][iCol] = waterMonsterSpriteSheet.getMonsterTile(iRow*3+iCol+1);
                //}else if (iRow == 1){
                //    monsterSprite[iRow][iCol] = waterMonsterSpriteSheet.getMonsterTile(iRow+iCol+3);
                //}else if (iRow == 2){
                //    monsterSprite[iRow][iCol] = waterMonsterSpriteSheet.getMonsterTile(iRow+iCol+7);
                //}else{
                //    monsterSprite[iRow][iCol] = waterMonsterSpriteSheet.getMonsterTile(iRow+iCol+10);
                //}

            }
        }
    }
}