package com.mua.mua.cshare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;

import com.mua.mua.cshare.R;

public class WelcomeActivity extends AppCompatActivity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        run();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        openMain();

    }


    void openMain()
    {
        Intent i=new Intent(WelcomeActivity.this,MainActivity.class);
        startActivity(i);
    }

}
