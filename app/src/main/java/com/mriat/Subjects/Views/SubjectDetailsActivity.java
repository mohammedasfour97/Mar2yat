package com.mriat.Subjects.Views;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.bumptech.glide.Glide;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.mriat.BaseActivity;
import com.mriat.Constants;
import com.mriat.DatabaseHelper;
import com.mriat.Fragments.VisitorDataFragment;
import com.mriat.ModelClasses.Comment;
import com.mriat.R;
import com.mriat.Subjects.Contract;
import com.mriat.Subjects.Presenters.SubjectDetailsPresenter;
import com.mriat.TinyDB;
import com.mriat.Views.HomeActivity;
import com.shehabic.droppy.DroppyClickCallbackInterface;
import com.shehabic.droppy.DroppyMenuItem;
import com.shehabic.droppy.DroppyMenuPopup;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SubjectDetailsActivity extends BaseActivity implements Contract.SubjectsDetailsContract.View {

    private TextView adviced , started_date , end_date , location , location_name , writer ,
            country , city, title , img_title , toolbar_title;
    private EditText comment_ed_txt;
    private Button send_comment , download_img , download_voice , download_video , share_img , share_video;
    private WebView subject_details;
    private ImageView iamge , share , like , dislike;
    private JcPlayerView jcPlayerView;
    private VideoView videoView;
    private String adviced_text , started_date_text , end_date_text , location_text , location_name_text , writer_text , country_text,
            city_text , subject_details_text , title_text , image_text , imag_title_text , voice_txt , video_txt , rate , rates_count ,
    avg_rates , slikes , sdislike , sub_id , uid ;
    private boolean ft;
    private Toolbar toolbar;
    private RatingBar ratingBar;
    private TableRow advisor_row , strted_date_row , end_date_row , location_row , location_name_row , country_row , city_row , writer_row;
    private SubjectDetailsPresenter subjectDetailsPresenter;
    private RecyclerView CommentsrecyclerView;
    private SubjectsCommentsAdapter mAdapter;
    private List<Comment> commentList;
    private List<Comment> repliesList;
    public String email , name  , dirPath;
    private DatabaseHelper databaseHelper;
    private LinearLayout img_proccess , video_process;
    private FrameLayout image , video_frame;
    private final int PERMISSION_REQUEST_CODE = 50;
    private ProgressBar progressBar ;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_details);

        initUI();

        setListners();

        initCommentsRecyclerView();

        toolbar_title.setText(getIntent().getStringExtra("title"));

        subjectDetailsPresenter = new SubjectDetailsPresenter(this);

        databaseHelper = new DatabaseHelper(this);

        sub_id = getIntent().getStringExtra("id");
        if (!new TinyDB(this).getString("username").equals(""))
        uid = databaseHelper.getUser().get(0).getID();
        else uid = "0";

        ft = false;

        loadSubjectDetails();
    }


    private void initUI(){

        toolbar = findViewById(R.id.toolbar);

        adviced = findViewById(R.id.advised_text);
        started_date = findViewById(R.id.started_date_text);
        end_date = findViewById(R.id.end_date_date);
        location = findViewById(R.id.location_text);
        location_name = findViewById(R.id.location_name_text);
        writer = findViewById(R.id.writer_text);
        country = findViewById(R.id.country_text);
        city = findViewById(R.id.city_text);
        subject_details = findViewById(R.id.subject_details);
        iamge = findViewById(R.id.sub_image);
        title = findViewById(R.id.title);
        img_title = findViewById(R.id.image_title);
        comment_ed_txt = findViewById(R.id.commentEditText);
        send_comment = findViewById(R.id.sendButton);
        jcPlayerView = findViewById(R.id.audio_player);
        videoView = findViewById(R.id.video_view);
        share = findViewById(R.id.share);
        toolbar_title = findViewById(R.id.toolbar_title);
        download_img = findViewById(R.id.download_img);
        download_video = findViewById(R.id.download_video);
        download_voice = findViewById(R.id.download_voice);
        img_proccess = findViewById(R.id.img_proccess);
        video_process = findViewById(R.id.video_proccess);
        share_img = findViewById(R.id.share_img);
        share_video = findViewById(R.id.share_video);
        image = findViewById(R.id.image_layout);
        advisor_row = findViewById(R.id.advisor_rox);
        strted_date_row = findViewById(R.id.started_date_row);
        end_date_row = findViewById(R.id.end_date_row);
        location_row = findViewById(R.id.location_row);
        location_name_row = findViewById(R.id.location_name_row);
        country_row = findViewById(R.id.country_row);
        city_row = findViewById(R.id.city_row);
        writer_row = findViewById(R.id.writer_row);
        ratingBar = findViewById(R.id.rating);
        like = findViewById(R.id.like);
        dislike = findViewById(R.id.dis_like);
        video_frame = findViewById(R.id.video_view_frsame);
        progressBar = findViewById(R.id.progress);

    }


    private void setListners(){

        comment_ed_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals(""))
                    send_comment.setEnabled(false);
                else
                    send_comment.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (new TinyDB(SubjectDetailsActivity.this).getString("username").equals("")){

                    FragmentManager fm = getSupportFragmentManager();
                    VisitorDataFragment visitorDataFragment = VisitorDataFragment.newInstance("0" ,
                            comment_ed_txt.getText().toString() , getIntent().getStringExtra("id"));
                    visitorDataFragment.show(fm, "fragment_new_activity");
                }
                else {

                    showProgress();
                    Comment comment1 = getCommentDetails(databaseHelper.getUser().get(0).getID(),
                            "0" , databaseHelper.getUser().get(0).getAppearName() ,
                            databaseHelper.getUser().get(0).getEmail() , databaseHelper.getUser().get(0).getPicture() ,
                            comment_ed_txt.getText().toString() , getIntent().getStringExtra("id") , "0");

                    addComment(comment1);
                }
            }
        });


        download_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dirPath = Environment.getExternalStorageDirectory() + "/mariat/images";

                downloadMedia(Constants.ImageURl + image_text , dirPath , image_text);

                Log.d("dirrrr", dirPath);
            }
        });


        download_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dirPath = Environment.getExternalStorageDirectory() + "/mariat/voices";

                downloadMedia(Constants.ImageURl + voice_txt , dirPath , voice_txt);
            }
        });


        download_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dirPath = Environment.getExternalStorageDirectory() + "/mariat/videos";

                downloadMedia(Constants.ImageURl + video_txt , dirPath , video_txt);
            }
        });


        share_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImg(image_text);
            }
        });


        share_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareVideo(video_txt);
            }
        });


        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!checkLikeAndDisslike(slikes)){

                    subjectDetailsPresenter.requestAddLikes(sub_id , uid , true);
                    loadSubjectDetails();
                }

            }
        });


        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!checkLikeAndDisslike(sdislike)){

                    subjectDetailsPresenter.requestAddLikes(sub_id , uid , false);
                    loadSubjectDetails();
                }

            }
        });

    }


    private void setRatingBarListner(){

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                if (!ft){
                    subjectDetailsPresenter.requestAddRate(sub_id , uid , (int) v);
                }
                else {
                    onFailure(R.string.already_rated);
                }

            }
        });
    }


    private void initPopUpMenu(){

        DroppyMenuPopup.Builder droppyBuilder = new DroppyMenuPopup.Builder(SubjectDetailsActivity.this, share);

// Add Item with icon
        droppyBuilder.addMenuItem(new DroppyMenuItem(getResources().getString(R.string.whatsapp), R.drawable.ic_whatsapp));
        droppyBuilder.addMenuItem(new DroppyMenuItem(getResources().getString(R.string.mail), R.drawable.ic_close_envelope));
        droppyBuilder.addMenuItem(new DroppyMenuItem(getResources().getString(R.string.fb), R.drawable.ic_fb));
        droppyBuilder.addMenuItem(new DroppyMenuItem(getResources().getString(R.string.twitter), R.drawable.ic_twitter));

// Set Callback handler
        droppyBuilder.setOnClick(new DroppyClickCallbackInterface() {
            @Override
            public void call(View v, int id) {

                switch (id){

                    case 0 :
                        share("com.whatsapp" , R.string.whatsapp_error);
                        break;

                    case 1 :
                      //  share("com.facebook.katana" , R.string.fb_error);
                        break;

                    case 2 :
                          share("com.facebook.katana" , R.string.fb_error);
                        break;

                    case 3 :
                        share("com.twitter.android" , R.string.twittererror);
                }

            }
        });

        droppyBuilder.build();
    }


    private void initCommentsRecyclerView(){

        CommentsrecyclerView = findViewById(R.id.comments_recycler);

        commentList = new ArrayList<>();
        repliesList = new ArrayList<>();
        mAdapter = new SubjectsCommentsAdapter(SubjectDetailsActivity.this, commentList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(SubjectDetailsActivity.this);
        CommentsrecyclerView.setLayoutManager(mLayoutManager);
        CommentsrecyclerView.setAdapter(mAdapter);
        CommentsrecyclerView.setNestedScrollingEnabled(false);

    }


    private void setTextsAndImages(){

        adviced.setText(adviced_text);
        started_date.setText(started_date_text);
        end_date.setText(end_date_text);
        location.setText(location_text);
        location_name.setText(location_name_text);
        writer.setText(writer_text);
        country.setText(country_text);
        city.setText(city_text);
        subject_details.loadData(subject_details_text, "text/html", "UTF-8");
        title.setText(title_text);
        img_title.setText(imag_title_text);

        if (!image_text.equals("")){
            Glide.with(this).load(Constants.ImageURl + image_text).into(iamge);
            img_proccess.setVisibility(View.VISIBLE);
            image.setVisibility(View.VISIBLE);
        }


        if (!voice_txt.equals("")){
            JcAudio jcAudio = JcAudio.createFromURL(Constants.ImageURl + voice_txt);
            jcPlayerView.addAudio(jcAudio);
            jcPlayerView.setVisibility(View.VISIBLE);
            download_voice.setVisibility(View.VISIBLE);
        }

        if (!video_txt.equals("")){
            String uriPath = Constants.ImageURl + video_txt; //update server url
            videoView.setVideoURI(Uri.parse(uriPath));
            final MediaController mediacontroller = new MediaController(this);
            mediacontroller.setAnchorView(videoView);
            videoView.setMediaController(mediacontroller);
            videoView.requestFocus();

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                        @Override
                        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                            videoView.setMediaController(mediacontroller);
                            mediacontroller.setAnchorView(videoView);

                            progressBar.setVisibility(View.GONE);

                        }
                    });
                }
            });

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                }
            });


            videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Log.d("API123", "What " + what + " extra " + extra);
                    return false;
                }
            });

            videoView.seekTo(100);
            video_frame.setVisibility(View.VISIBLE);
            video_process.setVisibility(View.VISIBLE);

            image.setVisibility(View.GONE);
            img_proccess.setVisibility(View.GONE);
        }

        if (!adviced_text.equals("")) advisor_row.setVisibility(View.VISIBLE);

        if (!started_date_text.equals("")) strted_date_row.setVisibility(View.VISIBLE);

        if (!end_date_text.equals("")) end_date_row.setVisibility(View.VISIBLE);

        if (!location_name_text.equals("")) location_name_row.setVisibility(View.VISIBLE);

        if (!location_text.equals("")) location_row.setVisibility(View.VISIBLE);

        if (!country_text.equals("")) country_row.setVisibility(View.VISIBLE);

        if (!city_text.equals("")) city_row.setVisibility(View.VISIBLE);

        if (!writer_text.equals("")) writer_row.setVisibility(View.VISIBLE);
    }


    private void initRatesLikesAndDislikes(){

        if (Float.parseFloat(rate) > 0){
            ft = true;
        }
        ratingBar.setRating(Float.parseFloat(rate));
        setRatingBarListner();

        if (checkLikeAndDisslike(slikes)) like.setImageDrawable(getResources().getDrawable(R.drawable.like));
        else like.setImageDrawable(getResources().getDrawable(R.drawable._like));

        if (checkLikeAndDisslike(sdislike)) dislike.setImageDrawable(getResources().getDrawable(R.drawable.dislike));
        else dislike.setImageDrawable(getResources().getDrawable(R.drawable._dislike));
    }


    private boolean checkLikeAndDisslike(String like_dislike){

        boolean check = false;
        if (like_dislike.equals("1")) check = true;

        return check;
    }


    private void share(String _package , int error){

        String sub_det = String.valueOf((Html.fromHtml(Html.fromHtml(subject_details_text).toString())));
        if (sub_det.length() > 50){
            sub_det = String.valueOf(sub_det.subSequence(0 , 50));
        }

        String share_txt = getResources().getString(R.string.sub_title) + "\n" + "\n" + title_text + "\n" + "\n"
                + "-----------------------------------" + "\n" + "\n" +
                getResources().getString(R.string.sub_text) + "\n" + "\n" + sub_det+
                "...." + "\n" + "\n"
                + "-----------------------------------" + "\n" + "\n" +
                getResources().getString(R.string.sub_link) + "\n" + Constants.SUBJECT_URL + getIntent().getStringExtra("id");

        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage(_package);
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, share_txt);
        try {
            startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(SubjectDetailsActivity.this, getResources().getString(error), Toast.LENGTH_SHORT).show();
        }
    }


    private void shareImg(final String img_text){

        final String dirPath = Environment.getExternalStorageDirectory() + "/mariat/images" ;

        if (Build.VERSION.SDK_INT >= 23 && !checkPermission())
        {
            requestPermission();
        }

        else {

            AndroidNetworking.initialize(getApplicationContext());

            File mFolder = new File(dirPath);
            if (!mFolder.exists()) {
                mFolder.mkdirs();

            }

            final ProgressDialog progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
            progressDialog.setTitle(getResources().getString(R.string.downloading));
            progressDialog.show();

            AndroidNetworking.download(Constants.ImageURl + img_text, dirPath, img_text)
                    .setTag("downloadTest")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .setDownloadProgressListener(new DownloadProgressListener() {
                        @Override
                        public void onProgress(long bytesDownloaded, long totalBytes) {

                            double progress = (100.0 * bytesDownloaded / totalBytes);
                            progressDialog.setMessage(((int) progress) + "%...");
                            progressDialog.setCanceledOnTouchOutside(false);

                        }
                    })
                    .startDownload(new DownloadListener() {
                        @Override
                        public void onDownloadComplete() {

                            progressDialog.dismiss();
                            Uri uri =  Uri.parse(dirPath + "/" + img_text);
                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("image/*");
                            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                            startActivity(Intent.createChooser(shareIntent, "Share image"));
                        }

                        @Override
                        public void onError(ANError error) {

                            progressDialog.dismiss();
                            Toast.makeText(SubjectDetailsActivity.this, getResources().getString(R.string.error_downloading), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }


    private void shareVideo(final String videotext){

        final String dirPath = Environment.getExternalStorageDirectory() + "/mariat/videos" ;

        if (Build.VERSION.SDK_INT >= 23 && !checkPermission())
        {
            requestPermission();
        }

        else {

            AndroidNetworking.initialize(getApplicationContext());

            File mFolder = new File(dirPath);
            if (!mFolder.exists()) {
                mFolder.mkdirs();

            }

            final ProgressDialog progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
            progressDialog.setTitle(getResources().getString(R.string.downloading));
            progressDialog.show();

            AndroidNetworking.download(Constants.ImageURl + videotext, dirPath, videotext)
                    .setTag("downloadTest")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .setDownloadProgressListener(new DownloadProgressListener() {
                        @Override
                        public void onProgress(long bytesDownloaded, long totalBytes) {

                            double progress = (100.0 * bytesDownloaded / totalBytes);
                            progressDialog.setMessage(((int) progress) + "%...");
                            progressDialog.setCanceledOnTouchOutside(false);

                        }
                    })
                    .startDownload(new DownloadListener() {
                        @Override
                        public void onDownloadComplete() {

                            progressDialog.dismiss();
                            Uri uri =  Uri.parse(dirPath + "/" + videotext);
                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("video/*");
                            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                            startActivity(Intent.createChooser(shareIntent, "Share video"));
                        }

                        @Override
                        public void onError(ANError error) {

                            progressDialog.dismiss();
                            Toast.makeText(SubjectDetailsActivity.this, getResources().getString(R.string.error_downloading), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }


    private void downloadMedia(String url , String dirPath , String fileName){

        if (Build.VERSION.SDK_INT >= 23 && !checkPermission())
        {
            requestPermission();
        }

        else {

            AndroidNetworking.initialize(getApplicationContext());

            File mFolder = new File(dirPath);
            if (!mFolder.exists()) {
                mFolder.mkdirs();

            }

            final ProgressDialog progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
            progressDialog.setTitle(getResources().getString(R.string.downloading));
            progressDialog.show();

            AndroidNetworking.download(url, dirPath, fileName)
                    .setTag("downloadTest")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .setDownloadProgressListener(new DownloadProgressListener() {
                        @Override
                        public void onProgress(long bytesDownloaded, long totalBytes) {

                            double progress = (100.0 * bytesDownloaded / totalBytes);
                            progressDialog.setMessage(((int) progress) + "%...");
                            progressDialog.setCanceledOnTouchOutside(false);

                        }
                    })
                    .startDownload(new DownloadListener() {
                        @Override
                        public void onDownloadComplete() {

                            progressDialog.dismiss();
                            Toast.makeText(SubjectDetailsActivity.this, getResources().getString(R.string.download_successfully), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(ANError error) {

                            progressDialog.dismiss();
                            Toast.makeText(SubjectDetailsActivity.this, getResources().getString(R.string.error_downloading), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    public void fillData(ArrayList<HashMap<String, String>> listHM) {
        for (HashMap<String , String> map : listHM){
            adviced_text =  map.get("Sponsor");
            started_date_text =  map.get("Startdate");
            end_date_text =  map.get("Enddate");
            location_text =  map.get("Location");
            location_name_text =  map.get("LocationName");
            writer_text =  map.get("Author");
            country_text =  map.get("Country");
            city_text =  map.get("City");
            subject_details_text = map.get("Fulltext");
            title_text =  map.get("Title");
            image_text =  map.get("picture");
            voice_txt =  map.get("Voice");
            video_txt =  map.get("Video");
            imag_title_text =  map.get("picturetext");
            rate = map.get("rat");
            rates_count = map.get("crat");
            avg_rates = map.get("trat");
            slikes = map.get("clikes");
            sdislike = map.get("cUnlikes");

            setTextsAndImages();
            initPopUpMenu();
            initRatesLikesAndDislikes();

            subjectDetailsPresenter.requestComments(getIntent().getStringExtra("id"));
        }
    }

    @Override
    public void fillComments(ArrayList<HashMap<String, String>> listHM) {

        Comment comment;
        repliesList = new ArrayList<>();
        for (HashMap<String , String> map : listHM){
            comment = new Comment();
            comment.setAppearName(map.get("AppearName"));
            comment.setComments(map.get("Comments"));
            comment.setCount(map.get("Count"));
            comment.setEmail(map.get("Email"));
            comment.setID(map.get("ID"));
            comment.setID_Comments(map.get("ID_Comments"));
            comment.setID_Customers(map.get("ID_Customers"));
            comment.setID_Subjects(map.get("ID_Subjects"));
            comment.setID_USER(map.get("ID_USER"));
            comment.setLastInteraction(map.get("LastInteraction"));
            comment.setPicture(map.get("picture"));
            comment.setSignupDate(map.get("SignupDate"));
            comment.setStatus(map.get("Status"));

            if (comment.getID_Comments().equals("0")) {
                commentList.add(comment);
            }
            else {
                repliesList.add(comment);
            }
            mAdapter.notifyDataSetChanged();
        }
    }


    public Comment getCommentDetails(String id_customer , String id_comments , String appear_name , String email , String pic ,
                                      String comment_txt , String id_subject , String id_user){

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

        Comment comment = new Comment();
        comment.setID_Customers(id_customer);
        comment.setID_Comments(id_comments);
        comment.setAppearName(appear_name);
        comment.setEmail(email);
        comment.setPicture(pic);
        comment.setSignupDate(df.format(c));
        comment.setComments(comment_txt);
        comment.setID_Subjects(id_subject);
        comment.setID_USER(id_user);

        return comment;
    }


    @Override
    public void addComment(Comment comment) {

        new SubjectDetailsPresenter(this).requestAddComments(comment);
    }

    @Override
    public void uploadImage(byte[] img, Comment comment) {
        new SubjectDetailsPresenter(this).requestuploadImage(img , comment);
    }


    @Override
    public void loadSubjectDetails() {

        showProgress();

        subjectDetailsPresenter.requestSubjectDetails(sub_id , uid);

    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }


    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

    @Override
    public void onFinished(String message) {

        switch (message){

            case "succ_add_comment" :
                Toast.makeText(this, getResources().getString(R.string.cooment_added_succ), Toast.LENGTH_SHORT).show();
                comment_ed_txt.setText("");
                break;

            case "succ_add_rating" :
                Toast.makeText(this, getResources().getString(R.string.succ_add_rating), Toast.LENGTH_SHORT).show();
        }

        //mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(int error) {

        Toast.makeText(this, getResources().getString(error), Toast.LENGTH_SHORT).show();
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
    protected void onPause() {
        super.onPause();
        jcPlayerView.pause();
    }


    public class SubjectsCommentsAdapter extends RecyclerView.Adapter<SubjectsCommentsAdapter.MyViewHolder>{
        private Context context;
        private List<Comment> commentList;
        private String id;
        private SubjectsRepliesAdapter rAdapter;


        public class MyViewHolder extends RecyclerView.ViewHolder {
            Context context;
            private TextView name, comment_text, date;
            private CircleImageView image;
            private RecyclerView recyclerView;
            private EditText write_comment;
            private Button send;
            private LinearLayout replies_layout;

            public MyViewHolder(View view) {
                super(view);
                name = view.findViewById(R.id.name);
                date = view.findViewById(R.id.date);
                comment_text = view.findViewById(R.id.comment_text);
                date = view.findViewById(R.id.date);
                recyclerView = view.findViewById(R.id.replies);
                image = view.findViewById(R.id.image);
                write_comment = view.findViewById(R.id.commentEditText);
                send = view.findViewById(R.id.sendButton);
                replies_layout = view.findViewById(R.id.repies_layout);
                context = itemView.getContext();

            }
        }

        public SubjectsCommentsAdapter(Context context, List<Comment> commentList) {
            this.context = context;
            this.commentList = commentList;

        }

        @Override
        public SubjectsCommentsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.comment_item, parent, false);

            return new SubjectsCommentsAdapter.MyViewHolder(itemView);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onBindViewHolder(final SubjectsCommentsAdapter.MyViewHolder holder, final int position) {

            final Comment comment = commentList.get(position);

            holder.name.setText(comment.getAppearName());
            holder.comment_text.setText(comment.getComments());

            SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date dateObj = curFormater.parse(comment.getSignupDate());
                holder.date.setText(dateObj.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Glide.with(context).load(Constants.ImageURl + comment.getPicture()).into(holder.image);

            holder.write_comment.setHint(getResources().getString(R.string.enter_reply));

            holder.replies_layout.setVisibility(View.GONE);

            for (Comment c : repliesList) {
                if (c.getID_Comments().equals(comment.getID())) {
                    holder.replies_layout.setVisibility(View.VISIBLE);
                    break;
                }
            }

            Log.d("signupdatw", comment.getSignupDate());

            rAdapter = new SubjectsRepliesAdapter(SubjectDetailsActivity.this, repliesList, comment.getID());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(SubjectDetailsActivity.this);
            holder.recyclerView.setLayoutManager(mLayoutManager);
            holder.recyclerView.setAdapter(rAdapter);
            holder.recyclerView.setNestedScrollingEnabled(false);


            holder.write_comment.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.toString().equals(""))
                        holder.send.setEnabled(false);
                    else
                        holder.send.setEnabled(true);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


            holder.send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (new TinyDB(context).getString("username").equals("")){

                        FragmentManager fm = getSupportFragmentManager();
                        VisitorDataFragment visitorDataFragment = VisitorDataFragment.newInstance(comment.getID() ,
                                holder.write_comment.getText().toString() ,  getIntent().getStringExtra("id"));
                        visitorDataFragment.show(fm, "fragment_new_activity");
                    }
                    else {

                        showProgress();
                        Comment comment1 = getCommentDetails(databaseHelper.getUser().get(0).getID(),
                                comment.getID() , databaseHelper.getUser().get(0).getAppearName() ,
                                        databaseHelper.getUser().get(0).getEmail() , databaseHelper.getUser().get(0).getPicture() ,
                                        holder.write_comment.getText().toString() , comment.getID_Subjects() , "0");

                        addComment(comment1);
                    }



                }
            });

        }

        @Override
        public int getItemCount() {
            return commentList.size();
        }


    }


    public class SubjectsRepliesAdapter extends RecyclerView.Adapter<SubjectsRepliesAdapter.MyViewHolder> {
        private Context context;
        private List<Comment> commentList;
        private String id;




        public class MyViewHolder extends RecyclerView.ViewHolder {
            Context context;
            private TextView name , comment_text , date ;
            private CircleImageView image ;
            private RecyclerView recyclerView;
            private LinearLayout.LayoutParams params;
            private LinearLayout rootView , replies_layout , comment_con;
            private EditText write_comment;
            private Button send;


            public MyViewHolder(View view) {
                super(view);
                name = view.findViewById(R.id.name);
                date = view.findViewById(R.id.date);
                comment_text = view.findViewById(R.id.comment_text);
                recyclerView = view.findViewById(R.id.replies);
                image = view.findViewById(R.id.image);
                params = new LinearLayout.LayoutParams(0, 0);
                rootView = view.findViewById(R.id.comment_layout);
                replies_layout = view.findViewById(R.id.repies_layout);
                comment_con = view.findViewById(R.id.newCommentContainer);
                write_comment = view.findViewById(R.id.commentEditText);
                send = view.findViewById(R.id.sendButton);
                context = itemView.getContext();

            }
        }

        public SubjectsRepliesAdapter(Context context, List<Comment> commentList , String id) {
            this.context = context;
            this.commentList = commentList;
            this.id = id;

        }

        @Override
        public SubjectsRepliesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.comment_item, parent, false);

            return new SubjectsRepliesAdapter.MyViewHolder(itemView);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onBindViewHolder(final SubjectsRepliesAdapter.MyViewHolder holder, final int position) {

            final Comment comment = commentList.get(position);

            if (comment.getID_Comments().equals(id)) {
                holder.name.setText(comment.getAppearName());
                holder.comment_text.setText(comment.getComments());

                SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date dateObj = curFormater.parse(comment.getSignupDate());
                    holder.date.setText(dateObj.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Glide.with(context).load(Constants.ImageURl + comment.getPicture()).into(holder.image);

                holder.replies_layout.setVisibility(View.GONE);
                holder.comment_con.setVisibility(View.GONE);

            }

            else {
                holder.rootView.setLayoutParams(holder.params);
            }

        }

        @Override
        public int getItemCount() {
            return commentList.size();
        }


    }
}
