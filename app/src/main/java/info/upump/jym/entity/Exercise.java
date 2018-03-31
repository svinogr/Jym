package info.upump.jym.entity;

import java.util.AbstractList;
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
    private String img;
    private String description;

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isTemplate() {
        return template;
    }

    public void setTemplate(boolean template) {
        this.template = template;
    }

    public String creteInfo(){
        return "инфо";
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "template"+ template+
                ", desc "+ description+
                ", typeMuscle=" + typeMuscle +
                ", defaultType=" + defaultType +
                ", setsList=" + setsList.size() +
                ", img='" + img + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                ", parentId=" + parentId +
                '}';
    }
}
