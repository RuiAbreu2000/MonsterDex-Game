package com.example.game.city;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.game.Backpack;
import com.example.game.MQTTHelper;
import com.example.game.MainActivity;
import com.example.game.R;
import com.example.game.SharedViewModel;
import com.example.game.databases.Monster;
import com.example.game.databases.MonsterDex;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Battle extends Fragment{
    private SharedViewModel viewModel;
    private int enemy_max_health;
    private Monster m;
    List<Monster> monsters;
    int new_id;
    int monster_change = 0;

    // variables to represent the characters and their stats
    // object to represent the character
    public class Character {
        // variables to represent the character's stats
        int health;
        int attack;
        int defense;
        String type;
        byte[] bArray;
        int level;
        int maxhealth;


        public Character(int health, int attack, int defense, String type, byte[] image, int level, int maxhealth) {
            this.health = health;
            this.attack = attack;
            this.defense = defense;
            this.type = type;
            this.bArray = image;
            this.level = level;
            this.maxhealth = maxhealth;
        }

        public void setValues(int health, int attack, int defense, String type, byte[] image){
            this.health = health;
            this.attack = attack;
            this.defense = defense;
            this.type = type;
            this.bArray = image;
        }

    }
    // Handler for updating the UI
    private Handler mHandler;

    // Thread for running the game loop
    private Thread mGameThread;

    // variables to represent the characters
    private Character player1;
    private Character player2;

    // variables to track whose turn it is
    private boolean player1Turn = true;
    private boolean player2Turn = false;
    private float temp = 25;
    private Toast toast;

    // views to display the characters' stats
    private ProgressBar player1HealthBar;
    private ProgressBar player2HealthBar;
    private ImageView player1image;
    private ImageView player2image;
    private TextView player1Label;
    private TextView player2Label;
    private Button attackButton;
    private Button runButton;
    private Button attackButton2;
    private Button backpackButton;
    private MQTTHelper helper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_battle2, container, false);


        if(viewModel.getCreated() == 1){
            // Get Monster
            new_id = viewModel.getCurrentMonster2();
            Monster m = viewModel.getMonsterById(new_id);
            player1 = new Character(m.health+8*m.level, m.attack+8*m.level, m.defense+8*m.level, m.type, m.bArray, 0, 0); //player mon
            player2 = viewModel.getCharacter();

            // initialize the views to display the characters' stats
            player1HealthBar = v.findViewById(R.id.progressBar_aliado);
            player2HealthBar = v.findViewById(R.id.progressBar_inimigo);
            player1image = v.findViewById(R.id.imageView3);
            player2image = v.findViewById(R.id.imageView2);
            player1Label = v.findViewById(R.id.textView_aliado);
            player2Label = v.findViewById(R.id.textView_inimigo);
            attackButton = v.findViewById(R.id.button_ataque);
            runButton = v.findViewById(R.id.button_fugir);
            //attackButton2 = v.findViewById(R.id.attack_button2);
            backpackButton = v.findViewById(R.id.button_monstros);
            // display the initial values for the characters' stats
            player1HealthBar.setMax(m.maxhealth+8*m.level);
            player1HealthBar.setProgress(player1.health);
            player1image.setImageBitmap(BitmapFactory.decodeByteArray(player1.bArray, 0, player1.bArray.length));
            player2HealthBar.setMax(player2.maxhealth+8*player2.level);
            player2HealthBar.setProgress(player2.health);
            player2image.setImageBitmap(BitmapFactory.decodeByteArray(player2.bArray, 0, player2.bArray.length));
            return v;
        }

        SecureRandom random = new SecureRandom();
        byte[] idBytes = new byte[16];
        random.nextBytes(idBytes);
        String id = new BigInteger(1, idBytes).toString(16);

        helper = new MQTTHelper(getActivity(), id, "battleaJunior");

        helper.connect();

        helper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                Log.w("TAG", "Connected!");

                helper.subscribeToTopic("tempJunior");

            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.w("TAG", "message: " + new String(message.getPayload()));
                Log.w("TAG", "topic: " + topic);

                if (topic.equals("tempJunior")){
                    float number = Float.parseFloat(new String(message.getPayload()));
                    temp = number;
                    mGameThread.start();
                }
            }


            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

        // LOAD PLAYER AND LOAD MONSTER HE IS FIGHTING FROM DATABASE
        monsters = viewModel.getDatabase().monsterDao().getAllMonsters();

        int level_inimigo = viewModel.getZoneLevel();
        int number = 0;
        m = monsters.get(number);
        number=number+1;
        while (m.health <= 0){
            m = monsters.get(number);
            if (m == null){
                m = monsters.get(0);
                //m.health=m.maxhealth;
            }
            number=number+1;
        }

        // LOAD ENEMY
        MonsterDex enemy = viewModel.getRandomMonsterByType(viewModel.getCurrentType());

        // initialize the characters and their stats

        player1 = new Character(m.health+8*m.level, m.attack+8*m.level, m.defense+8*m.level, m.type, m.bArray, 0, 0); //player mon
        player2 = new Character(enemy.health+4*level_inimigo, enemy.attack+4*level_inimigo, enemy.defense+4*level_inimigo, enemy.type, enemy.bArray, level_inimigo, enemy.health+4*level_inimigo+8*level_inimigo); //mon he is fighting
        enemy_max_health = player2.health+8*level_inimigo;

        // initialize the views to display the characters' stats
        player1HealthBar = v.findViewById(R.id.progressBar_aliado);
        player2HealthBar = v.findViewById(R.id.progressBar_inimigo);
        player1image = v.findViewById(R.id.imageView3);
        player2image = v.findViewById(R.id.imageView2);
        player1Label = v.findViewById(R.id.textView_aliado);
        player2Label = v.findViewById(R.id.textView_inimigo);
        attackButton = v.findViewById(R.id.button_ataque);
        runButton = v.findViewById(R.id.button_fugir);
        //attackButton2 = v.findViewById(R.id.attack_button2);
        backpackButton = v.findViewById(R.id.button_monstros);

        // display the initial values for the characters' stats
        player1HealthBar.setMax(m.maxhealth+8*m.level);
        player1HealthBar.setProgress(player1.health);
        player1image.setImageBitmap(BitmapFactory.decodeByteArray(player1.bArray, 0, player1.bArray.length));
        player2HealthBar.setMax(enemy_max_health);
        player2HealthBar.setProgress(player2.health);
        player2image.setImageBitmap(BitmapFactory.decodeByteArray(player2.bArray, 0, player2.bArray.length));


        // Create the handler for updating the UI
        mHandler = new Handler();

        // Create the game thread
        mGameThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Main game loop

                // CUIDADO PARA NAO ALTERAR O ATAQUE NA BASE DE DADOS DO MONSTRO, ALTERA SÓ DURANTE O COMBATE!
                if (temp >= 35){
                    Activity activity = getActivity();
                    if(activity != null) {
                        ((MainActivity) getActivity()).noti("Its hot...", "Ambiente quente, monstros de água estão enfraquecidos!");
                    }
                    if (player1.type.equals("water")){
                        player1.attack -= (int) Math.round(player1.attack * 0.1);
                    }
                    if (player2.type.equals("water")){
                        player2.attack -= (int) Math.round(player2.attack * 0.1);
                    }
                } else if (temp <= 5){

                    ((MainActivity)getActivity()).noti("Its cold...", "Ambiente frio, monstros de fogo estão enfraquecidos!");
                    if (player1.type.equals("fire")){
                        player1.attack -= (int) Math.round(player1.attack * 0.1);
                    }
                    if (player2.type.equals("fire")){
                        player2.attack -= (int) Math.round(player2.attack * 0.1);
                    }
                }

                Log.w("TAG", "p1atk " + player1.attack);
                Log.w("TAG", "p2atk: " + player2.attack);

                while (player1.health > 0 && player2.health > 0) {
                    viewModel.setCharacter(player2);
                    // Check for user input
                    if(viewModel.getMonsterChange() == 1){
                        // Get New Monster
                        new_id = viewModel.getCurrentMonster2();
                        Monster m = viewModel.getMonsterById(new_id);

                        // Save changes
                        viewModel.setHeal(player1.health, m.id);
                        viewModel.changeMonsterChange();

                        // update player object
                        player1.setValues(m.health+8*m.level, m.attack+8*m.level, m.defense+8*m.level, m.type, m.bArray);
                        player1image.setImageBitmap(BitmapFactory.decodeByteArray(player1.bArray, 0, player1.bArray.length));
                        player1HealthBar.setProgress(player1.health);

                    }
                    // Wait for player 1 to attack
                    attackButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (player1Turn) {

                                player2.health -= player1.attack - (int)(player1.attack*(player1.defense/m.maxhealth));

                                player1Turn = false;
                                player2Turn = true;

                                // Update the UI
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        player1HealthBar.setProgress(player1.health);
                                        player2HealthBar.setProgress(player2.health);
                                    }
                                });
                            }

                        }
                    });

                    runButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //getActivity().getSupportFragmentManager().beginTransaction().detach(this).attach(this).commit();
                            //Fragment goBack = viewModel.getLastFragment();
                            //goBack.onResume();
                            //TestMap fragment = new TestMap();
                            //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, goBack).commit();
                            viewModel.setCreatedZero();
                            getActivity().getSupportFragmentManager().popBackStackImmediate();
                        }
                    });
                    // Backpack Listener
                    backpackButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Log.w("texto", "ADDING FRAG");
                            viewModel.setCreatedOne();
                            Backpack backpack = new Backpack();
                            //viewModel.addFragment(getActivity().getSupportFragmentManager().findFragmentByTag("BATTLETAG"));

                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, backpack,"BATTLETAG").addToBackStack(null).commit();

                        }
                    });

                    //Aqui é como se tivessemos a lutar contra uma AI

                    if (player2Turn) {

                        // Pause the game loop for a short time
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // Aqui vai ser a "AI" que escolhe o ataque que vai usar
                        player1.health -= player2.attack - (int)(player2.attack*(player1.defense/m.maxhealth));
                        viewModel.setHeal(player1.health, m.id);

                        player1Turn = true;
                        player2Turn = false;

                        // Update the UI
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                player1HealthBar.setProgress(player1.health);
                                player2HealthBar.setProgress(player2.health);
                            }
                        });
                    }



                    // Pause the game loop for a short time
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }


                // Update the UI
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        player1HealthBar.setProgress(player1.health);
                        player2HealthBar.setProgress(player2.health);
                        // Print the winner
                        if (player1.health > 0) {
                            decisionPopup(enemy.id-1);

                        } else {
                            decisionPopup(-1);

                        }
                        // Go Back To Map
                        helper.stop();

                    }
                });

            }
        });




        return v;

    }
    public void decisionPopup(int id){

        if (id==-1){
            toast = Toast.makeText(getContext(), "Defeat!", Toast.LENGTH_SHORT);
            toast.show();
            //Fragment goBack = viewModel.getLastFragment();
            //goBack.onResume();
            //TestMap fragment = new TestMap();
            //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, goBack).commit();
            getActivity().getSupportFragmentManager().popBackStackImmediate();
        }
        else{
            toast = Toast.makeText(getContext(), "Victory!", Toast.LENGTH_SHORT);
            toast.show();
            // Dialog box
            AlertDialog.Builder alertDialogBuilder  = new AlertDialog.Builder(getActivity());

            alertDialogBuilder.setMessage("Caught or Consume Monster");

            alertDialogBuilder.setPositiveButton("Caught", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    viewModel.setMyMonster(id);

                }
            });

            alertDialogBuilder.setNegativeButton("Consume",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            // Refresh
            // refresh also List<Nota> notas and set to SharedModel
            viewModel.setCreatedZero();
            //getActivity().getSupportFragmentManager().beginTransaction().detach(this).attach(this).commit();
            //Fragment goBack = viewModel.getLastFragment();
            //goBack.onResume();
            //TestMap fragment = new TestMap();
            //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, goBack).commit();
            getActivity().getSupportFragmentManager().popBackStackImmediate();
        }

    }

}