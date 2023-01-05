package com.example.game;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.game.databases.Monster;
import com.example.game.databases.MonsterDex;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

public class Battle extends Fragment {
    private SharedViewModel viewModel;
    // variables to represent the characters and their stats
    // object to represent the character
    private class Character {
        // variables to represent the character's stats
        int health;
        int attack;
        int defense;
        String type;
        byte[] bArray;

        public Character(int health, int attack, int defense, String type, byte[] image) {
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
    private Button attackButton2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_battle, container, false);

        SecureRandom random = new SecureRandom();
        byte[] idBytes = new byte[16];
        random.nextBytes(idBytes);
        String id = new BigInteger(1, idBytes).toString(16);

        MQTTHelper helper = new MQTTHelper(getActivity(), id, "battleaJunior");

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
        List<Monster> monsters = viewModel.getDatabase().monsterDao().getAllMonsters();
        int level_inimigo = 0;
        for (int i = 0;i< monsters.size();i++){
            level_inimigo = level_inimigo + monsters.get(i).level;
        }
        level_inimigo = (int) (level_inimigo / monsters.size());
        Monster m = monsters.get(0);

        // LOAD ENEMY
        MonsterDex enemy = viewModel.getRandomMonsterByType("water");


        // initialize the characters and their stats

        player1 = new Character(m.health*m.level, m.attack*m.level, m.defense*m.level, m.type, m.bArray); //player mon
        player2 = new Character(enemy.health*level_inimigo, enemy.attack*level_inimigo, enemy.defense*level_inimigo, enemy.type, enemy.bArray); //mon he is fighting


        // initialize the views to display the characters' stats
        player1HealthBar = v.findViewById(R.id.progressBar_aliado);
        player2HealthBar = v.findViewById(R.id.progressBar_inimigo);
        player1image = v.findViewById(R.id.imageView3);
        player2image = v.findViewById(R.id.imageView2);
        player1Label = v.findViewById(R.id.textView_aliado);
        player2Label = v.findViewById(R.id.textView_inimigo);
        attackButton = v.findViewById(R.id.button_ataque);
        //attackButton2 = v.findViewById(R.id.attack_button2);

        // display the initial values for the characters' stats
        player1HealthBar.setMax(player1.health);
        player1HealthBar.setProgress(player1.health);
        player1image.setImageBitmap(BitmapFactory.decodeByteArray(player1.bArray, 0, player1.bArray.length));
        player2HealthBar.setMax(player2.health);
        player2HealthBar.setProgress(player2.health);
        player2image.setImageBitmap(BitmapFactory.decodeByteArray(player2.bArray, 0, player2.bArray.length));


        // Create the handler for updating the UI
        mHandler = new Handler();

        //attackButton2.setVisibility(View.GONE); // Im hiding the player 2 button because im using player vs AI monster

        // Create the game thread
        mGameThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Main game loop

                // CUIDADO PARA NAO ALTERAR O ATAQUE NA BASE DE DADOS DO MONSTRO, ALTERA SÓ DURANTE O COMBATE!
                if (temp >= 35){

                    ((MainActivity)getActivity()).noti("Its hot...", "Ambiente quente, monstros de água estão enfraquecidos!");

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
                    // Check for user input

                    // Wait for player 1 to attack
                    attackButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (player1Turn) {
                                player2.health -= player1.attack;

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

                    // Este bloco simula batalha entre 2 players em que cada um tem o seu botao....
                    // Wait for player 2 to attack
                    /*attackButton2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (player2Turn) {
                                player1.health -= player2.attack;

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
                        }
                    });*/

                    //Aqui é como se tivessemos a lutar contra uma AI

                    if (player2Turn) {

                        // Pause the game loop for a short time
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // Aqui vai ser a "AI" que escolhe o ataque que vai usar
                        player1.health -= player2.attack;

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
                            toast = Toast.makeText(getContext(), "Victory!", Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            toast = Toast.makeText(getContext(), "Defeat!", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        // Go Back To Map
                        helper.stop();
                        Fragment goBack = viewModel.getLastFragment();
                        goBack.onResume();
                        //TestMap fragment = new TestMap();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, goBack).commit();
                    }
                });

            }
        });




    return v;

    }
    public void decisionPopup(int id){

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
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

}