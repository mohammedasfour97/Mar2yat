package com.mriat.Auth.Models;

import android.os.AsyncTask;

import com.mriat.Auth.Contract;
import com.mriat.Constants;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginModel implements Contract.LoginContract.Model {

    private OnFinishedListener onFinishedListener;
    private ArrayList<HashMap<String, String>> listHM;

    public LoginModel(OnFinishedListener onFinishedListener) {
        this.onFinishedListener = onFinishedListener;
    }

    @Override
    public void Login(String username, String password) {
        new signin(username , password).execute();
    }

    public ArrayList<HashMap<String, String>> signin(String username, String password) {
        String FName = "getlogin";
        // Create request
        SoapObject request = new SoapObject(Constants.NAMESPACE, FName);

        PropertyInfo pid;

        pid = new PropertyInfo();
        pid.name="UserName";
        pid.type=String.class;
        pid.setValue(username);

        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="Password";
        pid.type=String.class;
        pid.setValue(password);

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
                    "LastInteraction", "ID_USER", "ActivationCode", "ActivationDate" , "error"};

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



    class signin extends AsyncTask<String, String, String> {

        String username , password;

        public signin(String username , String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {
            listHM = signin(username , password);
            return null;
        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {

            try {
                onFinishedListener.onFinished(listHM);
            }
            catch (Exception  e){
                onFinishedListener.onFailure(e.getMessage());
            }



        }

    }

}
