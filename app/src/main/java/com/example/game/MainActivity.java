package com.example.game;

import static com.example.game.SharedViewModel.TILESIZE;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.app.AppCompatActivity;

import com.example.game.databases.MonsterDex;
import com.example.game.graphics.Sprite;
import com.example.game.graphics.SpriteSheet;
import com.example.game.maps.TestMap;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private SharedViewModel viewModel;
    private Fragment savedFragment;
    private Sprite[][] monsterSprite;
    private SpriteSheet MonsterSpriteSheet;
    private String[] monsterNames = {
            "Crab", "Angry Crab", "Monster Crab",
            "Ocean Bear", "Ocean Bearsaur", "Oceanzilla",
            "Ocean Mutant", "Giant Ocean Mutant", "Mega Mutant",
            "Shark", "Shark Monster", "Shark Monstrosity",
    };

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        List<MonsterDex> monsters = viewModel.getDatabase().monsterDexDao().getAllMonsters();
        if (monsters.size()==0){
            // Load Monsters to MonsterDex Database
            MonsterSpriteSheet = viewModel.getAirMonsterSpriteSheet();
            monsterNames = new String[]{
                    "air00", "air01", "air02",
                    "air10", "air11", "air12",
                    "air20", "air21", "air22",
                    "air30", "air31", "air32",
            };
            buildMonsters("air");
            MonsterSpriteSheet = viewModel.getBugMonsterSpriteSheet();
            monsterNames = new String[]{
                    "bug00", "bug01", "bug02",
                    "bug10", "bug11", "bug12",
                    "bug20", "bug21", "bug22",
                    "bug30", "bug31", "bug32",
            };
            buildMonsters("bug");
            MonsterSpriteSheet = viewModel.getFireMonsterSpriteSheet();
            monsterNames = new String[]{
                    "fire00", "fire01", "fire02",
                    "fire10", "fire11", "fire12",
                    "fire20", "fire21", "fire22",
                    "fire30", "fire31", "fire32",
            };
            buildMonsters("fire");
            MonsterSpriteSheet = viewModel.getGroundMonsterSpriteSheet();
            monsterNames = new String[]{
                    "ground00", "ground01", "ground02",
                    "ground10", "ground11", "ground12",
                    "ground20", "ground21", "ground22",
                    "ground30", "ground31", "ground32",
            };
            buildMonsters("ground");
            MonsterSpriteSheet = viewModel.getWaterMonsterSpriteSheet();
            monsterNames = new String[]{
                    "Crab", "Angry Crab", "Monster Crab",
                    "Ocean Bear", "Ocean Bearsaur", "Oceanzilla",
                    "Ocean Mutant", "Giant Ocean Mutant", "Mega Mutant",
                    "Shark", "Shark Monster", "Shark Monstrosity",
            };
            buildMonsters("water");
        }

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new MainMenu()).commit();

        //fragmentManager = getSupportFragmentManager();
        // Show the main game screen when the activity is first created
        //showFragment(new MainMenu());
    }

    public void noti(String title, String text){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My notification", "My notificaton", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "My notification");
        builder.setContentTitle(title);
        builder.setContentText(text);
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
        managerCompat.notify(1, builder.build());
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
    public void buildSpriteArray(int numberOfRows, int numberOfColumns){

        // Build Map with Sprites
        for (int iRow = 0; iRow < numberOfRows; iRow++) {
            for (int iCol = 0; iCol < numberOfColumns; iCol++) {
                // get corresponding sprite
                monsterSprite[iRow][iCol] = MonsterSpriteSheet.getMonsterTile(iRow*3+iCol+1);


            }
        }
    }

    public void buildMonsters(String type){
        // Build SpriteSheet and Sprite Array

        monsterSprite = new Sprite[4][3];       // Numero de Water Monsters Atualmente
        buildSpriteArray(4, 3);

        // Build Bitmaps
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        String evolvesFrom = null;
        for (int iRow = 0; iRow < 4; iRow++) {
            for (int iCol = 0; iCol < 3; iCol++) {
                Bitmap bitmap = Bitmap.createBitmap(TILESIZE, TILESIZE, conf); // this creates a MUTABLE bitmap
                Canvas mapCanvas = new Canvas(bitmap);
                mapCanvas.drawBitmap(monsterSprite[iRow][iCol].getSpriteBitmap(), null, new Rect(0,0,256,256), null);
                // Transform to bit Array
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                // Create new Monster and Insert into Database
                MonsterDex newMonster = new MonsterDex();
                newMonster.name = monsterNames[iRow*3+iCol];
                newMonster.type = type;
                newMonster.health = 100;
                newMonster.attack = 100;
                newMonster.defense = 100;
                newMonster.isBoss = false;
                newMonster.bArray = bos.toByteArray();
                newMonster.rarity = "common";
                newMonster.evolution = iCol+1;
                if(iCol == 0){                              // Se for o primeiro da linha é evolução 1
                    newMonster.evolvesFrom = "base";
                }else{
                    newMonster.evolvesFrom = evolvesFrom;   // Adiciona o monstro do qual evolui
                }
                viewModel.createNewMonster(newMonster);
                evolvesFrom = monsterNames[iRow+iCol];
            }
        }
    }

}