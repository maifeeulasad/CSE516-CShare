package com.mua.mua.cshare.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mua.mua.cshare.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button listB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listB=findViewById(R.id.openList);
        Log.d(TAG,"onCreate: started.");

        listB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openList();
            }
        });
    }

    void openList()
    {

        Intent i=new Intent(MainActivity.this,RecyclerViewActivity.class);
        startActivity(i);
    }
}
