package com.mua.mua.cshare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mua.mua.cshare.R;

public class WelcomeActivity extends AppCompatActivity implements Runnable {

    private Button signInWithEmail, signUpWithEmail;
    private FirebaseAuth auth;

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is already authenticated
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        run();

        signInWithEmail = findViewById(R.id.signinwithemail);
        signUpWithEmail = findViewById(R.id.signupwithemail);

        auth = FirebaseAuth.getInstance();

        /*
         * Action listener for buttons
         */

        signInWithEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            }
        });

        signUpWithEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, RegisterActivity.class));
            }
        });
    }

    public void back(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
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
