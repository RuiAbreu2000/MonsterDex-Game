package com.example.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.game.databases.Monster;
import com.example.game.databases.MonsterDex;
import com.example.game.maps.MainCity;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class TradingMon extends Fragment {

    ArrayList<monster_class> monster = new ArrayList<>();
    monster_class selectedMonster;
    private SharedViewModel viewModel;


    private boolean isConnected = true;
    private boolean imPlayer1 = false;
    private boolean imPlayer2 = false;
    private boolean selected = false;
    private boolean player1Sent = false;
    private boolean OnGoingTrade = false;
    private Monster myTrade;
    private boolean iSent = false;

    private Button tradeButton;
    private Button receiveButton;
    private TextView trade;
    private TextView receive;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_trading_mon, container, false);

        SecureRandom random = new SecureRandom();
        byte[] idBytes = new byte[16];
        random.nextBytes(idBytes);
        String id = new BigInteger(1, idBytes).toString(16);

        RecyclerView recyclerView = v.findViewById(R.id.monsters);
        tradeButton = v.findViewById(R.id.button3);
        receiveButton = v.findViewById(R.id.button4);
        trade = v.findViewById(R.id.textView3);
        receive = v.findViewById(R.id.textView4);

        receiveButton.setEnabled(false);

        tradeButton.setEnabled(false);

        MQTTHelper helper = new MQTTHelper(getActivity(), id, "battleaJunior");

        helper.connect();

        //LOAD PLAYER DATA FROM DATABASE WHEN ITS READY
        setMonsters();

        recycler_view_adapter adapter = new recycler_view_adapter(((MainActivity)getActivity()), monster);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(((MainActivity)getActivity())));

        // In the fragment
        adapter.setOnItemClickListener(new recycler_view_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle the click event here
                selectedMonster = monster.get(position);

                String name = selectedMonster.name;

                Log.w("TAG", "NAME: " + name);

                trade.setText("Send: " + name);
                selected = true;

            }
        });


        // Wait for player 1 to attack
        tradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selected) {


                    tradeButton.setEnabled(false);
                    Monster monster = viewModel.getDatabase().monsterDao().getMonsterByName(selectedMonster.name);
                    String name = monster.name;
                    String type = monster.type;
                    String health = Integer.toString(monster.health);
                    String attack = Integer.toString(monster.attack);
                    String defense = Integer.toString(monster.defense);
                    String level = Integer.toString(monster.level);
                    String xp = Integer.toString(monster.xp);
                    String eve = Integer.toString(monster.eve);
                    String evolution = monster.evolution;
                    String message = name + " " + type + " " + health + " " + attack + " " + level + " " + defense + " " + xp + " " + eve + " " + evolution;
                    helper.publish("GetStatsJunior", message, 0, false);


                    iSent = true;



                }
            }
        });

        // Wait for player 1 to attack
        receiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selected && iSent) {

                    Monster monsterReceiving = viewModel.getDatabase().monsterDexDao().getMonsterByName(myTrade.name);
                    myTrade.bArray = monsterReceiving.bArray;

                    viewModel.getDatabase().monsterDao().addMonster(myTrade);
                    Monster deleting = viewModel.getDatabase().monsterDao().getMonsterByName(selectedMonster.name);
                    viewModel.getDatabase().monsterDao().deleteMonster(deleting);

                    helper.publish("ConnectTrading", "TradingOver", 0, true);
                    //getActivity().getSupportFragmentManager().beginTransaction().detach(this).attach(this).commit();
                    Fragment goBack = viewModel.getLastFragment();
                    goBack.onResume();
                    //TestMap fragment = new TestMap();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, goBack).commit();



                }
            }
        });


        helper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                Log.w("TAG", "Connected!");
                if (isConnected) {
                    helper.subscribeToTopic("ConnectTrading");
                    helper.subscribeToTopic("GetStatsJunior");
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


                if (topic.equals("ConnectTrading")) {

                    if (new String(message.getPayload()).equals("TradingOver")){

                        if (!OnGoingTrade) {
                            imPlayer1 = true;
                            //List<Monster> monsters = viewModel.getDatabase().monsterDao().getAllMonsters();
                            helper.publish("ConnectTrading", "Player1conn", 0, true);
                        }


                    } else if (new String(message.getPayload()).equals("Player1conn")){

                        if (!imPlayer1) { //IF IM PLAYER2

                            imPlayer2 = true;
                            //List<Monster> monsters = viewModel.getDatabase().monsterDao().getAllMonsters();
                            helper.publish("ConnectTrading", "Player2conn", 0, false);


                        }

                    } else if (new String(message.getPayload()).equals("Player2conn")){
                        Log.w("TAG", "Entrei no loop" + topic);
                        OnGoingTrade = true;

                        helper.publish("ConnectTrading", "TradingStarted", 0, true);

                        if (imPlayer1){
                            tradeButton.setEnabled(true);
                        }

                    } else if (new String(message.getPayload()).equals("TradingStarted")) {


                        if (imPlayer1 || imPlayer2) {
                            if (imPlayer1){
                                tradeButton.setEnabled(true);
                            }

                        } else {
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainCity()).commit();
                        }
                    }

                }

                if (topic.equals("GetStatsJunior")) {

                    Log.w("TAG", "recebi stats");
                    if (!imPlayer1 && !player1Sent) { //IF ITS PLAYER 2


                        String messageString = new String(message.getPayload());


                        String[] words = messageString.split(" ");

                        myTrade = new Monster();
                        myTrade.name = words[0];
                        myTrade.type = words[1];
                        myTrade.health = Integer.parseInt(words[2]);
                        myTrade.attack = Integer.parseInt(words[3]);
                        myTrade.defense = Integer.parseInt(words[4]);
                        myTrade.level = Integer.parseInt(words[5]);
                        myTrade.xp = Integer.parseInt(words[6]);
                        myTrade.eve = Integer.parseInt(words[7]);
                        myTrade.evolution = words[8];


                        receive.setText("Receive: " + words[0] + " " + words[1]);
                        receiveButton.setEnabled(true);

                        player1Sent = true;
                        tradeButton.setEnabled(true);


                    } else if (imPlayer1 && iSent) { //IF IM PLAYER 1

                        String messageString = new String(message.getPayload());

                        String[] words = messageString.split(" ");

                        myTrade = new Monster();
                        myTrade.name = words[0];
                        myTrade.type = words[1];
                        myTrade.health = Integer.parseInt(words[2]);
                        myTrade.attack = Integer.parseInt(words[3]);
                        myTrade.defense = Integer.parseInt(words[4]);
                        myTrade.level = Integer.parseInt(words[5]);
                        myTrade.xp = Integer.parseInt(words[6]);
                        myTrade.eve = Integer.parseInt(words[7]);
                        myTrade.evolution = words[8];

                        // Now you can access the separate words in the words array

                        receive.setText("Receive: " +words[0] + " " + words[1]);
                        receiveButton.setEnabled(true);

                        //helper.publish("ConnectTrading", "TradingOver", 0, true);


                    }

                }

                }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

        return v;

    }

    private void setMonsters() {
        List<Monster> monsters = viewModel.getDatabase().monsterDao().getAllMonsters();

        for (int i=0;i<monsters.size();i++){
            Monster m = monsters.get(i);
            Bitmap bitmap = BitmapFactory.decodeByteArray(m.bArray, 0, m.bArray.length);
            monster.add(new monster_class(bitmap, m.name));
        }
    }


}