package info.upump.jym.entity;

public class Sets extends Entity {
    private double weight;
    private int reps;

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
