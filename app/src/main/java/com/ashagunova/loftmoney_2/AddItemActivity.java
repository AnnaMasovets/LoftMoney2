package com.ashagunova.loftmoney_2;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {

    private EditText NameEditText;
    private EditText PriceEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        NameEditText = (EditText) findViewById(R.id.et_name);
        PriceEditText = (EditText) findViewById(R.id.et_price);



    }

}
