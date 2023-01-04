package com.example.game;

import android.os.Bundle;
import android.os.Handler;
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
import androidx.lifecycle.ViewModelProviders;

import com.example.game.maps.TestMap;

public class Battle extends Fragment {
    private SharedViewModel viewModel;
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

    // variables to track whose turn it is
    private boolean player1Turn = true;
    private boolean player2Turn = false;
    private Toast toast;

    // views to display the characters' stats
    private ProgressBar player1HealthBar;
    private ProgressBar player2HealthBar;
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

        // initialize the characters and their stats
        player1 = new Character(1000, 90, 20);
        player2 = new Character(1000, 100, 15);

        // initialize the views to display the characters' stats
        player1HealthBar = v.findViewById(R.id.progressBar_aliado);
        player2HealthBar = v.findViewById(R.id.progressBar_inimigo);
        player1Label = v.findViewById(R.id.textView_aliado);
        player2Label = v.findViewById(R.id.textView_inimigo);
        attackButton = v.findViewById(R.id.button_ataque);
        //attackButton2 = v.findViewById(R.id.attack_button2);

        // display the initial values for the characters' stats
        player1HealthBar.setProgress(player1.health);
        player2HealthBar.setProgress(player2.health);

        // Create the handler for updating the UI
        mHandler = new Handler();

        //attackButton2.setVisibility(View.GONE); // Im hiding the player 2 button because im using player vs AI monster

        // Create the game thread
        mGameThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Main game loop
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
                            toast = Toast.makeText(getContext(), "Player 1 wins!", Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            toast = Toast.makeText(getContext(), "Player 2 wins!", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        // Go Back To Map
                        Fragment goBack = viewModel.getLastFragment();
                        goBack.onResume();
                        //TestMap fragment = new TestMap();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, goBack).commit();
                    }
                });

            }
        });

    mGameThread.start();

    return v;

    }

}