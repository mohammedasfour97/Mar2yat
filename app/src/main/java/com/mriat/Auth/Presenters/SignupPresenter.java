package com.mriat.Auth.Presenters;

import android.util.Log;

import com.mriat.Auth.Contract;
import com.mriat.Auth.Models.SignupModel;

import java.util.ArrayList;
import java.util.HashMap;

public class SignupPresenter implements Contract.SignupContract.Presenter {

    private Contract.SignupContract.View SignUpActivity ;
    private com.mriat.Subjects.Contract.AddSubjectContract.View view;

    public SignupPresenter(com.mriat.Subjects.Contract.AddSubjectContract.View view) {
        this.view = view;
    }

    public SignupPresenter (Contract.SignupContract.View signUpActivity){
        this.SignUpActivity = signUpActivity;
    }


    @Override
    public void requestCheckUsers(String UserName, String AppearName, String Email) {

        new SignupModel(new Contract.SignupContract.Model.OnFinishedListener() {
            @Override
            public void onFinished(ArrayList<HashMap<String, String>> listHM) {

                if (!listHM.get(0).get("UserName").equals("0")){
                    SignUpActivity.hideProgress();
                    SignUpActivity.onFailure("u");
                }
                else if (!listHM.get(0).get("AppearName").equals("0")){
                    SignUpActivity.hideProgress();
                    SignUpActivity.onFailure("a");
                }
                else if (!listHM.get(0).get("Email").equals("0")){
                    SignUpActivity.hideProgress();
                    SignUpActivity.onFailure("e");
                }
                else {
                    SignUpActivity.signup();
                }

            }

            @Override
            public void onFinished(String listHM) {

            }

            @Override
            public void onFailure(String error) {
                SignUpActivity.hideProgress();
                SignUpActivity.onFailure(error);
            }
        }).checkUsers(UserName , AppearName , Email);
    }



    @Override
    public void requestSignup(String UserName, String AppearName, String Password,
                              String Email, int ID_Gender, int ID_Country, int ID_DefaultStyle, int ID_DateFormat,
                              boolean MonthFullName, boolean ActivateDaylight, int ID_TimeDifference, int ID_WeekBegin,
                              boolean Status, int ID_USER, String picture) {

        new SignupModel(new Contract.SignupContract.Model.OnFinishedListener() {
            @Override
            public void onFinished(ArrayList<HashMap<String, String>> listHM) {
                if (!listHM.get(0).get("ID").equals("0")) {
                    Log.d("activationcodde", listHM.get(0).get("ActivationCode"));
                    SignUpActivity.activation(listHM.get(0).get("ID") , listHM.get(0).get("Code") , listHM.get(0).get("ActivationCode"));
                }
                else {
                    SignUpActivity.hideProgress();
                    SignUpActivity.onFailure("");
                }

            }

            @Override
            public void onFinished(String listHM) {

            }

            @Override
            public void onFailure(String error) {
                SignUpActivity.hideProgress();
                SignUpActivity.onFailure(error);
            }
        }).signup(UserName, AppearName, Password,
                Email, ID_Gender, ID_Country, ID_DefaultStyle, ID_DateFormat,
        MonthFullName, ActivateDaylight, ID_TimeDifference, ID_WeekBegin,
        Status, ID_USER, picture);

    }


    @Override
    public void requestActivation(String ActivationCode, String NAME, String Email, int ID) {
        new SignupModel(new Contract.SignupContract.Model.OnFinishedListener() {
            @Override
            public void onFinished(ArrayList<HashMap<String, String>> listHM) {
                ///////////////
            }

            @Override
            public void onFinished(String listHM) {

                if (listHM.equals("تم الارسال")) {
                    SignUpActivity.hideProgress();
                    SignUpActivity.onFinished(listHM);
                }
                else
                    onFailure("error_sending_activation");
            }

            @Override
            public void onFailure(String error) {
                SignUpActivity.hideProgress();
                SignUpActivity.onFailure(error);
            }
        }).getActivation(ActivationCode , NAME , Email , ID);
    }

    @Override
    public void requestReActivation(String Email) {
        new SignupModel(new Contract.SignupContract.Model.OnFinishedListener() {
            @Override
            public void onFinished(ArrayList<HashMap<String, String>> listHM) {
                if (listHM.size()>0){
                    SignUpActivity.hideProgress();
                    SignUpActivity.onFinished("");
                }
                else {
                    onFailure("");
                }
            }

            @Override
            public void onFinished(String listHM) {
                /////////////
            }

            @Override
            public void onFailure(String error) {
                SignUpActivity.hideProgress();
                SignUpActivity.onFailure("");
            }
        }).getReActivation(Email);
    }

    @Override
    public void requestuploadImage(byte[] img, String img_name) {

        if (img!= null) {
            new SignupModel(new Contract.SignupContract.Model.OnFinishedListener() {
                @Override
                public void onFinished(ArrayList<HashMap<String, String>> listHM) {
                    /////////////////
                }

                @Override
                public void onFinished(String listHM) {

                    if (listHM.equals("Done")) {
                        SignUpActivity.updateUser();
                    } else {
                        onFailure("error_upload_img");
                    }
                }

                @Override
                public void onFailure(String error) {
                    SignUpActivity.hideProgress();
                    onFailure(error);
                }
            }).uploadImage(img, img_name);
        }
        else {
            SignUpActivity.updateUser();
        }
    }

