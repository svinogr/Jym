/*
package info.upump.jym.loaders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import info.upump.jym.bd.SetDao;
import info.upump.jym.entity.Sets;

import static info.upump.jym.activity.constant.Constants.LOADER_BY_PARENT_ID;


public class SetsLoader extends AsyncTaskLoader<List<Sets>> {
    private int operation;
    private long parentId;
    private Context context;

    public SetsLoader(Context context, int operation, long parentId) {
        super(context);
        this.operation = operation;
        this.parentId = parentId;
        this.context = context;
    }

    @Nullable
    @Override
    public List<Sets> loadInBackground() {
        SetDao setDao = new SetDao(context);
        List<Sets> setsList = null;
        switch (operation){
            case LOADER_BY_PARENT_ID:
                setsList =  setDao.getByParentId(parentId);
                break;
        }
        return setsList;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
    }

    @Override
    public void deliverResult(List<Sets> data) {
        super.deliverResult(data);
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }
}
*/
