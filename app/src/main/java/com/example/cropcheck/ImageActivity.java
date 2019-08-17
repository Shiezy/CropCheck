package com.example.cropcheck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.cropcheck.adapters.ImageAdapter;
import com.example.cropcheck.models.ImageList;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {

    ImageAdapter adapter;
    List<ImageList> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        RecyclerView rv = findViewById(R.id.image_recycler_view);

        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(sglm);

        this.adapter = new ImageAdapter(getApplicationContext());

        rv.setAdapter(adapter);

        imageList = new ArrayList<>();
        imageList.add(new ImageList("5d540c1696499.jpg"));
        imageList.add(new ImageList("5d540c1696499.jpg"));
        imageList.add(new ImageList("5d540c1696499.jpg"));
        imageList.add(new ImageList("5d540c1696499.jpg"));


        imageList.add(new ImageList(true));

        adapter.updateData(imageList);
    }


}
