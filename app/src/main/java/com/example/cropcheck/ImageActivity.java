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
        imageList = new ArrayList<>();

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


        Call<List<ImageList>> call = CoreUtils.getAuthRetrofitClient(getToken()).create(ImageService.class).getImage(farm_id, season_id);
        call.enqueue(new Callback<List<ImageList>>() {
            @Override
            public void onResponse(Call<List<ImageList>> call, final Response<List<ImageList>> response) {
                if (response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            List<ImageList> im=response.body();
                            im.add(new ImageList(true));

                            adapter.updateData(im);

                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(),"Add photo",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<ImageList>> call, Throwable t) {

                Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_SHORT).show();
            }
        });


        imageList.add(new ImageList(true));

        adapter.updateData(imageList);



    }
    public String getToken(){
        return PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("ACCESS_TOKEN", null);
    }


}
