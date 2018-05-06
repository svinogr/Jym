/*
package info.upump.jym.loaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import info.upump.jym.bd.ExerciseDao;
import info.upump.jym.entity.Exercise;
import info.upump.jym.entity.TypeMuscle;

import static info.upump.jym.activity.constant.Constants.LOADER_BY_PARENT_ID;
import static info.upump.jym.activity.constant.Constants.LOADER_BY_TEMPLATE_TYPE;


public class ExerciseFragmentLoader extends AsyncTaskLoader<List<Exercise>> {
    private Context context;
    private TypeMuscle typeMuscle;
    private int operation;
    private long parentId;

    public ExerciseFragmentLoader(@NonNull Context context, int operation, long parentId) {
        super(context);
        this.context = context;
        this.operation = operation;
        this.parentId = parentId;
    }

    public ExerciseFragmentLoader(Context context, int operation, TypeMuscle typeMuscle) {
        super(context);
        this.context = context;
        this.operation = operation;
        this.typeMuscle = typeMuscle;
    }

    @Override
    public List<Exercise> loadInBackground() {
        ExerciseDao exerciseDao = new ExerciseDao(context);
        List<Exercise> exerciseList = null;// TODO только темпейты
        switch (operation){
            case LOADER_BY_PARENT_ID:
                exerciseList =  exerciseDao.getByParentId(parentId);
                break;
            case LOADER_BY_TEMPLATE_TYPE:
                exerciseList = exerciseDao.getAllByTypeMuscleTemplate(typeMuscle);// все по типу мышцы и темплайты
        }

        return exerciseList;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
    }

    @Override
    public void deliverResult(List<Exercise> data) {
        super.deliverResult(data);
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }
}
*/
