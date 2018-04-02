package info.upump.jym.activity.exercise;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import info.upump.jym.R;
import info.upump.jym.activity.IItemFragment;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.activity.sets.SetActivityCreate;
import info.upump.jym.adapters.SetsAdapter;
import info.upump.jym.bd.ExerciseDao;
import info.upump.jym.bd.SetDao;
import info.upump.jym.entity.Exercise;
import info.upump.jym.entity.Sets;
import info.upump.jym.loaders.SetsLoader;

import static info.upump.jym.activity.constant.Constants.ID;
import static info.upump.jym.activity.constant.Constants.LOADER_BY_PARENT_ID;

public class ExerciseDetail extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<List<Sets>>, IItemFragment<Sets> {
    private Exercise exercise;
    private List<Sets> setsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FloatingActionButton addFab;
    private SetsAdapter setsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Подходы");

        exercise = getItemFromIntent();
        getSupportLoaderManager().initLoader(0, null, this);

        setsAdapter = new SetsAdapter(setsList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.exercise_activity_detail_recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        addFab = findViewById(R.id.exercise_activity_detail_fab_add);

        addFab.setOnClickListener(this);
        recyclerView.setAdapter(setsAdapter);

    }

    public static Intent createIntent(Context context, Exercise exercise) {
        Intent intent = new Intent(context, ExerciseDetail.class);
        intent.putExtra(ID, exercise.getId());
        return intent;
    }

    private Exercise getItemFromIntent() {
        Intent intent = getIntent();
        long id = intent.getLongExtra(ID, 0);
        ExerciseDao exerciseDao = new ExerciseDao(this);
        return exerciseDao.getById(id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exercise_activity_detail_fab_add:
                Intent intent = SetActivityCreate.createIntent(this, new Sets());
                startActivityForResult(intent, Constants.REQUEST_CODE_CREATE);
        }

    }


    @Override
    public Loader<List<Sets>> onCreateLoader(int id, Bundle args) {
        return new SetsLoader(this, LOADER_BY_PARENT_ID, exercise.getId());
    }

    @Override
    public void onLoadFinished(Loader<List<Sets>> loader, List<Sets> data) {
        setsList.clear();
        setsList.addAll(data);
        setsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Sets>> loader) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUEST_CODE_CREATE:
                    System.out.println("interfaceForItem ");
                    long id = data.getLongExtra(ID, 0);
                    addItem(id);
                    break;
            }
        }
    }

    @Override
    public void addChosenItem(long idItem) {

    }

    @Override
    public boolean clear() {
        return false;
    }

    @Override
    public void addItem(long longExtra) {
        if (longExtra >0){
            SetDao setDao = new SetDao(this);
            Sets sets = setDao.getById(longExtra);
            sets.setParentId(exercise.getId());
            setDao.update(sets);
            setsList.add(sets);
            System.out.println(sets);
            setsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        exit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            exit();
        }
        return super.onOptionsItemSelected(item);
    }

    private void exit() {

                    finishActivityWithAnimation();


    }
    private void finishActivityWithAnimation() {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else finish();
    }
}
