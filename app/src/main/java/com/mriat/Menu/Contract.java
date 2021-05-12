package com.mriat.Menu;

import java.util.ArrayList;
import java.util.HashMap;

public class Contract {

    public interface MenuContract{

        interface Model{

            interface OnFinishedListener {
                void onFinished(ArrayList<HashMap<String,String>> listHM);

                void onFailure(String t);
            }

            void getMenu();
        }

        interface Presenter{

            void requestMenu();
        }

        interface View{

            void onFinished(ArrayList<HashMap<String,String>> listHM);

            void onFailure(String error);

            void showProgress();
            void hideProgress();

        }
    }

    public interface SubMenuContract{

        interface Model{

            interface OnFinishedListener {
                void onFinished(ArrayList<HashMap<String,String>> listHM);

                void onFailure(String t);
            }

            void getMenu(String id , String pid);
        }

        interface Presenter{

            void requestMenu(String id , String pid);
        }

        interface View{

            void onFinished(ArrayList<HashMap<String,String>> listHM);

            void onFailure(String error);

        }
    }


}
