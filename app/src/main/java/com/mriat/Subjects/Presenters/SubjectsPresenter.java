package com.mriat.Subjects.Presenters;

import com.mriat.Constants;
import com.mriat.Subjects.Contract;
import com.mriat.Subjects.Models.SubjectsModel;

import java.util.ArrayList;
import java.util.HashMap;

public class SubjectsPresenter implements Contract.SubjectsActivityContract.Presenter {

  private Contract.SubjectsActivityContract.View view ;
  private com.mriat.Menu.Contract.SubMenuContract.Model model;


  public SubjectsPresenter(Contract.SubjectsActivityContract.View homeActivity){
    view = homeActivity;
  }


  @Override
  public void requestSubjects(String id , String sid) {
    view.showProgress();
    new SubjectsModel(new Contract.SubjectsActivityContract.Model.OnFinishedListener() {
      @Override
      public void onFinished(ArrayList<HashMap<String,String>> listHM) {
        if (listHM!=null){
          Constants.setEmpty(listHM);
          view.fillData(listHM);
        }
        view.hideProgress();

      }

      @Override
      public void onFailure(String error) {
        view.hideProgress();
        view.onFailure(error);
      }
    }).getSubjects(id , sid);
  }

  @Override
  public void requestMySubjects(String id) {

    view.showProgress();
    new SubjectsModel(new Contract.SubjectsActivityContract.Model.OnFinishedListener() {
      @Override
      public void onFinished(ArrayList<HashMap<String,String>> listHM) {
        if (listHM!=null){
          Constants.setEmpty(listHM);
          view.fillData(listHM);
        }
        view.hideProgress();

      }

      @Override
      public void onFailure(String error) {
        view.hideProgress();
        view.onFailure("error_loading_sub");
      }
    }).getMySubjects(id);
  }

}