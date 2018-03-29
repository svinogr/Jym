package info.upump.jym.adapters.holders;

import android.view.View;

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

    }
}
