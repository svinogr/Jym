package info.upump.jym.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by explo on 05.03.2018.
 */

public class Workout extends Entity {
    private boolean weekEven;
    private boolean defaultType;
    private boolean template;
    private Day day;
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

    public boolean isTemplate() {
        return template;
    }

    public void setTemplate(boolean template) {
        this.template = template;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }


    @Override
    public String toString() {
        return "Workout{" +
                "id=" + id +
                "com " + comment+
                ", title='" + title + '\'' +
                ", weekEven=" + weekEven +
                ", Day=" + day +
                ", defaultType=" + defaultType +
                ", cycle Id= " + parentId +
                ", exercises Size=" + exercises.size() +
                '}';
    }

}
