package com.mini.btechhub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mini.btechhub.activity.DetailActivity;

import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {
    EditText loginUsername,loginPassword,input_mobile_number;
    Button loginButton;
    TextView signupRedirectText;
    ProgressBar progressBar;
    //google
    Button google_button;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    private FirebaseAuth mAuth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressbar_sending_otp);

        loginUsername = findViewById(R.id.login_username);
        //loginPassword = findViewById(R.id.login_password);
        input_mobile_number = findViewById(R.id.input_mobile_number);
        signupRedirectText = findViewById(R.id.signupRedirecrText);
        loginButton = findViewById(R.id.login_button);

        //for google authentication
        google_button = findViewById(R.id.google_button);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            if(!validateUsername()  | !validateMobileNo()){

            }else {
                /*String str= input_mobile_number.getText().toString();
                Intent intent=new Intent(getApplicationContext(),VerifyEnterOtp.class);
                intent.putExtra("phoneno",str);*/
                    checkUser();
                }
            }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent=new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });
        google_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }
//Google Authentication code
    private void signIn() {
        Intent intent=gsc.getSignInIntent();
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,@Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);

            try{
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            }catch (ApiException e){
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void navigateToSecondActivity(){
        finish();
        Intent intent=new Intent(getApplicationContext(),Dashboard.class);
        startActivity(intent);
    }
    //till this

    public  Boolean validateUsername(){
        String val=loginUsername.getText().toString();
        if(val.isEmpty()){
            loginUsername.setError("Username cannot be empty");
            return false;

        }else {
            loginUsername.setError(null);
            return true;
        }
    }
    /*public  Boolean validatePassword(){
        String val=loginPassword.getText().toString();
        if(val.isEmpty()){
            loginPassword.setError("Password cannot be empty");
            return false;

        }else {
            loginPassword.setError(null);
            return true;
        }
    }*/
    private boolean validateMobileNo() {
        String val= input_mobile_number.getText().toString();
        if(val.isEmpty()){
            input_mobile_number.setError("Mobile number cannot be empty");
            return false;

        }else{
            input_mobile_number.setError(null);
            return true;
        }
    }

    public void checkUser(){
        String userUsername=loginUsername.getText().toString().trim();
        //String userPassword=loginPassword.getText().toString().trim();
        String userMobileNo=input_mobile_number.getText().toString();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");

        Query checkUserDatabase=reference.orderByChild("username").equalTo(userUsername);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    loginUsername.setError(null);
                    //String passwordFromDB= snapshot.child(userUsername).child("password").getValue(String.class);
                    String mobilenoFromDB=snapshot.child(userUsername).child("phoneno").getValue(String.class);


                    //if(passwordFromDB.equals(userPassword)){
                        //loginUsername.setError(null);
                        //Intent intent=new Intent(Login.this,Dashboard.class);
                        //startActivity(intent);
                        if (mobilenoFromDB.equals(userMobileNo)) {
                            loginUsername.setError(null);

                        }
                        else {
                            input_mobile_number.setError("Invalid Credintials");
                            input_mobile_number.requestFocus();
                        }

                        //getting data from firebase for profile page
                        String email=snapshot.child(userUsername).child("email").getValue(String.class);
                        String name=snapshot.child(userUsername).child("name").getValue(String.class);
                        String phoneno=snapshot.child(userUsername).child("phoneno").getValue(String.class);
                        String user=snapshot.child(userUsername).child("username").getValue(String.class);
                        //String fullname=snapshot.child(userUsername).child("username").getValue(String.class);
                        Intent intent=new Intent(getApplicationContext(),Dashboard.class);

                        intent.putExtra("message key",user);
                        intent.putExtra("name",name);
                        intent.putExtra("email",email);
                        intent.putExtra("phoneno",phoneno);

                        //intent.putExtra("fullname",fullname);

                        startActivity(intent);
                        finish();
                    /*}else {
                        loginPassword.setError("Invalid Credintials");
                        loginPassword.requestFocus();
                    }*/
                }else {
                    loginUsername.setError("User does not exists");
                    loginUsername.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
