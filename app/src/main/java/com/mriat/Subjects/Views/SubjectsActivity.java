package com.mriat.Subjects.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mriat.BaseActivity;
import com.mriat.Constants;
import com.mriat.DatabaseHelper;
import com.mriat.ModelClasses.Subject;
import com.mriat.R;
import com.mriat.Subjects.Contract;
import com.mriat.Subjects.Presenters.SubjectsPresenter;
import com.mriat.TinyDB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SubjectsActivity extends BaseActivity implements Contract.SubjectsActivityContract.View {

    private RecyclerView recyclerView;
    private List<Subject> itemsList;
    private   SubjectsActivityAdapter mAdapter;
    private Toolbar toolbar;
    private String uid;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler);

        initUI();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));

        if (!new TinyDB(this).getString("username").equals(""))
            uid = new DatabaseHelper(this).getUser().get(0).getID();
        else uid = "0";

        initRecyclerView();

        showProgress();

        new SubjectsPresenter(this)
                .requestSubjects(uid , getIntent().getStringExtra("id"));
    }


    private void initUI(){

        toolbar = findViewById(R.id.toolbar);
    }



    private void initRecyclerView(){

        recyclerView = findViewById(R.id.recyclerview);

        itemsList = new ArrayList<>();
        mAdapter = new SubjectsActivityAdapter(SubjectsActivity.this, itemsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(SubjectsActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

    }


    @Override
    public void fillData(ArrayList<HashMap<String, String>> listHM) {

        Subject subject;
        for (HashMap<String , String> map : listHM){
            subject = new Subject();
            subject.setAuthor(map.get("Author"));
            subject.setDateEdite(map.get("publishdate"));
            subject.setID(map.get("ID"));
            subject.setTitle(map.get("Title"));
            subject.setSummary(map.get("Summary"));
            subject.setReviews(map.get("Reviews"));
            subject.setViews(map.get("Views"));
            subject.setPicture(map.get("picture"));

            itemsList.add(subject);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFailure(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        showProgressDialog();
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }



    public class SubjectsActivityAdapter extends RecyclerView.Adapter<SubjectsActivityAdapter.MyViewHolder> {
        private Context context;
        private List<Subject> subjectList;



        public class MyViewHolder extends RecyclerView.ViewHolder {
            Context context;
            private TextView title , writer , summary , date , views , comments;
            private ImageView image ;


            public MyViewHolder(View view) {
                super(view);
                title = view.findViewById(R.id.title);
                writer = view.findViewById(R.id.writer);
                summary = view.findViewById(R.id.summary);
                date = view.findViewById(R.id.date);
                views = view.findViewById(R.id.view_number);
                comments = view.findViewById(R.id.comments_number);
                image = view.findViewById(R.id.image);
                context = itemView.getContext();


            }
        }

        public SubjectsActivityAdapter(Context context, List<Subject> subjectList) {
            this.context = context;
            this.subjectList = subjectList;
        }

        @Override
        public SubjectsActivityAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.subject_item, parent, false);

            return new SubjectsActivityAdapter.MyViewHolder(itemView);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onBindViewHolder(final SubjectsActivityAdapter.MyViewHolder holder, final int position) {

            final Subject subject = subjectList.get(position);

            holder.title.setText(subject.getTitle());

            SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
            try {
                holder.date.setText(curFormater.format(Date.parse(subject.getDateEdite())));
            }
            catch (Exception e){

            }
            holder.summary.setText(subject.getSummary());
            holder.writer.setText(subject.getAuthor());
            holder.comments.setText(subject.getReviews());
            holder.views.setText(subject.getViews());

            Glide.with(context).load(Constants.ImageURl + subject.getPicture()).into(holder.image);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SubjectsActivity.this , SubjectDetailsActivity.class);
                    intent.putExtra("id" , subject.getID());
                    intent.putExtra("title" , subject.getTitle());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return subjectList.size();
        }


    }
}
