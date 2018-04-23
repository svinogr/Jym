package info.upump.jym.adapters.holders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import info.upump.jym.R;
import info.upump.jym.activity.sets.SetActivityCreate;
import info.upump.jym.entity.Sets;


public class SetsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView weight, reps, set;
    private Context context;
    private Sets sets;

    public SetsViewHolder(View itemView) {
        super(itemView);
        set = itemView.findViewById(R.id.set_card_layout_label_set);
        weight = itemView.findViewById(R.id.set_card_layout_weight);
        reps = itemView.findViewById(R.id.set_card_layout_reps);
        context = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bind(Sets sets) {
        this.sets =sets;
        set.setText(String.valueOf(getAdapterPosition()+1)+". ");
        weight.setText(String.valueOf(sets.getWeight()));
        reps.setText(String.valueOf(sets.getReps()));
    }

    @Override
    public void onClick(View v) {
        Intent intent = SetActivityCreate.createIntent(context, sets);
        context.startActivity(intent);
    }
}
