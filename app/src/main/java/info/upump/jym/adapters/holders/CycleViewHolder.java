package info.upump.jym.adapters.holders;

import android.view.View;

import info.upump.jym.fragments.cycle.CRUD;

/*
* for my cycles
* */
public class CycleViewHolder extends CycleDefaultViewHolder {

    public CycleViewHolder(View itemView, CRUD crud) {
        super(itemView, crud);
    }

    @Override
    void setVariableViews() {
        date.setText(cycle.getStartStringFormatDate());
    }
}
