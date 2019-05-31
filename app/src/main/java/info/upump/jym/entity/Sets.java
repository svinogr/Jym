package info.upump.jym.entity;

public class Sets extends Entity {
    private double weight;
    private int reps;
    private double weightPast;

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

    public double getWeightPast() {
        return weightPast;
    }

    public void setWeightPast(double weightPast) {
        this.weightPast = weightPast;
    }

    @Override
    public String toString() {
        return "Sets{" +
                "weight=" + weight +
                ", reps=" + reps +
                ", formatDate='" + formatDate + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                ", parentId=" + parentId +
                '}';
    }
}
