package com.mua.mua.cshare.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.mua.mua.cshare.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button listB, logout_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listB=findViewById(R.id.openList);
        logout_btn = findViewById(R.id.logout_btn);
        Log.d(TAG,"onCreate: started.");

        listB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openList();
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    void openList()
    {

        Intent i=new Intent(MainActivity.this, Hit100Activity.class);
        startActivity(i);
    }
}
