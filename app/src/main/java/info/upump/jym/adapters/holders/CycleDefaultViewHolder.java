package info.upump.jym.adapters.holders;

import android.content.Intent;
import android.view.View;

import info.upump.jym.activity.cycle.CycleDetailDefaultActivity;



public class CycleDefaultViewHolder extends CycleViewHolder {
    public CycleDefaultViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected Intent createIntent() {
        System.out.println("createIntent CycleDetailDefaultActivity");
        Intent intent = CycleDetailDefaultActivity.createIntent(context, cycle);
        return intent;
    }
}
