package com.example.tugassql;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{
    List<Product> products;
    LayoutInflater inflater;

    public ProductAdapter(Context context, List<Product> products) {
        this.products = products;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.iId.setText(Integer.toString(product.getId()));
        holder.iName.setText(product.getName());
        holder.iPrice.setText(Integer.toString(product.getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), UpdateActivity.class);
                intent.putExtra("PROD_ID", product.getId());
                intent.putExtra("PROD_NAME", product.getName());
                intent.putExtra("PROD_PRICE", product.getPrice());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView iId,iName,iPrice;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            iId = itemView.findViewById(R.id.i_id);
            iName = itemView.findViewById(R.id.i_name);
            iPrice = itemView.findViewById(R.id.i_price);
        }
    }

}
