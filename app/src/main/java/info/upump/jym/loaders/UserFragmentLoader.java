package info.upump.jym.loaders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import info.upump.jym.bd.UserDao;
import info.upump.jym.entity.User;

/**
 * Created by explo on 10.04.2018.
 */

public class UserFragmentLoader extends AsyncTaskLoader<List<User>> {
    private Context context;

    public UserFragmentLoader(Context context) {
        super(context);
        this.context = context;
    }

    @Nullable
    @Override
    public List<User> loadInBackground() {
        UserDao userDao = new UserDao(context);
        List<User> userList = userDao.getAll();
        return userList;
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
    public void deliverResult(List<User> data) {
        super.deliverResult(data);
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }
}
