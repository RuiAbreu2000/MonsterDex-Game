package com.example.game.threads;

import android.os.Handler;
import android.util.Log;

import com.example.game.databases.AppDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Task_Manager {
    final Executor executor = Executors.newSingleThreadExecutor();
    final Handler handler = new Handler();

    public interface Callback {
        void onComplete();
    }

    public void executeAsync(AppDatabase db, int op){
        executor.execute(() -> {
            try {
                if(op == 0) {        // Heal All Player Monsters
                    int count = db.monsterDao().getCount();
                    for(int i=0; i<count; i++){
                        db.monsterDao().healMonster(i);
                    }
                }else if(op == 1) {    //
                }else if(op == 2){    //
                }else if(op == 3){    //
                }

            } catch(Exception ex){
                Log.e("Exception", ex.toString());
            }
        });
    }
}
