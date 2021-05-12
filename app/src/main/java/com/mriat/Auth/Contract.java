package com.mriat.Auth;

import java.util.ArrayList;
import java.util.HashMap;

public class Contract {

public interface LoginContract{

    interface Model{

        interface OnFinishedListener {
            void onFinished(ArrayList<HashMap<String,String>> listHM);

            void onFailure(String error);
        }

        void Login(String username , String password);
    }

    interface Presenter{

        void requestLogin(String username , String password);
    }

    interface View{

        void showProgress();
        void onFinished(ArrayList<HashMap<String,String>> listHM);
        void onError(String error);
        void hideProgress();

    }
}


public interface SignupContract{

        interface Model{

            interface OnFinishedListener {
                void onFinished(ArrayList<HashMap<String,String>> listHM);
                void onFinished(String listHM);

                void onFailure(String error);
            }

            void signup(String UserName ,String AppearName ,String Password ,String Email ,int ID_Gender ,int ID_Country ,
                        int ID_DefaultStyle ,int ID_DateFormat ,boolean MonthFullName ,boolean ActivateDaylight ,
                        int ID_TimeDifference ,int ID_WeekBegin ,boolean Status ,int ID_USER ,String picture);

            void checkUsers(String UserName, String AppearName, String Email);
            void getGender();
            void getCountries();
            void getStyles();
            void getDataFormat();
            void getTimeOptions();
            void getWeekDays();
            void getActivation(String ActivationCode, String NAME, String Email, int ID);
            void getReActivation(String Email);
            void uploadImage(byte[] img , String img_name);
            void checkUserBeforeUpdate(String UserName, String AppearName, String Email, int ID);
            void updateUser(int ID, String UserName, String AppearName, String Password, String Email, int ID_Gender, int ID_Country,
                            int ID_DefaultStyle, int ID_DateFormat, boolean MonthFullName, boolean ActivateDaylight, int ID_TimeDifference,
                            int ID_WeekBegin, boolean Status, int ID_USER, String picture, boolean AdminStatus);
        }

        interface Presenter{

            void requestCheckUsers(String UserName, String AppearName, String Email);
            void requestSignup(String UserName ,String AppearName ,String Password ,String Email ,int ID_Gender ,int ID_Country ,
                               int ID_DefaultStyle ,int ID_DateFormat ,boolean MonthFullName ,boolean ActivateDaylight ,
                               int ID_TimeDifference ,int ID_WeekBegin ,boolean Status ,int ID_USER ,String picture);
            void requestGender();
            void requestCountries();
            void requestStyles();
            void requestDataFormat();
            void requestWeekDays();
            void requestTimeOptions();
            void requestActivation(String ActivationCode, String NAME, String Email, int ID);
            void requestReActivation(String Email);
            void requestuploadImage(byte[] img , String img_name);
            void requestcheckUserBeforeUpdate(String UserName, String AppearName, String Email, int ID);
            void requestupdateUser(int ID, String UserName, String AppearName, String Password, String Email, int ID_Gender, int ID_Country,
                            int ID_DefaultStyle, int ID_DateFormat, boolean MonthFullName, boolean ActivateDaylight, int ID_TimeDifference,
                            int ID_WeekBegin, boolean Status, int ID_USER, String picture, boolean AdminStatus);
        }

        interface View{

            interface OnFinishedListener {
                void onFinished(ArrayList<HashMap<String,String>> listHM);

                void onFailure(String error);
            }


            void fillGender(ArrayList<HashMap<String,String>> listHM);
            void fillCountries(ArrayList<HashMap<String,String>> listHM);
            void fillStyles(ArrayList<HashMap<String,String>> listHM);
            void fillDateFormat(ArrayList<HashMap<String,String>> listHM);
            void fillWeekDays(ArrayList<HashMap<String,String>> listHM);
            void fillTimeOptions(ArrayList<HashMap<String,String>> listHM);
            void onFailure(String message);
            void onFinished(String message);
            void onUpdateFinished();
            void signup();
            void uploadImage();
            void updateUser();
            void activation(String id, String code, String activationCode);
            void showProgress();
            void hideProgress();

        }
    }


    public interface ForgetPassword{

        interface Model{

            interface OnFinishedListener {
                void onFinished(String result);

                void onFailure(String error);
            }

            void getForget(String email);
        }

        interface Presenter{

            void requestForgetPassword(String email);
        }

        interface View{

            void showProgress();
            void onFinished(String result);
            void onError(String error);
            void hideProgress();

        }

    }

}
