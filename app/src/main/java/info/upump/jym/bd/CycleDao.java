package info.upump.jym.bd;

import android.content.Context;

import java.util.List;

import info.upump.jym.entity.Cycle;

/**
 * Created by explo on 05.03.2018.
 */

public class CycleDao extends DBDAO implements IData<Cycle> {
    public CycleDao(Context context) {
        super(context);
    }

    @Override
    public List<Cycle> getAll() {
        return null;
    }
}
