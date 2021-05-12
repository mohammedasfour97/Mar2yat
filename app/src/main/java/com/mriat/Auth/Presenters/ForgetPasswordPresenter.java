package com.mriat.Auth.Presenters;

import com.mriat.Auth.Contract;
import com.mriat.Auth.Models.ForgetPasswordModel;

public class ForgetPasswordPresenter implements Contract.ForgetPassword.Presenter {

    private Contract.ForgetPassword.View view;

    public ForgetPasswordPresenter(Contract.ForgetPassword.View view) {
        this.view = view;
    }

    @Override
    public void requestForgetPassword(String email) {

        new ForgetPasswordModel(new Contract.ForgetPassword.Model.OnFinishedListener() {
            @Override
            public void onFinished(String result) {

                view.hideProgress();
                view.onFinished(result);
            }

            @Override
            public void onFailure(String error) {

                view.hideProgress();
                view.onError("error");
            }
        }).getForget(email);
    }
}
