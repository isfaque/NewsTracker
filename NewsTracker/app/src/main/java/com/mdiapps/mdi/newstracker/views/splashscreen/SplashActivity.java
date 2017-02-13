package com.mdiapps.mdi.newstracker.views.splashscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mdiapps.mdi.newstracker.R;
import com.mdiapps.mdi.newstracker.utils.ActivityUtil;

/**
 * Created by Mascot on 1/8/2017.
 */
public class SplashActivity extends AppCompatActivity implements SplashView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(2600);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    startMainActivity();
                }
            }
        };
        timerThread.start();
    }

    @Override
    public void startMainActivity() {
        new ActivityUtil(this).startMainActivity();
    }
}
