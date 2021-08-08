package com.ashagunova.loftmoney_2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class AddMoneyActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etPrice;
    private Button addButton;
    private Disposable disposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        etName = findViewById(R.id.et_name);
        etPrice = findViewById(R.id.et_price);
        addButton = findViewById(R.id.add_button);

        configureButton();
    }


    private void configureButton() {
        addButton.setOnClickListener(v -> {
            if (etName.getText().equals("") || etPrice.getText().equals("")) {
                Toast.makeText(getApplicationContext(), getString(R.string.fill_fields), Toast.LENGTH_LONG).show();
                return;
            }

            disposable = ((LoftApp) getApplication()).moneyApi.postMoney(
                    Integer.parseInt(etPrice.getText().toString()),
                    etName.getText().toString(),
                    "income",
                    getSharedPreferences(getString(R.string.app_name), 0).getString(LoftApp.AUTH_KEY, "")
            )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((Action) () -> {
                        Toast.makeText(getApplicationContext(), getString(R.string.success_added), Toast.LENGTH_LONG).show();
                        finish();
                    }, throwable -> {
                        Toast.makeText(getApplicationContext(), throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    });
        });
    }

    @Override
    protected void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }
}
