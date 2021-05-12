package com.mriat.Auth.Presenters;

import com.mriat.Auth.Contract;
import com.mriat.Auth.Models.LoginModel;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginPresenter implements Contract.LoginContract.Presenter {

    private Contract.LoginContract.View loginActivity;

    public LoginPresenter (Contract.LoginContract.View loginActivity){
        this.loginActivity = loginActivity;
    }
    @Override
    public void requestLogin(String username , String password) {

        new LoginModel(new Contract.LoginContract.Model.OnFinishedListener() {
            @Override
            public void onFinished(ArrayList<HashMap<String, String>> listHM) {
                loginActivity.hideProgress();
                if (!listHM.get(0).containsKey("error")){
                    loginActivity.onFinished(listHM);
                }
                else {
                    loginActivity.onError(listHM.get(0).get("error"));
                }


            }

            @Override
            public void onFailure(String error) {
                loginActivity.hideProgress();
                loginActivity.onError(error);
            }
        }).Login(username , password);

    }
}
