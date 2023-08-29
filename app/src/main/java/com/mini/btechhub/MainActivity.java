package com.mini.btechhub;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mini.btechhub.adapter.BookAdapter;
import com.mini.btechhub.model.Books;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
// *No use of this activity *//
public class MainActivity extends AppCompatActivity {

    List<Books> list=new ArrayList<>();
    RecyclerView recyclerView;
    BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        recyclerView=findViewById(R.id.recycler_books);
        adapter=new BookAdapter(list,MainActivity.this);
        recyclerView.setAdapter(adapter);

        loadData();
    }

    private void loadData() {
        FirebaseDatabase.getInstance().getReference()
                .child("books")
                .addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();

                        adapter.notifyDataSetChanged();

                        for(DataSnapshot dataSnapshot:snapshot.getChildren()) {

                            String title= Objects.requireNonNull(dataSnapshot.child("title").getValue()).toString();
                            String cover= Objects.requireNonNull(dataSnapshot.child("cover").getValue()).toString();
                            String content= Objects.requireNonNull(dataSnapshot.child("content").getValue()).toString();
                            String type= Objects.requireNonNull(dataSnapshot.child("type").getValue()).toString();
                            list.add(new Books(title,cover,content,type));
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}