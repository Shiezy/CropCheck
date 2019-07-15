package com.example.cropcheck.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.cropcheck.R;
import com.example.cropcheck.models.Policy;
import com.example.cropcheck.models.Site;

import java.util.List;


public class PolicyAdapter extends RecyclerView.Adapter<PolicyAdapter.ViewHolder> {

        private List<Policy> policies;
        Context context;

        public PolicyAdapter(Context context){
            this.context = context;

        }
    public void updateData(List<Policy> policies) {

        this.policies = policies;
        notifyDataSetChanged();
    }
        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView title;

            public ViewHolder(View itemView) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);

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
        Policy policy = policies.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.title;
        textView.setText(policy.getTitle());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return policies.size();
    }
}
