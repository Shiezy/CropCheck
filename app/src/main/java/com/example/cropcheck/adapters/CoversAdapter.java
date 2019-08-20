package com.example.cropcheck.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cropcheck.ClaimCoverActivity;
import com.example.cropcheck.R;
import com.example.cropcheck.models.Cover;

import java.util.ArrayList;
import java.util.List;

public class CoversAdapter extends RecyclerView.Adapter<CoversAdapter.ViewHolder> {

    private List<Cover> covers = new ArrayList<>();
    private Context context;
    public static Integer site_id;

    public CoversAdapter(Context context){
        this.context = context;

    }
    public void updateData(List<Cover> covers) {

        this.covers = covers;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView coverTitle;
        public ImageView nextButton;

        public ViewHolder(View itemView) {
            super(itemView);
            nextButton = (ImageView) itemView.findViewById(R.id.nextButton);
            coverTitle = (TextView) itemView.findViewById(R.id.coverTitle);
        }
    }

    @NonNull
    @Override
    public CoversAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.my_policy_row, viewGroup, false);

        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull CoversAdapter.ViewHolder viewHolder, int i) {
        // Get the data model based on position
        final Cover cover = covers.get(i);

        // Set item views based on your views and data model
        TextView textView = viewHolder.coverTitle;
        textView.setText(cover.getPolicy().getTitle());

        viewHolder.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(context, ClaimCoverActivity.class);
                t.putExtra("cover_id", cover.getId());
                t.putExtra("policy_id", cover.getPolicy_id());
                t.putExtra("user_id", cover.getUser_id());
                t.putExtra("farm_id", cover.getFarm_id());
                t.putExtra("expiry_date", cover.getExpiry_date());
                t.putExtra("start_date", cover.getStart_date());
                t.putExtra("status", cover.getStatus());
                t.putExtra("title", cover.getPolicy().getTitle());
                t.putExtra("premium", cover.getPolicy().getPremium());
                v.getContext().startActivity(t);
            }
        });
    }

    @Override
    public int getItemCount() {
        return covers.size();
    }
}
