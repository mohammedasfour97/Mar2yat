package com.mriat.Auth.Views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mriat.Auth.Contract;
import com.mriat.Auth.Presenters.SignupPresenter;
import com.mriat.BaseActivity;
import com.mriat.Constants;
import com.mriat.DatabaseHelper;
import com.mriat.ModelClasses.IDName;
import com.mriat.ModelClasses.User;
import com.mriat.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignupActivity extends BaseActivity implements Contract.SignupContract.View {

    private EditText usercode , realname , username , email , reemail , password , repassword ;
    private CheckBox agree_terms ,  complete_month_name , summer_time;
    private Spinner gender , countries , date_format , time_options , styles , weekDays;
    private Button signup ;
    private ImageView back , image;
    private TextView toolbar_text;
    private LinearLayout usercode_layout;
    private SignupPresenter signupPresenter;
    private String un , appn , em , reem , pass , repass , get_img_text;
    private boolean bool_full_days , bool_summer_time;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri img_filePath;
    private Bitmap bitmap;
    private byte[] img_bytes ;
    private DatabaseHelper databaseHelper;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupPresenter = new SignupPresenter(this);

        databaseHelper = new DatabaseHelper(this);

        get_img_text = "no-image.jpg";

        initUI();
        setListners();
        setCheckListnerOfCheckbox();
        setCheckedboxText();
        setSpinners();

        if (getIntent().getStringExtra("edit").equals("no")){
            usercode_layout.setVisibility(View.GONE);
            toolbar_text.setText(getString(R.string.sign_up));
        }
        else {
            setEditData();
        }
    }


    private void initUI(){

        username = findViewById(R.id.usrename);
        realname = findViewById(R.id.real_name);
        email = findViewById(R.id.email);
        reemail = findViewById(R.id.reemail);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        agree_terms = findViewById(R.id.aggree_to_terms);
        signup = findViewById(R.id.continuee);
        countries = findViewById(R.id.countries);
        styles = findViewById(R.id.styles);
        date_format = findViewById(R.id.date_format);
        weekDays = findViewById(R.id.week_days);
        time_options = findViewById(R.id.time_options);
        gender = findViewById(R.id.gender);
        back = findViewById(R.id.back);
        complete_month_name = findViewById(R.id.complete_month_name);
        summer_time = findViewById(R.id.summer_time);
        usercode = findViewById(R.id.usercode_name);
        image = findViewById(R.id.image);
        usercode_layout = findViewById(R.id.usercode_layout);
        toolbar_text = findViewById(R.id.toolbar_text);

    }


    private void setListners(){

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validation()) {

                    showProgress();

                    if (getIntent().getStringExtra("edit").equals("no")) {
                        signupPresenter.requestCheckUsers(username.getText().toString(), realname.getText().toString(),
                                email.getText().toString());

                    } else {
                        signupPresenter.requestcheckUserBeforeUpdate(un, appn, em,
                                Integer.parseInt(databaseHelper.getUser().get(0).getID()));
                    }
                }

           //     startActivity(new Intent(SignupActivity.this , HomeActivity.class));
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chooseImage();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    private void setCheckListnerOfCheckbox(){

        check(agree_terms.isChecked());

        agree_terms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                check(b);
            }
        });

        complete_month_name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                bool_full_days = b ;
            }
        });

        summer_time.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                bool_summer_time = b ;
            }
        });
    }


    private void setSpinners(){

        signupPresenter.requestCountries();
        signupPresenter.requestDataFormat();
        signupPresenter.requestGender();
        signupPresenter.requestStyles();
        signupPresenter.requestTimeOptions();
        signupPresenter.requestWeekDays();
    }


    private void check(boolean b){
        if (b){
            signup.setEnabled(true);
        }
        else {
            signup.setEnabled(false);
        }
    }


    private void setCheckedboxText(){
        SpannableString ss = new SpannableString(getString(R.string.agree_to_terms));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                //  startActivity(new Intent(MyActivity.this, NextActivity.class));
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
                ds.setColor(Color.BLUE);
            }
        };
        ss.setSpan(clickableSpan, 22, 53, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        agree_terms.setText(ss);
        agree_terms.setMovementMethod(LinkMovementMethod.getInstance());
        // agree_terms.setHighlightColor(Color.BLUE);
    }


    private boolean validation(){

        boolean _return ;

        un = username.getText().toString();
        appn  = realname.getText().toString();
        em = email.getText().toString();
        reem  = reemail.getText().toString();
        pass = password.getText().toString();
        repass  = repassword.getText().toString();

        if (TextUtils.isEmpty(un)){
            username.setError(getResources().getString(R.string.error_username));
            _return  = false;
        }
        else if (TextUtils.isEmpty(appn)){
            reemail.setError(getResources().getString(R.string.error_appname));
            _return  = false;
        }
        else if (TextUtils.isEmpty(em)){
            email.setError(getResources().getString(R.string.error_email));
            _return  = false;
        }
        else if (TextUtils.isEmpty(reem)){
            reemail.setError(getResources().getString(R.string.error_reemail));
            _return  = false;
        }
        else if (TextUtils.isEmpty(pass)){
            password.setError(getResources().getString(R.string.error_pass));
            _return  = false;
        }
        else if (TextUtils.isEmpty(repass)){
            password.setError(getResources().getString(R.string.error_repass));
            _return  = false;
        }
        else{
            _return = true;
        }



        return _return;

    }


    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    private void setEditData(){

        User user = new DatabaseHelper(this).getUser().get(0);

        realname.setText(user.getAppearName());
        username.setText(user.getUserName());
        usercode.setText(user.getCode());
        email.setText(user.getEmail());
        reemail.setText(user.getEmail());
        password.setText(user.getPassword());
        repassword.setText(user.getPassword());
        complete_month_name.setChecked(Boolean.parseBoolean(user.getMonthFullName()));
        summer_time.setChecked(Boolean.parseBoolean(user.getActivateDaylight()));
        toolbar_text.setText(getString(R.string.action_my_account));
       // password.setText(user.getPassword());
       // repassword.setText(user.getPassword());
        Glide.with(SignupActivity.this).load(Constants.ImageURl + user.getPicture()).into(image);

        get_img_text = user.getPicture();
    }


    private void fillSpinner(ArrayList<HashMap<String,String>> listHM , Spinner spinner){

        List<IDName> signupSpinnerList = new ArrayList<>();

        for(HashMap<String , String> map: listHM){
            signupSpinnerList.add(new IDName(map.get("ID") , map.get("NAME")));
        }
        ArrayAdapter<IDName> dataAdapter = new ArrayAdapter<IDName>(this,
                android.R.layout.simple_spinner_item, signupSpinnerList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        if (getIntent().getStringExtra("edit").equals("yes")){
            Constants.selectSpinnerItemByValue(gender , databaseHelper.getUser().get(0).getID_Gender());
            Constants.selectSpinnerItemByValue(countries , databaseHelper.getUser().get(0).getID_Country());
            Constants.selectSpinnerItemByValue(styles , databaseHelper.getUser().get(0).getID_DefaultStyle());
            Constants.selectSpinnerItemByValue(date_format , databaseHelper.getUser().get(0).getID_DateFormat());
            Constants.selectSpinnerItemByValue(time_options , databaseHelper.getUser().get(0).getID_TimeDifference());
            Constants.selectSpinnerItemByValue(weekDays , databaseHelper.getUser().get(0).getID_WeekBegin());
        }
    }


    private void getValuesToSignin(){
        IDName coun = (IDName) countries.getSelectedItem();
        String scoun= coun.getId();
        IDName sty = (IDName) styles.getSelectedItem();
        String ssty= sty.getId();
        IDName datef = (IDName) date_format.getSelectedItem();
        String sdatef= datef.getId();
        IDName weekd = (IDName) weekDays.getSelectedItem();
        String sweekd= weekd.getId();
        IDName time_op = (IDName) time_options.getSelectedItem();
        String stime_op= time_op.getId();
        IDName gen = (IDName) gender.getSelectedItem();
        String sgen= gen.getId();

        signup(un , appn , pass , em , Integer.parseInt(sgen) , Integer.parseInt(scoun) , Integer.parseInt(ssty) , Integer.parseInt(sdatef) ,
                bool_full_days , bool_summer_time , Integer.parseInt(stime_op) , Integer.parseInt(sweekd) ,
                false , 0, "no-image.jpg");
    }


    private void getValuesToUpdate(){
        IDName coun = (IDName) countries.getSelectedItem();
        String scoun= coun.getId();
        IDName sty = (IDName) styles.getSelectedItem();
        String ssty= sty.getId();
        IDName datef = (IDName) date_format.getSelectedItem();
        String sdatef= datef.getId();
        IDName weekd = (IDName) weekDays.getSelectedItem();
        String sweekd= weekd.getId();
        IDName time_op = (IDName) time_options.getSelectedItem();
        String stime_op= time_op.getId();
        IDName gen = (IDName) gender.getSelectedItem();
        String sgen= gen.getId();

        signupPresenter.requestupdateUser(Integer.parseInt(databaseHelper.getUser().get(0).getID()) , un , appn , pass , em ,
                Integer.parseInt(sgen) , Integer.parseInt(scoun) , Integer.parseInt(ssty) , Integer.parseInt(sdatef) ,
                bool_full_days , bool_summer_time , Integer.parseInt(stime_op) , Integer.parseInt(sweekd) ,
                Boolean.parseBoolean(databaseHelper.getUser().get(0).getStatus()), 0, get_img_text , Boolean.parseBoolean(databaseHelper.getUser().get(0).getAdminStatus()));
    }


    private void signup(String UserName, String AppearName, String Password,
                        String Email, int ID_Gender, int ID_Country, int ID_DefaultStyle, int ID_DateFormat,
                        boolean MonthFullName, boolean ActivateDaylight, int ID_TimeDifference, int ID_WeekBegin,
                        boolean Status, int ID_USER, String picture) {

        signupPresenter.requestSignup(UserName, AppearName, Password,
                Email,  ID_Gender, ID_Country, ID_DefaultStyle, ID_DateFormat,
                MonthFullName, ActivateDaylight, ID_TimeDifference, ID_WeekBegin,
                Status, ID_USER, picture);

    }



    @Override
    public void signup() {

        getValuesToSignin();
    }

    @Override
    public void uploadImage() {
        signupPresenter.requestuploadImage(img_bytes , get_img_text);
    }

    @Override
    public void updateUser() {

        getValuesToUpdate();

    }

    @Override
    public void activation(String id, String code, String activationCode) {

        signupPresenter.requestActivation(activationCode , appn , em , Integer.parseInt(id));

    }

    @Override
    public void fillGender(ArrayList<HashMap<String, String>> listHM) {
        fillSpinner(listHM , gender );
    }

    @Override
    public void fillCountries(ArrayList<HashMap<String, String>> listHM) {
        fillSpinner(listHM , countries );
    }

    @Override
    public void fillStyles(ArrayList<HashMap<String, String>> listHM) {
        fillSpinner(listHM , styles );
    }

    @Override
    public void fillDateFormat(ArrayList<HashMap<String, String>> listHM) {
        fillSpinner(listHM , date_format );
    }

    @Override
    public void fillWeekDays(ArrayList<HashMap<String, String>> listHM) {
        fillSpinner(listHM , weekDays );
    }

    @Override
    public void fillTimeOptions(ArrayList<HashMap<String, String>> listHM) {
        fillSpinner(listHM , time_options );
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            try {

                if (Constants.getImageSizeFromUriInMegaByte(SignupActivity.this , data.getData()) > 10) {
                    Toast.makeText(SignupActivity.this, getResources().getString(R.string.image_size_large), Toast.LENGTH_SHORT).show();
                    return;
                }

                String[] image_extentions = new String[]{"png", "jpg", "jpeg", "tif", "tiff", "raw", "gif", "bmp"};

                boolean img_ext = false;

                for (String ext : image_extentions) {
                    if (Constants.getFileExtension(SignupActivity.this , data.getData()).equals(ext)) {
                        img_ext = true;
                        break;
                    }
                }

                if (!img_ext) {
                    Toast.makeText(SignupActivity.this, getResources().getString(R.string.img_extention_wrong), Toast.LENGTH_SHORT).show();
                    return;
                }

                bitmap = MediaStore.Images.Media.getBitmap(SignupActivity.this.getContentResolver(), data.getData());
                image.setImageBitmap(bitmap);

                img_filePath = data.getData();

                get_img_text = System.currentTimeMillis() + (Constants.queryName(SignupActivity.this , img_filePath)).replace(" " , "_");

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                img_bytes = stream.toByteArray();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void showProgress() {
        showProgressDialog();
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    @Override
    public void onFinished(String message) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this , R.style.MyAlertDialogStyle);
        builder.setMessage(getResources().getString(R.string.activation_msg));
        builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Toast.makeText(SignupActivity.this, getResources().getString(R.string.successfully_sign_up), Toast.LENGTH_LONG).show();

                startLoginActivity("" , "");
            }
        });
        builder.setNegativeButton(R.string.reactivation, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                showProgress();
                signupPresenter.requestReActivation(em);
            }
        });
        builder.show();


    }

    @Override
    public void onUpdateFinished() {

        Toast.makeText(this, getResources().getString(R.string.user_data_updated_successfully), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onFailure(String error) {

       switch (error){

            case "u" :
                Toast.makeText(this, getResources().getString(R.string.username_repeated), Toast.LENGTH_SHORT).show();;
                break;

            case "e" :
                Toast.makeText(this, getResources().getString(R.string.uemail_repeated), Toast.LENGTH_SHORT).show();;
                break;

            case "a" :
                Toast.makeText(this, getResources().getString(R.string.appear_repeated), Toast.LENGTH_SHORT).show();;
                break;

            case  "error_upload_img" :
                Toast.makeText(this, getResources().getString(R.string.error_upload_img), Toast.LENGTH_SHORT).show();
                break;

           case "error_sending_activation" :
               Toast.makeText(this, getResources().getString(R.string.error_sending_activation), Toast.LENGTH_SHORT).show();
               break;

                default:
                    Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                    break;

        }

           }
}
