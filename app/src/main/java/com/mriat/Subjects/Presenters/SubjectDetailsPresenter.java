package com.mriat.Subjects.Presenters;

import android.util.Log;

import com.mriat.Constants;
import com.mriat.ModelClasses.Comment;
import com.mriat.R;
import com.mriat.Subjects.Contract;
import com.mriat.Subjects.Models.SubjectsDetailsModel;

import java.util.ArrayList;
import java.util.HashMap;

public class SubjectDetailsPresenter implements Contract.SubjectsDetailsContract.Presenter {

  private Contract.SubjectsDetailsContract.View view ;
  private com.mriat.Menu.Contract.SubMenuContract.Model model;


  public SubjectDetailsPresenter(Contract.SubjectsDetailsContract.View homeActivity){
    view = homeActivity;
  }


  @Override
  public void requestSubjectDetails(String sid , String id) {
    new SubjectsDetailsModel(new Contract.SubjectsDetailsContract.Model.OnFinishedListener() {
      @Override
      public void onFinished(ArrayList<HashMap<String,String>> listHM) {
        view.hideProgress();
        Constants.setEmpty(listHM);
        view.fillData(listHM);
      }

      @Override
      public void onFinished(String result) {
        /////////////////////////////
      }

      @Override
      public void onFailure(String error) {
        view.hideProgress();
        view.onFailure(R.string.error_loading_subject);
        Log.d("eeeeeeeeeeeeeeeee", error);
      }
    }).getSubjectsDetails(sid , id);
  }


  @Override
  public void requestComments(String id) {

    new SubjectsDetailsModel(new Contract.SubjectsDetailsContract.Model.OnFinishedListener() {
      @Override
      public void onFinished(ArrayList<HashMap<String,String>> listHM) {
        if (listHM!=null) {
          Constants.setEmpty(listHM);
          view.fillComments(listHM);
        }
        view.hideProgress();
      }

      @Override
      public void onFinished(String result) {
        ////////////////////////
      }

      @Override
      public void onFailure(String error) {
        view.hideProgress();
        view.onFailure(R.string.error_loading_comments);
      }
    }).getComments(id);
  }


  @Override
  public void requestAddComments(Comment comment) {

    new SubjectsDetailsModel(new Contract.SubjectsDetailsContract.Model.OnFinishedListener() {
      @Override
      public void onFinished(ArrayList<HashMap<String, String>> listHM) {

        if (!listHM.get(0).get("ID").equals("0")){
          view.hideProgress();
          view.onFinished("succ_add_comment");
        }
        else
          onFailure("");

      }

      @Override
      public void onFinished(String result) {
      //  view.onFinished();
      }

      @Override
      public void onFailure(String error) {
        view.hideProgress();
        Log.d("errrrrrrrror", error);
        view.onFailure(R.string.error_add_comment);
      }
    }).addComment(comment);
  }


  @Override
  public void requestuploadImage(byte[] img, final Comment comment) {

    new SubjectsDetailsModel(new Contract.SubjectsDetailsContract.Model.OnFinishedListener() {
      @Override
      public void onFinished(ArrayList<HashMap<String, String>> listHM) {
        ///////////////////
      }

      @Override
      public void onFinished(String result) {

        if (result.equals("Done")) {
          view.addComment(comment);
          Log.d("onfinonfin", result);
        }
        // view.addComment(comment);
      }

      @Override
      public void onFailure(String error) {
        view.hideProgress();
        view.onFailure(R.string.error_upload_img);
      }
    }).uploadImage(img, comment);
  }


  @Override
  public void requestAddRate(String sid, String id, int rating) {
    new SubjectsDetailsModel(new Contract.SubjectsDetailsContract.Model.OnFinishedListener() {
      @Override
      public void onFinished(ArrayList<HashMap<String,String>> listHM) {
        Constants.setEmpty(listHM);
        Log.d("ololo", listHM.get(0).get("error"));

        switch (listHM.get(0).get("error")){

          case "-1" :
            view.onFailure(R.string.already_rated);
            break;

          case "1" :
            view.onFinished("succ_add_rating");
            break;

            default:
              onFailure("");
              break;
        }

      }

      @Override
      public void onFinished(String result) {
        /////////////////////////////
      }

      @Override
      public void onFailure(String error) {
        view.hideProgress();
        view.onFailure(R.string.error_add_rating);
      }
    }).AddRate(sid , id , rating);
  }


  @Override
  public void requestAddLikes(String sid, String id, boolean likes) {

    new SubjectsDetailsModel(new Contract.SubjectsDetailsContract.Model.OnFinishedListener() {
      @Override
      public void onFinished(ArrayList<HashMap<String,String>> listHM) {
        Constants.setEmpty(listHM);

        if (!listHM.get(0).get("error").equals("1")) {

          onFailure("");
        }

      }

      @Override
      public void onFinished(String result) {
        /////////////////////////////
      }

      @Override
      public void onFailure(String error) {
        view.hideProgress();
        view.onFailure(R.string.error);
      }
    }).AddLikes(sid , id , likes);
  }
}
