package com.mriat.Subjects.Models;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.mriat.Constants;
import com.mriat.ModelClasses.Comment;
import com.mriat.Subjects.Contract;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;

public class SubjectsDetailsModel extends AppCompatActivity implements Contract.SubjectsDetailsContract.Model {

  private ArrayList<HashMap<String,String>> slistHM ;
  private OnFinishedListener onFinishedListener;
  private SoapPrimitive resultsString;


  public SubjectsDetailsModel(OnFinishedListener onFinishedListener){
    this.onFinishedListener = onFinishedListener;
  }


  @Override
  public void getSubjectsDetails(String sid , String id) {
    new SubjectsDetailsModel.getData("getmfSubjects2" , sid , id).execute();
  }

  @Override
  public void getComments(String id) {
    new getComments("GetMComments" , id).execute();
  }

  @Override
  public void addComment(Comment comment) {

    new AddComment(comment).execute();
  }

  @Override
  public void uploadImage(byte[] img, Comment comment) {
    new UploadImag(img , comment.getPicture()).execute();
  }

    @Override
    public void AddRate(String sid, String id, int rating) {
        new AddRate(sid , id , rating).execute();
    }

    @Override
    public void AddLikes(String sid, String id, boolean likes) {
        new AddLikes(sid , id , likes).execute();
    }


    public ArrayList<HashMap<String, String>> getData(String method , String sid , String id) {
    String FName = method;
    // Create request
    SoapObject request = new SoapObject(Constants.NAMESPACE, FName);


    PropertyInfo pid ;

    pid = new PropertyInfo();
    pid.name="PID";
    pid.type=Double.class;
    pid.setValue(Integer.parseInt(sid));
    request.addProperty(pid);

    pid = new PropertyInfo();
    pid.name="ID";
    pid.type=Double.class;
    pid.setValue(Integer.parseInt(id));
    request.addProperty(pid);

    /** end send*/
    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
            SoapEnvelope.VER11);
    envelope.dotNet = true;
    // Set output SOAP object
    envelope.setOutputSoapObject(request);

    /** if Double
     MarshalDouble md = new MarshalDouble();
     md.register(envelope);
     */

    // Create HTTP call object
    HttpTransportSE androidHttpTransport = new HttpTransportSE(Constants.URL);

    ArrayList<HashMap<String, String>> ArrayListHash = null;
    try {
      // Invole web service
      androidHttpTransport.call(Constants.SOAP_ACTION + FName, envelope);
      // Get the response

      SoapObject resultsString = (SoapObject) envelope.getResponse();
      //String response = resultsString.getProperty(0).toString();
      SoapObject Object1 = (SoapObject) resultsString.getProperty(1);
      SoapObject tables = (SoapObject) Object1.getProperty(0);


      ArrayListHash = new ArrayList<HashMap<String, String>>();
      String [] prop = new String[] {"ID", "Title", "Summary", "DateEdite", "Reviews", "Views", "picture" , "Author" , "Fulltext",
              "picturetext" , "Voice" , "Video" , "publisher", "Location", "ID_Country", "City", "LocationName", "Sponsor", "Startdate",
              "Enddate", "Status", "Ranking", "ID_USER", "DateEdite", "DateCreate", "Sections", "SubSections", "Country" , "rat" ,
      "crat" , "trat" , "clikes" , "cUnlikes"};
      SoapObject Objecttable;
      HashMap<String, String> map;
      for (int i = 0; i < tables.getPropertyCount(); i++) {
        Objecttable = (SoapObject) tables.getProperty(i);
        map = new HashMap<String, String>();
        for (String propr:prop) {
          if(Objecttable.hasProperty(propr))
            map.put(propr, Objecttable.getProperty(propr).toString());
        }
        ArrayListHash.add(map);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return ArrayListHash;
  }

  public ArrayList<HashMap<String, String>> getComments(String method , String id) {
    String FName = method;
    // Create request
    SoapObject request = new SoapObject(Constants.NAMESPACE, FName);


    PropertyInfo pid = new PropertyInfo();
    pid.name="PID";
    pid.type=Double.class;
    pid.setValue(Integer.parseInt(id));

    request.addProperty(pid);

    /** end send*/
    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
            SoapEnvelope.VER11);
    envelope.dotNet = true;
    // Set output SOAP object
    envelope.setOutputSoapObject(request);

    /** if Double
     MarshalDouble md = new MarshalDouble();
     md.register(envelope);
     */

    // Create HTTP call object
    HttpTransportSE androidHttpTransport = new HttpTransportSE(Constants.URL);

    ArrayList<HashMap<String, String>> ArrayListHash = null;
    try {
      // Invole web service
      androidHttpTransport.call(Constants.SOAP_ACTION + FName, envelope);
      // Get the response

      SoapObject resultsString = (SoapObject) envelope.getResponse();
      //String response = resultsString.getProperty(0).toString();
      SoapObject Object1 = (SoapObject) resultsString.getProperty(1);
      SoapObject tables = (SoapObject) Object1.getProperty(0);


      ArrayListHash = new ArrayList<HashMap<String, String>>();
      String [] prop = new String[] {"ID", "ID_Customers",  "AppearName",  "Email", "picture", "Status", "SignupDate", "Comments",
              "ID_Subjects", "ID_Comments", "ID_USER", "LastInteraction",  "Count"};
      SoapObject Objecttable;
      HashMap<String, String> map;
      for (int i = 0; i < tables.getPropertyCount(); i++) {
        Objecttable = (SoapObject) tables.getProperty(i);
        map = new HashMap<String, String>();
        for (String propr:prop) {
          if(Objecttable.hasProperty(propr))
            map.put(propr, Objecttable.getProperty(propr).toString());
        }
        ArrayListHash.add(map);

      }


    } catch (Exception e) {
      e.printStackTrace();
      //resTxt = "Error occured";
    }

    return ArrayListHash;
  }

