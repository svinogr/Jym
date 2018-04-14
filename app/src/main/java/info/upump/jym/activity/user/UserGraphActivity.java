package info.upump.jym.activity.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import info.upump.jym.R;
import info.upump.jym.bd.UserDao;
import info.upump.jym.entity.User;

public class UserGraphActivity extends AppCompatActivity implements View.OnClickListener, TabLayout.OnTabSelectedListener {
    private List<User> allUser;
    private GraphView graphView;
    private LineGraphSeries<DataPoint> series, series2;
    private DataPoint[] dataPoints = new DataPoint[0];
    private DataPoint[] dataPoints2 = new DataPoint[0];
    private Button btnWeight, btnFat, btnNeck, btnPectoral, btnShoulder, btnBiceps, btnAbs, btnLeg, btnCalves;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_graph);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("График");

        graphView = findViewById(R.id.user_graph_activity_graphView);
        tabLayout = findViewById(R.id.user_graph_activity_tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Вес"));
        tabLayout.addTab(tabLayout.newTab().setText("Жир"));
        tabLayout.addTab(tabLayout.newTab().setText("Шея"));
        tabLayout.addTab(tabLayout.newTab().setText("Плечи"));
        tabLayout.addTab(tabLayout.newTab().setText("Грудь"));
        tabLayout.addTab(tabLayout.newTab().setText("Бицепс"));
        tabLayout.addTab(tabLayout.newTab().setText("Талия"));
        tabLayout.addTab(tabLayout.newTab().setText("Бедра"));
        tabLayout.addTab(tabLayout.newTab().setText("Голень"));

        tabLayout.addOnTabSelectedListener(this);


        allUser = getAllUser();
        sortListByDate(allUser);
        initGraphView();
        tabLayout.getTabAt(0).select();

    }

    private void initGraphView() {
        series = new LineGraphSeries<>(dataPoints);
        series2 = new LineGraphSeries<>(dataPoints2);
        series2.setColor(Color.RED);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(20);
        series2.setDrawDataPoints(true);
        series2.setDataPointsRadius(20);

        graphView.getGridLabelRenderer().setNumHorizontalLabels(2); // only 4 because of the space
        graphView.addSeries(series);
        graphView.addSeries(series2);
        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                int index = getIndex(dataPoint.getX());
                Date date = allUser.get(index).getDate();
                Toast.makeText(getApplicationContext(), String.valueOf(dataPoint.getY())+" "+sdf.format(date), Toast.LENGTH_SHORT).show();
            }
        });

        series2.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                int index = getIndex(dataPoint.getX());
                Date date = allUser.get(index).getDate();
                Toast.makeText(getApplicationContext(), String.valueOf(dataPoint.getY())+" "+sdf.format(date), Toast.LENGTH_SHORT).show();            }
        });


        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {

            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    int index = getIndex(value);
                    return sdf.format(allUser.get(index).getDate());
                } else return super.formatLabel(value, isValueX);
            }
        });
        graphView.getViewport().setScalable(true);

    }

    private int getIndex(double value){
        int index =0;
        for (User u : allUser) {
            if (u.getId() == value) {
                index = allUser.indexOf(u);
                break;
            }
        }
        return index;
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, UserGraphActivity.class);
        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public List<User> getAllUser() {
        UserDao userDao = new UserDao(this);
        List<User> users = userDao.getAll();

        return users;
    }

    private void sortListByDate(List<User> list) {
        Collections.sort(list, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {

                return o1.getDate().compareTo(o2.getDate());
            }
        });
    }

    @Override
    public void onClick(View v) {
        createDataPoints(v.getId());

    }

    private void createDataPoints(int id) {
        dataPoints = new DataPoint[allUser.size()];
        dataPoints2 = new DataPoint[0];

        switch (id) {
            case 0:
                for (int i = 0; i < dataPoints.length; i++) {
                    dataPoints[i] = new DataPoint(allUser.get(i).getId(), allUser.get(i).getWeight());
                }
                break;
            case 1:
                for (int i = 0; i < dataPoints.length; i++) {
                    dataPoints[i] = new DataPoint(allUser.get(i).getId(), allUser.get(i).getFat());
                }
                break;
            case 2:
                for (int i = 0; i < dataPoints.length; i++) {
                    dataPoints[i] = new DataPoint(allUser.get(i).getId(), allUser.get(i).getNeck());
                }
                break;
            case 3:
                for (int i = 0; i < dataPoints.length; i++) {
                    dataPoints[i] = new DataPoint(allUser.get(i).getId(), allUser.get(i).getShoulder());
                }
                break;
            case 4:
                for (int i = 0; i < dataPoints.length; i++) {
                    dataPoints[i] = new DataPoint(allUser.get(i).getId(), allUser.get(i).getPectoral());
                }
                break;
            case 5:
                dataPoints2 = new DataPoint[allUser.size()];
                for (int i = 0; i < dataPoints.length; i++) {
                    dataPoints[i] = new DataPoint(allUser.get(i).getId(), allUser.get(i).getRightBiceps());
                    dataPoints2[i] = new DataPoint(allUser.get(i).getId(), allUser.get(i).getLeftBiceps());
                }
                break;
            case 6:
                for (int i = 0; i < dataPoints.length; i++) {
                    dataPoints[i] = new DataPoint(allUser.get(i).getId(), allUser.get(i).getFat());
                }
                break;
            case 7:
                dataPoints2 = new DataPoint[allUser.size()];
                for (int i = 0; i < dataPoints.length; i++) {
                    dataPoints[i] = new DataPoint(allUser.get(i).getId(), allUser.get(i).getRightLeg());
                    dataPoints2[i] = new DataPoint(allUser.get(i).getId(), allUser.get(i).getLeftLeg());
                }
                break;
            case 8:
                dataPoints2 = new DataPoint[allUser.size()];
                for (int i = 0; i < dataPoints.length; i++) {
                    dataPoints[i] = new DataPoint(allUser.get(i).getId(), allUser.get(i).getRightCalves());
                    dataPoints2[i] = new DataPoint(allUser.get(i).getId(), allUser.get(i).getLeftCalves());
                }
                break;

        }
        System.out.println(dataPoints.toString());
        series.resetData(dataPoints);
        series2.resetData(dataPoints2);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        createDataPoints(position);
        System.out.println(position);

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
