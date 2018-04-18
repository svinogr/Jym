package info.upump.jym.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by explo on 05.03.2018.
 */

public class Exercise extends Entity {
    private TypeMuscle typeMuscle;
    private boolean defaultType;
    private boolean template;
    private List<Sets> setsList = new ArrayList<>();
    private long descriptionId;
    private ExerciseDescription exerciseDescription;
    public Exercise() {
    }

    public ExerciseDescription getExerciseDescription() {
        return exerciseDescription;
    }

    public void setExerciseDescription(ExerciseDescription exerciseDescription) {
        this.exerciseDescription = exerciseDescription;
    }

    public long getDescriptionId() {
        return descriptionId;
    }

    public void setDescriptionId(long descriptionId) {
        this.descriptionId = descriptionId;
    }

    public TypeMuscle getTypeMuscle() {
        return typeMuscle;
    }

    public void setTypeMuscle(TypeMuscle typeMuscle) {
        this.typeMuscle = typeMuscle;
    }

    public List<Sets> getSetsList() {
        return setsList;
    }

    public void setSetsList(List<Sets> setsList) {
        this.setsList = setsList;
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

    public String createInfo(){
        return "инфо";
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "template"+ template+
                ", desc "+ descriptionId +
                ", typeMuscle=" + typeMuscle +
                ", defaultType=" + defaultType +
                ", setsList=" + setsList.size() +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                ", parentId=" + parentId +
                '}';
    }



}
