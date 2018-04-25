package info.upump.jym.adapters.holders;

import android.content.Intent;
import android.view.View;

import info.upump.jym.activity.exercise.ExerciseDetail;
import info.upump.jym.activity.exercise.ExerciseDetailDefaultActivity;

/**
 * Created by explo on 06.04.2018.
 */

public class ExerciseWithInfoDefaultViewHolder extends ExerciseWithInfoViewHolder {
    public ExerciseWithInfoDefaultViewHolder(View itemView) {
        super(itemView);
    }
    @Override
    public Intent createIntent() {
        System.out.println("ExerciseWithInfoDefaultViewHolder");
        Intent intent = ExerciseDetailDefaultActivity.createIntent(context, exercise);
        return intent;
    }

}
