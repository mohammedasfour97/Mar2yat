package com.mriat.Fragments;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mriat.R;
import com.mriat.Subjects.Views.SubjectDetailsActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

public class VisitorDataFragment extends DialogFragment {

    private EditText email , name;
    private Button contin ;
    private ImageView image;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    private Bitmap bitmap ;

    public VisitorDataFragment() {
    }

    public static VisitorDataFragment newInstance(String id_com , String comm , String id_sub ) {
        VisitorDataFragment visitorDataFragment = new VisitorDataFragment();
        Bundle args = new Bundle();
        args.putString("id_comments", id_com);
        args.putString("comment", comm);
        args.putString("id_sub", id_sub);
        visitorDataFragment.setArguments(args);
        return visitorDataFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_visitor_data, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        email = view.findViewById(R.id.email);
        name = view.findViewById(R.id.name);
        image = view.findViewById(R.id.add_photo);
        contin = view.findViewById(R.id.continuee);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        contin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(email.getText())){
                    email.setError(getResources().getString(R.string.error_email));
                    return;
                }
                if (TextUtils.isEmpty(name.getText())){
                    name.setError(getResources().getString(R.string.error_appname));
                    return;
                }

                ((SubjectDetailsActivity)getActivity()).showProgress();

                if (bitmap!=null){

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] bitmapdata = stream.toByteArray();

                    ((SubjectDetailsActivity)getActivity()).uploadImage(bitmapdata ,
                            ((SubjectDetailsActivity)getActivity()).getCommentDetails("0" , getArguments().getString("id_comments"),
                                    name.getText().toString() , email.getText().toString() ,
                                    (System.currentTimeMillis() + queryName()).replace(" " , "_") ,
                                    getArguments().getString("comment") ,
                                    getArguments().getString("id_sub") , "0"));
                }

                else {
                    ((SubjectDetailsActivity)getActivity()).addComment(((SubjectDetailsActivity)getActivity()).getCommentDetails("0" , getArguments().getString("id_comments"),
                            name.getText().toString() , email.getText().toString() , "no-image.jpg" ,
                            getArguments().getString("comment") ,
                            getArguments().getString("id_sub") , "0"));
                }



              dismiss();


            }
        });


    }


    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {

                if (getImageSizeFromUriInMegaByte() > 10){
                    Toast.makeText(getContext(), getResources().getString(R.string.image_size_large), Toast.LENGTH_SHORT).show();
                    return;
                }

                String[] image_extentions = new String []{"png" , "jpg" , "jpeg" , "tif" , "tiff" , "raw" , "gif" , "bmp"};

                boolean img_ext = false;

                for (String ext : image_extentions){
                    if (getFileExtension().equals(ext)){
                        img_ext = true;
                        break;
                    }
                }

                if (!img_ext) {
                    Toast.makeText(getContext(), getResources().getString(R.string.img_extention_wrong), Toast.LENGTH_SHORT).show();
                    return;
                }

                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                image.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private String getFileExtension() {
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(filePath));
    }


    private String queryName() {
        Cursor returnCursor =
                getContext().getContentResolver().query(filePath, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }


    private double getImageSizeFromUriInMegaByte() {
        String scheme = filePath.getScheme();
        double dataSize = 0;
        if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
            try {
                InputStream fileInputStream = getContext().getContentResolver().openInputStream(filePath);
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

    }
