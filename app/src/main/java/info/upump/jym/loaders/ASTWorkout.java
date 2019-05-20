package info.upump.jym.loaders;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import info.upump.jym.bd.WorkoutDao;
import info.upump.jym.entity.Workout;

import static info.upump.jym.activity.constant.Constants.LOADER_BY_DEFAULT_TYPE;
import static info.upump.jym.activity.constant.Constants.LOADER_BY_PARENT_ID;
import static info.upump.jym.activity.constant.Constants.LOADER_BY_TEMPLATE_TYPE;
import static info.upump.jym.activity.constant.Constants.LOADER_BY_USER_TYPE;

/**
 * Created by explo on 04.05.2018.
 */

public class ASTWorkout extends AsyncTask<Integer, Void, List<Workout>> {
    private Context context;

    public ASTWorkout(Context context) {
        this.context = context;
    }

    @Override
    protected List<Workout> doInBackground(Integer... integers) {
        int type = integers[0];
        long parentId= 0;
        if(integers.length>1){
            parentId = integers[1];
        }

        WorkoutDao workoutDao = WorkoutDao.getInstance(context, null);
        List<Workout> workoutList = null;
        switch (type) {
            case LOADER_BY_PARENT_ID:
                workoutList = workoutDao.getByParentId(parentId);
                break;
            case LOADER_BY_DEFAULT_TYPE:
                workoutList = workoutDao.getDefault();
                break;
            case LOADER_BY_USER_TYPE:
                workoutList = workoutDao.getTemplateUser();
                break;
            case LOADER_BY_TEMPLATE_TYPE:
                workoutList = workoutDao.getAllTemplate();
        }
        return workoutList;
    }

    }