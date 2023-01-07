package com.example.game.maps;

import static com.example.game.SharedViewModel.NUMBER_OF_MAP_COLUMNS;
import static com.example.game.SharedViewModel.TILESIZE;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.game.MQTTHelper;
import com.example.game.MainActivity;
import com.example.game.R;
import com.example.game.SharedViewModel;
import com.example.game.screens.MainMenu;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.math.BigInteger;
import java.security.SecureRandom;

public class ZoneSelection_3 extends Fragment implements View.OnTouchListener{

    // Variables
    private SharedViewModel viewModel;
    private ImageView image;
    int[] posXY;
    private Bitmap bitmap;
    private MQTTHelper helper;
    private float temp = 25;
    private Toast toast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView( @Nullable LayoutInflater inflater,  @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_zone_selection_3, container, false);

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
                    image = v.findViewById(R.id.MapHolder);
                    // Build map
                    if(temp > 50){
                        ((MainActivity) getActivity()).noti("Its hot...", "Ambiente quente, monstros de água estão enfraquecidos!");
                        viewModel.getMap("zoneSelection_3_hot");
                        // Get Bitmap
                        bitmap = viewModel.getBitmap();

                        // Save on screen map coordinates and set listener
                        image.setImageBitmap(bitmap);
                        posXY = new int[2];
                        image.getLocationOnScreen(posXY);
                        addListener();
                    }else if( temp <0){
                        ((MainActivity)getActivity()).noti("Its cold...", "Ambiente frio, monstros de fogo estão enfraquecidos!");
                        viewModel.getMap("zoneSelection_3_cold");
                        // Get Bitmap
                        bitmap = viewModel.getBitmap();

                        // Save on screen map coordinates and set listener
                        image.setImageBitmap(bitmap);
                        posXY = new int[2];
                        image.getLocationOnScreen(posXY);
                        addListener();
                    }

                }
            }


            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });


        Toolbar toolbar = v.findViewById(R.id.toolbar2);
        toolbar.inflateMenu(R.menu.menu_test);
        Menu menu = toolbar.getMenu();
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {

                case R.id.back:
                    // Navigate to settings screen

                    MainMenu menuback = new MainMenu();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, menuback).commit();
                    return true;

                default:
                    return false;
            }
        });


        // Inflate the layout for this fragment
        return v;
    }

    private void addListener(){
        image.setOnTouchListener(this);

    }

    // Image Touch Screen and calculates which Tile was clicked
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int touchX = (int) event.getX();
        int touchY = (int) event.getY();
        int imageX = touchX - posXY[0]; // posXY[0] is the X coordinate
        int imageY = touchY - posXY[1]; // posXY[1] is the y coordinate
        int idxRow = (int) (imageY/TILESIZE);
        int idxCol = (int) (imageX/TILESIZE);
        float tile = idxRow*NUMBER_OF_MAP_COLUMNS+idxCol;


        if( tile == 11 ){      // Right Arrow
            Log.w("texto", "Right Arrow");
            ZoneSelection_2 zone_2 = new ZoneSelection_2();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, zone_2).commit();

        }
        return false;
    }

}