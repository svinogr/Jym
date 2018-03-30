package info.upump.jym.adapters.holders;

import android.content.Intent;
import android.view.View;

import info.upump.jym.activity.workout.WorkoutDetailActivity;

/**
 * Created by explo on 27.03.2018.
 */

public class WorkoutDayViewHolder extends WorkoutViewHolder {
    public WorkoutDayViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    void setVariablyField() {
        variably.setText(workout.getDay().getName());
    }

    @Override
    public void onClick(View v) {
        Intent intent = WorkoutDetailActivity.createIntent(context,workout);
        context.startActivity(intent);
    }
}
