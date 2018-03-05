package info.upump.jym.entity;

/**
 * Created by explo on 05.03.2018.
 */

class Sets {
    private long id;
    private double weight;
    private int reps;
    private Exercise exercise;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    @Override
    public String toString() {
        return "Sets{" +
                "id=" + id +
                ", weight=" + weight +
                ", reps=" + reps +
                ", exercise id=" + exercise.getId() +
                ", exercise title=" + exercise.getTitle() +
                '}';
    }
}
