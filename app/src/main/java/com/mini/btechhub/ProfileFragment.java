package com.mini.btechhub;


import static android.content.Intent.getIntent;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
public class ProfileFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView avatartv;
    TextView name, email;
    EditText branch_edit,semester_edit;
    Button signout_button;
    RecyclerView postrecycle;
    FloatingActionButton fab;
    ProgressDialog pd;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // creating a  view to inflate the layout
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        firebaseAuth = FirebaseAuth.getInstance();

        // getting current user data
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");

        // Initialising the text view and imageview
        branch_edit = view.findViewById(R.id.branch_edit);
        semester_edit = view.findViewById(R.id.semester_edit);
        avatartv = view.findViewById(R.id.profile_image);
        signout_button = view.findViewById(R.id.signout_button);
        name = view.findViewById(R.id.fullname_text);
        email = view.findViewById(R.id.email_text);
        //fab = view.findViewById(R.id.fab);
        pd = new ProgressDialog(getActivity());
        //gettinguserdata();
        Intent intent = getActivity().getIntent();
        //String username=intent.getStringExtra("message key");
        String user_email = intent.getStringExtra("email");
        String user_fullname = intent.getStringExtra("name");
        //String username=intent.getStringExtra("username");


        email.setText(user_email);
        name.setText(user_fullname);
        //final FirebaseAuth[] mFirebaseAuth = {FirebaseAuth.getInstance()};

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(getContext(), gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
        if (account != null) {
            String Name = account.getDisplayName();
            String Email = account.getEmail();
            String PhoneNo = account.getId();
            name.setText(Name);
            email.setText(Email);
            //phonenno.setText(PhoneNo);

        }
        signout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth = FirebaseAuth.getInstance();
                startActivity(new Intent(getContext(), Login.class));
                signOut();
            }
        });
        pd.setCanceledOnTouchOutside(false);
        /*Query query = databaseReference.orderByChild("username").equalTo(firebaseUser.getEmail());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    // Retrieving Data from firebase
                    String namee = "" + dataSnapshot1.child("name").getValue();
                    String emaill = "" + dataSnapshot1.child("email").getValue();
                    //String image = "" + dataSnapshot1.child("image").getValue();
                    // setting data to our text view
                    name.setText(namee);

                    email.setText(emaill);

               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("register_users");
        //databaseReference=firebaseDatabase.getReference("register_users");
        //Query checkUserDatabase = databaseReference.orderByChild("users").equalTo(firebaseUser.getDisplayName());
        String username = intent.getStringExtra("message key");

        Query checkUserDatabase=databaseReference;

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
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    private void signOut() {
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {

                startActivity(new Intent(getContext(),Login.class));
            }
        });
    }



    /*private void gettinguserdata() {
        Intent intent=getIntent();
        String user_email=intent.getStringExtra("email");
        String user_fullname=intent.getStringExtra("name");

        email.setText(user_email);
        name.setText(user_fullname);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }*/
}