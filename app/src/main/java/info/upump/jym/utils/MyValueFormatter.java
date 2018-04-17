package info.upump.jym.utils;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;


public class MyValueFormatter implements IValueFormatter {

    public MyValueFormatter() {
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        // write your logic here
        return String.valueOf(value); // e.g. append a dollar-sign
    }
}