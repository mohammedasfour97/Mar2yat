package com.mriat.Auth.Models;

import android.os.AsyncTask;
import android.util.Log;

import com.mriat.Auth.Contract;
import com.mriat.BaseActivity;
import com.mriat.Constants;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SignupModel extends BaseActivity implements Contract.SignupContract.Model {

    private ArrayList<HashMap<String,String>> slistHM ;
    private SoapPrimitive resultsString;
    private OnFinishedListener onFinishedListener;

    public SignupModel(OnFinishedListener onFinishedListener){
        this.onFinishedListener = onFinishedListener;
    }

    @Override
    public void signup(String UserName, String AppearName, String Password, String Email, int ID_Gender,
                       int ID_Country, int ID_DefaultStyle, int ID_DateFormat, boolean MonthFullName,
                       boolean ActivateDaylight, int ID_TimeDifference, int ID_WeekBegin, boolean Status, int ID_USER, String picture) {

        new signup(UserName, AppearName, Password, Email, picture, ID_Gender,
        ID_Country, ID_DefaultStyle, ID_DateFormat, ID_USER, ID_TimeDifference, ID_WeekBegin,
        MonthFullName,  ActivateDaylight , Status).execute();
    }

    @Override
    public void checkUsers(String UserName, String AppearName, String Email) {
        new checkuser(UserName , AppearName , Email).execute();

    }

    @Override
    public void getGender() {
        new getData("GETGender").execute();
    }

    @Override
    public void getCountries() {
        new getData("GETCountry").execute();
    }

    @Override
    public void getStyles() {
        new getData("GETStyles").execute();
    }

    @Override
    public void getDataFormat() {
        new getData("GETDateFormat").execute();
    }

    @Override
    public void getTimeOptions() {
        new getData("GETTimeOptions").execute();
    }

    @Override
    public void getWeekDays() {
        new getData("GETWeekDays").execute();
    }

    @Override
    public void getActivation(String ActivationCode, String NAME, String Email, int ID) {
        new activation(ActivationCode , NAME, Email , ID).execute();
    }

    @Override
    public void getReActivation(String Email) {
        new reActivation(Email);
    }

    @Override
    public void uploadImage(byte[] img, String img_name) {
        new UploadImag(img , img_name).execute();
    }

    @Override
    public void checkUserBeforeUpdate(String UserName, String AppearName, String Email, int ID) {
        new checkuuser(UserName , AppearName , Email , ID).execute();
    }

    @Override
    public void updateUser(int ID, String UserName, String AppearName, String Password, String Email, int ID_Gender, int ID_Country,
                           int ID_DefaultStyle, int ID_DateFormat, boolean MonthFullName, boolean ActivateDaylight,
                           int ID_TimeDifference, int ID_WeekBegin, boolean Status, int ID_USER, String picture, boolean AdminStatus) {

        new updateuser(ID , UserName, AppearName, Password, Email, picture, ID_Gender,
                ID_Country, ID_DefaultStyle, ID_DateFormat, ID_USER, ID_TimeDifference, ID_WeekBegin,
                MonthFullName,  ActivateDaylight , Status , AdminStatus).execute();
    }


    public  ArrayList<HashMap<String, String>> getData(String method) {
        String FName = method;
        // Create request
        SoapObject request = new SoapObject(Constants.NAMESPACE, FName);

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
            for (int i = 0; i < tables.getPropertyCount(); i++) {
                SoapObject Objecttable = (SoapObject) tables.getProperty(i);
                HashMap<String, String> map = new HashMap<String, String>();
                if(Objecttable.hasProperty("ID"))
                    map.put("ID", Objecttable.getProperty("ID").toString());
                if(Objecttable.hasProperty("NAME"))
                    map.put("NAME", Objecttable.getProperty("NAME").toString());


                for (Map.Entry<String, String> entry : map.entrySet()) {
                    Log.d(entry.getKey(), entry.getValue());
                }
                ArrayListHash.add(map);

            }

        } catch (Exception e) {
            e.printStackTrace();
            //resTxt = "Error occured";
        }

        return ArrayListHash;
    }

    public  ArrayList<HashMap<String, String>> signUp(String UserName ,String AppearName ,String Password ,String Email ,
                                                            int ID_Gender ,int ID_Country ,
                                                            int ID_DefaultStyle ,int ID_DateFormat ,boolean MonthFullName ,
                                                            boolean ActivateDaylight ,
                                                            int ID_TimeDifference ,int ID_WeekBegin ,boolean Status ,int ID_USER ,
                                                            String picture) {
        String FName = "ADD_Customers";
        // Create request
        SoapObject request = new SoapObject(Constants.NAMESPACE, FName);

        PropertyInfo pid;

        pid = new PropertyInfo();
        pid.name="UserName";
        pid.type=String.class;
        pid.setValue(UserName);

        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="AppearName";
        pid.type=String.class;
        pid.setValue(AppearName);

        request.addProperty(pid);


        pid = new PropertyInfo();
        pid.name="Email";
        pid.type=String.class;
        pid.setValue(Email);

        request.addProperty(pid);


        pid = new PropertyInfo();
        pid.name="Password";
        pid.type=String.class;
        pid.setValue(Password);

        request.addProperty(pid);


        pid = new PropertyInfo();
        pid.name="ID_Gender";
        pid.type=Double.class;
        pid.setValue(ID_Gender);

        request.addProperty(pid);


        pid = new PropertyInfo();
        pid.name="ID_Country";
        pid.type=Double.class;
        pid.setValue(ID_Country);

        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="ID_DefaultStyle";
        pid.type=Double.class;
        pid.setValue(ID_DefaultStyle);

        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="ID_DateFormat";
        pid.type=Double.class;
        pid.setValue(ID_DateFormat);

        request.addProperty(pid);


        pid = new PropertyInfo();
        pid.name="MonthFullName";
        pid.type=Boolean.class;
        pid.setValue(MonthFullName);

        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="ActivateDaylight";
        pid.type=Boolean.class;
        pid.setValue(ActivateDaylight);

        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="ID_TimeDifference";
        pid.type=Double.class;
        pid.setValue(ID_TimeDifference);

        request.addProperty(pid);


        pid = new PropertyInfo();
        pid.name="ID_WeekBegin";
        pid.type=Double.class;
        pid.setValue(ID_WeekBegin);

        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="Status";
        pid.type=Boolean.class;
        pid.setValue(Status);

        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="ID_USER";
        pid.type=Double.class;
        pid.setValue(ID_USER);

        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="picture";
        pid.type=String.class;
        pid.setValue(picture);

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

            String [] prop = new String[] {"ID", "Code" , "ActivationCode"};

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

    public  void update_Customers(int ID, String UserName, String AppearName, String Password,
                                                                String Email, int ID_Gender, int ID_Country, int ID_DefaultStyle,
                                                                int ID_DateFormat, boolean MonthFullName, boolean ActivateDaylight,
                                                                int ID_TimeDifference, int ID_WeekBegin, boolean Status, int ID_USER,
                                                                String picture, boolean AdminStatus) {
        String FName = "Update_Customers";
        // Create request
        SoapObject request = new SoapObject(Constants.NAMESPACE, FName);

        PropertyInfo pid;

        pid = new PropertyInfo();
        pid.name="ID";
        pid.type=Integer.class;
        pid.setValue(ID);

        request.addProperty(pid);


        pid = new PropertyInfo();
        pid.name="UserName";
        pid.type=String.class;
        pid.setValue(UserName);

        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="AppearName";
        pid.type=String.class;
        pid.setValue(AppearName);

        request.addProperty(pid);


        pid = new PropertyInfo();
        pid.name="Email";
        pid.type=String.class;
        pid.setValue(Email);

        request.addProperty(pid);


        pid = new PropertyInfo();
        pid.name="Password";
        pid.type=String.class;
        pid.setValue(Password);

        request.addProperty(pid);


        pid = new PropertyInfo();
        pid.name="ID_Gender";
        pid.type=Double.class;
        pid.setValue(ID_Gender);

        request.addProperty(pid);


        pid = new PropertyInfo();
        pid.name="ID_Country";
        pid.type=Double.class;
        pid.setValue(ID_Country);

        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="ID_DefaultStyle";
        pid.type=Double.class;
        pid.setValue(ID_DefaultStyle);

        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="ID_DateFormat";
        pid.type=Double.class;
        pid.setValue(ID_DateFormat);

        request.addProperty(pid);


        pid = new PropertyInfo();
        pid.name="MonthFullName";
        pid.type=Boolean.class;
        pid.setValue(MonthFullName);

        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="ActivateDaylight";
        pid.type=Boolean.class;
        pid.setValue(ActivateDaylight);

        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="ID_TimeDifference";
        pid.type=Double.class;
        pid.setValue(ID_TimeDifference);

        request.addProperty(pid);


        pid = new PropertyInfo();
        pid.name="ID_WeekBegin";
        pid.type=Double.class;
        pid.setValue(ID_WeekBegin);

        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="Status";
        pid.type=Boolean.class;
        pid.setValue(Status);

        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="ID_USER";
        pid.type=Double.class;
        pid.setValue(ID_USER);

        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="picture";
        pid.type=String.class;
        pid.setValue(picture);

        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="AdminStatus";
        pid.type=Boolean.class;
        pid.setValue(AdminStatus);

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

        try {
            // Invole web service
            androidHttpTransport.call(Constants.SOAP_ACTION + FName, envelope);
            // Get the response

            SoapObject resultsString = (SoapObject) envelope.getResponse();
            //String response = resultsString.getProperty(0).toString();
            SoapObject Object1 = (SoapObject) resultsString.getProperty(1);
            SoapObject tables = (SoapObject) Object1.getProperty(0);


        } catch (Exception e) {
            e.printStackTrace();
            //resTxt = "Error occured";
        }

    }


    public  ArrayList<HashMap<String, String>> checkUser(String UserName, String AppearName, String Email) {
        String FName = "checkuser";
        // Create request
        SoapObject request = new SoapObject(Constants.NAMESPACE, FName);

        PropertyInfo pid;

        pid = new PropertyInfo();
        pid.name="UserName";
        pid.type=String.class;
        pid.setValue(UserName);

        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="AppearName";
        pid.type=String.class;
        pid.setValue(AppearName);

        request.addProperty(pid);


        pid = new PropertyInfo();
        pid.name="Email";
        pid.type=String.class;
        pid.setValue(Email);

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

            String [] prop = new String[] {"UserName", "AppearName", "Email"};

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


    public  ArrayList<HashMap<String, String>> check_user_before_update(String UserName, String AppearName, String Email, int ID) {
        String FName = "checkuuser";
        // Create request
        SoapObject request = new SoapObject(Constants.NAMESPACE, FName);

        PropertyInfo pid;

        pid = new PropertyInfo();
        pid.name="UserName";
        pid.type=String.class;
        pid.setValue(UserName);

        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="AppearName";
        pid.type=String.class;
        pid.setValue(AppearName);

        request.addProperty(pid);


        pid = new PropertyInfo();
        pid.name="Email";
        pid.type=String.class;
        pid.setValue(Email);

        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="ID";
        pid.type=Double.class;
        pid.setValue(ID);

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

            String [] prop = new String[] {"UserName", "AppearName", "Email"};

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



    public void activate(String ActivationCode, String NAME, String Email, int ID) {
        String FName = "ActiveUser";
        // Create request
        SoapObject request = new SoapObject(Constants.NAMESPACE, FName);

        PropertyInfo pid;

        pid = new PropertyInfo();
        pid.name = "ActivationCode";
        pid.type = String.class;
        pid.setValue(ActivationCode);

        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name = "NAME";
        pid.type = String.class;
        pid.setValue(NAME);

        request.addProperty(pid);


        pid = new PropertyInfo();
        pid.name = "Email";
        pid.type = String.class;
        pid.setValue(Email);

        request.addProperty(pid);


        pid = new PropertyInfo();
        pid.name = "ID";
        pid.type = Double.class;
        pid.setValue(ID);

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

        try {
            // Invole web service
            androidHttpTransport.call(Constants.SOAP_ACTION + FName, envelope);

            // Get the response

            resultsString = (SoapPrimitive) envelope.getResponse();


        } catch (Exception e) {
            e.printStackTrace();

            Log.d("errror", e.getMessage());
        }

    }


    public ArrayList<HashMap<String, String>> re_activation(String Email) {
        String FName = "REActivation";
        // Create request
        SoapObject request = new SoapObject(Constants.NAMESPACE, FName);

        PropertyInfo pid;
        pid = new PropertyInfo();
        pid.name = "Email";
        pid.type = String.class;
        pid.setValue(Email);

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

            String [] prop = new String[] {"ID", "Code", "UserName", "AppearName", "Password", "Email", "ID_Gender",
                    "ID_Country", "ID_DefaultStyle", "ID_DateFormat", "MonthFullName", "ActivateDaylight",
                    "ID_TimeDifference", "ID_WeekBegin", "picture", "Status", "AdminStatus", "SignupDate",
                    "LastInteraction", "ID_USER", "ActivationCode", "ActivationDate"};

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


    class checkuser extends AsyncTask<String, String, String> {

        String UserName, AppearName, Email;

        public checkuser(String UserName, String AppearName, String Email){
            this.AppearName = AppearName;
            this.UserName = UserName;
            this.Email = Email;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {
            slistHM = checkUser(UserName , AppearName , Email);
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


    class checkuuser extends AsyncTask<String, String, String> {

        String UserName, AppearName, Email ;
        int ID;

        public checkuuser(String UserName, String AppearName, String Email , int ID){
            this.AppearName = AppearName;
            this.UserName = UserName;
            this.Email = Email;
            this.ID = ID;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {
            slistHM = check_user_before_update(UserName , AppearName , Email , ID);
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


    class signup extends AsyncTask<String, String, String> {

        String UserName , AppearName , Password , Email , picture;
        int ID_Gender , ID_Country , ID_DefaultStyle , ID_DateFormat , ID_USER ,ID_TimeDifference , ID_WeekBegin;
        boolean MonthFullName , ActivateDaylight , Status;

        public signup(String userName, String appearName, String password, String email, String picture, int ID_Gender,
                      int ID_Country, int ID_DefaultStyle, int ID_DateFormat, int ID_USER, int ID_TimeDifference, int ID_WeekBegin,
                      boolean monthFullName, boolean activateDaylight , boolean Status) {
            UserName = userName;
            AppearName = appearName;
            Password = password;
            Email = email;
            this.picture = picture;
            this.ID_Gender = ID_Gender;
            this.ID_Country = ID_Country;
            this.ID_DefaultStyle = ID_DefaultStyle;
            this.ID_DateFormat = ID_DateFormat;
            this.ID_USER = ID_USER;
            this.ID_TimeDifference = ID_TimeDifference;
            this.ID_WeekBegin = ID_WeekBegin;
            MonthFullName = monthFullName;
            ActivateDaylight = activateDaylight;
            this.Status = Status;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {
            slistHM = signUp(UserName ,AppearName ,Password ,Email ,
            ID_Gender ,ID_Country ,
            ID_DefaultStyle , ID_DateFormat ,MonthFullName ,
            ActivateDaylight ,
            ID_TimeDifference , ID_WeekBegin ,Status , ID_USER , picture);
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



    class activation extends AsyncTask<String, String, String> {

        String ActivationCode , NAME, Email;
        int ID;

        public activation(String activationCode, String NAME, String email, int ID) {
            ActivationCode = activationCode;
            this.NAME = NAME;
            Email = email;
            this.ID = ID;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {
            activate(ActivationCode , NAME , Email , ID);
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



    class getData extends AsyncTask<String, String, String> {

        String method;

        getData(String method){

            this.method = method;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {
            slistHM = getData(method);
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



    class reActivation extends AsyncTask<String, String, String> {

        String Email;

        reActivation(String Email){

            this.Email = Email;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {
            re_activation(this.Email);
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


    class updateuser extends AsyncTask<String, String, String> {

        String UserName , AppearName , Password , Email , picture;
        int ID_Gender , ID_Country , ID_DefaultStyle , ID_DateFormat , ID_USER ,ID_TimeDifference , ID_WeekBegin , ID;
        boolean MonthFullName , ActivateDaylight , Status , AdminStatus;

        public updateuser(int ID , String userName, String appearName, String password, String email, String picture, int ID_Gender,
                      int ID_Country, int ID_DefaultStyle, int ID_DateFormat, int ID_USER, int ID_TimeDifference, int ID_WeekBegin,
                      boolean monthFullName, boolean activateDaylight , boolean Status , boolean AdminStatus) {
            this.ID = ID;
            UserName = userName;
            AppearName = appearName;
            Password = password;
            Email = email;
            this.picture = picture;
            this.ID_Gender = ID_Gender;
            this.ID_Country = ID_Country;
            this.ID_DefaultStyle = ID_DefaultStyle;
            this.ID_DateFormat = ID_DateFormat;
            this.ID_USER = ID_USER;
            this.ID_TimeDifference = ID_TimeDifference;
            this.ID_WeekBegin = ID_WeekBegin;
            MonthFullName = monthFullName;
            ActivateDaylight = activateDaylight;
            this.Status = Status;
            this.AdminStatus = AdminStatus;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {
            update_Customers(ID , UserName ,AppearName ,Password ,Email ,
                    ID_Gender ,ID_Country ,
                    ID_DefaultStyle , ID_DateFormat ,MonthFullName ,
                    ActivateDaylight ,
                    ID_TimeDifference , ID_WeekBegin ,Status , ID_USER , picture , AdminStatus);
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

}