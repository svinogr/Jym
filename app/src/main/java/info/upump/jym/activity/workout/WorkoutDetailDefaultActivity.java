package info.upump.jym.activity.workout;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.view.Menu;
import android.view.View;

import info.upump.jym.adapters.PagerAdapterWorkout;
import info.upump.jym.adapters.PagerAdapterWorkoutDefault;
import info.upump.jym.entity.Workout;

import static info.upump.jym.activity.constant.Constants.ID;

/**
 * Created by explo on 06.04.2018.
 */

public class WorkoutDetailDefaultActivity extends WorkoutDetailActivity {


    public static Intent createIntent(Context context, Workout workout) {
        System.out.println("WorkoutDetailDefaultActivity");
        Intent intent = new Intent(context, WorkoutDetailDefaultActivity.class);
        intent.putExtra(ID, workout.getId());
        return intent;
    }

    @Override
    protected void setFabVisible()
    {
        addFab.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    protected void setPagerAdapter() {
        pagerAdapterWorkout = new PagerAdapterWorkoutDefault(getSupportFragmentManager(), workout, this);
    }

    @Override
    protected void exit() {
        finishActivityWithAnimation();
    }
}
