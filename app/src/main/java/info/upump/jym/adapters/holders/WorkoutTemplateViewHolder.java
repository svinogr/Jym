package info.upump.jym.adapters.holders;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.util.Pair;
import android.view.View;

import info.upump.jym.R;
import info.upump.jym.activity.workout.WorkoutDetailActivity;

/**
 * Created by explo on 27.03.2018.
 */

public class WorkoutTemplateViewHolder extends WorkoutViewHolder {

    public WorkoutTemplateViewHolder(View itemView) {
        super(itemView);
        System.out.println("WorkoutTemplateViewHolder");
    }

    @Override
    public void setVariablyField() {
        if (!workout.isDefaultType()) {
            variably.setText(context.getResources().getString(R.string.card_type_item));
        } else variably.setText("");
    }

    @Override
    public void onClick(View v) {
        System.out.println("контекст "+context);
        System.out.println("WorkoutTemplateViewHolder");
        Intent intent = WorkoutDetailActivity.createIntent(context,workout);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View sharedViewIm = imageView;
            String transitionNameIm = "workout_card_layout_image";
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity)
                            context,
                    Pair.create(sharedViewIm, transitionNameIm));
            context.startActivity(intent, transitionActivityOptions.toBundle());
        } else context.startActivity(intent);

    }
}
