package info.upump.jym.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import info.upump.jym.bd.ExerciseDao;
import info.upump.jym.entity.Exercise;
import info.upump.jym.entity.TypeMuscle;

/**
 * Created by explo on 14.03.2018.
 */

public class ExerciseLoader extends AsyncTaskLoader<List<Exercise>> {
    private Context context;
    private TypeMuscle typeMuscle;

    public ExerciseLoader(Context context, TypeMuscle typeMuscle) {
        super(context);
        this.context = context;
        this.typeMuscle = typeMuscle;
    }

    @Override
    public List<Exercise> loadInBackground() {
        ExerciseDao exerciseDao = new ExerciseDao(context);
        List<Exercise> exerciseList = exerciseDao.getAllByTypeMuscle(typeMuscle);
        System.out.println(exerciseList.size());
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
