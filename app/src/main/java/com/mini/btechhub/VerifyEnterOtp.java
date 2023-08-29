package com.mini.btechhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class VerifyEnterOtp extends AppCompatActivity {
    EditText et1, et2,et3,et4,et5,et6;
    Button btnsubmit;
    String getotpbackend;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_enter_otp);
        et1 = findViewById(R.id.inputotp1);
        et2 = findViewById(R.id.inputotp2);
        et3 = findViewById(R.id.inputotp3);
        et4 = findViewById(R.id.inputotp4);
        et5 = findViewById(R.id.inputotp5);
        et6 = findViewById(R.id.inputotp6);

        progressBar=findViewById(R.id.progressbar_verify_otp);

        //get mobile number from mainActivity to this
        TextView textView = findViewById(R.id.textmobileshownumber);
        textView.setText(String.format(
                "+91-%S", getIntent().getStringExtra("mobile")
        ));

        getotpbackend=getIntent().getStringExtra("backendotp");
        btnsubmit=findViewById(R.id.buttongetotp);

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String userUsername=loginUsername.getText().toString().trim();
                String userPassword=loginPassword.getText().toString().trim();
                String userMobileNo=input_mobile_number.getText().toString();
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");

                Query checkUserDatabase=reference.orderByChild("username").equalTo(userUsername);
                checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            loginUsername.setError(null);
                            String passwordFromDB= snapshot.child(userUsername).child("password").getValue(String.class);
                            String mobilenoFromDB=snapshot.child(userUsername).child("phoneno").getValue(String.class);


                            if(passwordFromDB.equals(userPassword)){
                                loginUsername.setError(null);
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
                                String username=snapshot.child(userUsername).child("username").getValue(String.class);
                                //String fullname=snapshot.child(userUsername).child("username").getValue(String.class);
                                Intent intent=new Intent(getApplicationContext(),Dashboard.class);
                                intent.putExtra("name",name);
                                intent.putExtra("email",email);
                                intent.putExtra("phoneno",phoneno);
                                intent.putExtra("username",username);
                                //intent.putExtra("fullname",fullname);

                                startActivity(intent);
                                finish();
                            }else {
                                loginPassword.setError("Invalid Credintials");
                                loginPassword.requestFocus();
                            }
                        }else {
                            loginUsername.setError("User does not exists");
                            loginUsername.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }*/
                if (!et1.getText().toString().trim().isEmpty()
                        && !et2.getText().toString().trim().isEmpty()
                        && !et3.getText().toString().trim().isEmpty()
                        && !et4.getText().toString().trim().isEmpty()
                        && !et5.getText().toString().trim().isEmpty()
                        && !et6.getText().toString().trim().isEmpty()) {
                    //marging user input in a string
                    String entercodeotp=et1.getText().toString()+
                            et2.getText().toString()+
                            et3.getText().toString()+
                            et4.getText().toString()+
                            et5.getText().toString()+
                            et6.getText().toString();
                    //Toast.makeText(verifyenterotptwo.this, "otp verify", Toast.LENGTH_SHORT).show();
                    if(getotpbackend!=null){
                        progressBar.setVisibility(View.VISIBLE);
                        btnsubmit.setVisibility(View.INVISIBLE);
                        PhoneAuthCredential phoneAuthCredential= PhoneAuthProvider.getCredential(
                                getotpbackend,entercodeotp
                        );
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBar.setVisibility(View.VISIBLE);
                                        btnsubmit.setVisibility(View.INVISIBLE);

                                        if(task.isSuccessful()){
                                           String user=getIntent().getStringExtra("message key");
                                           String name=getIntent().getStringExtra("name");
                                            String email=getIntent().getStringExtra("email");

                                            Intent intent=new Intent(getApplicationContext(),Register.class);
                                            intent.putExtra("message key",user);
                                            intent.putExtra("name",name);
                                            intent.putExtra("email",email);

                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);

                                        }
                                        else {
                                            Toast.makeText(VerifyEnterOtp.this,"Enter the correct OTP",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }else{
                        Toast.makeText(VerifyEnterOtp.this, "please check internet connectivity", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(VerifyEnterOtp.this, "please enter all number", Toast.LENGTH_SHORT).show();

                }

            }
        });

        findViewById(R.id.textresendotp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        VerifyEnterOtp.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(VerifyEnterOtp.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newbackendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                getotpbackend=newbackendotp;
                                Toast.makeText(VerifyEnterOtp.this,"Otp sended Successfully",Toast.LENGTH_SHORT).show();

                            }
                        }

                );
            }
        });
        numberotpmove();
    }
    private void numberotpmove() {
        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()) {
                    et2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()) {
                    et3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()) {
                    et4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()) {
                    et5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()) {
                    et6.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}