package info.upump.jym.activity.exercise;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import info.upump.jym.R;
import info.upump.jym.activity.IItemFragment;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.activity.sets.SetActivityCreate;
import info.upump.jym.adapters.SetsAdapter;
import info.upump.jym.bd.ExerciseDao;
import info.upump.jym.bd.SetDao;
import info.upump.jym.entity.Exercise;
import info.upump.jym.entity.Sets;
import info.upump.jym.fragments.cycle.CRUD;
import info.upump.jym.loaders.ASTSets;

import static info.upump.jym.activity.constant.Constants.CLEAR;
import static info.upump.jym.activity.constant.Constants.CREATE;
import static info.upump.jym.activity.constant.Constants.DELETE;
import static info.upump.jym.activity.constant.Constants.ERROR;
import static info.upump.jym.activity.constant.Constants.ID;
import static info.upump.jym.activity.constant.Constants.PAST_WEIGHT;
import static info.upump.jym.activity.constant.Constants.QUANTITY;
import static info.upump.jym.activity.constant.Constants.REPS;
import static info.upump.jym.activity.constant.Constants.REQUEST_CODE_CHANGE_OPEN;
import static info.upump.jym.activity.constant.Constants.REQUEST_CODE_CREATE;
import static info.upump.jym.activity.constant.Constants.UPDATE;
import static info.upump.jym.activity.constant.Constants.UPDATE_DELETE;
import static info.upump.jym.activity.constant.Constants.USER_TYPE;
import static info.upump.jym.activity.constant.Constants.WEIGHT;

