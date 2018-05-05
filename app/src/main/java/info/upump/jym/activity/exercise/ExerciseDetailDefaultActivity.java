package info.upump.jym.activity.exercise;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

import info.upump.jym.adapters.SetsAdapter;
import info.upump.jym.entity.Exercise;

import static info.upump.jym.activity.constant.Constants.DEFAULT_TYPE;
import static info.upump.jym.activity.constant.Constants.ID;


public class ExerciseDetailDefaultActivity  extends ExerciseDetail{
    @Override
    protected void setFabVisible(boolean visible) {
        addFab.setVisibility(View.GONE);
    }

    @Override
    protected void setFab() {
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

    @Override
    protected void setAdapter(){
        setsAdapter = new SetsAdapter(setsList, DEFAULT_TYPE, null);
        recyclerView.setAdapter(setsAdapter);
    }
}
