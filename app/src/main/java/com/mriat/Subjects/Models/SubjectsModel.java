package com.mriat.Subjects.Models;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.mriat.Constants;
import com.mriat.Subjects.Contract;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;

public class SubjectsModel extends AppCompatActivity implements Contract.SubjectsActivityContract.Model {

    private ArrayList<HashMap<String,String>> slistHM ;
    private OnFinishedListener onFinishedListener;


    public SubjectsModel(OnFinishedListener onFinishedListener){
        this.onFinishedListener = onFinishedListener;
    }

    @Override
    public void getSubjects(String id , String sid) {
        new SubjectsModel.getData("getmSubjects" , id , sid).execute();
    }

    @Override
    public void getMySubjects(String id) {
        new SubjectsModel.getMyData("getUserSubjects" , id).execute();

    }


    public static ArrayList<HashMap<String, String>> getData(String method , String id , String sid) {
        String FName = method;
        // Create request
        SoapObject request = new SoapObject(Constants.NAMESPACE, FName);

        PropertyInfo pid1 = new PropertyInfo();
        pid1.name="SID";
        pid1.type=Double.class;
        pid1.setValue(Integer.parseInt(sid));

        request.addProperty(pid1);

        PropertyInfo pid = new PropertyInfo();
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
            String [] prop = new String[] {"ID", "Title", "Summary", "publishdate", "Reviews", "Views", "picture" , "Author"};
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


    public static ArrayList<HashMap<String, String>> getMyData(String method , String id) {
        String FName = method;
        // Create request
        SoapObject request = new SoapObject(Constants.NAMESPACE, FName);


        PropertyInfo pid = new PropertyInfo();
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
            String [] prop = new String[] {"ID", "Title", "Summary", "publishdate", "Reviews", "Views", "picture" , "Author"};
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

          //  Constants.setEmpty(ArrayListHash);

        } catch (Exception e) {
            e.printStackTrace();
            //resTxt = "Error occured";
        }

        return ArrayListHash;
    }

    class getData extends AsyncTask<String, String, String> {

        String method , id ,  sid;

        getData(String method , String id , String sid){

            this.method = method;
            this.id = id ;
            this.sid = sid;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {
            slistHM = getData(method , id , sid);
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



    class getMyData extends AsyncTask<String, String, String> {

        String method , id ;

        getMyData(String method , String id){

            this.method = method;
            this.id = id ;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {
            slistHM = getMyData(method , id);
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