public class ExerciseDetail extends AppCompatActivity implements View.OnClickListener, IItemFragment<Sets>, CRUD<Sets> {
    protected Exercise exercise;
    protected List<Sets> setsList = new ArrayList<>();
    protected RecyclerView recyclerView;
    protected FloatingActionButton addFab;
    protected SetsAdapter setsAdapter;
    private ASTSets astSets;
    private boolean update;
    private int index = -1;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == DELETE) {
                Toast.makeText(getApplicationContext(), R.string.toast_set_delete, Toast.LENGTH_SHORT).show();
            }

            if (msg.what == UPDATE) {
                Sets sets = (Sets) msg.obj;
                update(sets);
            }

            if (msg.what == ERROR) {
                long id = ((Sets) msg.obj).getId();
                Toast.makeText(getApplicationContext(), R.string.toast_dont_delete, Toast.LENGTH_SHORT).show();
                insertDeletedItem(id);
            }

            if (msg.what == CLEAR) {
                setsList.clear();
                setsAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), R.string.toast_exercise_delete_sets, Toast.LENGTH_SHORT).show();

            }

            if (msg.what == CREATE) {
                addItems((List<Sets>) msg.obj);
            }
        }
    };

    private void addItems(List<Sets> obj) {
        setsList.addAll(obj);
        setsAdapter.notifyDataSetChanged();
        recyclerView.smoothScrollToPosition(setsList.size() - 1);
        Toast.makeText(getApplicationContext(), R.string.toast_set_saved, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail_v2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.exercise_title_sets);
        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean(UPDATE_DELETE) != false) {
                update = true;
            }
        }

        exercise = getItemFromIntent();
        System.out.println(exercise.getId());
        createAsyncTask();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.exercise_activity_detail_recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        addFab = findViewById(R.id.exercise_activity_detail_fab_add);
        setAdapter();
        setFabVisible(true);
        addFab.setOnClickListener(this);
        setFab();
    }

    private void createAsyncTask() {
        astSets = new ASTSets(this);
        astSets.execute(Constants.LOADER_BY_PARENT_ID, (int) exercise.getId());
        try {
            setsList = astSets.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    protected void setAdapter() {
        setsAdapter = new SetsAdapter(setsList, USER_TYPE, this);
        recyclerView.setAdapter(setsAdapter);
    }

    protected void setFabVisible(boolean visible) {
        if (visible) {
            addFab.setVisibility(View.VISIBLE);
        } else addFab.setVisibility(View.GONE);
    }

    public static Intent createIntent(Context context, Exercise exercise) {
        Intent intent = new Intent(context, ExerciseDetail.class);
        intent.putExtra(ID, exercise.getId());
        return intent;
    }

    private Exercise getItemFromIntent() {
        Intent intent = getIntent();
        long id = intent.getLongExtra(ID, 0);
        ExerciseDao exerciseDao = ExerciseDao.getInstance(this, null);
        return exerciseDao.getById(id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exercise_activity_detail_fab_add:
                Intent intent = SetActivityCreate.createIntent(this, new Sets());
                startActivityForResult(intent, REQUEST_CODE_CREATE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            long id = data.getLongExtra(ID, 0);
            switch (requestCode) {
                case REQUEST_CODE_CREATE:
                    update = true;
                    addNewItem(data);
                    break;
                case REQUEST_CODE_CHANGE_OPEN:
                    int changeOrDelete = data.getIntExtra(UPDATE_DELETE, -1);
                    switch (changeOrDelete) {
                        case UPDATE:
                            updateInnerItem(data);
                            break;
                        case DELETE:
                            deleteInnerItem(id);
                            break;
                    }
            }
        }
    }

    private void deleteInnerItem(final long id) {
        update = true;
        for (Sets setsDel : setsList) {
            if (setsDel.getId() == id) {
                index = setsList.indexOf(setsDel);
            }
        }
        if (index != -1) {
            setsList.remove(index);
            setsAdapter.notifyItemRemoved(index);
            setsAdapter.notifyItemRangeChanged(index, setsList.size());
        }

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                SetDao setDao = SetDao.getInstance(getApplicationContext(), null);
                Sets sets = new Sets();
                sets.setId(id);
                boolean id = setDao.delete(sets);
                if (id) {
                    handler.sendMessageDelayed(handler.obtainMessage(DELETE, sets), 0);
                } else handler.sendMessageDelayed(handler.obtainMessage(ERROR, sets), 0);
            }
        });
        thread.start();
    }

    private void updateInnerItem(Intent data) {
        update = true;
        final Sets sets = new Sets();
        sets.setId(data.getLongExtra(ID, 0));
        sets.setWeight(data.getDoubleExtra(WEIGHT, 0));
        sets.setReps(data.getIntExtra(REPS, 0));
        sets.setWeightPast(data.getDoubleExtra(PAST_WEIGHT, 0));
        sets.setStartDate(new Date());
        sets.setFinishDate(new Date());
        sets.setParentId(exercise.getId());

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                SetDao setDao = SetDao.getInstance(getApplicationContext(), null);
                boolean id = setDao.update(sets);
                if (id) {
                    handler.sendMessageDelayed(handler.obtainMessage(UPDATE, sets), 0);
                }
            }
        });
        thread.start();
    }

    private void addNewItem(final Intent data) {
        update = true;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int q = data.getIntExtra(QUANTITY, 1);
                Sets sets = null;
                List<Sets> setsList = new ArrayList<>();
                SetDao setDao = SetDao.getInstance(getApplicationContext(), null);
                for (int i = 0; i < q; i++) {
                    sets = new Sets();
                    sets.setStartDate(new Date());
                    sets.setFinishDate(new Date());
                    sets.setWeight(data.getDoubleExtra(WEIGHT, 0));
                    sets.setWeightPast(data.getDoubleExtra(PAST_WEIGHT, 0));
                    sets.setReps(data.getIntExtra(REPS, 0));
                    sets.setParentId(exercise.getId());
                    long l = setDao.create(sets);
                    sets.setId(l);
                    setsList.add(sets);
                }
                if (setsList.size() != 0) {
                    handler.sendMessageDelayed(handler.obtainMessage(CREATE, setsList), 0);
                }
            }
        });
        thread.start();

    }

    @Override
    public void clear() {
        update = true;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ExerciseDao exerciseDao = ExerciseDao.getInstance(getApplicationContext(), null);
                boolean clear = exerciseDao.clear(exercise.getId());
                if (clear) {
                    handler.sendMessageDelayed(handler.obtainMessage(CLEAR), 0);
                }
            }
        });
        thread.start();
    }

    @Override
    public void addItem(Sets sets) {
    }


    @Override
    public void onBackPressed() {
        exit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            exit();
        }
        switch (item.getItemId()) {
            case R.id.edit_menu_delete:
                Snackbar.make(getCurrentFocus(), R.string.snack_delete, Snackbar.LENGTH_LONG)
                        .setAction(R.string.yes, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                delete(exercise.getId());

                            }
                        }).show();
                break;
            case R.id.edit_menu_clear:
                Snackbar.make(getCurrentFocus(), R.string.snack_exersice_delete_sets, Snackbar.LENGTH_LONG)
                        .setAction(R.string.yes, new View.OnClickListener() {
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
        if (update) {
            Intent intent = new Intent();
            intent.putExtra(UPDATE_DELETE, UPDATE);
            intent.putExtra(ID, exercise.getId());
            setResult(RESULT_OK, intent);
        }
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
        update = false;
        Intent intent = new Intent();
        intent.putExtra(UPDATE_DELETE, DELETE);
        intent.putExtra(ID, id);
        setResult(RESULT_OK, intent);
        exit();
    }

    @Override
    public void insertDeletedItem(long id) {
        SetDao setDao = SetDao.getInstance(this, null);
        Sets sets = setDao.getById(id);
        setsList.add(index, sets);
        setsAdapter.notifyItemInserted(index);
    }

    @Override
    public void update(Sets object) {
        int index = -1;
        long id = object.getId();
        for (Sets sUpdate : setsList) {
            if (sUpdate.getId() == id) {
                index = setsList.indexOf(sUpdate);
                break;
            }
        }
        if (index != -1) {
            setsList.set(index, object);
            setsAdapter.notifyItemChanged(index);
            recyclerView.smoothScrollToPosition(index);
            Toast.makeText(this, R.string.toast_set_update, Toast.LENGTH_SHORT).show();
        }
    }

    protected void setFab() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    // Scroll Down
                    if (addFab.isShown()) {
                        addFab.hide();
                    }
                } else if (dy <= 0) {
                    // Scroll Up
                    if (!addFab.isShown()) {
                        addFab.show();
                    }
                }
            }
        });

        addFab.setOnClickListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(UPDATE_DELETE, update);
    }


    @Override
    public void createIntentForResult(ActivityOptions activityOptions, Sets sets) {
        Intent intent = SetActivityCreate.createIntent(this, sets);
        if (activityOptions != null) {
            startActivityForResult(intent, REQUEST_CODE_CHANGE_OPEN, activityOptions.toBundle());
        } else startActivityForResult(intent, REQUEST_CODE_CHANGE_OPEN);

    }
}
