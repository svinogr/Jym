package info.upump.jym.adapters.holders;

import android.content.Intent;
import android.view.View;

import info.upump.jym.activity.exercise.ExerciseDetail;
import info.upump.jym.activity.exercise.ExerciseDetailDefaultActivity;

public class ExerciseWithInfoDefaultViewHolder extends ExerciseWithInfoViewHolder {
    public ExerciseWithInfoDefaultViewHolder(View itemView) {
        super(itemView);
    }
    @Override
    public Intent createIntent() {
        Intent intent = ExerciseDetailDefaultActivity.createIntent(context, exercise);
        return intent;
    }
}
