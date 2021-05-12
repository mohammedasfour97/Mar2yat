package com.mriat.Menu;

import java.util.ArrayList;
import java.util.HashMap;

public class MainMenuPresenter implements Contract.MenuContract.Presenter {

    private Contract.MenuContract.View view ;
    private Contract.MenuContract.Model model;
    private com.mriat.Subjects.Contract.AddSubjectContract.View add_subject_view;


    public MainMenuPresenter(Contract.MenuContract.View homeActivity){
        view = homeActivity;
    }

    public MainMenuPresenter(com.mriat.Subjects.Contract.AddSubjectContract.View homeActivity){
        add_subject_view = homeActivity;
    }

    @Override
    public void requestMenu() {
        new MainMenuModel(new Contract.MenuContract.Model.OnFinishedListener() {
            @Override
            public void onFinished(ArrayList<HashMap<String,String>> listHM) {
                if (view!=null) {
                    view.hideProgress();
                        view.onFinished(listHM);
                    }
                else add_subject_view.fillMainCategories(listHM);

            }

            @Override
            public void onFailure(String error) {
                if (view!=null) {
                    view.hideProgress();
                    view.onFailure(error);
                }
                else add_subject_view.onFailure(error);
            }
        }).getMenu();
    }
}
