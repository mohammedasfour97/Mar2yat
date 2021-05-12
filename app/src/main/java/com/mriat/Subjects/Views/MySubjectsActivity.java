package com.mriat.Subjects.Views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mriat.Auth.Views.SignupActivity;
import com.mriat.BaseActivity;
import com.mriat.Constants;
import com.mriat.DatabaseHelper;
import com.mriat.ModelClasses.Subject;
import com.mriat.R;
import com.mriat.Subjects.Contract;
import com.mriat.Subjects.Presenters.EditSubjectPresenter;
import com.mriat.Subjects.Presenters.SubjectsPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MySubjectsActivity extends BaseActivity implements Contract.SubjectsActivityContract.View {

    private RecyclerView recyclerView;
    private List<Subject> itemsList;
    private MySubjectsActivity.MySubjectsActivityAdapter mAdapter;
    private Toolbar toolbar;

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
        getSupportActionBar().setTitle(new DatabaseHelper(this).getUser().get(0).getAppearName());

        initRecyclerView();

        new SubjectsPresenter(this)
                .requestMySubjects(new DatabaseHelper(this).getUser().get(0).getID());

    }


    private void initUI(){

        toolbar = findViewById(R.id.toolbar);
    }



    private void initRecyclerView(){

        recyclerView = findViewById(R.id.recyclerview);

        itemsList = new ArrayList<>();
        mAdapter = new MySubjectsActivity.MySubjectsActivityAdapter(MySubjectsActivity.this, itemsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MySubjectsActivity.this);
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

        if (error.equals("error_delete")) {
            Toast.makeText(this, getResources().getString(R.string.error_while_delete_subject), Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, getResources().getString(R.string.error_loading_subject), Toast.LENGTH_SHORT).show();
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



    public class MySubjectsActivityAdapter extends RecyclerView.Adapter<MySubjectsActivity.MySubjectsActivityAdapter.MyViewHolder> implements
            Contract.EditSubjectContract.View {
        private Context context;
        private List<Subject> subjectList;
        private EditSubjectPresenter editSubjectPresenter;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            Context context;
            private TextView title , writer , summary , date , views , comments;
            private ImageView image , edit , delete;


            public MyViewHolder(View view) {
                super(view);
                title = view.findViewById(R.id.title);
                writer = view.findViewById(R.id.writer);
                summary = view.findViewById(R.id.summary);
                date = view.findViewById(R.id.date);
                views = view.findViewById(R.id.view_number);
                comments = view.findViewById(R.id.comments_number);
                image = view.findViewById(R.id.image);
                edit = view.findViewById(R.id.edit);
                delete = view.findViewById(R.id.delete);
                context = itemView.getContext();

            }
        }

        public MySubjectsActivityAdapter(Context context, List<Subject> subjectList) {
            this.context = context;
            this.subjectList = subjectList;
            editSubjectPresenter = new EditSubjectPresenter(MySubjectsActivityAdapter.this);
        }

        @Override
        public MySubjectsActivity.MySubjectsActivityAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.my_subject_item, parent, false);

            return new MySubjectsActivity.MySubjectsActivityAdapter.MyViewHolder(itemView);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onBindViewHolder(final MySubjectsActivity.MySubjectsActivityAdapter.MyViewHolder holder, final int position) {

            final Subject subject = subjectList.get(position);

            holder.title.setText(subject.getTitle());

            SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
            holder.date.setText(curFormater.format(Date.parse(subject.getDateEdite())));
            holder.summary.setText(subject.getSummary());
            holder.writer.setText(subject.getAuthor());
            holder.comments.setText(subject.getReviews());
            holder.views.setText(subject.getViews());

            Glide.with(context).load(Constants.ImageURl + subject.getPicture()).into(holder.image);

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                     final AlertDialog.Builder builder = new AlertDialog.Builder(MySubjectsActivity.this , R.style.MyAlertDialogStyle);
                        builder.setMessage(getResources().getString(R.string.want_to_delete_sub));
                        builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                showProgress();
                                editSubjectPresenter.requestdeleteSubject(Integer.parseInt(subject.getID()) , 0);
                            }
                        });
                        builder.setNegativeButton(R.string.cancel, null);
                        builder.show();

                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MySubjectsActivity.this , SubjectDetailsActivity.class);
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


        @Override
        public void onFinished(String message) {
            Toast.makeText(context, getResources().getString(R.string.successfully_delete_subject), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(String error) {
            Toast.makeText(context, getResources().getString(R.string.error_while_delete_subject), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void showProgress() {
            MySubjectsActivity.this.showProgress();
        }

        @Override
        public void hideProgress() {
            MySubjectsActivity.this.hideProgress();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id==R.id.action_my_account){
            Intent intent = new Intent(MySubjectsActivity.this , SignupActivity.class);
            intent.putExtra("edit" , "yes");
            startActivity(intent);
        }
        else if (id==R.id.action_add_subject){
            Intent intent = new Intent(MySubjectsActivity.this , AddSubjectActivity.class);
            intent.putExtra("edit" , "yes");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}

