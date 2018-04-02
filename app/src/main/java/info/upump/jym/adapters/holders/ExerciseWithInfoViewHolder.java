package info.upump.jym.adapters.holders;

import android.content.Intent;
import android.view.View;

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

    @Override
    public void setInfo() {
        SetDao setDao = new SetDao(context);
        List<Sets> setsList = setDao.getByParentId(exercise.getId());


        setInfo.setText(String.valueOf(setsList.size()));
    }
}
