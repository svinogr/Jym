//package info.upump.jym.adapters;
//
//import androidx.recyclerview.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import java.util.List;
//
//import info.upump.jym.R;
//import info.upump.jym.adapters.holders.ExerciseTemplateChooseViewHolder;
//import info.upump.jym.adapters.holders.ExerciseTemplateViewHolder;
//import info.upump.jym.adapters.holders.AbstractExerciseViewHolder;
//import info.upump.jym.adapters.holders.ExerciseWithInfoDefaultViewHolder;
//import info.upump.jym.adapters.holders.ExerciseWithInfoViewHolder;
//import info.upump.jym.entity.Exercise;
//import info.upump.jym.fragments.cycle.CRUD;
//
//
//public class ExerciseAdapter extends RecyclerView.Adapter<AbstractExerciseViewHolder> {
//    private List<Exercise> exerciseList;
//    public static final int INFO = 0;
//    public static final int INFO_DEFAULT = 7;
//    public static final int DEFAULT_TYPE = 1;
//    public static final int CHOOSE = 2;
//    private int type_holder;
//    private CRUD crud;
//
//    public ExerciseAdapter(List<Exercise> exerciseList, int type_holder, CRUD crud) {
//        this.exerciseList = exerciseList;
//        this.type_holder = type_holder;
//        this.crud = crud;
//    }
//
//    @Override
//    public AbstractExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_card_layout, parent, false);
//        AbstractExerciseViewHolder viewHolder = null;
//        switch (viewType){
//            case INFO:
//                viewHolder = new ExerciseWithInfoViewHolder(inflate, crud);
//                break;
//                case INFO_DEFAULT:
//                viewHolder = new ExerciseWithInfoDefaultViewHolder(inflate, crud);
//                break;
//
//            case DEFAULT_TYPE:
//                viewHolder = new ExerciseTemplateViewHolder(inflate, crud);
//                break;
//            case CHOOSE:
//                viewHolder = new ExerciseTemplateChooseViewHolder(inflate);
//                break;
//        }
//        return  viewHolder;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return type_holder;
//    }
//
//    @Override
//    public void onBindViewHolder(AbstractExerciseViewHolder holder, int position) {
//            holder.bind(exerciseList.get(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        return exerciseList.size();
//    }
//}
