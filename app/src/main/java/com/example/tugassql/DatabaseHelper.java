package com.example.tugassql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "toko-db";
    private static final int DATABASE_VERSION = 1;

    private static final String PRODUCT_TABLE = "products";
    private static final String PRODUCT_ID = "id";
    private static final String PRODUCT_NAME = "name";
    private static final String PRODUCT_PRICE = "price";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_PRODUCT = "CREATE TABLE "+PRODUCT_TABLE+
                "("+PRODUCT_ID+" INTEGER PRIMARY KEY, "+
                PRODUCT_NAME+" TEXT, "+
                PRODUCT_PRICE+" INTEGER)";
        sqLiteDatabase.execSQL(CREATE_TABLE_PRODUCT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DATABASE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addProduct(Product product){
        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME,product.getName());
        values.put(PRODUCT_PRICE,product.getPrice());

        db.insert(PRODUCT_TABLE,null,values);
        db.close();
    }

    public Product getProductFromName(String name){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(PRODUCT_TABLE,
                new String[]{PRODUCT_ID,PRODUCT_NAME,PRODUCT_PRICE},
                PRODUCT_NAME+"=?",new String[]{name},
                null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
        }

        Product product = new Product();
        product.setId(cursor.getInt(0));
        product.setName(cursor.getString(1));
        product.setPrice(cursor.getInt(2));

        return product;
    }

    public List<Product> getAllProducts(){
        List<Product> allProducts = new ArrayList<Product>();
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * from "+PRODUCT_TABLE;
        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                Product product = new Product();
                product.setId(cursor.getInt(0));
                product.setName(cursor.getString(1));
                product.setPrice(cursor.getInt(2));

                allProducts.add(product);
            } while (cursor.moveToNext());
        }

        return allProducts;
    }

    public int updateProduct(Product product){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME,product.getName());
        values.put(PRODUCT_PRICE,product.getPrice());

        return db.update(PRODUCT_TABLE, values, PRODUCT_ID+"=?",
                new String[] {String.valueOf(product.getId())});
    }

    public void deleteProduct(Product product){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(PRODUCT_TABLE,PRODUCT_ID+"=?",
                new String[] {String.valueOf(product.getId())});
        db.close();
    }
}

