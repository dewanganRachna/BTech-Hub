package com.mini.btechhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.material.navigation.NavigationView;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mini.btechhub.adapter.BookAdapter;
import com.mini.btechhub.model.Books;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseAuth firebaseAuth;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private View header;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    GoogleSignInApi gac;
    TextView namee, emaill, phonenno;
    Button signout_button, main_button;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser user;
    FirebaseDatabase database;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.tool_Bar);
        setSupportActionBar(toolbar);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        //View view= LayoutInflater.from(this).inflate(R.layout.header_main,null);
        database = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        //FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        //View header = navigationView.getHeaderView(0);

        //String uid = user.getUid();


        /*database.getReference().child("users").child(mFirebaseAuth.getUid()).

    addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange (@NonNull DataSnapshot snapshot){
            User user = snapshot.getValue(User.class);
            View header = navigationView.getHeaderView(0);
            emaill = header.findViewById(R.id.email);
            namee = header.findViewById(R.id.name);

            emaill.setText(user.getMail);
            namee.setText(user.getUserName());
        }

        @Override
        public void onCancelled (@NonNull DatabaseError error){

        }
    });*/



        //navigationView.addHeaderView(view);
        //View view=navigationView.inflateHeaderView(R.layout.header_main);
        /*name=header.findViewById(R.id.name);
        email=header.findViewById(R.id.email);
        Intent intent=getIntent();

        String user_email=intent.getStringExtra("email");
        String user_fullname=intent.getStringExtra("name");
        email.setText(user_email);
        name.setText(user_fullname);
        //phonenno=view.findViewById(R.id.phoneno);
        /*name=findViewById(R.id.name);
        email=findViewById(R.id.email);*/
        //phonenno=findViewById(R.id.phoneno);
        //signout_button=findViewById(R.id.signout_button);
        //main_button=findViewById(R.id.main_button);
//main code
        /*mFirebaseAuth=FirebaseAuth.getInstance();

        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc= GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            String Name=account.getDisplayName();
            String Email= account.getEmail();
            String PhoneNo=account.getId();
            namee.setText(Name);
            emaill.setText(Email);
            //phonenno.setText(PhoneNo);

        }
        else {
            //gettinguserdata();
        }*/




    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
                break;
            case R.id.nav_contact:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ContactFragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment()).commit();
                break;
            case R.id.nav_share:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ShareFragment()).commit();
                break;
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AboutFragment()).commit();
                break;
            /*case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Dashboard.this, Login.class);
                startActivity(intent);
                Toast.makeText(this,"Logout!", Toast.LENGTH_SHORT).show();
                break;*/

        }


        drawerLayout.closeDrawer(GravityCompat.START);
        return  true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }

    }

    private void signOut() {
        //mFirebaseAuth.signOut();
        gsc=GoogleSignIn.getClient(

               this,gso);
        firebaseAuth=FirebaseAuth.getInstance();
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }


}