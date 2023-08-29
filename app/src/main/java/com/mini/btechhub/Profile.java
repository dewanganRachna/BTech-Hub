package com.mini.btechhub;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
//*No use of this activity*//
public class Profile extends AppCompatActivity {
    EditText branch_edit,semester_edit;
    TextView name,email;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name=findViewById(R.id.fullname_text);
        email=findViewById(R.id.email_text);
        branch_edit=findViewById(R.id.branch_edit);
        semester_edit=findViewById(R.id.semester_edit);
        gettinguserdata();
        getdata();

    }
    private void getdata() {
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("register_users");
        Query checkUserDatabase=databaseReference.orderByChild("users").equalTo(firebaseUser.getDisplayName());

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
            // Retrieving Data from firebase
            String branch = "" + dataSnapshot1.child("selected_branch").getValue();
            String semester = "" + dataSnapshot1.child("selected_semester").getValue();
            //String image = "" + dataSnapshot1.child("image").getValue();
            // setting data to our text view
            branch_edit.setText(branch);
            semester_edit.setText(semester);
            /*try {
                Glide.with(getActivity()).load(image).into(avatartv);
            } catch (Exception e) {

            }*/
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});
    /*@Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        String branch=snapshot.getValue(String.class);
        branch_edit.setText(branch);

        String semester=snapshot.getValue(String.class);
        semester_edit.setText(semester);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        Toast.makeText(Profile.this,"Fail to get data",Toast.LENGTH_SHORT).show();
    }
});*/
    }

    private void gettinguserdata() {
        Intent intent=getIntent();
        String user_email=intent.getStringExtra("email");
        String user_fullname=intent.getStringExtra("name");
        //String username=intent.getStringExtra("username");


        //String branch=intent.getStringExtra("selected_branch");
        //String semester=intent.getStringExtra("selected_semester");

        email.setText(user_email);
        name.setText(user_fullname);
        //branch_edit.setText(branch);
        //semester_edit.setText(semester);

    }
}