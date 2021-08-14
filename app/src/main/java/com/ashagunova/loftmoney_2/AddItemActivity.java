package com.ashagunova.loftmoney_2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText priceEditText;
    private Button addButton;

    private String mName;
    private String mPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        nameEditText = findViewById(R.id.et_name);
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mName = s.toString();
                checkEditTextHasText();
            }
        });
        priceEditText = findViewById(R.id.et_price);
        priceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPrice = s.toString();
                checkEditTextHasText();
            }
        });

        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(mName) && !TextUtils.isEmpty(mPrice)) {
                    setResult(
                        RESULT_OK,
                        new Intent().putExtra("name", mName).putExtra("price", mPrice));
                    finish();
            }
        });



    }

    public void checkEditTextHasText() {
        addButton.setEnabled(!TextUtils.isEmpty(mName) && !TextUtils.isEmpty(mPrice));
    }

}
