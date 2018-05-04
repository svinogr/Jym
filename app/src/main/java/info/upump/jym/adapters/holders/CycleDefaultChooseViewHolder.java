package info.upump.jym.adapters.holders;

import android.view.View;

import info.upump.jym.activity.IChooseItem;
import info.upump.jym.fragments.cycle.CRUD;

/*
* for my choose activity for choose cycles
* */
public class CycleDefaultChooseViewHolder extends AbstractCycleViewHolder {
    private IChooseItem iChooseItem;

    public CycleDefaultChooseViewHolder(View itemView, CRUD crud) {
        super(itemView, crud);
        iChooseItem = (IChooseItem) context;
    }

    @Override
    void setVariableViews() {
// NOP
    }

    @Override
    void startActivity() {
        iChooseItem.createIntentForChooseResult(cycle);
    }
}
