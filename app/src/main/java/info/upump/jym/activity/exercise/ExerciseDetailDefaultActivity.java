package info.upump.jym.activity.exercise;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import info.upump.jym.R;
import info.upump.jym.entity.Exercise;

import static info.upump.jym.activity.constant.Constants.ID;

/**
 * Created by explo on 06.04.2018.
 */

public class ExerciseDetailDefaultActivity  extends ExerciseDetail{
    @Override
    protected void setFabVisible(boolean visible) {
        addFab.setVisibility(View.GONE);
    }

    public static Intent createIntent(Context context, Exercise exercise) {
        Intent intent = new Intent(context, ExerciseDetailDefaultActivity.class);
        intent.putExtra(ID, exercise.getId());
        return intent;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void exit() {
        finishActivityWithAnimation();
    }

}