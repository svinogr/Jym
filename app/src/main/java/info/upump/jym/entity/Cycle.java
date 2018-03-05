package info.upump.jym.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by explo on 05.03.2018.
 */

public class Cycle extends Entity {
    private Date startDate;
    private Date finishDate;
    private List<Workout> workoutList = new ArrayList<>();
    private int  getDaysBetweenDates(){
        //Todo getDaysBetweenDates
        return 0;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
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
                "id=" + id +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                ", workoutListSize=" + workoutList.size() +
                '}';
    }
}
