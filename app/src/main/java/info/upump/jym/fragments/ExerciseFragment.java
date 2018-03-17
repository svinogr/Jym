package info.upump.jym.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.upump.jym.temp.activity.exercise.ExerciseDetailActivityEdit;
import info.upump.jym.IControlFragment;
import info.upump.jym.ITitlable;
import info.upump.jym.R;
import info.upump.jym.adapters.PagerAdapter;
import info.upump.jym.entity.Exercise;
import info.upump.jym.entity.TypeMuscle;

public class ExerciseFragment extends Fragment implements TabLayout.OnTabSelectedListener, View.OnClickListener {
    private static final String ADAPTER_POSITION = "adapter position";
    private String[] tabNames;
    private ITitlable iTitlable;
    private TypeMuscle[] values;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PagerAdapter pagerAdapter;
    private IControlFragment iControlFragment;
    private static final int ICON_FAB = R.drawable.ic_add_black_24dp;
    private FloatingActionButton fab;
    private int tab = 2;

    public ExerciseFragment() {
    }

    public static ExerciseFragment newInstance() {
        ExerciseFragment fragment = new ExerciseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate");
        super.onCreate(savedInstanceState);
        values = TypeMuscle.values();
        tabNames = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            tabNames[i] = getResources().getString(values[i].getName());
        }
        pagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager(), values, getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_exercise, container, false);
        System.out.println("onCreateView");
        fab = getActivity().findViewById(R.id.main_fab);
        fab.setOnClickListener(this);
        setIconFab(fab);

        iTitlable.setTitle(getResources().getString(R.string.exercise_fragment_title));

        viewPager = inflate.findViewById(R.id.exercise_fragment_viewpager);
        // viewPager.setPageTransformer(true, );
        tabLayout = inflate.findViewById(R.id.exercise_fragment_tab_layout);


        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        // viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        return inflate;
    }

    private void setIconFab(FloatingActionButton fab) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fab.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_add, getContext().getTheme()));
        } else {
            fab.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_add));
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iTitlable = (ITitlable) context;
        iControlFragment = (IControlFragment) context;
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition(), true);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onClick(View v) {
        Exercise exercise = null;
        Intent intent = ExerciseDetailActivityEdit.createIntent(getContext(), exercise);
        startActivity(intent);

    }
}
