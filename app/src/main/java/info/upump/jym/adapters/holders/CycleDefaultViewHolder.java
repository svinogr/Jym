package info.upump.jym.adapters.holders;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.util.Pair;
import android.view.View;

import info.upump.jym.activity.cycle.CycleDetailDefaultActivity;
import info.upump.jym.activity.workout.WorkoutDetailDefaultActivity;
import info.upump.jym.entity.Cycle;
import info.upump.jym.fragments.cycle.CRUD;

/*
* for default cycles
* */
public class CycleDefaultViewHolder extends AbstractCycleViewHolder {

    public CycleDefaultViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    void setVariableViews(){}

    @Override
    void startActivity() {
       Intent intent = CycleDetailDefaultActivity.createIntent(context,cycle);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View sharedViewIm = imageView;
            String transitionNameIm = "cycle_card_layout_image";
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity)
                            context,
                    Pair.create(sharedViewIm, transitionNameIm));
            context.startActivity(intent, transitionActivityOptions.toBundle());
        } else  context.startActivity(intent);

    }
}
