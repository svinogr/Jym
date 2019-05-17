package info.upump.jym.entity;

import java.util.ArrayList;
import java.util.List;

public class Cycle extends Entity {
    private List<Workout> workoutList = new ArrayList<>();
    private boolean defaultType;
    private String image;
    private String defaultImg;

    private int getDaysBetweenDates() {
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
        Cycle cycle = (Cycle) o;

        if (id != cycle.id) return false;
        if (!title.equals(cycle.title)) return false;
        if (!getStartStringFormatDate().equals(cycle.getStartStringFormatDate())) return false;
        if (!getFinishStringFormatDate().equals(cycle.getFinishStringFormatDate())) return false;
        return comment.equals(cycle.getComment());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }
}
