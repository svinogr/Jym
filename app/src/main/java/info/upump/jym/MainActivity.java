package info.upump.jym;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import info.upump.jym.activity.SettingsActivity;
import info.upump.jym.bd.DBHelper;
import info.upump.jym.fragments.PravoFragment;
import info.upump.jym.fragments.cycle.CycleFragment;
import info.upump.jym.fragments.cycle.CycleFragmentDefault;
import info.upump.jym.fragments.exercises.ExerciseFragment;
import info.upump.jym.fragments.user.UserFragment;
import info.upump.jym.fragments.workout.WorkoutDefaultFragment;
import info.upump.jym.fragments.workout.WorkoutFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ITitleble, IControlFragment {
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private Fragment curFragment;
    private final String[] arrayPermissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private String[] arrayPermissionDescription;
    private Map<String, String> mapPermission = new HashMap<>();
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
        arrayPermissionDescription = new String[]{getResources().getString(R.string.permission_description), getResources().getString(R.string.permission_description_camera)};

        for (int i = 0; i < arrayPermissions.length; i++) {
            mapPermission.put(arrayPermissions[i], arrayPermissionDescription[i]);
        }

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getPermission();

        } else init(savedInstanceState);
    }

    public void getPermission() {
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

    private boolean hasPermission(String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
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

                if (grantResults.length > 0) {

                    for (int i = 0; i < permissions.length; i++) {

                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                            permissionDeny.add(permissions[i]);

                        } else {
                            if (!hasPermission(permissions[i])) {
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        DBHelper dbHelper = DBHelper.getHelper(this);
        dbHelper.getWritableDatabase();

        int firstFragment = Integer.parseInt(getPrefs());

        if (savedInstanceState == null) {
            createFragment(firstFragment);
        }
    }

    private String getPrefs() {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());
        String listPreference = prefs.getString("screen_choose", "3");
        return listPreference;
    }


    @Override
    public void createFragment(int firstFragment) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        Fragment fragment = null;
        switch (firstFragment) {
            case 0:
                fragment = CycleFragment.newInstance();
                break;
            case 1:
                fragment = WorkoutFragment.newInstance();
                break;
            case 2:
                fragment = UserFragment.newInstance();
                break;
            case 3:
                fragment = CycleFragmentDefault.newInstance();
                break;
            case 4:
                fragment = PravoFragment.newInstance();
                break;
            case 5:
                fragment = ExerciseFragment.newInstance();
                break;
        }
        curFragment = fragment;
        fragmentTransaction.replace(R.id.main_container, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void openApplicationSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.parse("package:" + getPackageName());
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_PERMISSION_IN_SETTINGS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PERMISSION_IN_SETTINGS) {
            getPermission();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Snackbar.make(findViewById(R.id.coordinator), R.string.snack_exit, Snackbar.LENGTH_LONG)
                    .setAction(R.string.yes, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    }).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        int fragment = -1;
        switch (id) {
            case R.id.nav_my_programs:
                fragment = 0;
                break;
            case R.id.nav_my_workouts:
                fragment = 1;
                break;
            case R.id.nav_progress:
                fragment = 2;
                break;
            case R.id.nav_programs:
                fragment = 3;
                break;
            case R.id.nav_pravo:
                fragment = 4;
                break;
            case R.id.nav_my_exercises:
                fragment = 5;
                break;
        }

        if (fragment != -1) {
            createFragment(fragment);
        }
        if (id == R.id.nav_settings) {
            Intent intent = SettingsActivity.createIntent(this);
            startActivity(intent);
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
}
