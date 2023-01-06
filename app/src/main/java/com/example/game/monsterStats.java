package com.example.game;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.game.databases.Monster;

public class monsterStats extends Fragment {
    private SharedViewModel viewModel;
    private Monster monster;
    private int id;
    private ImageView image;
    private EditText name;
    private TextView level;
    private TextView health;
    private TextView attack;
    private TextView defense;
    private ProgressBar xp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_monster_stats, container, false);
        
        id = viewModel.getCurrentMonster();

        monster = viewModel.getMonsterById(id);

        image = v.findViewById(R.id.imageView4);
        name = v.findViewById(R.id.editTextTextPersonName);
        level = v.findViewById(R.id.textViewLevel);
        health = v.findViewById(R.id.textViewHealth);
        attack = v.findViewById(R.id.textViewAttack);
        defense = v.findViewById(R.id.textViewDefense);
        xp = v.findViewById(R.id.progressBarXp);


        image.setImageBitmap(BitmapFactory.decodeByteArray(monster.bArray, 0, monster.bArray.length));
        name.setText(monster.name);
        level.setText(String.valueOf(monster.level));
        health.setText(String.valueOf(monster.health));
        attack.setText(String.valueOf(monster.attack));
        defense.setText(String.valueOf(monster.defense));
        xp.setMax(500);
        xp.setProgress(monster.xp);
        

        return v;
    }
}
