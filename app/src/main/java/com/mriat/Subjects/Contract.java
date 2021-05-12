package com.mriat.Subjects;

import com.mriat.ModelClasses.Comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Contract {

    public interface SubjectsActivityContract{

        interface Model{

            interface OnFinishedListener {
                void onFinished(ArrayList<HashMap<String,String>> listHM);

                void onFailure(String error);
            }

            void getSubjects(String id , String sid);
            void getMySubjects(String id);

        }

        interface Presenter{

            void requestSubjects(String id , String sid);
            void requestMySubjects(String id);

        }

        interface View{

            void fillData(ArrayList<HashMap<String,String>> listHM);

            void onFailure(String error);

            void showProgress();
            void hideProgress();
        }
    }

    public interface AddSubjectContract{

        interface Model{

            interface OnFinishedListener {
                void onFinished(ArrayList<HashMap<String,String>> listHM);

                void onFinished(String result);

                void onFailure(String error);

            }

         /*   void addSubject(int ID_Sections, int ID_SubSections, int ID_Customers, String Title, String Summary, String Fulltext,
                            String publishdate, int Reviews, int Views, String picture, String picturetext, String Voice, String Video,
                            String Author, String publisher, String Location, int ID_Country, String City, String LocationName,
                            String Sponsor, String Startdate, String Enddate, boolean Status, int Ranking, int ID_USER);
         */

            void addSubject(Map<String , String> stringStringHashMap , Map<String , Integer> integerHashMap , boolean status);

            void uploadMedia(byte[] bytes, String img_name);
        }

        interface Presenter{

          /*  void requestAddSubject(int ID_Sections, int ID_SubSections, int ID_Customers, String Title, String Summary, String Fulltext,
                                   String publishdate, int Reviews, int Views, String picture, String picturetext, String Voice, String Video,
                                   String Author, String publisher, String Location, int ID_Country, String City, String LocationName,
                                   String Sponsor, String Startdate, String Enddate, boolean Status, int Ranking, int ID_USER);
        */

            void requestAddSubject(Map<String , String> stringStringHashMap , Map<String , Integer> integerHashMap , boolean status);

            void requestUploadMedia();
        }

        interface View{

            void onFinished();

            void fillMainCategories(ArrayList<HashMap<String,String>> listHM);

            void fillSubCategories(ArrayList<HashMap<String,String>> listHM);

            void fillCountries(ArrayList<HashMap<String,String>> listHM);

            void addSubjectData();

            void addSubject();

            void onFailure(String error);

            void showProgress();
            void hideProgress();
        }
    }


    public interface EditSubjectContract{

        interface Model{

            interface OnFinishedListener {
                void onFinished(String result);

                void onFailure(String error);
            }

            void deleteSubject(int ID, int ID_USER);
        }

        interface Presenter{

            void requestdeleteSubject(int ID, int ID_USER);
        }

        interface View{

            void onFinished(String message);

            void onFailure(String error);

            void showProgress();
            void hideProgress();
        }
    }

    public interface SubjectsDetailsContract{

        interface Model{

            interface OnFinishedListener {
                void onFinished(ArrayList<HashMap<String,String>> listHM);

                void onFinished(String result);

                void onFailure(String error);
            }

            void getSubjectsDetails(String sid , String id);
            void getComments(String id);
            void addComment(Comment comment);
            void uploadImage(byte[] img , Comment comment);
            void AddRate(String sid , String id , int rating);
            void AddLikes(String sid , String id , boolean likes);

        }

        interface Presenter{

            void requestSubjectDetails(String sid , String id);
            void requestComments(String id);
            void requestAddComments(Comment comment);
            void requestuploadImage(byte[] img , Comment comment);
            void requestAddRate(String sid , String id , int rating);
            void requestAddLikes(String sid , String id , boolean likes);

        }

        interface View{

            void fillData(ArrayList<HashMap<String,String>> listHM);
            void fillComments(ArrayList<HashMap<String,String>> listHM);
            void addComment(Comment comment);
            void uploadImage(byte[] img , Comment comment);
            void loadSubjectDetails();
            void onFinished(String message);
            void onFailure(int error);
            void showProgress();
            void hideProgress();
        }
    }

}
