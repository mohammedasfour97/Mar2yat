package com.mriat.Subjects.Models;

import android.os.AsyncTask;
import android.util.Log;

import com.mriat.Constants;
import com.mriat.Subjects.Contract;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class EditSubjectModel implements Contract.EditSubjectContract.Model {

    private Contract.EditSubjectContract.Model.OnFinishedListener onFinishedListener;
    private SoapObject resultsString;


    public EditSubjectModel(OnFinishedListener onFinishedListener) {
        this.onFinishedListener = onFinishedListener;
    }


    @Override
    public void deleteSubject(int ID, int ID_USER) {

        new DeleteSubject(ID , ID_USER).execute();
    }


    public void delete_subject(int ID , int ID_USER) {
        String FName = "Delete_Subjects";
        // Create request
        SoapObject request = new SoapObject(Constants.NAMESPACE, FName);

        PropertyInfo pid;
        pid = new PropertyInfo();
        pid.name="ID";
        pid.type=Double.class;
        pid.setValue(ID);
        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="ID_USER";
        pid.type=Double.class;
        pid.setValue(ID_USER);
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

            resultsString = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("eeeeror", e.getMessage());
        }
    }

    class DeleteSubject extends AsyncTask<String, String, String> {

        int ID,  ID_USER;

        public DeleteSubject( int ID, int ID_USER) {
            this.ID = ID;
            this.ID_USER = ID_USER;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {
            delete_subject( ID , ID_USER);
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
