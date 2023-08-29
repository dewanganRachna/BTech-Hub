package com.mini.btechhub.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.mini.btechhub.R;
import com.mini.btechhub.adapter.TextBookAdapter;
import com.mini.btechhub.extrahelper.ViewPdf;
import com.mini.btechhub.model.TextBook;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {


    String title,content,type,cover;

    TextBookAdapter adapter;
    List<TextBook> list=new ArrayList<>();
    ViewPager2 viewpager2;
    // RecyclerView recyclerView;
    LinearLayout linearLayout;
    ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Bundle bundle=getIntent().getExtras();

        title=bundle.getString("title");
        content=bundle.getString("content");
        type=bundle.getString("type");
        cover=bundle.getString("cover");

        linearLayout=findViewById(R.id.pdfViewer);
        loader=findViewById(R.id.pdfloader);
        viewpager2=findViewById(R.id.text_book);

        //  Log.e("title",title);
        //Log.e("content",content);
        //  Log.e("type",type);

        if(type.contains("text")) {
            linearLayout.setVisibility(View.GONE);
            loader.setVisibility(View.VISIBLE);
            viewpager2.setVisibility(View.VISIBLE);
            initText();

        }else{
            linearLayout.setVisibility(View.VISIBLE);
            loader.setVisibility(View.VISIBLE);


            viewpager2.setVisibility(View.GONE);

            new ViewPdf(content,linearLayout,loader,DetailActivity.this);
            loader.setVisibility(View.GONE);
        }
    }

    private void initText(){
      /*  recyclerView=findViewById(R.id.recycler_view);
        adapter=new TextBookAdapter(list,DetailActivity.this);
        recyclerView.setAdapter(adapter);
*/


        loader.setVisibility(View.GONE);
        adapter =new TextBookAdapter(list,DetailActivity.this);

        viewpager2.setAdapter(adapter);


        if(content.contains("~~")){
            String[] data=content.split("~~");


            for (String datum : data) {
                String chaptername = datum.split("~")[0];
                String chapterContent = datum.split("~")[1];
                //  Log.v("",chaptername);
                // Log.v("",chapterContent);

                list.add(new TextBook(chaptername, chapterContent));
                adapter.notifyDataSetChanged();
            }
        }else{

            if(content.contains("~")){
                String chaptername = content.split("~")[0];
                String chapterContent = content.split("~")[1];

                list.add(new TextBook(chaptername,chapterContent));
                adapter.notifyDataSetChanged();

            }
        }



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();



        if(type.contains("pdf"))
            ViewPdf.stoppdf();
    }
}