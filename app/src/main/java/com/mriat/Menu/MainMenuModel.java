package com.mriat.Menu;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mriat.Constants;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;

public class MainMenuModel extends AppCompatActivity implements Contract.MenuContract.Model {

    private ArrayList<HashMap<String,String>> slistHM ;
    private OnFinishedListener onFinishedListener;


    public MainMenuModel(OnFinishedListener onFinishedListener){
        this.onFinishedListener = onFinishedListener;
    }

    @Override
    public void getMenu() {
        new getData("getmenu").execute();
    }



    public static ArrayList<HashMap<String, String>> getData(String method) {
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
                if(Objecttable.hasProperty("Sections"))
                    map.put("Sections", Objecttable.getProperty("Sections").toString());
                if(Objecttable.hasProperty("Notes"))
                    map.put("Notes", Objecttable.getProperty("Notes").toString());
                if(Objecttable.hasProperty("Status"))
                    map.put("Status", Objecttable.getProperty("Status").toString());
                if(Objecttable.hasProperty("Ranking"))
                    map.put("Ranking", Objecttable.getProperty("Ranking").toString());
                if(Objecttable.hasProperty("ID_parent"))
                    map.put("ID_parent", Objecttable.getProperty("ID_parent").toString());
                if(Objecttable.hasProperty("Count"))
                    map.put("Count", Objecttable.getProperty("Count").toString());
                if(Objecttable.hasProperty("Icon"))
                    map.put("Icon", Objecttable.getProperty("Icon").toString());

                ArrayListHash.add(map);

            }

        } catch (Exception e) {
            e.printStackTrace();
            //resTxt = "Error occured";
        }

        return ArrayListHash;
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
                Log.d("jhgbvfcx", String.valueOf(slistHM.size()));
            }
            catch (Exception  e){
                onFinishedListener.onFailure(e.getMessage());
            }



        }

    }


}
