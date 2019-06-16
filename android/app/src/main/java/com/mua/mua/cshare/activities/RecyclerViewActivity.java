package com.mua.mua.cshare.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.mua.mua.cshare.R;
import com.mua.mua.cshare.adapters.RVAdapter;
import com.mua.mua.cshare.model.Content;
import com.mua.mua.cshare.observer.CustomItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends Activity {

    private List<Content> contents;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recyclerview_activity);

        rv=(RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();
    }

    private void initializeData(){
        contents = new ArrayList<>();
        contents.add(new Content("pdf file 1", "memory te asd", R.drawable.pdf_icon));
        contents.add(new Content("image file 1", "internal e sad", R.drawable.image_icon));
        contents.add(new Content("pdf file 2", "memory teasd", R.drawable.pdf_icon));
    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(contents, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(RecyclerViewActivity.this, "Clicked Item: "+position,Toast.LENGTH_SHORT).show();
            }
        });
        rv.setAdapter(adapter);
    }
}
