package info.upump.jym.adapters.holders;

import android.content.Intent;
import android.view.View;

import info.upump.jym.activity.cycle.CycleDetailDefaultActivity;
import info.upump.jym.entity.Cycle;


public class CycleDefaultViewHolder extends CycleViewHolder {
    public CycleDefaultViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected Intent createIntent() {
        Intent intent = CycleDetailDefaultActivity.createIntent(context, cycle);
        return intent;
    }

    @Override
    public void bind(Cycle cycle) {
        this.cycle = cycle;
        title.setText(cycle.getTitle());
        setPic();
    }
}
