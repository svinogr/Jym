package info.upump.jym.adapters.holders;

import android.view.View;

import info.upump.jym.R;

/**
 * Created by explo on 27.03.2018.
 */

public class WorkoutDefaultViewHolder extends WorkoutViewHolder {

    public WorkoutDefaultViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setVariablyField() {
        if (workout.isDefaultType()) {
            variably.setText(context.getResources().getString(R.string.card_type_item));
        } else variably.setText("");
    }
}
