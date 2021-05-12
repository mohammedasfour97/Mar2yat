package com.mriat.Subjects.Models;

import android.os.AsyncTask;
import android.util.Log;

import com.mriat.Constants;
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
import java.util.Map;

public class AddSubjectModel implements Contract.AddSubjectContract.Model {

    private ArrayList<HashMap<String,String>> slistHM ;
    private OnFinishedListener onFinishedListener;
    private SoapPrimitive resultsString;

    public AddSubjectModel(OnFinishedListener onFinishedListener) {
        this.onFinishedListener = onFinishedListener;
    }

    @Override
    public void addSubject(Map<String , String> stringStringHashMap , Map<String , Integer> integerHashMap , boolean status) {

        new addsubject("ADD_Subjects", stringStringHashMap , integerHashMap , status).execute();
    }

    @Override
    public void uploadMedia(byte[] bytes, String img_name) {

        new UploadMedia(bytes , img_name).execute();
    }


    public static ArrayList<HashMap<String, String>> add_subject(String method , Map<String , String> stringStringHashMap ,
                                                                 Map<String , Integer> integerHashMap , boolean status) {
        String FName = method;
        // Create request
        SoapObject request = new SoapObject(Constants.NAMESPACE, FName);

        PropertyInfo pid;
        pid = new PropertyInfo();
        pid.name="ID_Sections";
        pid.type=Double.class;
        pid.setValue(integerHashMap.get("get_main_categ"));
        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="ID_SubSections";
        pid.type=Double.class;
        pid.setValue(integerHashMap.get("sub_categ"));
        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="ID_Customers";
        pid.type=Double.class;
        pid.setValue(integerHashMap.get("ID"));
        request.addProperty(pid);


        pid = new PropertyInfo();
        pid.name="Title";
        pid.type=String.class;
        pid.setValue(stringStringHashMap.get("get_address"));
        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="Summary";
        pid.type=String.class;
        pid.setValue(stringStringHashMap.get("get_short_text"));
        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="Fulltext";
        pid.type=String.class;
        pid.setValue(stringStringHashMap.get("get_full_text"));
        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="publishdate";
        pid.type=String.class;
        pid.setValue(stringStringHashMap.get("get_publish_date"));
        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="Reviews";
        pid.type=Double.class;
        pid.setValue(integerHashMap.get("Reviews"));
        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="Views";
        pid.type=Double.class;
        pid.setValue(integerHashMap.get("Views"));
        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="picture";
        pid.type=String.class;
        pid.setValue(stringStringHashMap.get("get_image"));
        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="picturetext";
        pid.type=String.class;
        pid.setValue(stringStringHashMap.get("get_image_video_sound_name"));
        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="Voice";
        pid.type=String.class;
        pid.setValue(stringStringHashMap.get("get_voice"));
        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="Video";
        pid.type=String.class;
        pid.setValue(stringStringHashMap.get("get_video"));
        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="Author";
        pid.type=String.class;
        pid.setValue(stringStringHashMap.get("get_writer"));
        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="publisher";
        pid.type=String.class;
        pid.setValue(stringStringHashMap.get("get_publisher"));
        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="Location";
        pid.type=String.class;
        pid.setValue(stringStringHashMap.get("get_location"));
        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="ID_Country";
        pid.type=Integer.class;
      //  pid.setValue(integerHashMap.get("ID_Country"));
        pid.setValue(integerHashMap.get("ID_Country"));
        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="City";
        pid.type=String.class;
        pid.setValue(stringStringHashMap.get("get_city"));
        request.addProperty(pid);


        pid = new PropertyInfo();
        pid.name="LocationName";
        pid.type=String.class;
        pid.setValue(stringStringHashMap.get("get_location_name"));
        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="Sponsor";
        pid.type=String.class;
        pid.setValue(stringStringHashMap.get("get_sponser"));
        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="Startdate";
        pid.type=String.class;
        pid.setValue(stringStringHashMap.get("get_start_date"));
        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="Enddate";
        pid.type=String.class;
        pid.setValue(stringStringHashMap.get("get_end_date"));
        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="Status";
        pid.type=Boolean.class;
        pid.setValue(status);
        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="Ranking";
        pid.type=Double.class;
        pid.setValue(integerHashMap.get("get_appear_arrange"));
        request.addProperty(pid);

        pid = new PropertyInfo();
        pid.name="ID_USER";
        pid.type=Double.class;
        pid.setValue(integerHashMap.get("ID_USER"));
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
            String [] prop = new String[] {"error" , "msg"};
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

    public void upload_media(byte[] bitmapdata, String img_name) {
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

        try {
            // Invole web service
            androidHttpTransport.call(Constants.SOAP_ACTION + FName, envelope);
            // Get the response

            resultsString = (SoapPrimitive) envelope.getResponse();
            //String response = resultsString.getProperty(0).toString();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("errrrrorr", e.getMessage());
        }

    }



    class addsubject extends AsyncTask<String, String, String> {

        String method;
        Map<String , String> stringStringHashMap;
        Map<String , Integer> integerHashMap;
        boolean Status;

        public addsubject(String method , Map<String , String> stringStringHashMap , Map<String , Integer> integerHashMap,
                          boolean status) {

            this.method = method;
            this.integerHashMap = integerHashMap;
            this.stringStringHashMap = stringStringHashMap;
            Status = status;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {
            slistHM = add_subject( method , stringStringHashMap , integerHashMap , Status);
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

    class UploadMedia extends AsyncTask<String, String, String> {

        byte[] bitmapdata;
        String img_name;

        public UploadMedia(byte[] bitmapdata , String img_name) {
            this.bitmapdata = bitmapdata;
            this.img_name = img_name;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {
            upload_media(bitmapdata , img_name);
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
                Log.d("errrrrorr", e.getMessage());
            }



        }

    }

}
