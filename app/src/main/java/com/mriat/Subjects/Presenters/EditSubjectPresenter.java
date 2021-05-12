package com.mriat.Subjects.Presenters;

import android.util.Log;

import com.mriat.Subjects.Contract;
import com.mriat.Subjects.Models.EditSubjectModel;

public class EditSubjectPresenter implements Contract.EditSubjectContract.Presenter {

    private Contract.EditSubjectContract.View view ;

    public EditSubjectPresenter(Contract.EditSubjectContract.View view) {
        this.view = view;
    }

    @Override
    public void requestdeleteSubject(int ID, int ID_USER) {

        new EditSubjectModel(new Contract.EditSubjectContract.Model.OnFinishedListener() {
            @Override
            public void onFinished(String result) {
                Log.d("llllllllllll", result);
                view.hideProgress();
                if (result.equals("1")) view.onFinished("ok");
                else view.onFailure("error");
            }

            @Override
            public void onFailure(String error) {
                view.hideProgress();
                view.onFailure("error_delete");
            }
        }).deleteSubject(ID , ID_USER);

    }
}
