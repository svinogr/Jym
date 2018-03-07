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
    private final String formatDate = "yyyy-MM-dd";
    private Date startDate;
    private Date finishDate;
    private List<Workout> workoutList = new ArrayList<>();

    private int getDaysBetweenDates() {
        //Todo getDaysBetweenDates
        return 0;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getStartStringFormatDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate, Locale.getDefault());
        return simpleDateFormat.format(startDate);
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setStartDate(String stringDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate, Locale.getDefault());
        try {
            this.startDate = simpleDateFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public String getFinishStringFormatDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate, Locale.getDefault());
        return simpleDateFormat.format(finishDate);
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public void setFinishDate(String stringDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate, Locale.getDefault());
        try {
            this.finishDate = simpleDateFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
