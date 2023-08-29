package com.mini.btechhub;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {
    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        /*mAuth=FirebaseAuth.getInstance();
        if(mAuth !=null){
            currentUser=mAuth.getCurrentUser();
        }*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //firebaseUser=firebaseAuth.getCurrentUser();
//                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
//                if(firebaseUser==null){
//                    Intent intent = new Intent(SplashScreen.this, Login.class);
//                    startActivity(intent);
//                    finish();
//                }else {
//                    //startActivity(new Intent(getApplicationContext(), Dashboard.class));
//                    Intent mainIntent = new Intent(SplashScreen.this, Dashboard.class);
//                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(mainIntent);
//                    finish();
//                }

                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        },2000);
    }
}