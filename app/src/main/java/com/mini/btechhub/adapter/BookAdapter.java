package com.mini.btechhub.adapter;

import static com.bumptech.glide.Glide.with;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.mini.btechhub.R;
import com.mini.btechhub.activity.DetailActivity;
import com.mini.btechhub.model.Books;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {


    List<Books> list;
    Activity activity;


    public BookAdapter(List<Books> list,Activity activity){
        this.list=list;
        this.activity=activity;

    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_books,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position) {
        Books books= list.get(position);

        holder.title.setText(books.getTitle());


        Glide.with(activity).load(books.getCover())
                .skipMemoryCache(true)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.cover);
        holder.itemView.setOnClickListener(view -> {

            Intent intent=new Intent(activity, DetailActivity.class);
            intent.putExtra("title",books.getTitle());
            intent.putExtra("cover",books.getCover());
            intent.putExtra("content",books.getContent());
            intent.putExtra("type", books.getType());
            activity.startActivity(intent);
        });

    }



    @Override
    public int getItemCount() {

        return list.size();
    }

    public static class BookHolder extends RecyclerView.ViewHolder{
        ImageView cover;
        TextView title;

        public BookHolder(@NonNull View itemView) {
            super(itemView);
            cover=itemView.findViewById(R.id.book_cover);
            title=itemView.findViewById(R.id.book_title);

        }
    }


}