package info.upump.jym.adapters.holders;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.util.Pair;
import android.view.View;

import info.upump.jym.R;
import info.upump.jym.activity.workout.WorkoutDetailDefaultActivity;
/*
* holder for activity with default workouts in default cycle
* */
public class WorkoutDayDefaultViewHolder extends WorkoutViewHolder {

    public WorkoutDayDefaultViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    void setVariablyField() {
        if(workout.isWeekEven()){
            week.setText(R.string.week_even);
        } else week.setText("");
        variably.setText(workout.getDay().getName());
    }

    @Override
    public void onClick(View v) {
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
