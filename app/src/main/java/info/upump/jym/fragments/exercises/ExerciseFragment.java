package info.upump.jym.fragments.exercises;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.upump.jym.IControlFragment;
import info.upump.jym.ITitleble;
import info.upump.jym.R;
import info.upump.jym.activity.exercise.ExerciseCreateActivity;
import info.upump.jym.adapters.PagerAdapterExercise;
import info.upump.jym.entity.Exercise;

public class ExerciseFragment extends Fragment implements View.OnClickListener {
    private ITitleble iTitlable;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PagerAdapterExercise pagerAdapter;
    private IControlFragment iControlFragment;
    private static final int ICON_FAB = R.drawable.ic_add_black_24dp;
    private FloatingActionButton addFab;
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
        pagerAdapter = new PagerAdapterExercise(getActivity().getSupportFragmentManager(), getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_exercise, container, false);
        System.out.println("onCreateView Exerso");

        iTitlable.setTitle(getResources().getString(R.string.exercise_fragment_title));
        viewPager = inflate.findViewById(R.id.exercise_fragment_viewpager);
        tabLayout = inflate.findViewById(R.id.exercise_fragment_tab_layout);
        addFab = inflate.findViewById(R.id.exercise_fragment_add_fab);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        setPageTransform();
        addFab.setOnClickListener(this);
        return inflate;
    }

    private void setPageTransform() {
        viewPager.setPageTransformer(
                false, new ViewPager.PageTransformer() {
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
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iTitlable = (ITitleble) context;
        iControlFragment = (IControlFragment) context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exercise_fragment_add_fab:
                Intent intent =  ExerciseCreateActivity.createIntent(getContext());
                startActivity(intent);
        }
    }
}