    @Override
    public void requestcheckUserBeforeUpdate(String UserName, String AppearName, String Email, int ID) {
        new SignupModel(new Contract.SignupContract.Model.OnFinishedListener() {
            @Override
            public void onFinished(ArrayList<HashMap<String, String>> listHM) {

                if (!listHM.get(0).get("UserName").equals("0")){
                    SignUpActivity.onFailure("u");
                }
                else if (!listHM.get(0).get("AppearName").equals("0")){
                    SignUpActivity.onFailure("a");
                }
                else if (!listHM.get(0).get("Email").equals("0")){
                    SignUpActivity.onFailure("e");
                }
                else {
                    SignUpActivity.uploadImage();
                }

            }

            @Override
            public void onFinished(String listHM) {

            }

            @Override
            public void onFailure(String error) {
                SignUpActivity.hideProgress();
                SignUpActivity.onFailure(error);
            }
        }).checkUserBeforeUpdate(UserName , AppearName , Email , ID);

    }

    @Override
    public void requestupdateUser(int ID, String UserName, String AppearName, String Password, String Email, int ID_Gender, int ID_Country,
                                  int ID_DefaultStyle, int ID_DateFormat, boolean MonthFullName, boolean ActivateDaylight,
                                  int ID_TimeDifference, int ID_WeekBegin, boolean Status, int ID_USER, String picture,
                                  boolean AdminStatus) {
        new SignupModel(new Contract.SignupContract.Model.OnFinishedListener() {
            @Override
            public void onFinished(ArrayList<HashMap<String, String>> listHM) {

            }

            @Override
            public void onFinished(String listHM) {
                SignUpActivity.hideProgress();

            }

            @Override
            public void onFailure(String error) {
                SignUpActivity.hideProgress();
                SignUpActivity.onFailure("");
            }
        }).updateUser(ID , UserName , AppearName , Password , Email , ID_Gender , ID_Country , ID_DefaultStyle , ID_DateFormat , MonthFullName,
                ActivateDaylight , ID_TimeDifference , ID_WeekBegin , Status , ID_USER , picture , AdminStatus);
    }


    @Override
    public void requestGender() {
        new SignupModel(new Contract.SignupContract.Model.OnFinishedListener() {
            @Override
            public void onFinished(ArrayList<HashMap<String, String>> listHM) {
                SignUpActivity.fillGender(listHM);
            }

            @Override
            public void onFinished(String listHM) {

            }

            @Override
            public void onFailure(String error) {

            }
        }).getGender();
    }

    @Override
    public void requestCountries() {
        new SignupModel(new Contract.SignupContract.Model.OnFinishedListener() {
            @Override
            public void onFinished(ArrayList<HashMap<String, String>> listHM) {
                if (SignUpActivity!=null)
                SignUpActivity.fillCountries(listHM);
                else view.fillCountries(listHM);
            }

            @Override
            public void onFinished(String listHM) {

            }


            @Override
            public void onFailure(String error) {
                if (SignUpActivity!=null)
                    SignUpActivity.onFailure(error);
                else view.onFailure(error);
            }
        }).getCountries();
    }

    @Override
    public void requestStyles() {
        new SignupModel(new Contract.SignupContract.Model.OnFinishedListener() {
            @Override
            public void onFinished(ArrayList<HashMap<String, String>> listHM) {
                SignUpActivity.fillStyles(listHM);
            }

            @Override
            public void onFinished(String listHM) {

            }

            @Override
            public void onFailure(String error) {

            }
        }).getStyles();
    }

    @Override
    public void requestDataFormat() {
        new SignupModel(new Contract.SignupContract.Model.OnFinishedListener() {
            @Override
            public void onFinished(ArrayList<HashMap<String, String>> listHM) {
                SignUpActivity.fillDateFormat(listHM);
            }

            @Override
            public void onFinished(String listHM) {

            }

            @Override
            public void onFailure(String error) {

            }
        }).getDataFormat();
    }

    @Override
    public void requestWeekDays() {
        new SignupModel(new Contract.SignupContract.Model.OnFinishedListener() {
            @Override
            public void onFinished(ArrayList<HashMap<String, String>> listHM) {
                SignUpActivity.fillWeekDays(listHM);
            }

            @Override
            public void onFinished(String listHM) {

            }

            @Override
            public void onFailure(String error) {

            }
        }).getWeekDays();
    }

    @Override
    public void requestTimeOptions() {
        new SignupModel(new Contract.SignupContract.Model.OnFinishedListener() {
            @Override
            public void onFinished(ArrayList<HashMap<String, String>> listHM) {
                SignUpActivity.fillTimeOptions(listHM);
            }

            @Override
            public void onFinished(String listHM) {

            }

            @Override
            public void onFailure(String error) {

            }
        }).getTimeOptions();
    }



}
