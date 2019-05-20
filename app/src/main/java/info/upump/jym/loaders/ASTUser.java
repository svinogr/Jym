package info.upump.jym.loaders;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import info.upump.jym.bd.UserDao;
import info.upump.jym.entity.User;

/**
 * Created by explo on 06.05.2018.
 */

public class ASTUser extends AsyncTask<Integer, Void, List<User>> {
    private Context context;

    public ASTUser(Context context) {
        this.context = context;
    }

    @Override
    protected List<User> doInBackground(Integer... integers) {
        UserDao userDao = UserDao.getInstance(context, null);
        List<User> userList = userDao.getAll();
        return userList;
    }
}