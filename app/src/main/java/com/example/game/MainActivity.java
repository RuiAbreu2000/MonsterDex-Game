package com.example.game;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.app.AppCompatActivity;

import com.example.game.maps.TestMap;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private SharedViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new Battle()).commit();

        //fragmentManager = getSupportFragmentManager();
        // Show the main game screen when the activity is first created
        //showFragment(new MainMenu());
    }

    // Method to show a given fragment on screen
    public void showFragment(Fragment fragment) {
        int duration = Toast.LENGTH_SHORT;
        CharSequence text = "i here";

        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}