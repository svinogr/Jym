package info.upump.jym.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.upump.jym.ITitleble;
import info.upump.jym.R;
public class PravoFragment extends Fragment {
    protected ITitleble iTitleble;

    public PravoFragment() {
        // Required empty public constructor
    }

    protected void setTitle() {
        iTitleble.setTitle(getResources().getString(R.string.pref_header_pravo));
    }


    public static PravoFragment newInstance() {
        PravoFragment fragment = new PravoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle();
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pravo, container, false);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iTitleble = (ITitleble) context;
    }

}
