package info.upump.jym.adapters.holders;

import android.view.View;

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
