package info.upump.jym.adapters.holders;

import android.view.View;

import info.upump.jym.activity.IChooseItem;

/**
 * Created by explo on 28.03.2018.
 */

public class WorkoutDefaultChooseViewHolder extends WorkoutDefaultViewHolder {
    private IChooseItem iChooseItem;

    public WorkoutDefaultChooseViewHolder(View itemView) {
        super(itemView);
        iChooseItem = (IChooseItem) context;
    }

    @Override
    public void onClick(View v) {
        System.out.println("choose");
        iChooseItem.createIntentForChooseResult(workout);

    }
}
