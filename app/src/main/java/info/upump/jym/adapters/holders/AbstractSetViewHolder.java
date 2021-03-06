package info.upump.jym.adapters.holders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.TextView;

import info.upump.jym.R;
import info.upump.jym.activity.sets.SetActivityCreate;
import info.upump.jym.entity.Sets;

/**
 * Created by explo on 05.05.2018.
 */

public abstract class AbstractSetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    protected TextView weight, reps, set;
    protected Context context;
    protected Sets sets;

    public AbstractSetViewHolder(View itemView) {
        super(itemView);
        set = itemView.findViewById(R.id.set_card_layout_label_set);
        weight = itemView.findViewById(R.id.set_card_layout_weight);
        reps = itemView.findViewById(R.id.set_card_layout_reps);
        context = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bind(Sets sets) {
        this.sets = sets;
        set.setText(String.valueOf(getAdapterPosition() + 1) + ". ");
        weight.setText(String.valueOf(sets.getWeight()));
        reps.setText(String.valueOf(sets.getReps()));
    }

    @Override
    public void onClick(View v) {
        startActivity();
    }

    abstract void  startActivity();

    protected Context getAnimationContext() {

        if (context instanceof Activity) {
            context = (Activity) context;
        } else context = ((ContextThemeWrapper) context).getBaseContext();
        return context;
    }

}