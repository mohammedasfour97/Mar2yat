package com.mriat.Auth.Views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mriat.Auth.Contract;
import com.mriat.Auth.Presenters.ForgetPasswordPresenter;
import com.mriat.BaseActivity;
import com.mriat.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ForgetPasswordActivity extends BaseActivity implements Contract.ForgetPassword.View {

    private EditText email ;
    private Button send;
    private ImageView back;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        initUI();
        setListners();
    }


    private void initUI(){

        email = findViewById(R.id.email);
        send = findViewById(R.id.continuee);
        back = findViewById(R.id.back);
    }

    private void setListners(){

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showProgress();
                new ForgetPasswordPresenter(ForgetPasswordActivity.this).requestForgetPassword(email.getText().toString());
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void showProgress() {
        showProgressDialog();
    }

    @Override
    public void onFinished(String result) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, getResources().getString(R.string.error_forget), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }
}
