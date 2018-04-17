package info.upump.jym.activity.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import info.upump.jym.R;
import info.upump.jym.bd.UserDao;
import info.upump.jym.entity.User;
import info.upump.jym.utils.MyValueFormatter;

public class UserGraphActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private List<User> allUser;
    private LineChart graphView;
    private List<Entry> listEntryWithoutDate, listEntryWithoutDate2;
    private List<Date> dates;
    private LineDataSet series, series2;
    private LineData lineData;
    private List<ILineDataSet> iLineDataSets;
    private Button btnWeight, btnFat, btnNeck, btnPectoral, btnShoulder, btnBiceps, btnAbs, btnLeg, btnCalves;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private TabLayout tabLayout;
    private static String TAB = "tab";


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

        if (savedInstanceState != null) {
            if (savedInstanceState.getString(TAB) != null) {
                int tab = Integer.parseInt(savedInstanceState.getString(TAB));
                tabLayout.getTabAt(tab).select();
                createEntries(tab);
            }

        } else createEntries(0);

    }

    private void createEntries(int idTab) {
        listEntryWithoutDate = new ArrayList<>();
        listEntryWithoutDate2 = new ArrayList<>();
        iLineDataSets.removeAll(iLineDataSets);
        switch (idTab) {
            case 0:
                for (int i = 0; i < allUser.size(); i++) {
                    System.out.println("флоат " + (float) allUser.get(i).getWeight());
                    listEntryWithoutDate.add(new Entry(i, (float) allUser.get(i).getWeight()));

                }
                series = new LineDataSet(listEntryWithoutDate, "Вес тела, кг");
                iLineDataSets.add(series);
                break;
            case 1:
                for (int i = 0; i < allUser.size(); i++) {
                    listEntryWithoutDate.add(new Entry(i, (float) allUser.get(i).getFat()));

                }
                series = new LineDataSet(listEntryWithoutDate, "Процент жира");
                iLineDataSets.add(series);
                break;
            case 2:
                for (int i = 0; i < allUser.size(); i++) {
                    listEntryWithoutDate.add(new Entry(i, (float) allUser.get(i).getNeck()));

                }
                series = new LineDataSet(listEntryWithoutDate, "Шея, см");
                iLineDataSets.add(series);
                break;
            case 3:
                for (int i = 0; i < allUser.size(); i++) {
                    listEntryWithoutDate.add(new Entry(i, (float) allUser.get(i).getShoulder()));

                }
                series = new LineDataSet(listEntryWithoutDate, "Плечи, см");
                iLineDataSets.add(series);
                break;
            case 4:
                for (int i = 0; i < allUser.size(); i++) {
                    listEntryWithoutDate.add(new Entry(i, (float) allUser.get(i).getPectoral()));

                }
                series = new LineDataSet(listEntryWithoutDate, "Грудь, см");
                iLineDataSets.add(series);
                break;
            case 5:
                for (int i = 0; i < allUser.size(); i++) {
                    listEntryWithoutDate.add(new Entry(i, (float) allUser.get(i).getRightBiceps()));
                    listEntryWithoutDate2.add(new Entry(i, (float) allUser.get(i).getLeftBiceps()));

                }
                series = new LineDataSet(listEntryWithoutDate, "Правый бицепс, см");
                series2 = new LineDataSet(listEntryWithoutDate2, "Левый бицепс, см");
                series2.setColor(Color.RED);
                iLineDataSets.add(series);
                iLineDataSets.add(series2);
                break;
            case 6:
                for (int i = 0; i < allUser.size(); i++) {
                    listEntryWithoutDate.add(new Entry(i, (float) allUser.get(i).getAbs()));

                }
                series = new LineDataSet(listEntryWithoutDate, "Талия, см");
                iLineDataSets.add(series);
                break;
            case 7:
                for (int i = 0; i < allUser.size(); i++) {
                    listEntryWithoutDate.add(new Entry(i, (float) allUser.get(i).getRightLeg()));
                    listEntryWithoutDate2.add(new Entry(i, (float) allUser.get(i).getLeftLeg()));

                }
                series = new LineDataSet(listEntryWithoutDate, "Правое бедро, см");
                series2 = new LineDataSet(listEntryWithoutDate2, "Левое бедро, см");
                series2.setColor(Color.RED);
                iLineDataSets.add(series);
                iLineDataSets.add(series2);
                break;
            case 8:
                for (int i = 0; i < allUser.size(); i++) {
                    listEntryWithoutDate.add(new Entry(i, (float) allUser.get(i).getRightCalves()));
                    listEntryWithoutDate2.add(new Entry(i, (float) allUser.get(i).getLeftCalves()));

                }
                series = new LineDataSet(listEntryWithoutDate, "Правая голень, см");
                series2 = new LineDataSet(listEntryWithoutDate2, "Левая голень, см");
                series2.setColor(Color.RED);
                iLineDataSets.add(series);
                iLineDataSets.add(series2);
                break;

        }
        System.out.println(iLineDataSets.toString());
        lineData = new LineData(iLineDataSets);
        graphView.setData(lineData);
        lineData.setValueFormatter(new MyValueFormatter());
        //lineData.notifyDataChanged();
        graphView.invalidate();
    }

    private void initGraphView() {
        dates = new ArrayList<>();
        for (User u : allUser) {
            dates.add(u.getDate());
        }
        System.out.println(dates.toString());
        iLineDataSets = new ArrayList<>();
        lineData = new LineData(iLineDataSets);
        // lineData.setValueFormatter(new MyValueFormatter());
        graphView.setData(lineData);
        graphView.getDescription().setEnabled(false);

        XAxis xAxis = graphView.getXAxis();
        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return sdf.format(dates.get((int) value));

            }

        };
        xAxis.setValueFormatter(formatter);
        xAxis.setGranularity(1.0f);


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
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        createEntries(position);

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        int selectedTabPosition = tabLayout.getSelectedTabPosition();
        outState.putString(TAB, String.valueOf(selectedTabPosition));
        super.onSaveInstanceState(outState);
    }
}
