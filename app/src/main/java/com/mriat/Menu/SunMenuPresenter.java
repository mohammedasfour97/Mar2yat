package com.mriat.Menu;

import com.mriat.Constants;

import java.util.ArrayList;
import java.util.HashMap;

public class SunMenuPresenter implements Contract.SubMenuContract.Presenter {

    private Contract.SubMenuContract.View view ;
    private Contract.SubMenuContract.Model model;
    private com.mriat.Subjects.Contract.AddSubjectContract.View add_subject_view;


    public SunMenuPresenter(Contract.SubMenuContract.View homeActivity){
        view = homeActivity;
    }

    public SunMenuPresenter(com.mriat.Subjects.Contract.AddSubjectContract.View add_subject_view){
        this.add_subject_view = add_subject_view;
    }


    @Override
    public void requestMenu(String id , String pid) {
        new SubMenuModel(new Contract.SubMenuContract.Model.OnFinishedListener() {
            @Override
            public void onFinished(ArrayList<HashMap<String,String>> listHM) {
                if (listHM!=null) {
                    Constants.setEmpty(listHM);
                    if (view!=null)
                        view.onFinished(listHM);
                    else add_subject_view.fillSubCategories(listHM);
                }
            }

            @Override
            public void onFailure(String error) {
                if (view!=null)
                view.onFailure(error);
                else add_subject_view.onFailure(error);
            }
        }).getMenu(id , pid);
    }
}
