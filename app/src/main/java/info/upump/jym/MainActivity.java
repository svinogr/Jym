package info.upump.jym;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.upump.jym.bd.DBHelper;
import info.upump.jym.fragments.exercises.ExerciseFragment;
import info.upump.jym.fragments.cycle.CycleFragment;
import info.upump.jym.fragments.workout.WorkoutFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ITitleble, IControlFragment {
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private Fragment curFragment;
    private final static String[] arrayPermissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private final static String[] arrayPermissionDescription = {"Хранилище файлов", "Камера"};
    private static Map<String, String> mapPermission = new HashMap<>();
    private static final int PERMISSION_CODE = 1;
    private static final int REQUEST_PERMISSION_IN_SETTINGS = 10;
    private Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        this.savedInstanceState = savedInstanceState;

       // fab = findViewById(R.id.main_fab);
       /* fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/


        // TypeMuscle typeMuscle = TypeMuscle.valueOf("BICEPS");
        // System.out.println(getResources().getString(typeMuscle.getName())+" "+ typeMuscle.getColor());
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
//         InflaiterDB inflaiterDB = new InflaiterDB(getApplicationContext());
//        inflaiterDB.insertInBasicExercise();

        for (int i = 0; i < arrayPermissions.length; i++) {
            mapPermission.put(arrayPermissions[i], arrayPermissionDescription[i]);
        }

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            System.out.println("запрос PERMISSION на версии SDK " + android.os.Build.VERSION.SDK_INT);
            getPermission();

        } else init(savedInstanceState);

    }

    public void getPermission() {
        System.out.println("getPermission");

        List<String> permissions = new ArrayList<>();

        for (int i = 0; i < arrayPermissions.length; i++) {

            if (!hasPermission(arrayPermissions[i])) {
                permissions.add(arrayPermissions[i]);
            }
        }

        if (permissions.size() > 0) {
            showSnackBarWithNeededPermissionS(permissions);


            String[] permissionsToGet = new String[permissions.size()];
            permissions.toArray(permissionsToGet);

            ActivityCompat.requestPermissions(MainActivity.this,
                    permissionsToGet,
                    PERMISSION_CODE);
        } else init(savedInstanceState);


    }

    //    проверяем получено ли уже разрешение
    private boolean hasPermission(String permission) {
        System.out.println("hasPermission  " + permission);
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            System.out.println("hasPermission inside  " + permission);
            return true;
        } else return false;
    }

    private void showSnackBarWithNeededPermissionS(List<String> permissions) {
        StringBuilder stringBuilder = new StringBuilder(getResources().getString(R.string.permission_text_request));
        for (int i = 0; i < permissions.size(); i++) {
            stringBuilder.append(mapPermission.get(permissions.get(i)));
            if (i < permissions.size() - 1) {
                stringBuilder.append(", ");
            } else stringBuilder.append(".");
        }
        Snackbar snackbar = Snackbar.make(findViewById(R.id.drawer_layout), stringBuilder,
                Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        TextView textView = view.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(5);
        snackbar.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE:
                List<String> permissionDeny = new ArrayList<>();
                List<String> permissionDontAsk = new ArrayList<>();
                System.out.println("G " + grantResults.toString());
                System.out.println("P " + permissions.toString());

                if (grantResults.length > 0) {

                    for (int i = 0; i < permissions.length; i++) {

                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                            System.out.println("1" + true);
                            permissionDeny.add(permissions[i]);

                        } else {
                            if (!hasPermission(permissions[i])) {
                                System.out.println("2" + false);
                                permissionDontAsk.add(permissions[i]);
                            }
                        }
                    }

                    if (permissionDeny.size() > 0) {
                        getPermission();
                    } else if (permissionDontAsk.size() > 0) {
                        int size = permissionDontAsk.size();
                        int count = 0;

                        StringBuilder stringBuilder = new StringBuilder(getResources().getString(R.string.permission_text_request));
                        for (String permission : permissionDontAsk) {
                            stringBuilder.append(mapPermission.get(permission));
                            count++;
                            if (count < size) {
                                stringBuilder.append(", ");
                            } else stringBuilder.append(".");

                        }

                        Snackbar snackbar = Snackbar.make(findViewById(R.id.drawer_layout), stringBuilder,
                                Snackbar.LENGTH_INDEFINITE).setAction(R.string.permission_text_options, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                openApplicationSettings();
                            }
                        });

                        View view = snackbar.getView();
                        TextView textView = view.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setMaxLines(5);
                        snackbar.show();

                    } else init(savedInstanceState);
                }

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void init(Bundle savedInstanceState) {
        System.out.println("init");
        System.out.println(savedInstanceState);

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
        if (savedInstanceState == null) {
          //  ExerciseFragment exerciseFragment = ExerciseFragment.newInstance();
        //    CycleFragment exerciseFragment = CycleFragment.newInstance();
            WorkoutFragment exerciseFragment = WorkoutFragment.newInstance();
            createFragment(exerciseFragment);
        }
    }


    @Override
    public void createFragment(Fragment fragment) {
        curFragment = fragment;
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment);
        fragmentTransaction.commitAllowingStateLoss();
        //fragmentTransaction.commit();
    }

    private void openApplicationSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.parse("package:"+ getPackageName());
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_PERMISSION_IN_SETTINGS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if(requestCode == REQUEST_PERMISSION_IN_SETTINGS){
                getPermission();
            }


      //  super.onActivityResult(requestCode, resultCode, data);
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

        if (id == R.id.nav_programs) {
            if(!(curFragment instanceof CycleFragment)){
                curFragment = CycleFragment.newInstance();
            }
            // Handle the camera action
        } else if (id == R.id.nav_exercises) {
            if(!(curFragment instanceof ExerciseFragment)){
                curFragment = ExerciseFragment.newInstance();
            }

        } else if (id == R.id.nav_workouts) {
            if(!(curFragment instanceof WorkoutFragment)){
                curFragment = WorkoutFragment.newInstance();
            }


        } else if (id == R.id.nav_progress) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        createFragment(curFragment);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setTitle(String title) {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
    }


}
