//package info.upump.jym.adapters.holders;
//
//import android.content.Intent;
//import android.view.View;
//
//import info.upump.jym.R;
//import info.upump.jym.activity.IChooseItem;
//
///*
//*  holder for choose exercise activity
//*/
//public class ExerciseTemplateChooseViewHolder extends AbstractExerciseViewHolder {
//    private IChooseItem iChooseItem;
//    public ExerciseTemplateChooseViewHolder(View inflate) {
//        super(inflate);
//        iChooseItem = (IChooseItem) context;
//    }
//
//
//    @Override
//    public void setInfo() {
//        if (!exercise.isDefaultType()) {
//            type.setText(context.getResources().getString(R.string.card_type_item_exercise));
//        } else
//            type.setText(context.getResources().getString(R.string.card_type_item_default_exercise));
//
//    }
//
//    @Override
//    protected void startActivity() {
//        iChooseItem.createIntentForChooseResult(exercise);
//    }
//}
//
