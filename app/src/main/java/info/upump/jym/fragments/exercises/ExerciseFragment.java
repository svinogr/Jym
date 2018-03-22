package info.upump.jym.fragments.exercises;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.upump.jym.IControlFragment;
import info.upump.jym.ITitlable;
import info.upump.jym.R;
import info.upump.jym.activity.exercise.ExerciseDetailActivityEdit;
import info.upump.jym.adapters.PagerAdapter;
import info.upump.jym.entity.Exercise;
import info.upump.jym.entity.TypeMuscle;

public class ExerciseFragment extends Fragment implements TabLayout.OnTabSelectedListener, View.OnClickListener {
    private String[] tabNames;
    private ITitlable iTitlable;
    private TypeMuscle[] values;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PagerAdapter pagerAdapter;
    private IControlFragment iControlFragment;
    private static final int ICON_FAB = R.drawable.ic_add_black_24dp;
    private FloatingActionButton mainFab;

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
        mainFab = getActivity().findViewById(R.id.main_fab);
        mainFab.setOnClickListener(this);
       // setIconFab();

        iTitlable.setTitle(getResources().getString(R.string.exercise_fragment_title));

        viewPager = inflate.findViewById(R.id.exercise_fragment_viewpager);
        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View view, float position) {
                int pageWidth = view.getWidth();
                int pageHeight = view.getHeight();
                final float MIN_SCALE = 0.85f;
                float MIN_ALPHA = 0.5f;

                if (position < -1)

                { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    view.setAlpha(0);

                } else if (position <= 1)

                { // [-1,1]
                    // Modify the default slide transition to shrink the page as well
                    float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                    float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                    float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                    if (position < 0) {
                        view.setTranslationX(horzMargin - vertMargin / 2);
                    } else {
                        view.setTranslationX(-horzMargin + vertMargin / 2);
                    }

                    // Scale the page down (between MIN_SCALE and 1)
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);

                    // Fade the page relative to its size.
                    view.setAlpha(MIN_ALPHA +
                            (scaleFactor - MIN_SCALE) /
                                    (1 - MIN_SCALE) * (1 - MIN_ALPHA));

                } else

                { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    view.setAlpha(0);
                }
            }


        });
        tabLayout = inflate.findViewById(R.id.exercise_fragment_tab_layout);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return inflate;
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
        Exercise exercise = new Exercise();
//        ДОБАВЛЯЕМ МУСКУЛ ЧТОБЫ ЗНАТЬ В КАКУЮ ГРУППУ СОХРАНИТЬ
        exercise.setTypeMuscle(values[viewPager.getCurrentItem()]);
        Intent intent = ExerciseDetailActivityEdit.createIntent(getContext(), exercise);
        startActivity(intent);
//        startActivityForResult(intent, ExerciseDetailActivityEdit.REQUEST_CODE_FOR_NEW_EXERCISE);
    }


}
