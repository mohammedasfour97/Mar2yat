package com.mriat.Presenters;

import com.mriat.Contract;
import com.mriat.Models.HomeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class HomePresenter implements Contract.HomeActivityContract.Presenter {

    private Contract.HomeActivityContract.View view ;
    private Contract.HomeActivityContract.Model model;


    public HomePresenter (Contract.HomeActivityContract.View homeActivity){
        view = homeActivity;
    }

    @Override
    public void requestAlbums(String id) {
        new HomeModel(new Contract.HomeActivityContract.Model.OnFinishedListener() {
            @Override
            public void onFinished(ArrayList<HashMap<String, String>> listHM) {
                if (listHM!=null)
                view.onFinished(listHM , "albums");
            }

            @Override
            public void onFailure(String error) {
                view.onFailure(error);
            }
        }).getAlbums(id);
    }

    @Override
    public void requestFixed(String id) {
        new HomeModel(new Contract.HomeActivityContract.Model.OnFinishedListener() {
            @Override
            public void onFinished(ArrayList<HashMap<String, String>> listHM) {
                if (listHM!=null)
                view.onFinished(listHM , "fixed");
            }

            @Override
            public void onFailure(String error) {
                view.onFailure(error);
            }
        }).getFixed(id);
    }

    @Override
    public void requestNew(String id) {
        new HomeModel(new Contract.HomeActivityContract.Model.OnFinishedListener() {
            @Override
            public void onFinished(ArrayList<HashMap<String, String>> listHM) {
                if (listHM!=null)
                view.onFinished(listHM , "new");
            }

            @Override
            public void onFailure(String error) {
                view.onFailure(error);
            }
        }).getNew(id);
    }

    @Override
    public void requestNews(String id) {
        new HomeModel(new Contract.HomeActivityContract.Model.OnFinishedListener() {
            @Override
            public void onFinished(ArrayList<HashMap<String, String>> listHM) {
                if (listHM!=null)
                view.onFinished(listHM , "news");
            }

            @Override
            public void onFailure(String error) {
                view.onFailure(error);
            }
        }).getNews(id);
    }

    @Override
    public void requestVarious(String id) {
        new HomeModel(new Contract.HomeActivityContract.Model.OnFinishedListener() {
            @Override
            public void onFinished(ArrayList<HashMap<String, String>> listHM) {
                if (listHM!=null)
                view.onFinished(listHM , "various");
            }

            @Override
            public void onFailure(String error) {
                view.onFailure(error);
            }
        }).getVarious(id);
    }

    @Override
    public void requestVedio(String id) {
        new HomeModel(new Contract.HomeActivityContract.Model.OnFinishedListener() {
            @Override
            public void onFinished(ArrayList<HashMap<String, String>> listHM) {
                if (listHM!=null)
                view.onFinished(listHM , "video");
            }

            @Override
            public void onFailure(String error) {
                view.onFailure(error);
            }
        }).getVedio(id);
    }
}
