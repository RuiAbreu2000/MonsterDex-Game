package com.example.game;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class BattleCOOPboss extends Fragment {
    // variables to represent the characters and their stats
    // object to represent the character
    private class Character {
        // variables to represent the character's stats
        int health;
        int attack;
        int defense;

        public Character(int health, int attack, int defense) {
            this.health = health;
            this.attack = attack;
            this.defense = defense;
        }
    }
    // Handler for updating the UI
    private Handler mHandler;

    // Thread for running the game loop
    private Thread mGameThread;


    // variables to represent the characters
    private Character player1;
    private Character player2;
    private Character Boss;

    private int player1HP = 1;
    private int player2HP = 1;
    private int BossHP = 1;

    // variables to track whose turn it is

    private boolean isConnected = true;
    private boolean imPlayer1 = false;
    private boolean imPlayer2 = false;
    //private boolean player1IsConnected = false;
    //private boolean player2IsConnected = false;
    private boolean player1Turn = true;
    private boolean player2Turn = false;
    private boolean BossTurn = false;
    private Toast toast;

    // views to display the characters' stats
    private ProgressBar player1HealthBar;
    private ProgressBar player2HealthBar;
    private ProgressBar BossHealthBar;
    private TextView player1Label;
    private TextView player2Label;
    private TextView Boss2Label;
    private Button attackButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_battle_c_o_o_pboss, container, false);

        SecureRandom random = new SecureRandom();
        byte[] idBytes = new byte[16];
        random.nextBytes(idBytes);
        String id = new BigInteger(1, idBytes).toString(16);

        // initialize the characters and their stats
        //player1 = new Character(1000, 90, 20);
        //player2 = new Character(1000, 100, 15);

        // initialize the views to display the characters' stats
        player1HealthBar = v.findViewById(R.id.progressBar_aliado);
        player2HealthBar = v.findViewById(R.id.progressBar_inimigo);
        BossHealthBar = v.findViewById(R.id.progressBar_Boss);
        player1Label = v.findViewById(R.id.textView_aliado);
        player2Label = v.findViewById(R.id.textView_inimigo);
        Boss2Label = v.findViewById(R.id.textView_Boss);
        attackButton = v.findViewById(R.id.button_ataque);
        Button joinmatch = v.findViewById(R.id.button_join);
        TextView gameActionsTextView = v.findViewById(R.id.game_actions_text_view);

        TextView gameActionsTextView2 = v.findViewById(R.id.game_actions_text_view2);
        gameActionsTextView.setText("Waiting for battle to start!");
        joinmatch.setVisibility(v.INVISIBLE);

        // display the initial values for the characters' stats
        //player1HealthBar.setProgress(player1.health);
        //player2HealthBar.setProgress(player2.health);
        attackButton.setEnabled(false);

        // Create the handler for updating the UI
        mHandler = new Handler();

        Log.w("TAG", "ID ========== !" + id);
        MQTTHelper helper = new MQTTHelper(getActivity(), id, "battleaJunior");

        helper.connect();


        joinmatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imPlayer2) {

                    String hp = Integer.toString(player2HP);
                    helper.publish("ConnectJunior", "Player2conn", 0, true);
                    helper.publish("GetHPJunior", hp, 0, true);
                    joinmatch.setVisibility(v.INVISIBLE);

                }

                if (imPlayer1) {

                    String hp = Integer.toString(player1HP);
                    helper.publish("ConnectJunior", "Player1conn", 0, true);
                    helper.publish("GetHPJunior", hp, 0, true);
                    joinmatch.setVisibility(v.INVISIBLE);

                }
            }
        });

        helper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                Log.w("TAG", "Connected!");
                if (isConnected) {
                    helper.subscribeToTopic("ConnectJunior");
                    helper.subscribeToTopic("GetHPJunior");
                    helper.subscribeToTopic("battleJunior");
                    isConnected = false;
                }
            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.w("TAG", "message: " + new String(message.getPayload()));
                Log.w("TAG", "topic: " + topic);

                if (message.isRetained()) {
                    Log.w("TAG", "message was retained" + topic);
                }

                if (topic.equals("ConnectJunior")) {

                    if (new String(message.getPayload()).equals("Gameover")){

                        imPlayer1 = true;

                        //LOAD PLAYER DATA FROM DATABASE WHEN ITS READY
                        player1 = new Character(900, 100, 20);

                        // Publish the message to notify player 1 is in
                        player1HealthBar.setMax(player1.health);
                        player1HealthBar.setProgress(player1.health);

                        player1Label.setText("Player 1 - " + player1.health);
                        player1HP = player1.health;



                        //String hp = Integer.toString(player1.health);
                        // Publish the message to the HP topic
                        //helper.publish("GetHPJunior", hp, 0, true);
                        //helper.publish("ConnectJunior", "Player1connected", 0, true);
                        //player1IsConnected = true;
                        joinmatch.setVisibility(v.VISIBLE);


                    } else if (new String(message.getPayload()).equals("Player1conn")){

                        if (!imPlayer1) { //IF IM PLAYER2
                            imPlayer2 = true;

                            //LOAD PLAYER DATA FROM DATABASE WHEN ITS READY
                            player2 = new Character(1000, 100, 20);

                            // Publish the message to notify player 1 is in
                            player2HealthBar.setMax(player2.health);
                            player2HealthBar.setProgress(player2.health);

                            player2Label.setText("Player 2 - " + player2.health);
                            player2HP = player2.health;

                            // Publish the message to notify player 2 is in
                            // String hp = Integer.toString(player2.health);
                            joinmatch.setVisibility(v.VISIBLE);
                            //helper.publish("ConnectJunior", "Player2connected", 0, false);


                            // Publish the message to the attack damage topic
                            //helper.publish("GetHPJunior", hp, 0, true);

                        }

                    } else if (new String(message.getPayload()).equals("Player2conn")){

                        //LOAD BOSS DATA FROM DATABASE WHEN ITS READY
                        Boss = new Character(2000, 100, 20);

                        BossHP = Boss.health;
                        BossHealthBar.setMax(Boss.health);
                        BossHealthBar.setProgress(Boss.health);

                        Boss2Label.setText("Boss - " + Boss.health);

                        helper.publish("ConnectJunior", "BossIsReady", 0, true);


                    } else if (new String(message.getPayload()).equals("BossIsReady")){
                        Log.w("TAG", "Entrei no loop" + topic);
                        Log.w("TAG", "player1hp= " + player1HP);
                        Log.w("TAG", "player2hp= " + player2HP);
                        Log.w("TAG", "BossHP= " + Boss.health);


                        if (imPlayer1) {
                            attackButton.setEnabled(true);
                        }

                        mGameThread.start();
                    }

                }

                if (topic.equals("GetHPJunior")) {

                    if (message.isRetained()) {
                        Log.w("TAG", "message was retained" + topic);
                        // The message is a retained message
                        if (!imPlayer1) { //IF ITS PLAYER 2
                            int number = Integer.parseInt(new String(message.getPayload()));
                            player1HealthBar.setMax(number);
                            player1HealthBar.setProgress(number);

                            player1Label.setText("Player 1 - " + number);
                            player1HP = number;

                        }
                    } else {
                        Log.w("TAG", "message was not retained" + topic);
                        // The message is a normal message
                        if (imPlayer1) { //IF IM PLAYER 1
                            int number = Integer.parseInt(new String(message.getPayload()));
                            player2HealthBar.setMax(number);
                            player2HealthBar.setProgress(number);

                            player2Label.setText("Player 2 - " + number);

                            player2HP = number;

                        }
                    }

                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

        // Create the game thread
        mGameThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Main game loop

                while (player1HP > 0 && player2HP > 0 && BossHP > 0) {
                    // Check for user input



                    if (player1Turn) {
                        Log.w("TAG", "Player 1 turn");
                        // Update the UI
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                gameActionsTextView2.setText("Its player 1 turn!");
                            }
                        });
                    }
                    else if (player2Turn) {
                        Log.w("TAG", "Player 2 turn");
                        // Update the UI
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                gameActionsTextView2.setText("Its player 2 turn!");
                            }
                        });
                    }
                    else if (BossTurn) {
                        Log.w("TAG", "Boss turn");

                        // Update the UI
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                gameActionsTextView2.setText("Its the boss turn!");
                            }
                        });

                        //bossAttacked = true;
                        // Pause the game loop for a short time
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        //DECIDE WHICH PLAYER BOSS WILL ATTACK

                        long seed = BossHP + player1HP + player2HP;
                        long seed2 = BossHP + player1HP - player2HP;
                        Log.w("TAG", "seed" + seed);
                        Log.w("TAG", "seed2" + seed2);
                        Random random = new Random(seed);
                        int chance = random.nextInt(100); // generates a random number between 0 and 1

                        Log.w("TAG", "cahnce" + chance);

                        if (chance > 50) {

                            Random random2 = new Random(seed2);
                            int chance2 = random2.nextInt(100); // generates a random number between 0 and 1

                            if (chance2 > 50) {
                                // do something
                                player1HP -= Boss.attack;

                                // Update the UI
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        gameActionsTextView.setText("Boss attacked player 1!");
                                    }
                                });

                            } else {
                                // do something else
                                player2HP -= Boss.attack;

                                // Update the UI
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        gameActionsTextView.setText("Boss attacked player 2!");
                                    }
                                });

                            }
                        } else {
                            BossHP += (int) Math.round(BossHP * 0.07);

                            // Update the UI
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    gameActionsTextView.setText("Boss used heal!");
                                }
                            });
                        }

                        player1Turn = true;
                        player2Turn = false;
                        BossTurn = false;

                        // Update the UI
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                player1HealthBar.setProgress(player1HP);
                                player2HealthBar.setProgress(player2HP);
                                BossHealthBar.setProgress(BossHP);

                                if (imPlayer1) {
                                    attackButton.setEnabled(true);
                                }
                            }
                        });

                    }

                    helper.setCallback(new MqttCallbackExtended() {
                        @Override
                        public void connectComplete(boolean reconnect, String serverURI) {
                            Log.w("TAG", "Connected!");

                        }

                        @Override
                        public void connectionLost(Throwable cause) {

                        }

                        @Override
                        public void messageArrived(String topic, MqttMessage message) throws Exception {
                            Log.w("TAG", "message: " + new String(message.getPayload()));
                            Log.w("TAG", "topic: " + topic);

                            if (topic.equals("battleJunior")) {
                                int number = Integer.parseInt(new String(message.getPayload()));

                                if (player1Turn) {

                                    Log.w("TAG", "Boss levou damage do player1" + topic);

                                    // Update the UI
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            gameActionsTextView.setText("Player 1 attacked boss!");
                                        }
                                    });
                                    BossHP -= number;

                                    player1Turn = false;
                                    player2Turn = true;

                                    if (imPlayer1) {
                                        attackButton.setEnabled(false);
                                    }

                                    if (imPlayer2) {
                                        attackButton.setEnabled(true);
                                    }

                                    // Update the UI
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            player1HealthBar.setProgress(player1HP);
                                            player2HealthBar.setProgress(player2HP);
                                            BossHealthBar.setProgress(BossHP);
                                        }
                                    });



                                } else if (player2Turn) {

                                    Log.w("TAG", "Boss levou damage do player2" + topic);

                                    // Update the UI
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            gameActionsTextView.setText("Player 2 attacked boss!");
                                        }
                                    });

                                    BossHP -= number;

                                    if (imPlayer2) {
                                        attackButton.setEnabled(false);
                                    }

                                    if (imPlayer1) {
                                        attackButton.setEnabled(false);
                                    }

                                    player1Turn = false;
                                    player2Turn = false;
                                    BossTurn = true;

                                    // Update the UI
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            player1HealthBar.setProgress(player1HP);
                                            player2HealthBar.setProgress(player2HP);
                                            BossHealthBar.setProgress(BossHP);
                                        }
                                    });

                                }
                            }
                        }

                        @Override
                        public void deliveryComplete(IMqttDeliveryToken token) {
                        }
                    });

                    // Wait for player 1 to attack
                    attackButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (player1Turn) {

                                Log.w("TAG", "Attack as player 1!");
                                String attack = Integer.toString(player1.attack);
                                // Publish the message to the attack damage topic

                                //player1Turn = false;
                                //player2Turn = true;

                                //attackButton.setEnabled(false);

                                helper.publish("battleJunior", attack, 0, false);


                            } else if (player2Turn) {

                                Log.w("TAG", "Attack as player 2!");
                                String attack = Integer.toString(player2.attack);
                                // Publish the message to the attack damage topic

                                //player1Turn = true;
                                //player2Turn = false;

                                //attackButton.setEnabled(false);

                                helper.publish("battleJunior", attack, 0, false);


                                //attackButton.setEnabled(false);


                            }

                        }
                    });

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
                        player1HealthBar.setProgress(player1HP);
                        player2HealthBar.setProgress(player2HP);
                        BossHealthBar.setProgress(BossHP);
                        // Print the winner
                        if (player1HP > 0 || player2HP > 0) {
                            toast = Toast.makeText(getContext(), "You win!", Toast.LENGTH_SHORT);
                            toast.show();
                            // Update the UI
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    gameActionsTextView.setText("Victory!");
                                }
                            });
                            helper.publish("ConnectJunior", "Gameover", 0, true);
                            helper.publish("GetHPJunior", "Gameover", 0, true);
                            helper.stop();
                            // GET OUT OF MAP

                        } else if (BossHP > 0){
                            toast = Toast.makeText(getContext(), "You lose!", Toast.LENGTH_SHORT);
                            toast.show();
                            // Update the UI
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    gameActionsTextView.setText("Defeat!");
                                }
                            });
                            helper.publish("ConnectJunior", "Gameover", 0, true);
                            helper.publish("GetHPJunior", "Gameover", 0, true);
                            helper.stop();
                            // GET OUT OF MAP
                        }
                    }
                });

            }
        });



        //mGameThread.start();

        return v;

    }



}

