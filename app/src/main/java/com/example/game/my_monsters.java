package com.example.game;

import static com.example.game.SharedViewModel.TILESIZE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.game.graphics.Sprite;
import com.example.game.graphics.SpriteSheet;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class my_monsters extends Fragment {
    ArrayList<monster_class> monster = new ArrayList<>();
    private SharedViewModel viewModel;
    private Sprite monsterSprite;
    private SpriteSheet waterMonsterSpriteSheet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_my_monster, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.monsters);

        setMonsters();

        recycler_view_adapter adapter = new recycler_view_adapter(((MainActivity)getActivity()), monster);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(((MainActivity)getActivity())));


        return v;
    }

    private void setMonsters() {
        waterMonsterSpriteSheet = viewModel.getWaterMonsterSpriteSheet();
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = Bitmap.createBitmap(TILESIZE, TILESIZE, conf); // this creates a MUTABLE bitmap
        Canvas mapCanvas = new Canvas(bitmap);
        monsterSprite = waterMonsterSpriteSheet.getMonsterTile(2);
        mapCanvas.drawBitmap(monsterSprite.getSpriteBitmap(), null, new Rect(0,0,0,0), null);


        for (int i=0;i<12;i++){
            monster.add(new monster_class(bitmap, Integer.toString(i)));
        }
    }
}
