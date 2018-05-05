package info.upump.jym.adapters.holders;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.util.Pair;
import android.view.View;

import info.upump.jym.R;
import info.upump.jym.activity.exercise.ExerciseDetailTemplateActivity;
/*
*  holder for all template exercise activity
*/

public class ExerciseTemplateViewHolder extends AbstractExerciseViewHolder {
    public ExerciseTemplateViewHolder(View itemView) {
        super(itemView);
    }

 /*   @Override
    public Intent createIntent() {
        Intent intent = ExerciseDetailTemplateActivity.createIntent(context, exercise);
        return intent;
    }
*/
    @Override
    public void setInfo() {
        if (!exercise.isDefaultType()) {
            type.setText(context.getResources().getString(R.string.card_type_item_exercise));
        } else
            type.setText(context.getResources().getString(R.string.card_type_item_default_exercise));
    }

    @Override
    void startActivity() {
        Intent intent = ExerciseDetailTemplateActivity.createIntent(context, exercise);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View sharedViewIm = image;
            String transitionNameIm = "exercise_activity_create_image";
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity)
                            getAnimationContext(),
                    Pair.create(sharedViewIm, transitionNameIm));
            context.startActivity(intent, transitionActivityOptions.toBundle());
        } else context.startActivity(intent);
    }


    @Override
    protected void showExercise() {
    }
}
