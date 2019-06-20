package com.mua.mua.cshare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.mua.mua.cshare.R;

public class ProfileActivity extends AppCompatActivity {

    de.hdodenhof.circleimageview.CircleImageView profileimage;
    TextView NameofUser, BioofUser, MailofUser, MobileofUser, like, dislike;
    Integer likecount = 182, dislikecount = 38;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileimage = findViewById(R.id.profile_image);
        NameofUser = findViewById(R.id.nameofuser);
        BioofUser = findViewById(R.id.biooftheuser);
        MailofUser = findViewById(R.id.mailofuser);
        MobileofUser = findViewById(R.id.phonenumberofuser);
        like = findViewById(R.id.likecounter);
        dislike = findViewById(R.id.dislikecounter);

        like.setText(likecount.toString());
        dislike.setText(dislikecount.toString());
    }

    public void like(View view) {
        likecount = likecount + 1;
        like.setText(likecount.toString());
    }

    public void dislike(View view) {
        dislikecount = dislikecount + 1;
        dislike.setText(dislikecount.toString());
    }

    public void edit(View view) {
        Intent intent = new Intent(this, navigation.class);
        startActivity(intent);
        finish();
    }
}






