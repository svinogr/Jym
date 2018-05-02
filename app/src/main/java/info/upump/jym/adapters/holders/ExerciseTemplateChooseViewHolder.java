package info.upump.jym.adapters.holders;

import android.content.Intent;
import android.view.View;

import info.upump.jym.R;
import info.upump.jym.activity.IChooseItem;


public class ExerciseTemplateChooseViewHolder extends ExerciseViewHolder {
    private IChooseItem iChooseItem;
    public ExerciseTemplateChooseViewHolder(View inflate) {
        super(inflate);
        iChooseItem = (IChooseItem) context;
    }

    @Override
    public Intent createIntent() {
       return null;
    }

    @Override
    public void onClick(View v) {
        iChooseItem.createIntentForChooseResult(exercise);
    }

    @Override
    public void setInfo() {
        if (!exercise.isDefaultType()) {
            type.setText(context.getResources().getString(R.string.card_type_item_exercise));
        } else
            type.setText(context.getResources().getString(R.string.card_type_item_default_exercise));

    }
}

