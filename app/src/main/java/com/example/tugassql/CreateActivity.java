package com.example.tugassql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateActivity extends AppCompatActivity {
    EditText cNameEdt, cPriceEdt;
    Button cSubmit;
    DatabaseHelper cDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        cDatabaseHelper = new DatabaseHelper(this);

        cNameEdt = findViewById(R.id.create_name);
        cPriceEdt = findViewById(R.id.create_price);
        cSubmit = findViewById(R.id.create_submit_btn);

        cSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product product = new Product();
                product.setName(cNameEdt.getText().toString());
                product.setPrice(Integer.parseInt(cPriceEdt.getText().toString()));

                cDatabaseHelper.addProduct(product);
                Toast.makeText(CreateActivity.this, "Product Added", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}