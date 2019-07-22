package com.example.cropcheck.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cropcheck.R;
import com.example.cropcheck.models.Risk;

import java.util.ArrayList;
import java.util.List;

public class RiskAdapter  extends RecyclerView.Adapter<RiskAdapter.ViewHolder> {

    private List<Risk> risks = new ArrayList<>();
    private Context context;

    public RiskAdapter(Context context){
        this.context = context;

    }
    public void updateData(List<Risk> risks) {

        this.risks = risks;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.risk_name);
        }
    }

    @NonNull
    @Override
    public RiskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.risk_item_view, viewGroup, false);

        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull RiskAdapter.ViewHolder viewHolder, int i) {
        final Risk risk = risks.get(i);
        TextView textView = viewHolder.name;
        textView.setText(risk.getName());
    }

    @Override
    public int getItemCount() {
        return risks.size();
    }
}
