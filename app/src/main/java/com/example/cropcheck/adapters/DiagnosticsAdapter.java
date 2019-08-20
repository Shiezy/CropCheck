package com.example.cropcheck.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cropcheck.R;
import com.example.cropcheck.models.Diagnostic;

import java.util.ArrayList;
import java.util.List;

public class DiagnosticsAdapter extends RecyclerView.Adapter<DiagnosticsAdapter.ViewHolder>  {

    private Context context;
    private List<Diagnostic> diagnostics = new ArrayList<>();

    public DiagnosticsAdapter(Context context){
        this.context = context;
    }

    public void updateData(List<Diagnostic> diagnostics) {
        this.diagnostics = diagnostics;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView description;
        public TextView recommendation;

        public ViewHolder(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.description);
            recommendation = itemView.findViewById(R.id.recomendations);
        }
    }

    @NonNull
    @Override
    public DiagnosticsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.recommendations_row, viewGroup, false);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull DiagnosticsAdapter.ViewHolder viewHolder, int i) {
        final Diagnostic diagnostic = diagnostics.get(i);
        viewHolder.description.setText(diagnostic.getDescription());
        viewHolder.recommendation.setText(diagnostic.getRecommendation());
    }

    @Override
    public int getItemCount() {
        return diagnostics.size();
    }
}
