package info.upump.jym.adapters.holders;

import android.content.Intent;
import android.view.View;

import info.upump.jym.R;
import info.upump.jym.activity.exercise.ExerciseDetailTemplateActivity;


public class ExerciseTemplateViewHolder extends ExerciseViewHolder {
    public ExerciseTemplateViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public Intent createIntent() {
        Intent intent = ExerciseDetailTemplateActivity.createIntent(context, exercise);
        return intent;
    }

    @Override
    public void setInfo() {
        if (!exercise.isDefaultType()) {
            type.setText(context.getResources().getString(R.string.card_type_item_exercise));
        } else
            type.setText(context.getResources().getString(R.string.card_type_item_default_exercise));
    }

    @Override
    protected void showExercise() {
    }
}
