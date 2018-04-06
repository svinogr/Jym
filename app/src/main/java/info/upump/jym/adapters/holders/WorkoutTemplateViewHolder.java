package info.upump.jym.adapters.holders;

import android.content.Intent;
import android.view.View;

import info.upump.jym.R;
import info.upump.jym.activity.workout.WorkoutDetailActivity;

/**
 * Created by explo on 27.03.2018.
 */

public class WorkoutTemplateViewHolder extends WorkoutViewHolder {

    public WorkoutTemplateViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setVariablyField() {
        if (!workout.isDefaultType()) {
            variably.setText(context.getResources().getString(R.string.card_type_item));
        } else variably.setText("");
    }

    @Override
    public void onClick(View v) {
        System.out.println("WorkoutTemplateViewHolder");
        Intent intent = WorkoutDetailActivity.createIntent(context,workout);
        context.startActivity(intent);
    }
}
