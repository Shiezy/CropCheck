package com.example.cropcheck.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cropcheck.ApplyPolicyActivity;
import com.example.cropcheck.R;
import com.example.cropcheck.models.Policy;
import com.example.cropcheck.models.Site;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class PolicyAdapter extends RecyclerView.Adapter<PolicyAdapter.ViewHolder> {

        private List<Policy> policies = new ArrayList<>();
        private Context context;

        public PolicyAdapter(Context context){
            this.context = context;

        }
    public void updateData(List<Policy> policies) {

        this.policies = policies;
        notifyDataSetChanged();
    }
        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView title;
            public ImageView direction_button;

            public ViewHolder(View itemView) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);
                direction_button = (ImageView) itemView.findViewById(R.id.direction_button);
                title = (TextView) itemView.findViewById(R.id.policyTitle);
            }
        }


    @Override
    public PolicyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.policy_row, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(PolicyAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        final Policy policy = policies.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.title;
        textView.setText(policy.getTitle());

        viewHolder.direction_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(context, ApplyPolicyActivity.class);
                t.putExtra("id", policy.getId());
                t.putExtra("premium", policy.getPremium());
                t.putExtra("terms", policy.getTerms());
                t.putExtra("title", policy.getTitle());
                t.putExtra("risk", (Serializable) policy.getRisks());
                v.getContext().startActivity(t);
            }
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return policies.size();
    }
}
