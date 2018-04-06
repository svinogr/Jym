package info.upump.jym.adapters.holders;

import android.content.Intent;
import android.view.View;

import info.upump.jym.activity.workout.WorkoutDetailDefaultActivity;

/**
 * Created by explo on 06.04.2018.
 */

public class WorkoutDayDefaultViewHolder extends WorkoutViewHolder {

    public WorkoutDayDefaultViewHolder(View itemView) {
        super(itemView);
        System.out.println("WorkoutDayDefaultViewHolder");
    }

    @Override
    void setVariablyField() {
        variably.setText(workout.getDay().getName());
    }

    @Override
    public void onClick(View v) {
        Intent intent = WorkoutDetailDefaultActivity.createIntent(context,workout);
        context.startActivity(intent);
    }
}
