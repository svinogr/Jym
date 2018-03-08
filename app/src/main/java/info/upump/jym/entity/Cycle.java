package info.upump.jym.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by explo on 05.03.2018.
 */

public class Cycle extends Entity {
    private List<Workout> workoutList = new ArrayList<>();

    private int getDaysBetweenDates() {
        //Todo getDaysBetweenDates
        return 0;
    }

    public List<Workout> getWorkoutList() {
        return workoutList;
    }

    public void setWorkoutList(List<Workout> workoutList) {
        this.workoutList = workoutList;
    }

    @Override
    public String toString() {
        return "Cycle{" +
                "startDate=" + startDate +
                ", finishDate=" + finishDate +
                ", workoutList=" + workoutList.size() +
                '}';
    }
}