  public ArrayList<HashMap<String , String>> add_comment(Comment comment) {


    String FName = "ADD_Comments";
    // Create request
    SoapObject request = new SoapObject(Constants.NAMESPACE, FName);

    PropertyInfo pid;

    pid = new PropertyInfo();
    pid.name="ID_Customers";
    pid.type=Double.class;
    pid.setValue(Integer.parseInt(comment.getID_Customers()));

    request.addProperty(pid);

    pid = new PropertyInfo();
    pid.name="AppearName";
    pid.type=String.class;
    pid.setValue(comment.getAppearName());

    request.addProperty(pid);

    pid = new PropertyInfo();
    pid.name="Email";
    pid.type=String.class;
    pid.setValue(comment.getEmail());

    request.addProperty(pid);

    pid = new PropertyInfo();
    pid.name="picture";
    pid.type=String.class;
    pid.setValue(comment.getPicture());

    request.addProperty(pid);

    pid = new PropertyInfo();
    pid.name="Status";
    pid.type=Boolean.class;
    pid.setValue(false);

    request.addProperty(pid);

   /* pid = new PropertyInfo();
    pid.name="SignupDate";
    pid.type=String.class;
    pid.setValue(comment.getSignupDate());

    request.addProperty(pid);
*/

    pid = new PropertyInfo();
    pid.name="Comments";
    pid.type=String.class;
    pid.setValue(comment.getComments());

    request.addProperty(pid);

    pid = new PropertyInfo();
    pid.name="ID_Subjects";
    pid.type=Double.class;
    pid.setValue(Integer.parseInt(comment.getID_Subjects()));

    request.addProperty(pid);

    pid = new PropertyInfo();
    pid.name="ID_Comments";
    pid.type=Double.class;
    pid.setValue(Integer.parseInt(comment.getID_Comments()));

    request.addProperty(pid);

    pid = new PropertyInfo();
    pid.name="ID_USER";
    pid.type=Double.class;
    pid.setValue(Integer.parseInt(comment.getID_USER()));

    request.addProperty(pid);

    /** end send*/
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
            SoapEnvelope.VER11);
    envelope.dotNet = true;
     /*   new MarshalBase64().register(envelope); //serialization
        envelope.encodingStyle = SoapEnvelope.ENC;
*/
    // Set output SOAP object
    envelope.setOutputSoapObject(request);

    /** if Double
     MarshalDouble md = new MarshalDouble();
     md.register(envelope);
     */

    // Create HTTP call object
    HttpTransportSE androidHttpTransport = new HttpTransportSE(Constants.URL);

