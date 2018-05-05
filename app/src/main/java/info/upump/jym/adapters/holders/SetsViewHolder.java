package info.upump.jym.adapters.holders;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import info.upump.jym.R;
import info.upump.jym.activity.sets.SetActivityCreate;
import info.upump.jym.entity.Sets;
import info.upump.jym.fragments.cycle.CRUD;


public class SetsViewHolder extends AbstractSetViewHolder{
    private CRUD crud;
    public SetsViewHolder(View itemView, CRUD crud) {
        super(itemView);
        this.crud = crud;
    }

    @Override
    void startActivity() {
        crud.createIntentForResult(null, sets);
    }

}
