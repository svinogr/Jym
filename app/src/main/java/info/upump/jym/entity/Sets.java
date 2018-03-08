package info.upump.jym.entity;

/**
 * Created by explo on 05.03.2018.
 */

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
                "id=" + id +
                ", weight=" + weight +
                ", reps=" + reps +
                ", exercise id=" +parentId+
                '}';
    }
}
