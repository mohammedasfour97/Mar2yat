package com.mriat.Subjects.Presenters;

import android.util.Log;

import com.mriat.Subjects.Contract;
import com.mriat.Subjects.Models.AddSubjectModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddSubjectPresenter implements Contract.AddSubjectContract.Presenter {

    private Contract.AddSubjectContract.View view;
    private byte[] img_bytes , voice_bytes , video_bytes;
    private String img_text , voice_text , video_text;

    public AddSubjectPresenter(Contract.AddSubjectContract.View view){
        this.view = view;
    }

    public AddSubjectPresenter(Contract.AddSubjectContract.View view , byte[] img_bytes , byte[] voice_bytes , byte[] video_bytes ,
                               String img_text , String voice_text , String video_text){
        this.view = view;
        this.img_bytes = img_bytes;
        this.voice_bytes = voice_bytes;
        this.video_bytes = video_bytes;
        this.img_text = img_text;
        this.voice_text = voice_text;
        this.video_text = video_text;
    }

    @Override
    public void requestAddSubject(Map<String , String> stringStringHashMap , Map<String , Integer> integerHashMap , boolean status) {

        new AddSubjectModel(new Contract.AddSubjectContract.Model.OnFinishedListener() {
            @Override
            public void onFinished(ArrayList<HashMap<String, String>> listHM) {
                view.hideProgress();
                switch (listHM.get(0).get("error")){

                    case "0" :
                        view.onFailure(listHM.get(0).get("msg"));
                        break;

                    case "-1" :
                        view.onFailure("dublicate_title");
                        break;

                        default:
                            view.onFinished();
                            break;
                }

            }

            @Override
            public void onFinished(String result) {
                ////////////////////
            }

            @Override
            public void onFailure(String error) {
                view.hideProgress();
                view.onFailure(error);
            }
        }).addSubject(stringStringHashMap , integerHashMap , status);

    }

    @Override
    public void requestUploadMedia() {

        if (img_bytes != null){
            requestUploadImg();
        }
        else if (voice_bytes != null){
            requestUploadVoice();
        }
        else if (video_bytes != null){
            requestUploadVideo();
        }
        else {
            view.addSubjectData();
        }
    }


    private void requestUploadImg(){

            new AddSubjectModel(new Contract.AddSubjectContract.Model.OnFinishedListener() {
                @Override
                public void onFinished(ArrayList<HashMap<String, String>> listHM) {
                    ////////////////////////
                }

                @Override
                public void onFinished(String result) {

                    if (result.equals("Done")) {
                        if (voice_bytes != null){
                            requestUploadVoice();
                        }
                        else if (video_bytes != null){
                            requestUploadVideo();
                        }
                        else {
                            view.addSubjectData();
                        }
                    }
                    else {
                        onFailure("error_upload_img");
                    }
                }

                @Override
                public void onFailure(String error) {
                    view.hideProgress();
                    view.onFailure("error_upload_img");
                }
            }).uploadMedia(img_bytes , img_text);
        }


    private void requestUploadVoice(){


            new AddSubjectModel(new Contract.AddSubjectContract.Model.OnFinishedListener() {
                @Override
                public void onFinished(ArrayList<HashMap<String, String>> listHM) {
                    ////////////////////////
                }

                @Override
                public void onFinished(String result) {

                    if (result.equals("Done")) {
                        if (video_bytes != null){
                            requestUploadVideo();
                        }
                        else {
                            view.addSubjectData();
                        }
                    }
                    else {
                        onFailure("error_upload_voice");
                    }
                }

                @Override
                public void onFailure(String error) {
                    view.hideProgress();
                    view.onFailure("error_upload_voice");
                    Log.d("errrrrorrfffff", error);
                }
            }).uploadMedia(voice_bytes , voice_text);
        }


    private void requestUploadVideo(){

            new AddSubjectModel(new Contract.AddSubjectContract.Model.OnFinishedListener() {
                @Override
                public void onFinished(ArrayList<HashMap<String, String>> listHM) {
                    ////////////////////////
                }

                @Override
                public void onFinished(String result) {

                    if (result.equals("Done")) {
                        view.addSubjectData();
                    }
                    else {
                        Log.d("errrrrorr", result);
                        onFailure("error_upload_video");
                    }
                }

                @Override
                public void onFailure(String error) {
                    view.hideProgress();
                    view.onFailure("error_upload_video");
                }
            }).uploadMedia(video_bytes , video_text);
        }

    }
