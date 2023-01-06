package com.example.game;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.game.databases.Monster;
import com.example.game.screens.MainMenu;

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
        health.setText(String.valueOf(monster.health)+"/"+String.valueOf(monster.maxhealth + 8 *monster.level));
        attack.setText(String.valueOf(monster.attack + 8 *monster.level));
        defense.setText(String.valueOf(monster.defense + 8 *monster.level));
        xp.setMax(250*monster.level);
        xp.setProgress(monster.xp);

        Toolbar toolbar = v.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_test);
        Menu menu = toolbar.getMenu();
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {

                case R.id.back:
                    // Navigate to settings screen

                    my_monsters my_monsters = new my_monsters();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, my_monsters).commit();
                    return true;

                //case R.id.save:
                    //monster.name = String.valueOf(name.getText());

                    //funçao para atualizar o nome na BD

                    //my_monsters mon = new my_monsters();
                    //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mon).commit();
                    //return true;



                default:
                    return false;
            }
        });


        return v;
    }
}
