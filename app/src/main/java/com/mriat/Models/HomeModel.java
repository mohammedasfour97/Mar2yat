package com.mriat.Models;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mriat.Constants;
import com.mriat.Contract;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeModel extends AppCompatActivity implements Contract.HomeActivityContract.Model {

    private ArrayList<HashMap<String,String>> slistHM ;
    private OnFinishedListener onFinishedListener;


    public HomeModel (OnFinishedListener onFinishedListener){
        this.onFinishedListener = onFinishedListener;
    }


    @Override
    public void getAlbums(String id) {
        new getData("GetAlbums" , id).execute();
    }

    @Override
    public void getFixed(String id) {
        new getData("GetFixed" , id).execute();
    }

    @Override
    public void getNew(String id) {
        new getData("GetNew" , id).execute();
    }

    @Override
    public void getNews(String id) {
        new getData("GetNews" , id).execute();
    }

    @Override
    public void getVarious(String id) {
        new getData("GetVarious" , id).execute();
    }

    @Override
    public void getVedio(String id) {
        new getData("GetVideo" , id).execute();
    }


    public static ArrayList<HashMap<String, String>> getData(String method , String id) {
        String FName = method;
        // Create request
        SoapObject request = new SoapObject(Constants.NAMESPACE, FName);

        PropertyInfo pid0 = new PropertyInfo();
        pid0.name="ID";
        pid0.type=Double.class;
        pid0.setValue(Integer.parseInt(id));

        request.addProperty(pid0);

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
                if(Objecttable.hasProperty("ID_Sections"))
                map.put("ID_Sections", Objecttable.getProperty("ID_Sections").toString());
                if(Objecttable.hasProperty("ID_SubSections"))
                map.put("ID_SubSections", Objecttable.getProperty("ID_SubSections").toString());
                if(Objecttable.hasProperty("ID_Customers"))
                map.put("ID_Customers", Objecttable.getProperty("ID_Customers").toString());
                if(Objecttable.hasProperty("Title"))
                map.put("Title", Objecttable.getProperty("Title").toString());
                if(Objecttable.hasProperty("Summary"))
                map.put("Summary", Objecttable.getProperty("Summary").toString());
                if(Objecttable.hasProperty("Fulltext"))
                map.put("Fulltext", Objecttable.getProperty("Fulltext").toString());
                if(Objecttable.hasProperty("publishdate"))
                map.put("publishdate", Objecttable.getProperty("publishdate").toString());
                if(Objecttable.hasProperty("Reviews"))
                map.put("Reviews", Objecttable.getProperty("Reviews").toString());
                if(Objecttable.hasProperty("Views"))
                map.put("Views", Objecttable.getProperty("Views").toString());
                if(Objecttable.hasProperty("picture"))
                map.put("picture", Objecttable.getProperty("picture").toString());
                if(Objecttable.hasProperty("picturetext"))
                map.put("picturetext", Objecttable.getProperty("picturetext").toString());
                if(Objecttable.hasProperty("Voice"))
                map.put("Voice", Objecttable.getProperty("Voice").toString());
                if(Objecttable.hasProperty("Video"))
                map.put("Video", Objecttable.getProperty("Video").toString());
                if(Objecttable.hasProperty("Author"))
                map.put("Author", Objecttable.getProperty("Author").toString());
                if(Objecttable.hasProperty("publisher"))
                map.put("publisher", Objecttable.getProperty("publisher").toString());
                if(Objecttable.hasProperty("Location"))
                map.put("Location", Objecttable.getProperty("Location").toString());
                if(Objecttable.hasProperty("ID_Country"))
                map.put("ID_Country", Objecttable.getProperty("ID_Country").toString());
                if(Objecttable.hasProperty("City"))
                map.put("City", Objecttable.getProperty("City").toString());
                if(Objecttable.hasProperty("LocationName"))
                map.put("LocationName", Objecttable.getProperty("LocationName").toString());
                if(Objecttable.hasProperty("Sponsor"))
                map.put("Sponsor", Objecttable.getProperty("Sponsor").toString());
                if(Objecttable.hasProperty("Startdate"))
                map.put("Startdate", Objecttable.getProperty("Startdate").toString());
                if(Objecttable.hasProperty("Enddate"))
                map.put("Enddate", Objecttable.getProperty("Enddate").toString());
                if(Objecttable.hasProperty("Status"))
                map.put("Status", Objecttable.getProperty("Status").toString());
                if(Objecttable.hasProperty("Ranking"))
                map.put("Ranking", Objecttable.getProperty("Ranking").toString());
                if(Objecttable.hasProperty("ID_USER"))
                map.put("ID_USER", Objecttable.getProperty("ID_USER").toString());
                if(Objecttable.hasProperty("DateEdite"))
                map.put("DateEdite", Objecttable.getProperty("DateEdite").toString());
                if(Objecttable.hasProperty("DateCreate"))
                map.put("DateCreate", Objecttable.getProperty("DateCreate").toString());
                if(Objecttable.hasProperty("Sections"))
                map.put("Sections", Objecttable.getProperty("Sections").toString());
                if(Objecttable.hasProperty("SubSections"))
                map.put("SubSections", Objecttable.getProperty("SubSections").toString());
                if(Objecttable.hasProperty("AppearName"))
                map.put("AppearName", Objecttable.getProperty("AppearName").toString());
                if(Objecttable.hasProperty("Country"))
                map.put("Country", Objecttable.getProperty("Country").toString());

                for (Map.Entry<String, String> entry : map.entrySet()) {
                    Log.d(entry.getKey(), entry.getValue());
                }
                ArrayListHash.add(map);

            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("pollopp", e.getMessage());
        }

        return ArrayListHash;
    }

    class getData extends AsyncTask<String, String, String> {

        String method ,  id;

        getData(String method , String id){

            this.method = method;
            this.id = id;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {
            slistHM = getData(method , id);
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
