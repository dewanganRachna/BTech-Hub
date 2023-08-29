package com.mini.btechhub;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class SignUp extends AppCompatActivity {
    ProgressBar progressBar;
    EditText signupName,signupEmail,signupUsername,signupPhoneno,signupPassword;
    TextView loginRedirectText;
    Button signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        progressBar=findViewById(R.id.progressbar_sending_otp);

        signupName=findViewById(R.id.signup_name);
        signupEmail=findViewById(R.id.signup_email);
        signupUsername=findViewById(R.id.signup_username);
        signupPhoneno=findViewById(R.id.signup_phonenumber);
        //signupPassword=findViewById(R.id.signup_password);
        signupButton=findViewById(R.id.signup_button);
        loginRedirectText=findViewById(R.id.loginRedirectText);

        signupButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                database = FirebaseDatabase.getInstance();
                                                reference = database.getReference("users");

                                                String name = signupName.getText().toString();
                                                String email = signupEmail.getText().toString();
                                                String username = signupUsername.getText().toString();
                                                //String password = signupPassword.getText().toString();
                                                String phonenumber = signupPhoneno.getText().toString();

                                                HelperClass helperClass = new HelperClass(name, email, username, phonenumber);
                                                reference.child(username).setValue(helperClass);

                                                //for sending username
                                                /*String user=signupUsername.getText().toString();
                                                Intent intent=new Intent(getApplicationContext(),Register.class);
                                                intent.putExtra("message key",user);
                                                startActivity(intent);
                                                Toast.makeText(SignUp.this, "You have sign up successfully", Toast.LENGTH_SHORT).show();
                                                //Intent intent= new Intent(SignUp.this, Register.class);
                                                //startActivity(intent);*/

                                                if (!signupPhoneno.getText().toString().trim().isEmpty()) {
                                                    if ((signupPhoneno.getText().toString().trim()).length() == 10) {
                                                        progressBar.setVisibility(View.VISIBLE);
                                                        signupButton.setVisibility(View.INVISIBLE);
                                                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                                                "+91" + signupPhoneno.getText().toString(),
                                                                60,
                                                                TimeUnit.SECONDS,
                                                                SignUp.this,
                                                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                                                    @Override
                                                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                                                        progressBar.setVisibility(View.GONE);
                                                                        signupButton.setVisibility(View.VISIBLE);
                                                                    }

                                                                    @Override
                                                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                                                        progressBar.setVisibility(View.GONE);
                                                                        signupButton.setVisibility(View.VISIBLE);
                                                                        Toast.makeText(SignUp.this, e.getMessage(), LENGTH_SHORT).show();
                                                                    }

                                                                    @Override
                                                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                                                        progressBar.setVisibility(View.GONE);
                                                                        signupButton.setVisibility(View.VISIBLE);
                                                                        String user=signupUsername.getText().toString();
                                                                        String name=signupName.getText().toString();
                                                                        String email=signupEmail.getText().toString();
                                                                        Intent intent = new Intent(getApplicationContext(), VerifyEnterOtp.class);
                                                                        intent.putExtra("mobile", signupPhoneno.getText().toString());
                                                                        intent.putExtra("message key",user);
                                                                        intent.putExtra("name",name);
                                                                        intent.putExtra("email",email);
                                                                        intent.putExtra("backendotp", backendotp);
                                                                        startActivity(intent);
                                                                    }
                                                                }

                                                        );
                                                    } else {
                                                        Toast.makeText(SignUp.this, "Please enter correct number", LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(SignUp.this, "Enter Mobile number", LENGTH_SHORT).show();
                                                }

                                            }
                                        });

                //for sending username
                /*String user=signupUsername.getText().toString();
                Intent intent=new Intent(getApplicationContext(),Register.class);
                intent.putExtra("message key",user);
                startActivity(intent);
                Toast.makeText(SignUp.this, "You have sign up successfully", Toast.LENGTH_SHORT).show();
                //Intent intent= new Intent(SignUp.this, Register.class);
                //startActivity(intent);


            }*/

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SignUp.this, Login.class);
                startActivity(intent);

            }
        });
    }


}
