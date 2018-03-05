package info.upump.jym.entity;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by explo on 05.03.2018.
 */

public class Exercise extends Entity {
    private TypeMuscle typeMuscle;
    private Workout workout;
    private List<Sets> setsList = new ArrayList<>();

    public TypeMuscle getTypeMuscle() {
        return typeMuscle;
    }

    public void setTypeMuscle(TypeMuscle typeMuscle) {
        this.typeMuscle = typeMuscle;
    }


    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public List<Sets> getSetsList() {
        return setsList;
    }

    public void setSetsList(List<Sets> setsList) {
        this.setsList = setsList;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", title = '" + title + '\'' +
                ", typeMuscle = " + typeMuscle.getName() +
                ", workout id = " + workout.getId() +
                ", workout title = " + workout.getTitle() +
                ", setsListSize = " + setsList.size() +
                '}';
    }
}
