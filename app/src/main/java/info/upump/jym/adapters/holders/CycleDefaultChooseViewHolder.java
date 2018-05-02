package info.upump.jym.adapters.holders;

import android.view.View;

import info.upump.jym.activity.IChooseItem;


public class CycleDefaultChooseViewHolder extends CycleDefaultViewHolder {
    private IChooseItem iChooseItem;
    public CycleDefaultChooseViewHolder(View itemView) {
        super(itemView);
        iChooseItem = (IChooseItem) context;
    }

    @Override
    public void onClick(View v) {
        iChooseItem.createIntentForChooseResult(cycle);
    }
}
