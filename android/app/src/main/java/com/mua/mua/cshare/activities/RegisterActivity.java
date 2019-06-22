package com.mua.mua.cshare.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mua.mua.cshare.R;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText input_name, input_email, input_password, input_confirm;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        input_name = findViewById(R.id.input_name);
        input_email = findViewById(R.id.input_email);
        input_password = findViewById(R.id.input_password);
        input_confirm = findViewById(R.id.input_confirm);
        Button sign_up = findViewById(R.id.button_signup);
        TextView signin_text = findViewById(R.id.text_signin);

        // Customizing toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Sign up");

        auth = FirebaseAuth.getInstance();

        sign_up.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {
                String name = input_name.getText().toString();
                String email = input_email.getText().toString();
                String password = input_password.getText().toString();
                String confirm_password = input_confirm.getText().toString();

                if (name.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirm_password)) {
                    Toast.makeText(RegisterActivity.this, "Passwords must be equal", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog = new ProgressDialog(RegisterActivity.this);
                    progressDialog.setMessage("Please wait!");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();
                    createNewUser(name, email, password);
                }
            }
        });

        signin_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private void createNewUser(final String name, final String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @SuppressLint("ShowToast")
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            // Add new User data to the database
                            DatabaseReference reference = FirebaseDatabase.getInstance()
                                    .getReference().child("Users").child(user.getUid());

                            HashMap<String, Object> info = new HashMap<>();
			    info.put("bio", "Hey! I am using CShare");
                            info.put("email", email);
                            info.put("isAdmin", false);
                            info.put("name", name);
			    info.put("phoneNumber", "Not Provided");
                            info.put("profilePhotoURL", "https://firebasestorage.googleapis.com/v0/b/cshare-9ab86.appspot.com/o/Profile_Images%2Fdefault.jpg?alt=media&token=3bced513-3e12-4fb8-8f4d-a62fa325c61d");
                            info.put("totalDownVote", 0);
                            info.put("totalUpVote", 0);
                            // TODO: Add more info
                            reference.setValue(info);

                            // Starts login activity and send email verification link
                            sendEmailVerificationLink(user);
                            auth.signOut();
                        } else {
                            try {
                                throw task.getException();
                            } catch(Exception e) {
                                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void sendEmailVerificationLink(FirebaseUser user) {
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Email verification link sent", Toast.LENGTH_SHORT).show();

                            // Delays 2 seconds to see the toast message
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                }
                            }, 2000);
                        } else {
                            try {
                                throw task.getException();
                            } catch (Exception e) {
                                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
