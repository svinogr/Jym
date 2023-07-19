//package info.upump.jym.loaders;
//
//import android.content.Context;
//import android.os.AsyncTask;
//
//import java.util.List;
//
//import info.upump.jym.activity.constant.Constants;
//import info.upump.jym.bd.CycleDao;
//import info.upump.jym.entity.Cycle;
//
///**
// * Created by explo on 03.05.2018.
// */
//
//public class ASTCycle extends AsyncTask<Integer,Void, List<Cycle>> {
//  private Context context;
//
//    public ASTCycle(Context context) {
//        this.context = context;
//    }
//    @Override
//    protected List<Cycle> doInBackground(Integer... integers) {
//        int type = integers[0];
//        CycleDao cycleDao = CycleDao.getInstance(context, null);
//        List<Cycle> cycleList = null;
//        switch (type) {
//            case Constants.LOADER_BY_DEFAULT_TYPE:
//                cycleList = cycleDao.getAllDefault();
//                break;
//            case Constants.LOADER_BY_USER_TYPE:
//                cycleList = cycleDao.getAllUser();
//                break;
//        }
//
//        return cycleList;
//
//    }
//}
