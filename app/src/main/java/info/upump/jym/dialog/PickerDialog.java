package info.upump.jym.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import java.util.Arrays;

import info.upump.jym.R;
import info.upump.jym.activity.user.IPicable;

/**
 * Created by explo on 10.04.2018.
 */

public class PickerDialog extends DialogFragment implements View.OnClickListener {
    private NumberPicker numberPicker;
    private IPicable iPicable;
    private Button cancel, yes;
    private String[] value;
    public static String VALUE = "value";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        double min = 0;
        int max = 200;
        double step = 0.5;
        value = getArrayWithSteps(min, max, step);
        int valueStart =0;
        if(arguments.getString(VALUE) !=null){
            valueStart = Arrays.asList(value).indexOf(String.valueOf(arguments.getString(VALUE)));
        }
        View inflate = inflater.inflate(R.layout.picker_dialog, container, false);
        numberPicker = inflate.findViewById(R.id.picker_dialog_number_picker);
        cancel = inflate.findViewById(R.id.picker_dialog_cancel);
        yes = inflate.findViewById(R.id.picker_dialog_save);


        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(200);

        numberPicker.setDisplayedValues(value);
        numberPicker.setValue(valueStart);
        cancel.setOnClickListener(this);
        yes.setOnClickListener(this);
        return inflate;
    }


    @Override
    public void onClick(View v) {
        iPicable = (IPicable) getActivity();

        System.out.println(numberPicker.getValue());
        switch (v.getId()) {
            case R.id.picker_dialog_save:
                iPicable.setPicker(Double.parseDouble(value[numberPicker.getValue()]));
                dismiss();
                break;
            case R.id.picker_dialog_cancel:
                dismiss();
                break;
        }

    }

    public String[] getArrayWithSteps(double iMinValue, int iMaxValue, double iStep) {
        int iStepsArray = 800; //get the lenght array that will return

        String[] arrayValues = new String[iStepsArray]; //Create array with length of iStepsArray

        for (int i = 0; i < iStepsArray; i++) {
            arrayValues[i] = String.valueOf(iMinValue + (i * iStep));
        }

        return arrayValues;
    }
}
