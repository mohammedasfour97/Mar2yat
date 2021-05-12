package com.mriat.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mriat.Constants;
import com.mriat.DatabaseHelper;
import com.mriat.Menu.Contract;
import com.mriat.Menu.SunMenuPresenter;
import com.mriat.ModelClasses.SubMenu;
import com.mriat.R;
import com.mriat.Subjects.Views.SubjectsActivity;
import com.mriat.TinyDB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SubMenuFragment extends DialogFragment implements Contract.SubMenuContract.View {

    private RecyclerView recyclerView;
    public List<SubMenu> itemsList;
    public SubMenuAdapter mAdapter;
    private Toolbar toolbar;
    private String uid ;

    public SubMenuFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }


    public static SubMenuFragment newInstance(String title , String id) {
        SubMenuFragment newActivity = new SubMenuFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("id", id);
        newActivity.setArguments(args);
        return newActivity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initUI(view);

        initRecyclerView(view);

        if (!new TinyDB(getContext()).getString("username").equals(""))
            uid = new DatabaseHelper(getContext()).getUser().get(0).getID();
        else uid = "0";

        new SunMenuPresenter(this)
                .requestMenu(uid , getArguments().getString("id", "1"));


        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);

        toolbar.setTitle(title);


    }



    private void initUI(View view){

        toolbar = view.findViewById(R.id.toolbar);
    }


    private void initRecyclerView(View view){

        recyclerView = view.findViewById(R.id.recyclerview);

        itemsList = new ArrayList<>();
        mAdapter = new SubMenuAdapter(getContext(), itemsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext() );
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

    }

    @Override
    public void onFinished(ArrayList<HashMap<String, String>> listHM) {

        SubMenu subMenu;

        for (HashMap<String , String> map : listHM) {
            subMenu = new SubMenu(map.get("ID") , map.get("Image") , map.get("SubSections") , map.get("Description") , map.get("Subjects") ,
                    map.get("publishdate"));
            itemsList.add(subMenu);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(String error) {

    }


    public class SubMenuAdapter extends RecyclerView.Adapter<SubMenuAdapter.MyViewHolder> {
        private Context context;
        private List<SubMenu> subMenusList;



        public class MyViewHolder extends RecyclerView.ViewHolder {
            Context context;
            private TextView title , desc , content_number , date ;
            private ImageView image ;


            public MyViewHolder(View view) {
                super(view);
                title = view.findViewById(R.id.title);
                desc = view.findViewById(R.id.desc);
                content_number = view.findViewById(R.id.content_number);
                date = view.findViewById(R.id.date);
                image = view.findViewById(R.id.image);
                context = itemView.getContext();


            }
        }

        public SubMenuAdapter(Context context, List<SubMenu> subMenuList) {
            this.context = context;
            this.subMenusList = subMenuList;
        }

        @Override
        public SubMenuAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sub_menu_item, parent, false);

            return new SubMenuAdapter.MyViewHolder(itemView);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onBindViewHolder(final SubMenuAdapter.MyViewHolder holder, final int position) {

            final SubMenu subMenu = subMenusList.get(position);

            holder.title.setText(subMenu.getTitle());

            SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
            try {
                holder.date.setText(curFormater.format(Date.parse(subMenu.getDate())));
            }
            catch (Exception e){

            }

            holder.desc.setText(subMenu.getDesc());
            holder.content_number.setText(subMenu.getContent_number() + " " +  getResources().getString(R.string.contents));

            Glide.with(context).load(Constants.ImageURl + subMenu.getImage()).into(holder.image);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext() , SubjectsActivity.class);
                    intent.putExtra("id" , subMenu.getId());
                    intent.putExtra("title" , subMenu.getTitle());
                    startActivity(intent);
                    dismiss();
                }
            });
        }

        @Override
        public int getItemCount() {
            return subMenusList.size();
        }


    }
}

