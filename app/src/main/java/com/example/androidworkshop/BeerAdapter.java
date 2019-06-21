package com.example.androidworkshop;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.androidworkshop.networking.Beer;

import java.util.ArrayList;
import java.util.List;

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.MyViewHolder> {

    private List<Beer> currentList = new ArrayList<>();

    public BeerAdapter(List<Beer> listOfBeers) {
        currentList.clear();
        currentList.addAll(listOfBeers);
    }

    public void updateData(List<Beer> listOfBeers) {
        currentList.clear();
        currentList.addAll(listOfBeers);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BeerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return new MyViewHolder(inflater.inflate(R.layout.item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BeerAdapter.MyViewHolder viewHolder, int position) {
        viewHolder.displayItem(currentList.get(position));
    }

    @Override
    public int getItemCount() {
        return currentList.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    class MyViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        TextView beerName;
        TextView beerDesc;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public MyViewHolder(@NonNull View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            beerName = (TextView) itemView.findViewById(R.id.beerName);
            beerDesc = (TextView) itemView.findViewById(R.id.beerDesc);
        }

        public void displayItem(Beer beer) {

            beerName.setText(beer.getName());
            beerDesc.setText(beer.getDescription());

        }

    }

}
