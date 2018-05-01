package info.upump.jym.adapters.holders;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.util.Pair;
import android.view.View;

import info.upump.jym.activity.workout.WorkoutDetailActivity;
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
        System.out.println("WorkoutDayDefaultViewHolder");
        Intent intent = WorkoutDetailDefaultActivity.createIntent(context,workout);
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