    ArrayList<HashMap<String , String>> ArrayListHash = null;
    try {
      // Invole web service
      androidHttpTransport.call(Constants.SOAP_ACTION + FName, envelope);

      // Get the response

      SoapObject resultsString = (SoapObject) envelope.getResponse();
      //String response = resultsString.getProperty(0).toString();
      SoapObject Object1 = (SoapObject) resultsString.getProperty(1);
      SoapObject tables = (SoapObject) Object1.getProperty(0);


      ArrayListHash = new ArrayList<HashMap<String, String>>();
      String [] prop = new String[] {"ID"};
      SoapObject Objecttable;
      HashMap<String, String> map;
      for (int i = 0; i < tables.getPropertyCount(); i++) {
        Objecttable = (SoapObject) tables.getProperty(i);
        map = new HashMap<String, String>();
        for (String propr:prop) {
          if(Objecttable.hasProperty(propr))
            map.put(propr, Objecttable.getProperty(propr).toString());
        }
        ArrayListHash.add(map);

      }


    } catch (Exception e) {
      e.printStackTrace();
      //resTxt = "Error occured";
    }

    return ArrayListHash;
  }

  public void upload_img(byte[] bitmapdata, String img_name) {
    String FName = "UploadFile";
    // Create request
    SoapObject request = new SoapObject(Constants.NAMESPACE, FName);

    PropertyInfo pid;

    pid = new PropertyInfo();
    pid.name="fle";
    pid.type=Byte[].class;
    pid.setValue(bitmapdata);

    request.addProperty(pid);

    pid = new PropertyInfo();
    pid.name="FileName";
    pid.type=String.class;
    pid.setValue(img_name);

    request.addProperty(pid);


    /** end send*/
    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
            SoapEnvelope.VER11);
    envelope.dotNet = true;
    new MarshalBase64().register(envelope); //serialization
    envelope.encodingStyle = SoapEnvelope.ENC;

    // Set output SOAP object
    envelope.setOutputSoapObject(request);

    /** if Double
     MarshalDouble md = new MarshalDouble();
     md.register(envelope);
     */

    // Create HTTP call object
    HttpTransportSE androidHttpTransport = new HttpTransportSE(Constants.URL);

    ArrayList<HashMap<String, String>> ArrayListHash = null;
    try {
      // Invole web service
      androidHttpTransport.call(Constants.SOAP_ACTION + FName, envelope);
      // Get the response

      resultsString = (SoapPrimitive) envelope.getResponse();
      //String response = resultsString.getProperty(0).toString();

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public ArrayList<HashMap<String, String>> add_rate(String sid , String id , int rate) {
        String FName = "ADD_Rating";
        // Create request
        SoapObject request = new SoapObject(Constants.NAMESPACE, FName);


        PropertyInfo pid ;

        pid = new PropertyInfo();
        pid.name="ID_Subjects";
        pid.type=Double.class;
        pid.setValue(Integer.parseInt(sid));
        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="ID_Customers";
        pid.type=Double.class;
        pid.setValue(Integer.parseInt(id));
        request.addProperty(pid);

      pid = new PropertyInfo();
      pid.name="Rating";
      pid.type=Double.class;
      pid.setValue(rate);
      request.addProperty(pid);

        /** end send*/
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);

        /** if Double
         MarshalDouble md = new MarshalDouble();
         md.register(envelope);
         */

        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(Constants.URL);

        ArrayList<HashMap<String, String>> ArrayListHash = null;
        try {
            // Invole web service
            androidHttpTransport.call(Constants.SOAP_ACTION + FName, envelope);
            // Get the response

            SoapObject resultsString = (SoapObject) envelope.getResponse();
            //String response = resultsString.getProperty(0).toString();
            SoapObject Object1 = (SoapObject) resultsString.getProperty(1);
            SoapObject tables = (SoapObject) Object1.getProperty(0);


            ArrayListHash = new ArrayList<HashMap<String, String>>();
            String [] prop = new String[] {"error"};
            SoapObject Objecttable;
            HashMap<String, String> map;
            for (int i = 0; i < tables.getPropertyCount(); i++) {
                Objecttable = (SoapObject) tables.getProperty(i);
                map = new HashMap<String, String>();
                for (String propr:prop) {
                    if(Objecttable.hasProperty(propr))
                        map.put(propr, Objecttable.getProperty(propr).toString());
                }
                ArrayListHash.add(map);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ArrayListHash;
    }

    public ArrayList<HashMap<String, String>> add_likes(String sid , String id , boolean like) {
        String FName = "ADD_Likes";
        // Create request
        SoapObject request = new SoapObject(Constants.NAMESPACE, FName);


        PropertyInfo pid ;

        pid = new PropertyInfo();
        pid.name="ID_Subjects";
        pid.type=Double.class;
        pid.setValue(Integer.parseInt(sid));
        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="ID_Customers";
        pid.type=Double.class;
        pid.setValue(Integer.parseInt(id));
        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="Likes";
        pid.type=Boolean.class;
        pid.setValue(like);
        request.addProperty(pid);

        /** end send*/
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);

        /** if Double
         MarshalDouble md = new MarshalDouble();
         md.register(envelope);
         */

        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(Constants.URL);

        ArrayList<HashMap<String, String>> ArrayListHash = null;
        try {
            // Invole web service
            androidHttpTransport.call(Constants.SOAP_ACTION + FName, envelope);
            // Get the response

            SoapObject resultsString = (SoapObject) envelope.getResponse();
            //String response = resultsString.getProperty(0).toString();
            SoapObject Object1 = (SoapObject) resultsString.getProperty(1);
            SoapObject tables = (SoapObject) Object1.getProperty(0);


            ArrayListHash = new ArrayList<HashMap<String, String>>();
            String [] prop = new String[] {"error"};
            SoapObject Objecttable;
            HashMap<String, String> map;
            for (int i = 0; i < tables.getPropertyCount(); i++) {
                Objecttable = (SoapObject) tables.getProperty(i);
                map = new HashMap<String, String>();
                for (String propr:prop) {
                    if(Objecttable.hasProperty(propr))
                        map.put(propr, Objecttable.getProperty(propr).toString());
                }
                ArrayListHash.add(map);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ArrayListHash;
    }



  class getData extends AsyncTask<String, String, String> {

    String method , id , sid ;

    getData(String method , String sid , String id){

      this.method = method;
      this.id = id ;
      this.sid = sid;
    }
    @Override
    protected void onPreExecute() {
      super.onPreExecute();

    }

    protected String doInBackground(String... args) {
      slistHM = getData(method , sid , id);
      return null;
    }
    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(String file_url) {

      try {
        onFinishedListener.onFinished(slistHM);
      }
      catch (Exception  e){
        onFinishedListener.onFailure(e.getMessage());

      }



    }

  }

  class getComments extends AsyncTask<String, String, String> {

    String method , id ;

    getComments(String method , String id){

      this.method = method;
      this.id = id ;
    }
    @Override
    protected void onPreExecute() {
      super.onPreExecute();

    }

    protected String doInBackground(String... args) {
      slistHM = getComments(method , id);
      return null;
    }
    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(String file_url) {

      try {
        onFinishedListener.onFinished(slistHM);
      }
      catch (Exception  e){
        onFinishedListener.onFailure(e.getMessage());
      }



    }

  }

  class AddComment extends AsyncTask<String, String, String> {

    Comment comment;

    public AddComment(Comment comment) {
      this.comment = comment;
    }

    @Override
    protected void onPreExecute() {
      super.onPreExecute();

    }

    protected String doInBackground(String... args) {
      slistHM = add_comment(comment);
      return null;
    }
    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(String file_url) {

      try {
        onFinishedListener.onFinished(slistHM);
      }
      catch (Exception  e){
        onFinishedListener.onFailure(e.getMessage());
      }



    }

  }

  class UploadImag extends AsyncTask<String, String, String> {

    byte[] bitmapdata;
    String img_name;

    public UploadImag(byte[] bitmapdata , String img_name) {
      this.bitmapdata = bitmapdata;
      this.img_name = img_name;
    }

    @Override
    protected void onPreExecute() {
      super.onPreExecute();

    }

    protected String doInBackground(String... args) {
      upload_img(bitmapdata , img_name);
      return null;
    }
    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(String file_url) {

      try {
        onFinishedListener.onFinished(resultsString.toString());
      }
      catch (Exception  e){
        onFinishedListener.onFailure(e.getMessage());
      }



    }

  }

  class AddRate extends AsyncTask<String, String, String> {

        String sid , id ;
        int rate;

        public AddRate(String sid, String id, int rate) {
            this.sid = sid;
            this.id = id;
            this.rate = rate;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {
            slistHM = add_rate(sid , id , rate);
            return null;
        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {

            try {
                onFinishedListener.onFinished(slistHM);
            }
            catch (Exception  e){
                onFinishedListener.onFailure(e.getMessage());
            }



        }

    }

    class AddLikes extends AsyncTask<String, String, String> {

        String sid , id ;
        boolean like;

        public AddLikes(String sid, String id, boolean likes) {
            this.sid = sid;
            this.id = id;
            this.like = likes;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {
            slistHM = add_likes(sid , id , like);
            return null;
        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {

            try {
                onFinishedListener.onFinished(slistHM);
            }
            catch (Exception  e){
                onFinishedListener.onFailure(e.getMessage());
            }



        }

    }

}

