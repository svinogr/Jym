package info.upump.jym.activity.user;

import android.content.Context;
import android.content.Intent;
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

public class UserGraphActivity extends AppCompatActivity implements View.OnClickListener {
    private List<User> allUser;
    private GraphView graphView;
    private LineGraphSeries<DataPoint> series, series2;
    private DataPoint[] dataPoints = new DataPoint[0];
    private DataPoint[] dataPoints2 = new DataPoint[0];
    private Button btnWeight, btnFat, btnNeck, btnPectoral, btnShoulder, btnBiceps, btnAbs, btnLeg, btnCalves;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_graph);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("График");

        graphView = findViewById(R.id.user_graph_activity_graphView);
        btnWeight = findViewById(R.id.user_graph_activity_radioButtonWeight);
        btnFat = findViewById(R.id.user_graph_activity_radioButtonFat);
        btnNeck = findViewById(R.id.user_graph_activity_radioButtonNeck);
        btnShoulder = findViewById(R.id.user_graph_activity_radioButtonShoulders);
        btnPectoral = findViewById(R.id.user_graph_activity_radioButtonPectoral);
        btnBiceps = findViewById(R.id.user_graph_activity_radioButtonBiceps);
        btnAbs = findViewById(R.id.user_graph_activity_radioButtonAbs);
        btnLeg = findViewById(R.id.user_graph_activity_radioButtonLeg);
        btnCalves = findViewById(R.id.user_graph_activity_radioButtonCalves);

        btnWeight.setOnClickListener(this);
        btnNeck.setOnClickListener(this);
        btnShoulder.setOnClickListener(this);
        btnPectoral.setOnClickListener(this);
        btnBiceps.setOnClickListener(this);
        btnAbs.setOnClickListener(this);
        btnLeg.setOnClickListener(this);
        btnCalves.setOnClickListener(this);
        btnFat.setOnClickListener(this);

        allUser = getAllUser();
        sortListByDate(allUser);
        initGraphView();


        //  viewPager = findViewById(R.id.user_graph_activity_viewpager);
        // tabLayout = findViewById(R.id.user_graph_activity_tab_layout);


        //  pagerAdapterUserGraph = new PagerAdapterUserGraph(getSupportFragmentManager(), this);
        //   viewPager.setAdapter(pagerAdapterUserGraph);
        //  tabLayout.setupWithViewPager(viewPager);

    }

    private void initGraphView() {
        series = new LineGraphSeries<>(dataPoints);
        series2 = new LineGraphSeries<>(dataPoints2);
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
                Toast.makeText(getApplicationContext(), String.valueOf(dataPoint.getY()), Toast.LENGTH_SHORT).show();
            }
        });

        series2.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getApplicationContext(), String.valueOf(dataPoint.getY()), Toast.LENGTH_SHORT).show();
            }
        });


        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {

            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                   // return sdf.format(new Date((long) value));
                    int index = 0;
                    for (User u: allUser){
                        if(u.getId() == value){
                            index = allUser.indexOf(u);
                            break;
                        }
                    }

                    return sdf.format(allUser.get(index).getDate());
                } else return super.formatLabel(value, isValueX);
            }
        });
        graphView.getViewport().setScalable(true);

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
            case R.id.user_graph_activity_radioButtonWeight:
                for (int i = 0; i < dataPoints.length; i++) {
                    dataPoints[i] = new DataPoint(allUser.get(i).getId(), allUser.get(i).getWeight());
                }
                break;
            case R.id.user_graph_activity_radioButtonFat:
                for (int i = 0; i < dataPoints.length; i++) {
                    dataPoints[i] = new DataPoint(allUser.get(i).getId(), allUser.get(i).getFat());
                }
                break;
            case R.id.user_graph_activity_radioButtonNeck:
                for (int i = 0; i < dataPoints.length; i++) {
                    dataPoints[i] = new DataPoint(allUser.get(i).getId(), allUser.get(i).getNeck());
                }
                break;
            case R.id.user_graph_activity_radioButtonShoulders:
                for (int i = 0; i < dataPoints.length; i++) {
                    dataPoints[i] = new DataPoint(allUser.get(i).getId(), allUser.get(i).getShoulder());
                }
                break;
            case R.id.user_graph_activity_radioButtonPectoral:
                for (int i = 0; i < dataPoints.length; i++) {
                    dataPoints[i] = new DataPoint(allUser.get(i).getId(), allUser.get(i).getPectoral());
                }
                break;
            case R.id.user_graph_activity_radioButtonBiceps:
                dataPoints2 = new DataPoint[allUser.size()];
                for (int i = 0; i < dataPoints.length; i++) {
                    dataPoints[i] = new DataPoint(allUser.get(i).getId(), allUser.get(i).getRightBiceps());
                    dataPoints2[i] = new DataPoint(allUser.get(i).getId(), allUser.get(i).getLeftBiceps());
                }
                break;
            case R.id.user_graph_activity_radioButtonAbs:
                for (int i = 0; i < dataPoints.length; i++) {
                    dataPoints[i] = new DataPoint(allUser.get(i).getId(), allUser.get(i).getFat());
                }
                break;
            case R.id.user_graph_activity_radioButtonLeg:
                dataPoints2 = new DataPoint[allUser.size()];
                for (int i = 0; i < dataPoints.length; i++) {
                    dataPoints[i] = new DataPoint(allUser.get(i).getId(), allUser.get(i).getRightLeg());
                    dataPoints2[i] = new DataPoint(allUser.get(i).getId(), allUser.get(i).getLeftLeg());
                }
                break;
            case R.id.user_graph_activity_radioButtonCalves:
                dataPoints2 = new DataPoint[allUser.size()];
                for (int i = 0; i < dataPoints.length; i++) {
                    dataPoints[i] = new DataPoint(allUser.get(i).getId(), allUser.get(i).getRightCalves());
                    dataPoints2[i] = new DataPoint(allUser.get(i).getId(), allUser.get(i).getLeftCalves());
                }
                break;

        }
        series.resetData(dataPoints);
            series2.resetData(dataPoints2);

    }
}
