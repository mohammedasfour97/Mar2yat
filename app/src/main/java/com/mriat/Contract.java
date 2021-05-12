package com.mriat;

import java.util.ArrayList;
import java.util.HashMap;

public class Contract {

    public static class HomeActivityContract{

        public interface Model{

            interface OnFinishedListener {
                void onFinished(ArrayList<HashMap<String,String>> listHM);

                void onFailure(String error);
            }

            void getAlbums(String id);
            void getFixed(String id);
            void getNew(String id);
            void getNews(String id);
            void getVarious(String id);
            void getVedio(String id);

        }

        public interface Presenter{

            void requestAlbums(String id);
            void requestFixed(String id);
            void requestNew(String id);
            void requestNews(String id);
            void requestVarious(String id);
            void requestVedio(String id);

        }

        public interface View{

            void onFinished(ArrayList<HashMap<String,String>> listHM , String title);

            void onFailure(String error);

            void showProgress();
            void hideProgress();
        }
    }
}
