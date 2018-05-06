package info.upump.jym.fragments.exercises;


import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import info.upump.jym.R;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.activity.exercise.ExerciseCreateActivity;
import info.upump.jym.activity.exercise.ExerciseDetailTemplateActivity;
import info.upump.jym.adapters.ExerciseAdapter;
import info.upump.jym.bd.ExerciseDao;
import info.upump.jym.entity.Exercise;
import info.upump.jym.entity.ExerciseDescription;
import info.upump.jym.entity.TypeMuscle;
import info.upump.jym.fragments.cycle.CRUD;
import info.upump.jym.loaders.ASTExercise;

import static info.upump.jym.activity.constant.Constants.CREATE;
import static info.upump.jym.activity.constant.Constants.DEFAULT_IMAGE;
import static info.upump.jym.activity.constant.Constants.DEFAULT_TYPE_ITEM;
import static info.upump.jym.activity.constant.Constants.DELETE;
import static info.upump.jym.activity.constant.Constants.DESCRIPTION;
import static info.upump.jym.activity.constant.Constants.ERROR;
import static info.upump.jym.activity.constant.Constants.ID;
import static info.upump.jym.activity.constant.Constants.IMAGE;
import static info.upump.jym.activity.constant.Constants.REQUEST_CODE_CHANGE_OPEN;
import static info.upump.jym.activity.constant.Constants.REQUEST_CODE_CREATE;
import static info.upump.jym.activity.constant.Constants.TEMPLATE_TYPE_ITEM;
import static info.upump.jym.activity.constant.Constants.TITLE;
import static info.upump.jym.activity.constant.Constants.TYPE_MUSCLE;
import static info.upump.jym.activity.constant.Constants.UPDATE;
import static info.upump.jym.activity.constant.Constants.UPDATE_DELETE;

