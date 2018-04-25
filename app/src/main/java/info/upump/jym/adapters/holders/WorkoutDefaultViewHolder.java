package info.upump.jym.adapters.holders;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.util.Pair;
import android.view.View;

import info.upump.jym.activity.workout.WorkoutDetailActivity;
import info.upump.jym.activity.workout.WorkoutDetailDefaultActivity;
import info.upump.jym.entity.Workout;

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
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View sharedViewIm = imageView;
            String transitionNameIm = "workout_card_layout_image";
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity)
                            getAnimationContext(),
                    Pair.create(sharedViewIm, transitionNameIm));
            context.startActivity(intent, transitionActivityOptions.toBundle());
        } else context.startActivity(intent);

    }

    @Override
    public void setVariablyField() {
    }
/* @Override
    public void bind(Workout workout) {
            this.workout = workout;
            System.out.println(workout);
            setPic();
            title.setText(workout.getTitle());
            setVariablyField();
        }
    }*/
}
