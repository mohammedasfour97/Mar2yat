package com.mriat.Subjects.Views;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mriat.Auth.Presenters.SignupPresenter;
import com.mriat.BaseActivity;
import com.mriat.DatabaseHelper;
import com.mriat.Menu.MainMenuPresenter;
import com.mriat.Menu.SunMenuPresenter;
import com.mriat.ModelClasses.IDName;
import com.mriat.R;
import com.mriat.Subjects.Contract;
import com.mriat.Subjects.Presenters.AddSubjectPresenter;
import com.mriat.TinyDB;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import in.nashapp.androidsummernote.Summernote;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddSubjectActivity extends BaseActivity implements Contract.AddSubjectContract.View {

    private EditText adress , publisher ,writer  , location , location_name , sponser , publish_date , start_date , end_date , comments_num,
    views_num , appear_arrange , short_text , image_video_sound_name, city;
    private Spinner main_categ , sub_categ , country;
    private TextView image_text , video_text, audio_text;
    private ImageView image , voice , video , back ;
    private Summernote richEditor ;
    private Button save , cancel ;
    private String get_address , get_publisher , get_writer , get_location , get_location_name , get_sponser , get_publish_date ,
            get_start_date , get_end_date , get_comments_num , get_views_num  , get_appear_arrange , get_short_text ,
            get_image_video_sound_name , get_status , get_image , get_video , get_voice , get_full_text , get_main_categ ,
            get_sub_categ , get_country , get_city , get_img_text , get_voice_text , get_video_text;
    private byte[] img_bytes , voice_bytes , video_bytes;
    private List<IDName> main_categories_list , sub_categories_list , countries_list;
    private Map<String , String > string_map;
    private Map<String , Integer> int_map;
    private final int PICK_IMAGE_REQUEST = 71;
    private final int PICK_AUDIO_REQUEST = 72;
    private final int PICK_VIDEO_REQUEST = 73;
    private Uri img_filePath , audio_filePath , video_filePath;
    private Bitmap bitmap;
    private Calendar myCalendar;
    private SimpleDateFormat sdf;
    private String uid;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_subject);

        initUI();
        setListeners();

        get_img_text = "";

        get_voice_text = get_video_text = "";

        main_categories_list = new ArrayList<>();
        sub_categories_list = new ArrayList<>();
        countries_list = new ArrayList<>();

        myCalendar = Calendar.getInstance();

        String myFormat = "dd/MM/yyyy";
        sdf = new SimpleDateFormat(myFormat, Locale.US);

        if (!new TinyDB(this).getString("username").equals(""))
            uid = new DatabaseHelper(this).getUser().get(0).getID();
        else uid = "0";

        new MainMenuPresenter(this).requestMenu();
        new SignupPresenter(this).requestCountries();
    }

    private void initUI(){

        adress = findViewById(R.id.address);
        publisher = findViewById(R.id.publisher);
        writer = findViewById(R.id.writer);
        location = findViewById(R.id.location);
        location_name = findViewById(R.id.location_name);
        sponser = findViewById(R.id.sponser);
        appear_arrange = findViewById(R.id.appear_arrange);
        publish_date = findViewById(R.id.published_date);
        start_date = findViewById(R.id.started_date);
        end_date = findViewById(R.id.end_date);
        city = findViewById(R.id.city);
        comments_num = findViewById(R.id.comments_number);
        views_num = findViewById(R.id.view_number);
        short_text = findViewById(R.id.short_text);
        image_video_sound_name = findViewById(R.id.image_video_sound_name);
        image = findViewById(R.id.add_photo);
        voice = findViewById(R.id.add_voice);
        video = findViewById(R.id.add_video);
        richEditor = findViewById(R.id.summernote);
        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);
        main_categ = findViewById(R.id.main_category);
        sub_categ = findViewById(R.id.sub_category);
        country = findViewById(R.id.country);
        image_text = findViewById(R.id.image_title);
        audio_text = findViewById(R.id.voice_title);
        video_text = findViewById(R.id.video_title);
        back = findViewById(R.id.back);
    }


    private void setListeners(){

        publish_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                new DatePickerDialog(AddSubjectActivity.this, R.style.MyAlertDialogStyle, getDateSetListener(publish_date),
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

       start_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View view, boolean b) {

               new DatePickerDialog(AddSubjectActivity.this, R.style.MyAlertDialogStyle ,getDateSetListener(start_date),
                       myCalendar.get(Calendar.YEAR),
                       myCalendar.get(Calendar.MONTH),
                       myCalendar.get(Calendar.DAY_OF_MONTH)).show();
           }
       });

        end_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                new DatePickerDialog(AddSubjectActivity.this, R.style.MyAlertDialogStyle , getDateSetListener(end_date),
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validation()){

                    showProgress();

                    getTexts();

                    addSubject();
                }

            }
        });

        main_categ.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                IDName idName = (IDName) adapterView.getSelectedItem();
                String id = idName.getId();
                new SunMenuPresenter(AddSubjectActivity.this).requestMenu(uid ,  id);
                get_main_categ = id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chooseImage();
            }
        });

        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chooseVoice();
            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chooseVideo();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }


    private boolean validation (){

        boolean b = true;

        if (adress.getText().toString().equals("")){
            b = false ;
            adress.setError(getResources().getString(R.string.title_error));
            adress.requestFocus();
        }
        else if (appear_arrange.getText().toString().equals("")){
            b = false;
            appear_arrange.requestFocus();
        }
        return b ;
    }


    private void getTexts(){

        get_address = adress.getText().toString();
        get_publisher = publisher.getText().toString();
        get_writer = writer.getText().toString();
        get_location = location.getText().toString();
        get_location_name = location_name.getText().toString();
        get_sponser = sponser.getText().toString();
        get_appear_arrange = appear_arrange.getText().toString();
        get_publish_date = publish_date.getText().toString();
        get_start_date = start_date.getText().toString();
        get_end_date = end_date.getText().toString();
        get_comments_num = comments_num.getText().toString();
        get_views_num = views_num.getText().toString();
        get_appear_arrange = appear_arrange.getText().toString();
        get_short_text = short_text.getText().toString();
        get_image_video_sound_name = image_video_sound_name.getText().toString();
        get_city = city.getText().toString();
        get_full_text = richEditor.getText();

        collectData();
    }


    private void collectData(){

        string_map = new HashMap<>();
        string_map.put("get_address" , get_address);
        string_map.put("get_short_text" , get_short_text);
        string_map.put("get_full_text" , get_full_text);
        string_map.put("get_publish_date" , get_publish_date);
        string_map.put("get_image" , get_img_text);
        string_map.put("get_image_video_sound_name" , get_image_video_sound_name);
        string_map.put("get_voice" , get_voice_text);
        string_map.put("get_video" , get_video_text);
        string_map.put("get_writer" , get_writer);
        string_map.put("get_publisher" , get_publisher);
        string_map.put("get_location" , get_location);
        string_map.put("get_city" , get_city);
        string_map.put("get_location_name" , get_location_name);
        string_map.put("get_sponser" , get_sponser);
        string_map.put("get_start_date" , get_start_date);
        string_map.put("get_end_date" , get_end_date);

        int_map = new HashMap<>();
        int_map.put("get_main_categ" , Integer.valueOf(get_main_categ));
        int_map.put("Reviews" , 0);
        int_map.put("sub_categ" , Integer.valueOf(((IDName)sub_categ.getSelectedItem()).getId()));
        int_map.put("ID" , Integer.valueOf(new DatabaseHelper(AddSubjectActivity.this).getUser().get(0).getID()));
        int_map.put("Views" , 0);
        int_map.put("ID_Country" , Integer.valueOf(((IDName)country.getSelectedItem()).getId()));
        int_map.put("get_appear_arrange" , Integer.valueOf(get_appear_arrange));
        int_map.put("ID_USER" , 0);
    }


    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    private void chooseVoice() {
        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Voice"), PICK_AUDIO_REQUEST);
    }


    private void chooseVideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Video"), PICK_VIDEO_REQUEST);
    }


    private DatePickerDialog.OnDateSetListener getDateSetListener (final EditText e){

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                myCalendar.set(Calendar.YEAR, i);
                myCalendar.set(Calendar.MONTH, i1);
                myCalendar.set(Calendar.DAY_OF_MONTH, i2);
                e.setText(sdf.format(myCalendar.getTime()));
            }
        };

        return dateSetListener;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            try {

                if (getImageSizeFromUriInMegaByte(data.getData()) > 10) {
                    Toast.makeText(AddSubjectActivity.this, getResources().getString(R.string.image_size_large), Toast.LENGTH_SHORT).show();
                    return;
                }

                String[] image_extentions = new String[]{"png", "jpg", "jpeg", "tif", "tiff", "raw", "gif", "bmp"};

                boolean img_ext = false;

                for (String ext : image_extentions) {
                    if (getFileExtension(data.getData()).equals(ext)) {
                        img_ext = true;
                        break;
                    }
                }

                if (!img_ext) {
                    Toast.makeText(AddSubjectActivity.this, getResources().getString(R.string.img_extention_wrong), Toast.LENGTH_SHORT).show();
                    return;
                }

                bitmap = MediaStore.Images.Media.getBitmap(AddSubjectActivity.this.getContentResolver(), data.getData());
                image.setImageBitmap(bitmap);

                img_filePath = data.getData();

                get_img_text = System.currentTimeMillis() + (queryName(img_filePath)).replace(" " , "_");
                image_text.setText(get_img_text);
                image_text.setVisibility(View.VISIBLE);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                img_bytes = stream.toByteArray();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_AUDIO_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {


            if (getImageSizeFromUriInMegaByte(data.getData()) > 10) {
                Toast.makeText(AddSubjectActivity.this, getResources().getString(R.string.voice_size_large), Toast.LENGTH_SHORT).show();
                return;
            }

            String[] image_extentions = new String[]{"mp3", "3GP", "WAV"};

            boolean img_ext = false;

            for (String ext : image_extentions) {
                if (getFileExtension(data.getData()).equals(ext)) {
                    img_ext = true;
                    break;
                }
            }

            if (!img_ext) {
                Toast.makeText(AddSubjectActivity.this, getResources().getString(R.string.voice_extention_wrong), Toast.LENGTH_SHORT).show();
                return;
            }

            audio_filePath = data.getData();

            get_voice_text = System.currentTimeMillis() + (queryName(audio_filePath) + "." + getFileExtension(audio_filePath)).replace(" " , "_");
            audio_text.setText(get_voice_text);
            audio_text.setVisibility(View.VISIBLE);

            try {
                InputStream iStream = getContentResolver().openInputStream(audio_filePath);
                voice_bytes = getBytes(iStream);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {


            if (getImageSizeFromUriInMegaByte(data.getData()) > 10) {
                Toast.makeText(AddSubjectActivity.this, getResources().getString(R.string.video_size_large), Toast.LENGTH_SHORT).show();
                return;
            }

            String[] image_extentions = new String[]{"mp4", "3GP"};

            boolean img_ext = false;

            for (String ext : image_extentions) {
                if (getFileExtension(data.getData()).equals(ext)) {
                    img_ext = true;
                    break;
                }
            }

            if (!img_ext) {
                Toast.makeText(AddSubjectActivity.this, getResources().getString(R.string.vido_extention_wrong), Toast.LENGTH_SHORT).show();
                return;
            }

            video_filePath = data.getData();

            get_video_text = System.currentTimeMillis() + (queryName(video_filePath)).replace(" " , "_");
            video_text.setText(get_video_text);
            video_text.setVisibility(View.VISIBLE);

            try {
                InputStream iStream = getContentResolver().openInputStream(video_filePath);
                video_bytes = getBytes(iStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private String getFileExtension(Uri filePath) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(filePath));
    }


    private String queryName(Uri filePath) {
        Cursor returnCursor =
                getContentResolver().query(filePath, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }


    private double getImageSizeFromUriInMegaByte(Uri filePath) {
        String scheme = filePath.getScheme();
        double dataSize = 0;
        if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
            try {
                InputStream fileInputStream = getContentResolver().openInputStream(filePath);
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


    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }



    @Override
    public void onFinished() {
        Toast.makeText(this, getResources().getString(R.string.subject_added_succs), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void fillMainCategories(ArrayList<HashMap<String, String>> listHM) {
        main_categories_list.clear();
        IDName idName;
        for (HashMap<String , String> map : listHM){
            idName = new IDName();
            idName.setId(map.get("ID"));
            idName.setName(map.get("Sections"));
            main_categories_list.add(idName);
        }
        ArrayAdapter<IDName> dataAdapter = new ArrayAdapter<IDName>(this,
                android.R.layout.simple_spinner_item, main_categories_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        main_categ.setAdapter(dataAdapter);
    }

    @Override
    public void fillSubCategories(ArrayList<HashMap<String, String>> listHM) {
        sub_categories_list.clear();
        IDName idName;
        for (HashMap<String , String> map : listHM){
            idName = new IDName();
            idName.setId(map.get("ID"));
            idName.setName(map.get("SubSections"));
            sub_categories_list.add(idName);
        }
        ArrayAdapter<IDName> dataAdapter = new ArrayAdapter<IDName>(this,
                android.R.layout.simple_spinner_item, sub_categories_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sub_categ.setAdapter(dataAdapter);
    }

    @Override
    public void fillCountries(ArrayList<HashMap<String, String>> listHM) {
        countries_list.clear();
        IDName idName;
        for (HashMap<String , String> map : listHM){
            idName = new IDName();
            idName.setId(map.get("ID"));
            idName.setName(map.get("NAME"));
            countries_list.add(idName);
        }
        ArrayAdapter<IDName> dataAdapter = new ArrayAdapter<IDName>(this,
                android.R.layout.simple_spinner_item, countries_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(dataAdapter);

    }

    @Override
    public void addSubjectData() {

        showProgress();
        new AddSubjectPresenter(AddSubjectActivity.this).requestAddSubject(string_map , int_map , false);
    }

    @Override
    public void addSubject() {

        new AddSubjectPresenter(this , img_bytes , voice_bytes , video_bytes , get_img_text , get_voice_text , get_video_text)
                .requestUploadMedia();
    }

    @Override
    public void onFailure(String error) {

        switch (error){
            case "dublicate_title" :
                Toast.makeText(this, getResources().getString(R.string.error_duplicate_title), Toast.LENGTH_SHORT).show();

            case "error_upload_img" :
                Toast.makeText(this, getResources().getString(R.string.error_upload_img), Toast.LENGTH_SHORT).show();

            case "error_upload_voice" :
                Toast.makeText(this, getResources().getString(R.string.error_upload_voice), Toast.LENGTH_SHORT).show();

            case "error_upload_video" :
                Toast.makeText(this, getResources().getString(R.string.error_upload_video), Toast.LENGTH_SHORT).show();

        /*  default:
               Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
               */

        }
        }


    @Override
    public void showProgress() {
        showProgressDialog();
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }
}
