package info.upump.jym.activity.exercise;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
    protected Exercise exercise;
    protected List<Sets> setsList = new ArrayList<>();
    protected RecyclerView recyclerView;
    protected FloatingActionButton addFab;
    protected SetsAdapter setsAdapter;
 //   private ImageForItem exerciseDescription;

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
        setFabVisible(true);
        addFab.setOnClickListener(this);
        recyclerView.setAdapter(setsAdapter);



    }

    protected void setFabVisible(boolean visible) {
        if (visible) {
            addFab.setVisibility(View.VISIBLE);
        } else  addFab.setVisibility(View.GONE);

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
  //      ImageDao imageDao = new ImageDao(this);
  //      exerciseDescription = imageDao.getById(exercise.getImageId());
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
        ExerciseDao exerciseDao = new ExerciseDao(this);
        boolean clear = exerciseDao.clear(exercise.getId());
        if (clear) {
            setsList.clear();
            setsAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Подходы удалены", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "Не удалось очистить", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    @Override
    public void addItem(long longExtra) {
        if (longExtra > 0) {
        /*    SetDao setDao = new SetDao(this);
            Sets sets = setDao.getById(longExtra);
            sets.setParentId(exercise.getId());*/
            SetDao setDao = new SetDao(this);
            List<Sets> newSets = setDao.getSetsFromId(longExtra);
            for (Sets s : newSets) {
                s.setParentId(exercise.getId());
                setDao.update(s);
            }
            setsList.addAll(newSets);
            System.out.println(newSets);
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
        switch (item.getItemId()) {
            case R.id.edit_menu_delete:
                Snackbar.make(getCurrentFocus(), "Удалить упражнение?", Snackbar.LENGTH_LONG)
                        .setAction("Да", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                delete(exercise.getId());

                            }
                        }).show();
                break;
            case R.id.edit_menu_clear:
                Snackbar.make(getCurrentFocus(), "Очистить упражнение?", Snackbar.LENGTH_LONG)
                        .setAction("Да", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clear();
                            }
                        }).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void exit() {

        finishActivityWithAnimation();


    }

    protected void finishActivityWithAnimation() {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_exercise_menu, menu);
        return true;
    }


    public void delete(long id) {
        ExerciseDao exerciseDao = new ExerciseDao(this);
        if (exerciseDao.delete(exercise)) {
            Toast.makeText(this, "времен, упражнение  удален", Toast.LENGTH_SHORT).show();
            //exit();
            finishActivityWithAnimation();
        } else Toast.makeText(this, "времен, упражнение  не удалено", Toast.LENGTH_SHORT).show();

    }
}
