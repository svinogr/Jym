package info.upump.jym.adapters.holders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import info.upump.jym.R;
import info.upump.jym.activity.IChooseItem;
import info.upump.jym.bd.SetDao;
import info.upump.jym.entity.Sets;

/**
 * Created by explo on 01.04.2018.
 */

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

