package com.example.gocar.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gocar.R;

import java.util.List;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.ProductViewHolder> {
    private Context mCtx;
    private List<cars> productList;

    public CarsAdapter(Context mCtx, List<cars> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }


    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.product_list, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarsAdapter.ProductViewHolder holder, int position) {
        cars product = productList.get(position);

        //loading the image
        Glide.with(mCtx)
                .load(product.getImage())
                .into(holder.imageView);

        holder.textViewModel_Name.setText(product.getModelName());
        holder.textViewProductionYear.setText(String.valueOf(product.getProductionYear()));
        holder.textViewLatitude.setText(String.valueOf(product.getLatitude()));
        holder.textViewLongitude.setText(String.valueOf(product.getLongitude()));
        holder.textViewFuelLevel.setText(String.valueOf(product.getFuelLevel()));

    }


    @Override
    public int getItemCount() {
        return productList.size();

    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewModel_Name, textViewProductionYear, textViewLatitude, textViewLongitude, textViewFuelLevel;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewModel_Name = itemView.findViewById(R.id.textViewModel_Name);
            textViewProductionYear = itemView.findViewById(R.id.textViewProductionYear);
            textViewLatitude = itemView.findViewById(R.id.textViewLatitude);
            textViewLongitude = itemView.findViewById(R.id.textViewLongitude);
            textViewFuelLevel = itemView.findViewById(R.id.textViewFuelLevel);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
