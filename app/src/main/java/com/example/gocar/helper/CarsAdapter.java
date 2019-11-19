package com.example.gocar.helper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gocar.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.ProductViewHolder> {
    private View view;
    private Context mCtx;
    private List<cars> productList;
    public cars product;
    private OnCarListener mylistener;

    public CarsAdapter(Context mCtx, List<cars> productList, OnCarListener mylistener ) {
        this.mCtx = mCtx;
        this.productList = productList;
        this.mylistener = mylistener;

    }
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        view = inflater.inflate(R.layout.product_list, parent, false);
        return new ProductViewHolder(view, mylistener);


    }
    @Override
    public void onBindViewHolder(@NonNull final CarsAdapter.ProductViewHolder holder, final int position) {
        product = productList.get(position);
        holder.textViewModel_Name.setText(product.getModelName());
        holder.textViewProductionYear.setText(String.valueOf(product.getProductionYear()));
        holder.textViewdist.setText(String.valueOf(product.getdist()));
        holder.textViewFuelLevel.setText(String.valueOf(product.getFuelLevel()));
//        holder.textViewLatitude.setText(String.valueOf(product.getLatitude()));
        Picasso.get().load("http://192.168.1.6/" + product.getImage()).into(holder.imageView);
        Log.i("nadine",product.getImage());

    }
    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewModel_Name, textViewProductionYear, textViewLatitude, textViewlongitude, textViewdist, textViewFuelLevel;

        ImageView imageView;
        OnCarListener mylistener;

        public ProductViewHolder(View itemView, OnCarListener mylistener) {
            super(itemView);
            this.mylistener = mylistener;
            textViewModel_Name = itemView.findViewById(R.id.textViewModel_Name);
            textViewProductionYear = itemView.findViewById(R.id.textViewProductionYear);
            //textViewLatitude = itemView.findViewById(R.id.textViewLatitude);
            textViewdist = itemView.findViewById(R.id.textViewdist);
            textViewFuelLevel = itemView.findViewById(R.id.textViewFuelLevel);
            imageView = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v){
            mylistener.oncarclick(getAdapterPosition());
        }







}
        public interface OnCarListener{
            void oncarclick(int position);

        }




}
