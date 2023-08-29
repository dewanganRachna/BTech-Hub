package com.mini.btechhub;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mini.btechhub.adapter.BookAdapter;
import com.mini.btechhub.model.Books;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    List<Books> list=new ArrayList<>();
    RecyclerView recyclerView;
    BookAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=view.findViewById(R.id.recycler_books);
        adapter=new BookAdapter(list , (Activity) view.getContext());
        recyclerView.setAdapter(adapter);

        loadData();

        return view;
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