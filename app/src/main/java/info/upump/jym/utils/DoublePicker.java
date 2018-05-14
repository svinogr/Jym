package info.upump.jym.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.NumberPicker;

/**
 * Created by explo on 05.04.2018.
 */

public class DoublePicker extends NumberPicker {
    public DoublePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setFormatter(Formatter formatter) {
        super.setFormatter(formatter);
    }
}
