package info.upump.jym.adapters.holders;

import android.content.Intent;
import android.view.View;

import info.upump.jym.activity.workout.WorkoutDetailActivity;
import info.upump.jym.activity.workout.WorkoutDetailDefaultActivity;

/**
 * Created by explo on 06.04.2018.
 */

public class WorkoutDefaultViewHolder extends WorkoutTemplateViewHolder {
    public WorkoutDefaultViewHolder(View itemView) {
        super(itemView);
        System.out.println("WorkoutDefaultViewHolder");

    }

    @Override
    public void onClick(View v) {
        System.out.println("WorkoutDefaultViewHolder");
        Intent intent = WorkoutDetailDefaultActivity.createIntent(context,workout);
        context.startActivity(intent);
    }
}
