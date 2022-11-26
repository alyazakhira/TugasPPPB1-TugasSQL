package com.example.tugassql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class UpdateActivity extends AppCompatActivity {
    DatabaseHelper uDatabaseHelper;
    List<Product> products;
    EditText uNameEdt,uPriceEdt;
    Button uSubmitBtn;
    String name;
    int id,price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        uDatabaseHelper = new DatabaseHelper(this);
        products = uDatabaseHelper.getAllProducts();

        uNameEdt = findViewById(R.id.up_name);
        uPriceEdt = findViewById(R.id.up_price);
        uSubmitBtn = findViewById(R.id.up_submit_btn);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("PROD_ID");
        name = bundle.getString("PROD_NAME");
        price = bundle.getInt("PROD_PRICE");

        uNameEdt.setText(name);
        uPriceEdt.setText(Integer.toString(price));

        uSubmitBtn.setOnClickListener(view -> {
            for (Product item : products){
                if (item.getId() == id){

                    Product product = new Product();
                    product.setId(id);
                    product.setName(uNameEdt.getText().toString());
                    product.setPrice(Integer.parseInt(uPriceEdt.getText().toString()));

                    uDatabaseHelper.updateProduct(product);
                    Toast.makeText(UpdateActivity.this, "Product updated", Toast.LENGTH_SHORT).show();
                }
                continue;
            }
            Intent intent = new Intent(view.getContext(),MainActivity.class);
            startActivity(intent);
        });
    }
}