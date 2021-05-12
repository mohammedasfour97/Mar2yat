package com.mriat.Auth.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mriat.Auth.Contract;
import com.mriat.Auth.Presenters.LoginPresenter;
import com.mriat.BaseActivity;
import com.mriat.DatabaseHelper;
import com.mriat.ModelClasses.User;
import com.mriat.R;
import com.mriat.TinyDB;

import java.util.ArrayList;
import java.util.HashMap;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends BaseActivity implements Contract.LoginContract.View {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private Button signup, signin , visitor ;
    private EditText username, password;
    private LoginPresenter loginPresenter;
    private DatabaseHelper databaseHelper;
    private TinyDB tinyDB;
    private ProgressBar progressBar;
    private TextView forget;

    @Override
    protected void onStart() {
        super.onStart();

        progressBar = findViewById(R.id.progress);

        tinyDB = new TinyDB(this);

        loginPresenter = new LoginPresenter(this);

        if (!getSavedUserAndPassword()[0].equals("")){
            loginPresenter.requestLogin(getSavedUserAndPassword()[0], getSavedUserAndPassword()[1]);
        }
        else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUi();
        setListners();

        databaseHelper = new DatabaseHelper(this);


    }

    private void initUi() {

        signup = findViewById(R.id.btn_sign_up);
        signin = findViewById(R.id.btn_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        visitor = findViewById(R.id.btn_login_visitor);
        forget = findViewById(R.id.txt_forget_password);
    }

    private void setListners() {

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  new checkusers().execute();
                Intent intent = new Intent(LoginActivity.this , SignupActivity.class);
                intent.putExtra("edit" , "no");
                startActivity(intent);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validation();

                // startActivity(new Intent(LoginActivity.this , MainActivity.class));
            }
        });

        visitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startMainActivityActivity("app_name" , "");
            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this , ForgetPasswordActivity.class));
            }
        });
    }

    private void validation() {

        String susename, spassword;
        susename = username.getText().toString();
        spassword = password.getText().toString();

        if (TextUtils.isEmpty(susename)) {
            username.setError(getResources().getString(R.string.error_email));
            return;
        }
        if (TextUtils.isEmpty(spassword)) {
            password.setError(getResources().getString(R.string.error_pass));
            return;
        }

        showProgress();

            tinyDB.putString("username" , susename);
            tinyDB.putString("password" , spassword);
            loginPresenter.requestLogin(susename, spassword);

    }

    private String[] getSavedUserAndPassword(){

        String[] username_pass = new String[2];

        if (!tinyDB.getString("username").equals("")){
            username_pass[0] = tinyDB.getString("username");
            username_pass[1] = tinyDB.getString("password");
        }
        else {
            username_pass[0] = "" ;
        }
        return username_pass;
    }


    private void updateUser(ArrayList<HashMap<String, String>> listHM){

            User user = new User();
            user.fillUserInfo(listHM);

            databaseHelper.deleteAndInsertUser(user);

    }


    private void fillUserInfo(ArrayList<HashMap<String, String>> listHM) {


            User user = new User();
            user.fillUserInfo(listHM);

            databaseHelper.insertUser(user);


    }


    @Override
    public void showProgress() {
        showProgressDialog();
    }

    @Override
    public void onFinished(ArrayList<HashMap<String, String>> listHM) {

        if (databaseHelper.getUser().size() != 0) {
            updateUser(listHM);
        }
        else {
            fillUserInfo(listHM);
        }


        startMainActivityActivity("app_name" , listHM.get(0).get("AppearName"));


    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }


}


