package com.example.cropcheck;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.example.cropcheck.adapters.ImageAdapter;
import com.example.cropcheck.models.Image;
import com.example.cropcheck.models.ImageList;
import com.example.cropcheck.services.ImageService;
import com.example.cropcheck.utils.CoreUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageActivity extends AppCompatActivity {

    ImageAdapter adapter;
    List<ImageList> imageList;
    List<Image> images;
    int farm_id;
    int season_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Bundle b = getIntent().getExtras();
        if(b != null) {
            farm_id = b.getInt("farm_id");
            season_id = b.getInt("season_id");
        } else {
            farm_id =1;
            season_id = 1;
        }

        RecyclerView rv = findViewById(R.id.image_recycler_view);

        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(sglm);

        this.adapter = new ImageAdapter(getApplicationContext());

        rv.setAdapter(adapter);
        adapter.passparams(farm_id, season_id);
        imageList = new ArrayList<>();


        Call<List<Image>> call = CoreUtils.getAuthRetrofitClient(getToken()).create(ImageService.class).getImage(2, 1);
        call.enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                if (response.isSuccessful()){
                    images = response.body();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"successful",Toast.LENGTH_SHORT).show();
                            for (int i = 0; i<images.size(); i++){
                                String filename = images.get(i).getFilename();
                                imageList.add(new ImageList(filename));
                            }
                            imageList.add(new ImageList(true));

                            adapter.updateData(imageList);
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(),"Add photo",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {

                Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_SHORT).show();
            }
        });

//        imageList = new ArrayList<>();
//        imageList.add(new ImageList("5d540c1696499.jpg"));
//        imageList.add(new ImageList("5d540c1696499.jpg"));
//        imageList.add(new ImageList("5d540c1696499.jpg"));
//        imageList.add(new ImageList("5d540c1696499.jpg"));



    }
    public String getToken(){
        return PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("ACCESS_TOKEN", null);
    }


}
