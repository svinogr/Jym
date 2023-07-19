//package info.upump.jym.loaders;
//
//import android.content.Context;
//import android.os.AsyncTask;
//
//import java.util.List;
//
//import info.upump.jym.bd.ExerciseDao;
//import info.upump.jym.entity.Exercise;
//import info.upump.jym.entity.TypeMuscle;
//
//import static info.upump.jym.activity.constant.Constants.LOADER_BY_PARENT_ID;
//import static info.upump.jym.activity.constant.Constants.LOADER_BY_TEMPLATE_TYPE;
//
///**
// * Created by explo on 05.05.2018.
// */
//
//public class ASTExercise extends AsyncTask<Integer, Void, List<Exercise>> {
//    private Context context;
//
//    public ASTExercise(Context context) {
//        this.context = context;
//    }
//
//    @Override
//    protected List<Exercise> doInBackground(Integer... integers) {
//        int type = integers[0];
//        long parentId = 0;
//        int typeMuscle = 0;
//        if(integers.length>1){
//            parentId = integers[1];
//        }
//        if (integers.length > 2) {
//            typeMuscle = integers[2];
//        }
//        TypeMuscle typeMuscle2 = TypeMuscle.values()[typeMuscle];
//        ExerciseDao exerciseDao = ExerciseDao.getInstance(context, null);
//        List<Exercise> exerciseList = null;// TODO только темпейты
//        switch (type){
//            case LOADER_BY_PARENT_ID:
//                exerciseList =  exerciseDao.getByParentId(parentId);
//                break;
//            case LOADER_BY_TEMPLATE_TYPE:
//                exerciseList = exerciseDao.getAllByTypeMuscleTemplate(typeMuscle2);// все по типу мышцы и темплайты
//        }
//
//        return exerciseList;
//
//    }
//
//}