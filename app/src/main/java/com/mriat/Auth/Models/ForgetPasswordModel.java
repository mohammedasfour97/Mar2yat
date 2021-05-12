package com.mriat.Auth.Models;

import android.os.AsyncTask;
import android.util.Log;

import com.mriat.Auth.Contract;
import com.mriat.Constants;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;

public class ForgetPasswordModel implements Contract.ForgetPassword.Model {

    private OnFinishedListener onFinishedListener ;
    private ArrayList<HashMap<String , String>> listHM;
    private SoapPrimitive resultsString;

    public ForgetPasswordModel(OnFinishedListener onFinishedListener) {
        this.onFinishedListener = onFinishedListener;
    }

    @Override
    public void getForget(String email) {

        new getFoget(email).execute();
    }

    private ArrayList<HashMap<String , String>> get_forget(String email){

        String FName = "getForgot";
        // Create request
        SoapObject request = new SoapObject(Constants.NAMESPACE, FName);

        PropertyInfo pid;
        pid = new PropertyInfo();
        pid.name="Email";
        pid.type=String.class;
        pid.setValue(email);

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
                    "ID_TimeDifference", "ID_WeekBegin", "picture", "Status", "SignupDate"};

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

    private void forget (String username , String pass , String name , String email , int id){

        String FName = "ForgotUser";
        // Create request
        SoapObject request = new SoapObject(Constants.NAMESPACE, FName);

        PropertyInfo pid;
        pid = new PropertyInfo();
        pid.name="username";
        pid.type=String.class;
        pid.setValue(username);

        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="password";
        pid.type=String.class;
        pid.setValue(pass);

        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="NAME";
        pid.type=String.class;
        pid.setValue(name);

        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="Email";
        pid.type=String.class;
        pid.setValue(email);

        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="ID";
        pid.type=Double.class;
        pid.setValue(id);

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

        ArrayList<HashMap<String, String>> ArrayListHash = null;
        try {
            // Invole web service
            androidHttpTransport.call(Constants.SOAP_ACTION + FName, envelope);
            // Get the response

            resultsString = (SoapPrimitive) envelope.getResponse();
            //String response = resultsString.getProperty(0).toString();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("errortttttttttttttt", e.getMessage());
        }

    }


    class getFoget extends AsyncTask<String, String, String> {

        String email;

        public getFoget(String email) {
            this.email = email;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {
            listHM = get_forget(email);
            return null;
        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {

            try {
                HashMap<String , String> map = listHM.get(0);
                new Forget(map.get("UserName") , map.get("Password") , map.get("AppearName") , map.get("Email") ,
                        Integer.parseInt(map.get("ID"))).execute();
            }
            catch (Exception  e){
                onFinishedListener.onFailure(e.getMessage());
            }



        }

    }

    class Forget extends AsyncTask<String, String, String> {

        String username , pass ,  name ,  email ;
        int id ;

        public Forget(String username, String pass, String name, String email, int id) {
            this.username = username;
            this.pass = pass;
            this.name = name;
            this.email = email;
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {
            forget(username , pass , name , email , id);
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
