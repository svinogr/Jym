package info.upump.jym.adapters.holders;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.util.Pair;
import android.view.View;

import info.upump.jym.activity.workout.WorkoutDetailActivity;


public class WorkoutTemplateViewHolder extends WorkoutViewHolder {

    public WorkoutTemplateViewHolder(View itemView) {
        super(itemView);
        System.out.println("WorkoutTemplateViewHolder");
    }

    @Override
    public void setVariablyField() {
    }

    @Override
    public void onClick(View v) {
        Intent intent = WorkoutDetailActivity.createIntent(context,workout);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View sharedViewIm = imageView;
            String transitionNameIm = "workout_card_layout_image";
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity)
                            getAnimationContext(),
                    Pair.create(sharedViewIm, transitionNameIm));
            context.startActivity(intent, transitionActivityOptions.toBundle());
        } else context.startActivity(intent);

    }
}
