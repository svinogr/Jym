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
    private boolean defaultType;
    private String image;
    private String defaultImg;

    private int getDaysBetweenDates() {
        //Todo getDaysBetweenDates
        return 0;
    }

    public String getDefaultImg() {
        return defaultImg;
    }

    public void setDefaultImg(String defaultImg) {
        this.defaultImg = defaultImg;
    }

    public boolean isDefaultType() {
        return defaultType;
    }

    public void setDefaultType(boolean defaultType) {
        this.defaultType = defaultType;
    }

    public List<Workout> getWorkoutList() {
        return workoutList;
    }

    public void setWorkoutList(List<Workout> workoutList) {
        this.workoutList = workoutList;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Cycle{" +
                "id" + id +
                "com " + comment +
                "title " + title +
                " startDate=" + getStartStringFormatDate() +
                ", finishDate=" + getFinishStringFormatDate() +
                ", userList=" + workoutList.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cycle)) return false;
       // if (!super.equals(o)) return false;

        Cycle cycle = (Cycle) o;

        if (id != cycle.id) return false;
        if (!title.equals(cycle.title)) return false;
        if (!getStartStringFormatDate().equals(cycle.getStartStringFormatDate())) return false;
        if (!getFinishStringFormatDate().equals(cycle.getFinishStringFormatDate())) return false;
        if (!comment.equals(cycle.getComment())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }
}
