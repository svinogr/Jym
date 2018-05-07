package info.upump.jym.fragments.exercises;


import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Date;

import javax.xml.transform.Result;

import info.upump.jym.IControlFragment;
import info.upump.jym.ITitleble;
import info.upump.jym.R;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.activity.exercise.ExerciseCreateActivity;
import info.upump.jym.adapters.PagerAdapterExercise;
import info.upump.jym.bd.ExerciseDao;
import info.upump.jym.entity.Exercise;
import info.upump.jym.entity.ExerciseDescription;
import info.upump.jym.entity.TypeMuscle;
import info.upump.jym.fragments.cycle.CRUD;

import static info.upump.jym.activity.constant.Constants.CLEAR;
import static info.upump.jym.activity.constant.Constants.CREATE;
import static info.upump.jym.activity.constant.Constants.DEFAULT_IMAGE;
import static info.upump.jym.activity.constant.Constants.DEFAULT_TYPE_ITEM;
import static info.upump.jym.activity.constant.Constants.DELETE;
import static info.upump.jym.activity.constant.Constants.DESCRIPTION;
import static info.upump.jym.activity.constant.Constants.ID;
import static info.upump.jym.activity.constant.Constants.IMAGE;
import static info.upump.jym.activity.constant.Constants.REQUEST_CODE_CHANGE_OPEN;
import static info.upump.jym.activity.constant.Constants.REQUEST_CODE_CREATE;
import static info.upump.jym.activity.constant.Constants.TEMPLATE_TYPE_ITEM;
import static info.upump.jym.activity.constant.Constants.TITLE;
import static info.upump.jym.activity.constant.Constants.TYPE_MUSCLE;
import static info.upump.jym.activity.constant.Constants.UPDATE;
import static info.upump.jym.activity.constant.Constants.UPDATE_DELETE;

public class ExerciseFragment extends Fragment implements TabChanger{
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
        super.onCreate(savedInstanceState);
        pagerAdapter = new PagerAdapterExercise(getActivity().getSupportFragmentManager(), getContext(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_exercise, container, false);
        iTitlable.setTitle(getResources().getString(R.string.exercise_fragment_title));
        viewPager = inflate.findViewById(R.id.exercise_fragment_viewpager);
        tabLayout = inflate.findViewById(R.id.exercise_fragment_tab_layout);
//        addFab = inflate.findViewById(R.id.exercise_fragment_add_fab);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        setPageTransform();
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
//        iControlFragment = (IControlFragment) context;
    }


    @Override
    public void setToFinalPositionRecyclerView() {
        System.out.println("dugwdgwildgwgidigwgdf");
    }
}
