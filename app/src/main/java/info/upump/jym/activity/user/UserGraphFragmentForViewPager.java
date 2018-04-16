/*
package info.upump.jym.activity.user;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import info.upump.jym.R;
import info.upump.jym.entity.User;
import info.upump.jym.entity.UserProgressEnum;
import info.upump.jym.entity.Workout;
import info.upump.jym.loaders.UserProgressLoader;

import static info.upump.jym.activity.constant.Constants.TYPE_USER_PROGRESS;

public class UserGraphFragmentForViewPager extends Fragment implements LoaderManager.LoaderCallbacks<DataPoint[]> {
    private List<User> userList = new ArrayList<>();
    private UserProgressEnum userProgressEnum;
    private GraphView graph;
    private LineGraphSeries<DataPoint> series;
    private DataPoint[] dataPoints = new DataPoint[0];
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    //  LineChart chart;

    public static UserGraphFragmentForViewPager newInstance(UserProgressEnum progressEnum) {
        UserGraphFragmentForViewPager fragment = new UserGraphFragmentForViewPager();
        Bundle args = new Bundle();
        args.putString(TYPE_USER_PROGRESS, progressEnum.toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_user_graph_fragment_for_view_pager, container, false);
        graph = inflate.findViewById(R.id.user_graph_activity_graph);
        //  graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {

            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
//                    return sdf.format(new Date((long) value));
                    return sdf.format(new Date((long) value));
                } else return super.formatLabel(value, isValueX);
            }
        });
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space
        series = new LineGraphSeries<>(dataPoints);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(20);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getActivity(), String.valueOf(dataPoint.getY()), Toast.LENGTH_SHORT).show();
            }
        });

        graph.getGridLabelRenderer().setHumanRounding(true);
        graph.getViewport().setScalable(true);
        graph.getViewport().setMinY(250);
     //   graph.getViewport().setScalableY(true);
        graph.addSeries(series);
        */
/*DataPoint[] dataPointArr = new DataPoint[userList.size()];
        userList.toArray(dataPointArr);
        graph.getViewport().scrollToEnd();
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(2);
        series = new LineGraphSeries<>(dataPointArr);
        graph.addSeries(series);
        // chart = inflate.findViewById(R.id.chart);*//*




    */
/*
        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();
*//*


// you can directly pass Date objects to DataPoint-Constructor
// this will convert the Date to double via Date#getTime()


// as we use dates as labels, the human rounding to nice readable numbers
// is not necessary


        return inflate;
    }

    @Override
    public Loader<DataPoint[]> onCreateLoader(int id, Bundle args) {
        UserProgressLoader userProgressLoader = new UserProgressLoader(getContext(), userProgressEnum);
        return userProgressLoader;
    }

    @Override
    public void onLoadFinished(Loader<DataPoint[]> loader, DataPoint[] data) {
        //  userList.clear();
        //    userList.addAll(data);
        dataPoints = data;
        sortListByDate(dataPoints);
        graph.getViewport().setMinY(dataPoints[0].getX());
        series.resetData(dataPoints);
        //  Date min = new Date((long) data[0].getX());
        //   Date max = new Date((long) data[data.length-1].getX());


// set date label formatter


// set manual x bounds to have nice steps
// graph.getViewport().setMinX(min.getTime());
        //   graph.getViewport().setMaxX(max.getTime());
        //     graph.getViewport().setXAxisBoundsManual(true);


        // graph.getViewport().setMinX(min.getTime());
        //  graph.getViewport().setMaxX(max.getTime());
        //  graph.getViewport().setScalable(true);

        //   graph.getViewport().setMaxXAxisSize(2);
        //  graph.getViewport().setMaxY(250);
        //   graph.getViewport().setMinY(0);
        //   graph.getViewport().setYAxisBoundsManual(true);

    */
/*    DataPoint[] dataPointArr = new DataPoint[userList.size()];
        userList.toArray(dataPointArr);
        series = new LineGraphSeries<>(dataPointArr);*//*

        //  graph.getViewport().scrollToEnd();

        //

    }

    @Override
    public void onLoaderReset(Loader<DataPoint[]> loader) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments().getString(TYPE_USER_PROGRESS) != null) {
            userProgressEnum = UserProgressEnum.valueOf(getArguments().getString(TYPE_USER_PROGRESS));
        }
        getLoaderManager().initLoader(0, null, this);

    }

    private void sortListByDate(DataPoint[] list) {
        Arrays.sort(list, new Comparator<DataPoint>() {
            @Override
            public int compare(DataPoint o1, DataPoint o2) {

                return new Date((long) o1.getX()).compareTo(new Date((long) o2.getX()));
            }
        });
    }
}
*/
