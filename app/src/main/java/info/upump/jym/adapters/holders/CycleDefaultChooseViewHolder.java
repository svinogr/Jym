package info.upump.jym.adapters.holders;

import android.view.View;

import info.upump.jym.activity.IChooseItem;
import info.upump.jym.fragments.cycle.CRUD;


public class CycleDefaultChooseViewHolder extends CycleDefaultViewHolder {
    private IChooseItem iChooseItem;
    public CycleDefaultChooseViewHolder(View itemView, CRUD crud) {
        super(itemView, crud);
        iChooseItem = (IChooseItem) context;
    }

    @Override
    public void onClick(View v) {
        iChooseItem.createIntentForChooseResult(cycle);
    }
}
