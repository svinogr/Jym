package info.upump.jym;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import info.upump.jym.activity.exercise.ExerciseDetailActivity;
import info.upump.jym.bd.DBHelper;
import info.upump.jym.fragments.ExerciseFragment;
import info.upump.jym.temp.InflaiterDB;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ITitlable, IControlFragment {
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private Fragment curFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);


        //fab = findViewById(R.id.main_fab);
       /* fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        DBHelper dbHelper = DBHelper.getHelper(this);
        dbHelper.getWritableDatabase();

        System.out.println(dbHelper.getDatabaseName());

       if(savedInstanceState == null){
            ExerciseFragment exerciseFragment = ExerciseFragment.newInstance();
            createFragment(exerciseFragment);
        }
        // TypeMuscle typeMuscle = TypeMuscle.valueOf("BICEPS");
        // System.out.println(getResources().getString(typeMuscle.getName())+" "+ typeMuscle.getImg());
     /*  TypeMuscle[] values = TypeMuscle.values();
        for (TypeMuscle t:values){
            System.out.println(t.toString());
        }*/
      /*  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        System.out.println(date);
        System.out.println(simpleDateFormat.format(date));
        Date date1 = null;
        try {
            date1 = simpleDateFormat.parse(simpleDateFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date1);*/
/*
         InflaiterDB inflaiterDB = new InflaiterDB(getApplicationContext());
        inflaiterDB.insertInBasicExercise();
*/


    }


    @Override
    public void createFragment(Fragment fragment) {
        curFragment = fragment;
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment);
        fragmentTransaction.commit();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setTitle(String title) {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
    }

 /*   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("in ac1");
        System.out.println(resultCode);
        System.out.println(requestCode);
      if(resultCode == RESULT_OK) {
          System.out.println("in ac1.5");
          if (requestCode == ExerciseDetailActivity.REQUEST_OPEN_OR_CHANGE) {
              System.out.println("in ac2");

              ((ExerciseFragment) curFragment).deliteExercise(data.getLongExtra(ExerciseDetailActivity.ID_EXERCISE, 0));
          }
      }

    }*/
}
