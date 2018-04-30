package info.upump.jym.adapters.holders;

import android.content.Intent;
import android.view.View;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import info.upump.jym.activity.exercise.ExerciseDetail;
import info.upump.jym.bd.SetDao;
import info.upump.jym.entity.Sets;

/**
 * Created by explo on 14.03.2018.
 */

public class ExerciseWithInfoViewHolder extends ExerciseViewHolder {
    public ExerciseWithInfoViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public Intent createIntent() {
        Intent intent = ExerciseDetail.createIntent(context, exercise);
        return intent;
    }

/*    @Override
    public void setInfo() {
        SetDao setDao = new SetDao(context);
        List<Sets> setsList = setDao.getByParentId(exercise.getId());
        if (setsList.size() > 1) {
            Collections.sort(setsList, new Comparator<Sets>() {
                @Override
                public int compare(Sets o1, Sets o2) {
                    return o1.getReps() < o2.getReps() ? -1 : o1.getReps() == o2.getReps() ? 0 : 1;
}
            });
                    if(setsList.get(0).getReps() == setsList.get(setsList.size() - 1).getReps()) {
                    setInfo.setText(String.valueOf(setsList.size()+ " x " + setsList.get(0).getReps()));
                    } else setInfo.setText(String.valueOf(setsList.size() + " x " + setsList.get(0).getReps() + " - " + setsList.get(setsList.size() - 1).getReps()));
                    } else if(setsList.size()==1){
                    setInfo.setText(1 + " x " + setsList.get(0).getReps());
                    } else setInfo.setText("0");
                    }*/

    @Override
    public void setInfo() {
        SetDao setDao = new SetDao(context);
        List<Sets> setsList = setDao.getByParentId(exercise.getId());
        if (setsList.size() > 1) {
            Collections.sort(setsList, new Comparator<Sets>() {
                @Override
                public int compare(Sets o1, Sets o2) {
                    return o1.getReps() < o2.getReps() ? -1 : o1.getReps() == o2.getReps() ? 0 : 1;
                }
            });
            if(setsList.get(0).getReps() == setsList.get(setsList.size() - 1).getReps()) {
                type.setText(String.valueOf(setsList.size()+ " x " + setsList.get(0).getReps()));
            } else type.setText(String.valueOf(setsList.size() + " x " + setsList.get(0).getReps() + " - " + setsList.get(setsList.size() - 1).getReps()));
        } else if(setsList.size()==1){
            type.setText(1 + " x " + setsList.get(0).getReps());
        } else type.setText("0");
    }

}
