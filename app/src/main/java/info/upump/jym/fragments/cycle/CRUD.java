package info.upump.jym.fragments.cycle;

import android.app.ActivityOptions;
import android.content.Intent;

import info.upump.jym.entity.Cycle;

/**
 * Created by explo on 03.05.2018.
 */

public interface CRUD<T> {
  void createIntentForResult(ActivityOptions activityOptions, T object);
}
