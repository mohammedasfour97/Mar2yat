package com.mriat.api;

import java.util.ArrayList;
import java.util.HashMap;

public class WebService {

    public static ArrayList<HashMap<String, String>> GetMSlides() {
        MasterSlayer MS = new MasterSlayer("GetMSlides");
        //1 - any parameter send
        //2 - request
        ArrayList<String> request_params = new ArrayList<String>();
        request_params.add("ID");
        request_params.add("Slides");
        MS.setRequest_paramName(request_params);

        //3 - any image
        MS.setIsImage1("Slides");

        return MS.Call();
    }

    public static ArrayList<HashMap<String, String>> GetSearchPro(String txt) {
        MasterSlayer MS = new MasterSlayer("GetSearchPro");
        /** HINT
         *
         */
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("txt");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(txt);
        MS.addsendparam(send_params, send_params_value);

        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
        request_params.add("LNK");
        request_params.add("num");
        request_params.add("ID");
        request_params.add("Products");
        request_params.add("SubjectsSummary");
        request_params.add("SubjectsArticle");
        request_params.add("Video");
        request_params.add("Voice");
        request_params.add("AFile");
        request_params.add("Image");
        request_params.add("Type");
        request_params.add("Notes");
        request_params.add("Status");
        request_params.add("Watch");
        request_params.add("Download");
        request_params.add("Ranking");
        request_params.add("Price");
        request_params.add("ID_Sections");
        request_params.add("Sections");
        request_params.add("ID_PublisherSections");
        request_params.add("PublisherSections");
        request_params.add("FLG");
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
        MS.setIsImage1("Image");
        MS.setIsImage2("Voice");

        return MS.Call();

    }
}
