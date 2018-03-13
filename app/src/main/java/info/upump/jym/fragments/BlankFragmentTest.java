package info.upump.jym.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import info.upump.jym.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragmentTest#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragmentTest extends Fragment {

    String s;
    // TODO: Rename and change types of parameters


    public BlankFragmentTest() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static BlankFragmentTest newInstance(String s) {
        BlankFragmentTest fragment = new BlankFragmentTest();
        Bundle args = new Bundle();
        args.putString("pa", s);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
         s = getArguments().getString("pa");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_blank_fragment_test, container, false);
        TextView textView = inflate.findViewById(R.id.blank_text);
        textView.setText(s);
        return inflate;
    }

}
