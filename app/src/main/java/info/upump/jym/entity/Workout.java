package info.upump.jym.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by explo on 05.03.2018.
 */

public class Workout extends Entity {
    private boolean weekEven;
    private boolean defaultType;
    private List<Exercise> exercises = new ArrayList<>();

    public boolean isWeekEven() {
        return weekEven;
    }

    public void setWeekEven(boolean weekEven) {
        this.weekEven = weekEven;
    }

    public boolean isDefaultType() {
        return defaultType;
    }

    public void setDefaultType(boolean defaultType) {
        this.defaultType = defaultType;
    }



    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", weekEven=" + weekEven +
                ", defaultType=" + defaultType +
                ", cycle Id= " + parentId +
                ", exercises Size=" + exercises.size() +
                '}';
    }
}
