package com.mini.btechhub;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button button_register;
    Spinner spinner_branch,spinner_semester;
    EditText username_edit;
    TextView textView_branch,textview_semester;
    DatabaseReference databaseReference;
    String item,item2,item3;
    RegisterHelperClass registerHelperClass;

    String[] selected_branch={"Select branch","Computer Science","Information Technology","Electrical","Electronics & Telecommunication","Civil","Mechanical","Mining"};
    String[] selected_semester={"select semester","1st","2nd","3rd","4th","5th","6th","7th","8th"};
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        button_register=findViewById(R.id.button_register);
        textView_branch=findViewById(R.id.textview_branch);
        textview_semester=findViewById(R.id.textview_semester);
        username_edit=findViewById(R.id.username);
        //for getting username
        Intent intent=getIntent();
        String user=intent.getStringExtra("message key");
        username_edit.setText(user);

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference(" register_users");
        spinner_branch=findViewById(R.id.spinner_branch);
        spinner_branch.setOnItemSelectedListener(this);

        spinner_semester=findViewById(R.id.spinner_semester);
        spinner_semester.setOnItemSelectedListener(this);

        registerHelperClass=new RegisterHelperClass();
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,selected_branch);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter arrayAdapter2=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,selected_semester);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_branch.setAdapter(arrayAdapter);
        spinner_semester.setAdapter(arrayAdapter2);
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveValue(item,item2,item3);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        item=spinner_branch.getSelectedItem().toString();
        //textView_branch.setText(item);
        item2=spinner_semester.getSelectedItem().toString();
        //textview_semester.setText(item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    void SaveValue(String item, String item2,String item3){
        if (item=="Select branch" | item2=="select semester" | item3=="users" ){
            Toast.makeText(this,"please select a branch",Toast.LENGTH_SHORT).show();
        }
        else {
            registerHelperClass.setSelected_branch(item);
            registerHelperClass.setSelected_semester(item2);
            registerHelperClass.setUser(item3);
            String user=username_edit.getText().toString();
            databaseReference.child(user).setValue(registerHelperClass);
            Toast.makeText(this,"value saved",Toast.LENGTH_SHORT).show();
            String name=getIntent().getStringExtra("name");
            String email=getIntent().getStringExtra("email");

            Intent intent=new Intent(Register.this,Dashboard.class);
            intent.putExtra("message key",user);
            intent.putExtra("name",name);
            intent.putExtra("email",email);
            startActivity(intent);
        }
    }
}