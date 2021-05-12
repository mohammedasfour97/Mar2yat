package com.mriat.Views;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.BaseSliderView;
import com.glide.slider.library.SliderTypes.TextSliderView;
import com.glide.slider.library.Tricks.ViewPagerEx;
import com.mriat.Auth.Views.SignupActivity;
import com.mriat.BaseActivity;
import com.mriat.Constants;
import com.mriat.Contract;
import com.mriat.DatabaseHelper;
import com.mriat.DrawerListAdapter;
import com.mriat.Fragments.SubMenuFragment;
import com.mriat.Menu.MainMenuPresenter;
import com.mriat.Presenters.HomePresenter;
import com.mriat.R;
import com.mriat.Subjects.Views.AddSubjectActivity;
import com.mriat.Subjects.Views.MySubjectsActivity;
import com.mriat.Subjects.Views.SubjectDetailsActivity;
import com.mriat.TinyDB;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HomeActivity extends BaseActivity implements Contract.HomeActivityContract.View,
        BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener,
        com.mriat.Menu.Contract.MenuContract.View{

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private ArrayList<String> listUrl , listName , listAdvices , ListStartDate , ListEndDtae , ListLocation , listLocationName,
            ListCountries , ListCities , ListWriters , ListFullText, ids_list;
    private SliderLayout getAlbums , getFixed , getNew , getNews , getVarious , getVideos;
    private SearchView searchView;
    private DrawerLayout drawer ;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar ;
    private HomePresenter homePresenter ;
    private static int[] drawer_icons = {R.drawable.ic_menu_camera,
            R.drawable.ic_menu_camera,
            R.drawable.ic_menu_camera};

    private TextView tv_selected_navigation;

    private ArrayList<String> navigation_items;
    private ArrayList<String> ids;
    private List<String> icons;
    private DrawerListAdapter drawerListAdapter;
    private ListView lv_drawer;
    private String uid ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showSnackBar(getResources().getString(R.string.hello) + " " + getIntent().getStringExtra("app_name"));

        listUrl = new ArrayList<>();
        listName = new ArrayList<>();
        listAdvices= new ArrayList<>();
        ListStartDate = new ArrayList<>();
        ListEndDtae = new ArrayList<>();
        ListLocation = new ArrayList<>();
        listLocationName = new ArrayList<>();
        ListCountries = new ArrayList<>();
        ListCities = new ArrayList<>();
        ListWriters = new ArrayList<>();
        ListFullText = new ArrayList<>();
        navigation_items = new ArrayList<>();
        ids_list = new ArrayList<>();
        ids = new ArrayList<>();
        icons = new ArrayList<>();

//adding menu items for naviations

        homePresenter = new HomePresenter(this);

        initUI();
        setListners();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));

        initSeachViewConfig();
        if (!new TinyDB(this).getString("username").equals(""))
            uid = new DatabaseHelper(this).getUser().get(0).getID();
        else uid = "0";


        showProgress();

        requestData();

        new MainMenuPresenter(this).requestMenu();
    }


    private void initUI(){

        getAlbums = findViewById(R.id.getalbums_slider);
        getFixed = findViewById(R.id.GetFixed_slider);
        getNew = findViewById(R.id.GetNew_slider);
        getNews = findViewById(R.id.GetNews_slider);
        getVarious = findViewById(R.id.getvariousz_slider);
        getVideos = findViewById(R.id.GetVideo_slider);
        searchView = findViewById(R.id.searchview);
        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        lv_drawer = (ListView) findViewById(R.id.lv_drawer);
    }


    private void setListners(){

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                return false;
            }
        });


    }


    private void SetDrawer() {

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        drawerListAdapter = new DrawerListAdapter(HomeActivity.this, navigation_items, drawer_icons);
        lv_drawer.setAdapter(drawerListAdapter);

        lv_drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FragmentManager fm = getSupportFragmentManager();
                SubMenuFragment newActivity = SubMenuFragment.newInstance(listName.get(position) , ids.get(position));
                newActivity.show(fm, "fragment_new_activity");

                drawer.closeDrawers();
            }
        });

    }


    private void initSlider(SliderLayout mDemoSlider){

        //    fillImpNewsSlider();

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();
        //.diskCacheStrategy(DiskCacheStrategy.NONE)
        //.placeholder(R.drawable.placeholder)
        //.error(R.drawable.placeholder);

        for (int i = 0; i < listUrl.size(); i++) {
            TextSliderView sliderView = new TextSliderView(this);
            // if you want show image only / without description text use DefaultSliderView instead

            // initialize SliderLayout
            sliderView
                    .image(listUrl.get(i))
                    .description(listName.get(i))
                    .setRequestOption(requestOptions)
                    .setProgressBarVisible(true)
                    .setOnSliderClickListener(this);

            //add your extra information
            sliderView.bundle(new Bundle());
            sliderView.getBundle().putString("id", ids_list.get(i));
            sliderView.getBundle().putString("title", listName.get(i));
            sliderView.getBundle().putString("image", listUrl.get(i));
            sliderView.getBundle().putString("advisor", listAdvices.get(i));
            sliderView.getBundle().putString("startdate", ListStartDate.get(i));
            sliderView.getBundle().putString("enddate", ListEndDtae.get(i));
            sliderView.getBundle().putString("location", ListLocation.get(i));
            sliderView.getBundle().putString("locationname", listLocationName.get(i));
            sliderView.getBundle().putString("country", ListCountries.get(i));
            sliderView.getBundle().putString("cities", ListCities.get(i));
            sliderView.getBundle().putString("writer", ListWriters.get(i));
            sliderView.getBundle().putString("Fulltext", ListFullText.get(i));
            mDemoSlider.addSlider(sliderView);
        }

        // set Slider Transition Animation
        // mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);

        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
        mDemoSlider.stopCyclingWhenTouch(false);

    }


    private void initSeachViewConfig(){

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = findViewById(R.id.searchview);
        searchView.onActionViewExpanded();
        searchView.setIconifiedByDefault(false);
        searchView.clearFocus();
        searchView.setFocusable(false);
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint(getString(R.string.search));
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
        searchView.requestFocus();

        final EditText searchEditText = (EditText) searchView.findViewById(R.id.search_src_text);
        searchEditText.setTextColor(Color.GRAY);
        //    searchEditText.setTypeface(tf);

    }


    private void loadSlider(ArrayList<HashMap<String, String>> listHM , SliderLayout sliderLayout){

        listUrl.clear();
        listName.clear();
        listAdvices.clear();
        listLocationName.clear();
        ListCities.clear();
        ListCountries.clear();
        ListEndDtae.clear();
        ListLocation.clear();
        ListStartDate.clear();
        ListWriters.clear();
        ListFullText.clear();
        ids_list.clear();

        for (int i = 0 ; i < listHM.size() ; i++){
            listUrl.add(Constants.ImageURl + listHM.get(i).get("picture"));
            listName.add(listHM.get(i).get("Title"));
            listAdvices.add(listHM.get(i).get("Sponsor"));
            listLocationName.add(listHM.get(i).get("LocationName"));
            ListCities.add(listHM.get(i).get("City"));
            ListCountries.add(listHM.get(i).get("Country"));
            ListEndDtae.add(listHM.get(i).get("Enddate"));
            ListLocation.add(listHM.get(i).get("Location"));
            ListStartDate.add(listHM.get(i).get("Startdate"));
            ListWriters.add(listHM.get(i).get("Author"));
            ListFullText.add(listHM.get(i).get("Fulltext"));
            ids_list.add(listHM.get(i).get("ID"));
            initSlider(sliderLayout);
        }

    }


    private void requestData (){

        homePresenter.requestAlbums(uid);
        homePresenter.requestFixed(uid);
        homePresenter.requestNew(uid);
        homePresenter.requestNews(uid);
        homePresenter.requestVarious(uid);
        homePresenter.requestVedio(uid);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (!new TinyDB(HomeActivity.this).getString("username").equals(""))
        getMenuInflater().inflate(R.menu.main, menu);
        else
            getMenuInflater().inflate(R.menu.main2, menu);
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
            Intent intent = new Intent(HomeActivity.this , SignupActivity.class);
            intent.putExtra("edit" , "yes");
            startActivity(intent);
        }
        else if (id==R.id.action_my_subjects){
            Intent intent = new Intent(HomeActivity.this , MySubjectsActivity.class);
            intent.putExtra("edit" , "yes");
            startActivity(intent);
        }
        else if (id==R.id.action_add_subject){
            Intent intent = new Intent(HomeActivity.this , AddSubjectActivity.class);
            startActivity(intent);
        }
        else if (id==R.id.action_signout){
            new TinyDB(HomeActivity.this).putString("username" , "");
            startLoginActivity("" , "");
            HomeActivity.this.finish();
        }
        else if (id==R.id.action_login){
            startLoginActivity("" , "");
            HomeActivity.this.finish();
        }
        else if (id==R.id.action_add_subjectt){

            Constants.showSignInMessage(HomeActivity.this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        getVideos.stopAutoCycle();
        getVarious.stopAutoCycle();
        getNew.stopAutoCycle();
        getNews.stopAutoCycle();
        getFixed.stopAutoCycle();
        getAlbums.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

        Intent intent = new Intent(HomeActivity.this , SubjectDetailsActivity.class);
        intent.putExtra("title" ,slider.getBundle().getString("title") );
        intent.putExtra("id" ,slider.getBundle().getString("id") );
        startActivity(intent);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onFinished(ArrayList<HashMap<String,String>> listHM,  String title) {

        switch (title) {

            case "albums" :
                loadSlider(listHM , getAlbums);
                break;

            case "fixed" :
                loadSlider(listHM , getFixed);
                break;

            case "new" :
                loadSlider(listHM , getNew);
                break;

            case "news" :
                loadSlider(listHM , getNews);
                break;

            case "various" :
                loadSlider(listHM , getVarious);
                break;

            case "video" :
                loadSlider(listHM , getVideos);
                break;
        }

    }

    @Override
    public void onFinished(ArrayList<HashMap<String, String>> listHM) {
        for (int i = 0 ; i < listHM.size() ; i++){
            navigation_items.add(listHM.get(i).get("Sections"));
            ids.add(listHM.get(i).get("ID"));
            icons.add(listHM.get(i).get("Icon"));
        }

        SetDrawer();
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

}
