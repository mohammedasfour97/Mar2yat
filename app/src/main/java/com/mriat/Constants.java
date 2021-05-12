package com.mriat;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.v7.app.AlertDialog;
import android.webkit.MimeTypeMap;
import android.widget.Adapter;
import android.widget.Spinner;

import com.mriat.Auth.Views.LoginActivity;
import com.mriat.ModelClasses.IDName;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static String NAMESPACE = "http://tempuri.org/";
    public static String URL = "http://mriatservice.mriat.com/Service.asmx";///MyService
    public static String SOAP_ACTION = "http://tempuri.org/";
    public static String ImageURl = "http://mriat.com/dist/img/";
    public static String SUBJECT_URL = "mriat.com/User/view.aspx?PID=";

    public static void setEmpty(ArrayList<HashMap<String,String>> listHM){
        for (Map<String , String> map : listHM){
            for (Map.Entry<String,String> entry : map.entrySet()) {
                if (entry.getValue().equals("anyType{}")) {
                    entry.setValue("");
                }

            }

        }
    }

    public static String queryName(Context context , Uri filePath) {
        Cursor returnCursor =
                context.getContentResolver().query(filePath, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }

    public static double getImageSizeFromUriInMegaByte(Context context , Uri filePath) {
        String scheme = filePath.getScheme();
        double dataSize = 0;
        if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
            try {
                InputStream fileInputStream = context.getContentResolver().openInputStream(filePath);
                if (fileInputStream != null) {
                    dataSize = fileInputStream.available();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (scheme.equals(ContentResolver.SCHEME_FILE)) {
            String path = filePath.getPath();
            File file = null;
            try {
                file = new File(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (file != null) {
                dataSize = file.length();
            }
        }
        return dataSize / (1024 * 1024);
    }

    public static String getFileExtension(Context context , Uri filePath) {
        ContentResolver cR = context.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(filePath));
    }

    public static void selectSpinnerItemByValue(Spinner spnr, String value) {
        try {
            Adapter adapter = spnr.getAdapter();
            IDName idName;
            for (int position = 0; position < adapter.getCount(); position++) {
                idName = (IDName) adapter.getItem(position);
                if(idName.getId().equals(value)) {
                    spnr.setSelection(position);
                    return;
                }
            }
        }
        catch (Exception e){

        }

    }

    public static void showSignInMessage(final Context contex){

        final AlertDialog.Builder builder = new AlertDialog.Builder(contex, R.style.MyAlertDialogStyle);
        builder.setMessage(contex.getResources().getString(R.string.must_signin));
        builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton(R.string.login, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

              contex.startActivity(new Intent(contex , LoginActivity.class));
            }
        });
        builder.show();
    }

}
