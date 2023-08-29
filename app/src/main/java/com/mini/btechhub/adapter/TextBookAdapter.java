package com.mini.btechhub.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mini.btechhub.R;
import com.mini.btechhub.model.TextBook;

import com.mini.btechhub.R;

import java.util.List;

public class TextBookAdapter extends RecyclerView.Adapter<TextBookAdapter.TextBookHolder> {


    List<TextBook> list;
    Activity activity;
    public TextBookAdapter(List<TextBook> list, Activity activity){
        this.list=list;
        this.activity=activity;
    }


    @NonNull
    @Override
    public TextBookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TextBookHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TextBookHolder holder, int position) {
        //  TextBook textBook=list.get(position);
        //TextBook textbook = list.get(position);

        TextBook textbook = list.get(position);
        holder.chapter.setText(textbook.getChapter());
        holder.chapter_name.setText(textbook.getChaptername());
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public static class TextBookHolder extends RecyclerView.ViewHolder{

        TextView chapter_name,chapter;
        public TextBookHolder(@NonNull View itemView) {
            super(itemView);
            chapter_name=itemView.findViewById(R.id.text_book_chaptername);
            chapter=itemView.findViewById(R.id.Text_book_content);
        }
    }
}