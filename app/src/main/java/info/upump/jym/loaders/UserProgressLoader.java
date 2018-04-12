package info.upump.jym.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.github.mikephil.charting.data.Entry;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.List;

import info.upump.jym.bd.UserDao;
import info.upump.jym.entity.User;
import info.upump.jym.entity.UserProgressEnum;

/**
 * Created by explo on 12.04.2018.
 */

public class UserProgressLoader extends AsyncTaskLoader<DataPoint[]> {
    private UserProgressEnum userProgressEnum;
    private Context context;

    public UserProgressLoader(Context context, UserProgressEnum userProgressEnum) {
        super(context);
        this.context = context;
        this.userProgressEnum = userProgressEnum;
    }

    @Override
    public DataPoint[]loadInBackground() {
        UserDao userDao = new UserDao(context);
        List<User> users = userDao.getAll();
        DataPoint[] dataPoints = new DataPoint[users.size()];
        switch (userProgressEnum){
            case WEIGHT:
                for(int i = 0; i < dataPoints.length; i++){
                    dataPoints[i] = new DataPoint(users.get(i).getDate(), users.get(i).getWeight());
                }
                break;
            case HEIGHT:
                for(int i = 0; i < dataPoints.length; i++){
                    dataPoints[i] = new DataPoint(users.get(i).getDate(), users.get(i).getHeight());
                }
                break;
            case FAT:
                for(int i = 0; i < dataPoints.length; i++){
                    dataPoints[i] = new DataPoint(users.get(i).getDate(), users.get(i).getFat());
                }
                break;
            case NECK:
                for(int i = 0; i < dataPoints.length; i++){
                    dataPoints[i] = new DataPoint(users.get(i).getDate(), users.get(i).getNeck());
                }
                break;
            case SHOULDER:
                for(int i = 0; i < dataPoints.length; i++){
                    dataPoints[i] = new DataPoint(users.get(i).getDate(), users.get(i).getShoulder());
                }
                break;
            case PECTORAL:
                for(int i = 0; i < dataPoints.length; i++){
                    dataPoints[i] = new DataPoint(users.get(i).getDate(), users.get(i).getPectoral());
                }
                break;
            case BICEPS_LEFT:
                for(int i = 0; i < dataPoints.length; i++){
                    dataPoints[i] = new DataPoint(users.get(i).getDate(), users.get(i).getLeftBiceps());
                }
                break;
            case BICEPS_RIGHT:
                for(int i = 0; i < dataPoints.length; i++){
                    dataPoints[i] = new DataPoint(users.get(i).getDate(), users.get(i).getRightBiceps());
                }
                break;
            case LEG_LEFT:
                for(int i = 0; i < dataPoints.length; i++){
                    dataPoints[i] = new DataPoint(users.get(i).getDate(), users.get(i).getLeftLeg());
                }
                break;
            case LEG_RIGHT:
                for(int i = 0; i < dataPoints.length; i++){
                    dataPoints[i] = new DataPoint(users.get(i).getDate(), users.get(i).getRightLeg());
                }
                break;
            case CALVES_LEFT:
                for(int i = 0; i < dataPoints.length; i++){
                    dataPoints[i] = new DataPoint(users.get(i).getDate(), users.get(i).getLeftCalves());
                }
                break;
            case CALVES_RIGHT:
                for(int i = 0; i < dataPoints.length; i++){
                    dataPoints[i] = new DataPoint(users.get(i).getDate(), users.get(i).getRightCalves());
                }
                break;
            case ABS:
                for(int i = 0; i < dataPoints.length; i++){
                    dataPoints[i] = new DataPoint(users.get(i).getDate(), users.get(i).getAbs());
                }
                break;
        }

        return dataPoints;
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
    public void deliverResult(DataPoint[]data) {
        super.deliverResult(data);
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }
}
