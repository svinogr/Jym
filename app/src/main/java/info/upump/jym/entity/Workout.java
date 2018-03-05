package info.upump.jym.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by explo on 05.03.2018.
 */

class Workout extends Entity {
    private boolean weekEven;
    private boolean defaultType;
    private Cycle cycle;
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

    public Cycle getCycle() {
        return cycle;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
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
                ", cycle Id= " + cycle.getId()+
                ", cycle Title= " + cycle.title +
                ", exercises Size=" + exercises.size() +
                '}';
    }
}
