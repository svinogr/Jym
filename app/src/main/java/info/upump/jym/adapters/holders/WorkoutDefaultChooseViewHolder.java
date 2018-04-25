package info.upump.jym.adapters.holders;

import android.view.View;

import info.upump.jym.R;
import info.upump.jym.activity.IChooseItem;

/**
 * Created by explo on 28.03.2018.
 */

public class WorkoutDefaultChooseViewHolder extends WorkoutTemplateViewHolder {
    private IChooseItem iChooseItem;

    public WorkoutDefaultChooseViewHolder(View itemView) {
        super(itemView);
        System.out.println("WorkoutDefaultChooseViewHolder");
        iChooseItem = (IChooseItem) context;
    }

    @Override
    public void onClick(View v) {
        System.out.println("choose");
        iChooseItem.createIntentForChooseResult(workout);

    }

    @Override
    public void setVariablyField() {
        if (!workout.isDefaultType()) {
            variably.setText(context.getResources().getString(R.string.card_type_item));
        } else variably.setText(context.getResources().getString(R.string.card_type_item_default));
    }
}