public class ExerciseListFragmentForViewPager extends Fragment implements TabChanger, CRUD<Exercise>, View.OnClickListener {
    private TypeMuscle typeMuscle;
    private List<Exercise> exerciseList = new ArrayList<>();
    private ExerciseAdapter exerciseAdapter;
    private RecyclerView recyclerView;
    private ASTExercise astExercise;
    private FloatingActionButton addFab;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == DELETE) {
                Toast.makeText(getContext(), R.string.toast_exercise_delete, Toast.LENGTH_SHORT).show();
            }

            if (msg.what == UPDATE) {
                Exercise exercise = (Exercise) msg.obj;
                update(exercise);

            }

          /*  if (msg.what == ERROR) {
                long id = (long) msg.obj;
                Toast.makeText(getApplicationContext(), R.string.toast_dont_delete, Toast.LENGTH_SHORT).show();
                iItemFragment.insertDeletedItem(id);
            }*/
        /*    if(msg.what == CLEAR){
                iItemFragment.clear();
            }*/

            if (msg.what == CREATE) {
                Exercise exercise = (Exercise) msg.obj;
                if(exercise.getTypeMuscle() == typeMuscle){
                    addItem(exercise);
                } else   Toast.makeText(getContext(), R.string.toast_exercise_saved, Toast.LENGTH_SHORT).show();

            }
        }
    };

    private void addItem(Exercise exercise) {
        exerciseList.add(exercise);
        exerciseAdapter.notifyItemInserted(exerciseList.size()-1);
        recyclerView.smoothScrollToPosition(exerciseList.size()-1);
    }

    private void insertDeletedItem(Exercise exercise) {

    }




    public ExerciseListFragmentForViewPager() {
    }

    public static ExerciseListFragmentForViewPager newInstance(TypeMuscle typeMuscle) {
        ExerciseListFragmentForViewPager fragment = new ExerciseListFragmentForViewPager();
        Bundle args = new Bundle();
        args.putString(TYPE_MUSCLE, typeMuscle.toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String t = getArguments().getString(TYPE_MUSCLE);
            typeMuscle = TypeMuscle.valueOf(t);
        }
        createAsyncTask();

        exerciseAdapter = new ExerciseAdapter(exerciseList, ExerciseAdapter.DEFAULT_TYPE, this);
    }

    private void createAsyncTask() {
        astExercise = new ASTExercise(getContext());
        astExercise.execute(Constants.LOADER_BY_TEMPLATE_TYPE, 0, typeMuscle.ordinal());
        try {
            exerciseList = astExercise.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.exercise_list_fragment_for_view_pager, container, false);
        recyclerView = inflate.findViewById(R.id.exercise_list_fragment_for_view_pager_recycler_view);
        addFab = inflate.findViewById(R.id.exercise_fragment_add_fab);
        addFab.setOnClickListener(this);
        System.out.println("onCreateView "+ typeMuscle);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(exerciseAdapter);
        return inflate;
    }

    @Override
    public void onAttach(Context context) {
        System.out.println("resu atacj "+ typeMuscle);
        super.onAttach(context);
        if (getArguments() != null) {
            String t = getArguments().getString(TYPE_MUSCLE);
            typeMuscle = TypeMuscle.valueOf(t);
        }
    }


    @Override
    public void setToFinalPositionRecyclerView() {
//TODO сделать когдаить
    }

    @Override
    public void createIntentForResult(ActivityOptions activityOptions, Exercise object) {
        Intent intent = ExerciseDetailTemplateActivity.createIntent(getContext(), object);
        if (activityOptions != null) {
            startActivityForResult(intent, REQUEST_CODE_CHANGE_OPEN, activityOptions.toBundle());
        } else startActivityForResult(intent, REQUEST_CODE_CHANGE_OPEN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            switch (requestCode) {
                case REQUEST_CODE_CREATE:
                    addNewItem(data);
                    break;
                case REQUEST_CODE_CHANGE_OPEN:
                    int changeOrDelete = data.getIntExtra(UPDATE_DELETE, -1);
                    long id = data.getLongExtra(ID, 0);
                    switch (changeOrDelete) {
                        case UPDATE:
                            updateInnerItem(id);
                            System.out.println("up");
                            break;
                        case DELETE:
                            deleteInnerItem(id);
                            System.out.println("del");
                            break;
                    }
                    break;
            }
        }
    }


    private void updateInnerItem(final long id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ExerciseDao exerciseDao = new ExerciseDao(getActivity());
                Exercise exercise = exerciseDao.getById(id);
                if (exercise != null) {
                    handler.sendMessageDelayed(handler.obtainMessage(UPDATE, exercise), 0);
                }
            }
        });
        thread.start();
    }

    private void deleteInnerItem(final long id) {
        int index = -1;
        for (Exercise exerUpdate : exerciseList) {
            if (exerUpdate.getId() == id) {
                index = exerciseList.indexOf(exerUpdate);
                break;
            }
        }
        if (index != -1) {
            exerciseList.remove(index);
            exerciseAdapter.notifyItemRemoved(index);
        }


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ExerciseDao exerciseDao = new ExerciseDao(getActivity());
                Exercise exercise = new Exercise();
                exercise.setId(id);
                if (exerciseDao.delete(exercise)) {
                    handler.sendMessageDelayed(handler.obtainMessage(DELETE, exercise), 0);
                }else handler.sendMessageDelayed(handler.obtainMessage(ERROR, exercise), 0);
            }
        });
        thread.start();


    }

    private void update(Exercise exercise) {
        int index = -1;
        long id = exercise.getId();
        for (Exercise exerUpdate : exerciseList) {
            if (exerUpdate.getId() == id) {
                index = exerciseList.indexOf(exerUpdate);
                break;
            }
        }
        if (index != -1) {
            exerciseList.set(index, exercise);
            exerciseAdapter.notifyItemChanged(index);
            exerciseAdapter.notifyItemRangeChanged(index, exerciseList.size());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exercise_fragment_add_fab:
                /*для залочкм типа мышцы*/
                Exercise exercise = new Exercise();
                exercise.setTypeMuscle(typeMuscle);
                /**/
                Intent intent = ExerciseCreateActivity.createIntent(getContext(),exercise);
                startActivityForResult(intent, REQUEST_CODE_CREATE);
        }
    }

    private void addNewItem(Intent data) {
        ExerciseDescription exerciseDescription = new ExerciseDescription();
        final Exercise exercise = new Exercise();
        exercise.setExerciseDescription(exerciseDescription);

        exerciseDescription.setTitle(data.getStringExtra(TITLE));
        exerciseDescription.setDefaultImg(data.getStringExtra(DEFAULT_IMAGE));
        exerciseDescription.setImg(data.getStringExtra(IMAGE));

        exercise.setComment(data.getStringExtra(DESCRIPTION));
        TypeMuscle typeMuscle = TypeMuscle.valueOf(data.getStringExtra(TYPE_MUSCLE));
        exercise.setTypeMuscle(typeMuscle);
        exercise.setDefaultType(data.getBooleanExtra(DEFAULT_TYPE_ITEM, false));
        exercise.setTemplate(data.getBooleanExtra(TEMPLATE_TYPE_ITEM, true));
        exercise.setStartDate(new Date());
        exercise.setFinishDate(new Date());

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ExerciseDao exerciseDao = new ExerciseDao(getContext());
                long id = exerciseDao.create(exercise);
                exercise.setId(id);
                if (id != -1) {
                    handler.sendMessageDelayed(handler.obtainMessage(CREATE, exercise), 0);
                }
            }
        });
        thread.start();
    }
}
