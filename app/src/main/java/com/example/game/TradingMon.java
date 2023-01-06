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
    private boolean player2Sent = false;
    private String myTrade;

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
                    Monster monster = viewModel.getDatabase().monsterDexDao().getMonsterByName(selectedMonster.name);

                    helper.publish("GetStatsJunior", monster.name, 0, false);

                    if (imPlayer1){
                        player1Sent = true;
                    }


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

                if (message.isRetained()) {
                    Log.w("TAG", "message was retained" + topic);
                }

                if (topic.equals("ConnectTrading")) {

                    if (new String(message.getPayload()).equals("TradingOver")){

                        imPlayer1 = true;
                        List<Monster> monsters = viewModel.getDatabase().monsterDao().getAllMonsters();
                        helper.publish("ConnectTrading", "Player1conn", 0, true);


                    } else if (new String(message.getPayload()).equals("Player1conn")){

                        if (!imPlayer1) { //IF IM PLAYER2

                            imPlayer2 = true;
                            List<Monster> monsters = viewModel.getDatabase().monsterDao().getAllMonsters();
                            helper.publish("ConnectTrading", "Player2conn", 0, false);


                        }

                    } else if (new String(message.getPayload()).equals("Player2conn")){
                        Log.w("TAG", "Entrei no loop" + topic);

                        if (imPlayer1){
                            tradeButton.setEnabled(true);
                        }

                    }

                }

                if (topic.equals("GetStatsJunior")) {


                    if (!imPlayer1 && !player1Sent) { //IF ITS PLAYER 2

                        myTrade = new String(message.getPayload());
                        receive.setText("Receive: " + myTrade);
                        receiveButton.setEnabled(true);

                        player1Sent = true;
                        tradeButton.setEnabled(true);


                    } else if (imPlayer1 && player1Sent) { //IF IM PLAYER 1

                        myTrade = new String(message.getPayload());
                        receive.setText("Receive: " + myTrade);
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
        List<MonsterDex> monsters = viewModel.getDatabase().monsterDexDao().getAllMonsters();

        for (int i=0;i<monsters.size();i++){
            MonsterDex m = monsters.get(i);
            Bitmap bitmap = BitmapFactory.decodeByteArray(m.bArray, 0, m.bArray.length);
            monster.add(new monster_class(bitmap, m.name));
        }
    }


}