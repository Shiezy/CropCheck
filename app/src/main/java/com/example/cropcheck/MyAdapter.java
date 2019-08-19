package com.example.cropcheck;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cropcheck.models.Site;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Site> sites = new ArrayList<>();
    private Context context;

    public void updateData(List<Site> sites) {

        this.sites = sites;
        notifyDataSetChanged();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView site_name;
        public TextView village;
        public TextView county;
        public TextView division;
        public TextView tvCounter;
        public MyViewHolder(View v) {
            super(v);
            site_name = v.findViewById(R.id.site_name);
            village = v.findViewById(R.id.village);
            county = v.findViewById(R.id.county);
            division = v.findViewById(R.id.division);
            tvCounter = v.findViewById(R.id.tvCounter);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Context context) {
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_view, parent, false);

        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final Site current = sites.get(position);

//        holder.tvCounter.setText(position);

        holder.site_name.setText(sites.get(position).getSite_name());
        holder.village.setText(sites.get(position).getVillage());
        holder.county.setText(sites.get(position).getCounty());
        holder.division.setText(sites.get(position).getDivision());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(context, SiteActivity.class);
                t.putExtra("id",current.getId());
                t.putExtra("site_name",current.getSite_name());
                t.putExtra("division", current.getDivision());
                t.putExtra("county", current.getCounty());
                t.putExtra("village", current.getVillage());
                v.getContext().startActivity(t);
//                Toast.makeText(context, "You Clicked This Item" ,Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return sites.size();
    }
}