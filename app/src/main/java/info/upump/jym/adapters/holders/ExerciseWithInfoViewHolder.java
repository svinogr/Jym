package info.upump.jym.adapters.holders;

import android.app.Activity;
import android.app.ActivityOptions;
import android.os.Build;
import android.util.Pair;
import android.view.View;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import info.upump.jym.bd.SetDao;
import info.upump.jym.entity.Sets;
import info.upump.jym.fragments.cycle.CRUD;

/*
*  holder for exercise in my programs  template exercise activity
*/

public class ExerciseWithInfoViewHolder extends AbstractExerciseViewHolder {
    private CRUD crud;
    public ExerciseWithInfoViewHolder(View itemView, CRUD crud) {
        super(itemView);
        this.crud = crud;

    }

    @Override
    public void setInfo() {
        SetDao setDao = SetDao.getInstance(context, null);
        List<Sets> setsList = setDao.getByParentId(exercise.getId());
        if (setsList.size() > 1) {
            Collections.sort(setsList, new Comparator<Sets>() {
                @Override
                public int compare(Sets o1, Sets o2) {
                    return o1.getReps() < o2.getReps() ? -1 : o1.getReps() == o2.getReps() ? 0 : 1;
                }
            });
            if(setsList.get(0).getReps() == setsList.get(setsList.size() - 1).getReps()) {
                type.setText(setsList.size() + " x " + setsList.get(0).getReps());
            } else
                type.setText(setsList.size() + " x " + setsList.get(0).getReps() + " - " + setsList.get(setsList.size() - 1).getReps());
        } else if(setsList.size()==1){
            type.setText(1 + " x " + setsList.get(0).getReps());
        } else type.setText("0");
    }

    @Override
    void startActivity() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View sharedViewIm = image;
            String transitionNameIm = "exercise_activity_create_image";
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity)
                            getAnimationContext(),
                    Pair.create(sharedViewIm, transitionNameIm));
            crud.createIntentForResult(transitionActivityOptions, exercise);
        } else  crud.createIntentForResult(null, exercise);
    }
}
