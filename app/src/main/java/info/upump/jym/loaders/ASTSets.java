//package info.upump.jym.loaders;
//
//import android.content.Context;
//import android.os.AsyncTask;
//
//import java.util.List;
//
//import info.upump.jym.bd.SetDao;
//import info.upump.jym.entity.Sets;
//
//import static info.upump.jym.activity.constant.Constants.LOADER_BY_PARENT_ID;
//
///**
// * Created by explo on 05.05.2018.
// */
//
//public class ASTSets extends AsyncTask<Integer, Void, List<Sets>> {
//    private Context context;
//
//    public ASTSets(Context context) {
//        this.context = context;
//    }
//
//    @Override
//    protected List<Sets> doInBackground(Integer... integers) {
//        int type = integers[0];
//        long parentId = 0;
//        if (integers.length > 1) {
//            parentId = integers[1];
//        }
//        SetDao setDao = SetDao.getInstance(context, null);
//        List<Sets> setsList = null;
//        switch (type) {
//            case LOADER_BY_PARENT_ID:
//                setsList = setDao.getByParentId(parentId);
//                break;
//        }
//        return setsList;
//    }
//
//}