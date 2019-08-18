package com.example.cropcheck.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cropcheck.ApplyPolicyActivity;
import com.example.cropcheck.ImageActivity;
import com.example.cropcheck.R;
import com.example.cropcheck.SiteActivity;
import com.example.cropcheck.UploadImageActivity;
import com.example.cropcheck.models.ImageList;
import com.example.cropcheck.utils.CoreUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public  class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.GridItemViewHolder> {


    private Context c;
    private List<ImageList> imageList;
    private int farm_id;
    private int season_id;

    public void updateData(List<ImageList> imageList) {

        this.imageList = imageList;
    }

    public void passparams(int farm_id, int season_id){
        this.season_id = season_id;
        this.farm_id = farm_id;
    }

    public class GridItemViewHolder extends RecyclerView.ViewHolder {
        SquareImageView siv;

        public GridItemViewHolder(View view) {
            super(view);
            siv = view.findViewById(R.id.siv);
        }
    }

    public ImageAdapter(Context c) {
        this.c = c;


    }

    @Override
    public GridItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if(viewType==0){
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_content, parent, false);
        }else{
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_image_content, parent, false);
        }
        return new GridItemViewHolder(itemView);
    }


    @Override
    public int getItemViewType(int position) {
        ImageList current = imageList.get(position);
        if(current.is_last){
            return 1;
        }
        return 0;

    }

    @Override
    public void onBindViewHolder(GridItemViewHolder holder, int position) {
        if(getItemViewType(position)==0){
            final String file_name = imageList.get(position).filename;

            Picasso.get()
                    .load(CoreUtils.base_url +"get_farm_images/"+file_name)
                    .resize(250, 250)
                    .centerCrop()
                    .into(holder.siv);


            holder.siv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //handle click event on image
                }
            });
        }
        else {
            holder.siv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent t = new Intent(c, UploadImageActivity.class);
                    Toast.makeText(c,"Add photo",Toast.LENGTH_SHORT).show();
                    Bundle b = new Bundle();
                    b.putInt("farm_id", farm_id); //Your id
                    b.putInt("season_id", season_id); //Your id
                    t.putExtras(b); //Put your id to your next Intent
                    v.getContext().startActivity(t);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
}
