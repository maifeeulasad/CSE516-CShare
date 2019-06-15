package com.mua.mua.cshare.activities;

<<<<<<< HEAD
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
=======
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
>>>>>>> 8af3bdd6c183dafb6dc5e84ef4fcb37b9c0d6128

import com.mua.mua.cshare.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void back(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);

    }
}
