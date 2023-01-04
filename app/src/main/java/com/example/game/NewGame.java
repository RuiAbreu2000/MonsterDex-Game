package com.example.game;

import static com.example.game.SharedViewModel.NUMBER_OF_MAP_COLUMNS;
import static com.example.game.SharedViewModel.NUMBER_OF_MAP_ROWS;
import static com.example.game.SharedViewModel.TILESIZE;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;

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
    private Button button1;
    private Button button2;
    private RecyclerView recyclerView;
    private Sprite[][] monsterSprite;
    private SpriteSheet waterMonsterSpriteSheet;
    Bitmap[] monster = new Bitmap[3];
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_new_game, container, false);


        button = v.findViewById(R.id.button);
        button1 = v.findViewById(R.id.button1);
        button2 = v.findViewById(R.id.button2);

        recyclerView = v.findViewById(R.id.monsters2);

        setMonsters();

        recycler_view_adapter2 adapter = new recycler_view_adapter2(((MainActivity)getActivity()), monster);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(((MainActivity)getActivity())));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                
                continuarButton();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                continuarButton();
                }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                continuarButton();
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

    private void continuarButton(){
        TestMap fragment = new TestMap();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

        //my_monsters fragment = new my_monsters();
        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }
    private void setMonsters() {


        List<MonsterDex> monsters = viewModel.getDatabase().monsterDexDao().getAllMonsters();


        for (int i=0;i<3;i++){
            MonsterDex m = monsters.get(i*3);
            Bitmap bitmap = BitmapFactory.decodeByteArray(m.bArray, 0, m.bArray.length);
            monster[i] = bitmap;
        }
    }


}