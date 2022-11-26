package com.example.tugassql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Product> products;
    RecyclerView productRecycle;
    Button mAddBtn;
    DatabaseHelper mDatabaseHelper;
    ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabaseHelper = new DatabaseHelper(this);
        products = mDatabaseHelper.getAllProducts();
        adapter = new ProductAdapter(this,products);

        productRecycle = findViewById(R.id.rv_product);
        productRecycle.setLayoutManager(new LinearLayoutManager(this));
        productRecycle.setAdapter(adapter);

        mAddBtn = findViewById(R.id.main_add_btn);
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),CreateActivity.class);
                startActivity(intent);
            }
        });
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(productRecycle);
    }

    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            Product deletedProduct = products.get(position);
            products.remove(position);
            mDatabaseHelper.deleteProduct(deletedProduct);
            Toast.makeText(MainActivity.this, "Delete Success", Toast.LENGTH_SHORT).show();
        }
    };
}