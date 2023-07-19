//package info.upump.jym.fragments.workout;
//
//
//import android.app.ActivityOptions;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ExecutionException;
//
//import info.upump.jym.ITitleble;
//import info.upump.jym.R;
//import info.upump.jym.activity.workout.WorkoutCreateActivity;
//import info.upump.jym.activity.workout.WorkoutDetailActivity;
//import info.upump.jym.adapters.WorkoutAdapter;
//import info.upump.jym.bd.WorkoutDao;
//import info.upump.jym.entity.Day;
//import info.upump.jym.entity.Workout;
//import info.upump.jym.fragments.cycle.CRUD;
//import info.upump.jym.loaders.ASTWorkout;
//
//import static info.upump.jym.activity.constant.Constants.CREATE;
//import static info.upump.jym.activity.constant.Constants.DAY;
//import static info.upump.jym.activity.constant.Constants.DEFAULT_TYPE_ITEM;
//import static info.upump.jym.activity.constant.Constants.DELETE;
//import static info.upump.jym.activity.constant.Constants.DESCRIPTION;
//import static info.upump.jym.activity.constant.Constants.ERROR;
//import static info.upump.jym.activity.constant.Constants.FINISH_DATA;
//import static info.upump.jym.activity.constant.Constants.ID;
//import static info.upump.jym.activity.constant.Constants.LOADER_BY_USER_TYPE;
//import static info.upump.jym.activity.constant.Constants.PARENT_ID;
//import static info.upump.jym.activity.constant.Constants.REQUEST_CODE_CHANGE_OPEN;
//import static info.upump.jym.activity.constant.Constants.REQUEST_CODE_CREATE;
//import static info.upump.jym.activity.constant.Constants.START_DATA;
//import static info.upump.jym.activity.constant.Constants.TEMPLATE_TYPE_ITEM;
//import static info.upump.jym.activity.constant.Constants.TITLE;
//import static info.upump.jym.activity.constant.Constants.UPDATE;
//import static info.upump.jym.activity.constant.Constants.UPDATE_DELETE;
//import static info.upump.jym.activity.constant.Constants.WEEK_EVEN;
//
//public class WorkoutFragment extends Fragment implements View.OnClickListener, CRUD<Workout> {
//    protected ITitleble iTitleble;
//    protected RecyclerView recyclerView;
//    protected WorkoutAdapter workoutAdapter;
//    protected List<Workout> workoutList = new ArrayList<>();
//    protected FloatingActionButton addFab;
//    protected ASTWorkout astWorkout;
//    private int index = -1;
//    private final Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            if (msg.what == DELETE) {
//                Toast.makeText(getContext(), R.string.toast_cycle_delete, Toast.LENGTH_SHORT).show();
//            }
//
//            if (msg.what == UPDATE) {
//                Workout workout = (Workout) msg.obj;
//                long id = workout.getId();
//                int index = -1;
//                for (Workout delWorkout : workoutList) {
//                    if (delWorkout.getId() == id) {
//                        index = workoutList.indexOf(delWorkout);
//                        break;
//                    }
//                }
//                if (index != -1) {
//                    workoutList.set(index, workout);
//                    recyclerView.smoothScrollToPosition(index);
//                    workoutAdapter.notifyItemChanged(index);
//                }
//            }
//
//            if (msg.what == ERROR) {
//                long id = (long) msg.obj;
//                Toast.makeText(getContext(), R.string.toast_dont_delete, Toast.LENGTH_SHORT).show();
//                insertDeletedItem(id);
//            }
//
//
//            if (msg.what == CREATE) {
//                Workout workout = (Workout) msg.obj;
//                workoutList.add(workout);
//                int position = workoutList.size() - 1;
//                workoutAdapter.notifyItemInserted(position);
//                recyclerView.smoothScrollToPosition(position);
//            }
//        }
//    };
//
//    private void insertDeletedItem(long id) {
//        WorkoutDao workoutDao = WorkoutDao.getInstance(getContext(), null);
//        Workout workout = workoutDao.getById(id);
//        workoutList.add(index, workout);
//        workoutAdapter.notifyItemInserted(index);
//    }
//
//    public WorkoutFragment() {
//    }
//
//    public static WorkoutFragment newInstance() {
//        WorkoutFragment fragment = new WorkoutFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//        }
//        createAsyncTask();
//        createAdapter();
//    }
//
//    protected void createAsyncTask() {
//        astWorkout = new ASTWorkout(getContext());
//        astWorkout.execute(LOADER_BY_USER_TYPE);
//        try {
//            workoutList = astWorkout.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        setTitle();
//        View inflate = inflater.inflate(R.layout.fragment_workout, container, false);
//
//        recyclerView = inflate.findViewById(R.id.workout_fragment_recycler_view);
//        addFab = inflate.findViewById(R.id.workout_fragment_fab_add);
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(workoutAdapter);
//        setFab();
//
//        return inflate;
//    }
//
//    protected void setTitle() {
//        iTitleble.setTitle(getResources().getString(R.string.workout_fragment_title));
//    }
//
//
//    protected void createAdapter() {
//        workoutAdapter = new WorkoutAdapter(workoutList, LOADER_BY_USER_TYPE, this);
//    }
//
//    protected void setFab() {
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                if (dy > 0) {
//                    // Scroll Down
//                    if (addFab.isShown()) {
//                        addFab.hide();
//                    }
//                } else if (dy <= 0) {
//                    // Scroll Up
//                    if (!addFab.isShown()) {
//                        addFab.show();
//                    }
//                }
//            }
//        });
//        addFab.setOnClickListener(this);
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        iTitleble = (ITitleble) context;
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.workout_fragment_fab_add:
//                createNewItem();
//                break;
//        }
//    }
//
//    private void createNewItem() {
//        Workout workout = new Workout();
//        workout.setId(-1);
//        Intent intent = WorkoutCreateActivity.createIntent(getContext(), workout);
//        startActivityForResult(intent, REQUEST_CODE_CREATE);
//    }
//
//    @Override
//    public void createIntentForResult(ActivityOptions activityOptions, Workout workout) {
//        Intent intent = WorkoutDetailActivity.createIntent(getContext(), workout);
//        if (activityOptions != null) {
//            startActivityForResult(intent, REQUEST_CODE_CHANGE_OPEN, activityOptions.toBundle());
//        } else startActivityForResult(intent, REQUEST_CODE_CHANGE_OPEN);
//
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == -1) {
//            if (requestCode == REQUEST_CODE_CHANGE_OPEN) {
//                long id = data.getLongExtra(ID, 0);
//                int changeOrDelete = data.getIntExtra(UPDATE_DELETE, -1);
//                switch (changeOrDelete) {
//                    case UPDATE:
//                        updateItem(id);
//                        break;
//                    case DELETE:
//                        deleteItem(id);
//                        break;
//                }
//
//            }
//            if (requestCode == REQUEST_CODE_CREATE) {
//                addNewItem(data);
//            }
//        }
//    }
//
//    private void addNewItem(Intent data) {
//        final Workout workout = new Workout();
//        workout.setTitle(data.getStringExtra(TITLE));
//        workout.setComment(data.getStringExtra(DESCRIPTION));
//        workout.setWeekEven(data.getBooleanExtra(WEEK_EVEN, false));
//        workout.setDefaultType(data.getBooleanExtra(DEFAULT_TYPE_ITEM, false));
//        workout.setTemplate(data.getBooleanExtra(TEMPLATE_TYPE_ITEM, false));
//        workout.setDay(Day.valueOf(data.getStringExtra(DAY)));
//        workout.setStartDate(data.getStringExtra(START_DATA));
//        workout.setFinishDate(data.getStringExtra(FINISH_DATA));
//        workout.setParentId(data.getLongExtra(PARENT_ID, 0));
//
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                WorkoutDao workoutDao = WorkoutDao.getInstance(getContext(), null);
//                long id = workoutDao.create(workout);
//                if (id != -1) {
//                    workout.setId(id);
//                    handler.sendMessageDelayed(handler.obtainMessage(CREATE, workout), 0);
//                }
//            }
//        });
//        thread.start();
//    }
//
//    private void updateItem(final long id) {
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                WorkoutDao workoutDao = WorkoutDao.getInstance(getContext(), null);
//                Workout workout = workoutDao.getById(id);
//                if (workout != null) {
//                    handler.sendMessageDelayed(handler.obtainMessage(UPDATE, workout), 0);
//                }
//            }
//        });
//        thread.start();
//
//    }
//
//    private void deleteItem(final long id) {
//        for (Workout delWorkout : workoutList) {
//            if (delWorkout.getId() == id) {
//                index = workoutList.indexOf(delWorkout);
//                break;
//            }
//        }
//
//        if (index != -1) {
//            workoutList.remove(index);
//            workoutAdapter.notifyItemRemoved(index);
//            workoutAdapter.notifyItemRangeChanged(index, workoutList.size());
//        }
//
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                WorkoutDao workoutDao = WorkoutDao.getInstance(getContext(), null);
//                Workout workout = new Workout();
//                workout.setId(id);
//                if (workoutDao.delete(workout)) {
//                    handler.sendMessageDelayed(handler.obtainMessage(DELETE, id), 0);
//                } else handler.sendMessageDelayed(handler.obtainMessage(ERROR, id), 0);
//            }
//        });
//        thread.start();
//    }
//}
