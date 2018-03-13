package info.upump.jym.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.upump.jym.ITitlable;
import info.upump.jym.R;
import info.upump.jym.adapters.PagerAdapter;
import info.upump.jym.entity.TypeMuscle;

public class ExerciseFragment extends Fragment {
    private String[] tabNames;
    private ITitlable iTitlable;

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
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

        TypeMuscle[] values = TypeMuscle.values();
        tabNames = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            tabNames[i] = getResources().getString(values[i].getName());
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_exercise, container, false);
        iTitlable.setTitle(getResources().getString(R.string.exercise_fragment_title));
        System.out.println(getResources().getString(R.string.exercise_fragment_title));
        final ViewPager viewPager = inflate.findViewById(R.id.exercise_fragment_viewpager);
        TabLayout tabLayout = inflate.findViewById(R.id.exercise_fragment_tab_layout);

        for (int i = 0; i < tabNames.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabNames[i]));
        }

        PagerAdapter pagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager(), tabNames.length);

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tabSelected)
            {
                viewPager.setCurrentItem(tabSelected.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tabSelected){}

            @Override
            public void onTabReselected(TabLayout.Tab tabSelected){

            }
        });

        return inflate;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
         iTitlable = (ITitlable) context;
    }
}
