package info.upump.jym.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class User {
    private final String formatDate = "yyyy-MM-dd";
    private long id;
    private String name;
    private double weight;
    private double fat;
    private double height;
    private double neck;
    private double pectoral;

    private double shoulder;
    private double leftBiceps;
    private double rightBiceps;

    private double abs;
    private double leftLeg;
    private double rightLeg;
    private double leftCalves;
    private double rightCalves;
    private Date date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getNeck() {
        return neck;
    }

    public void setNeck(double neck) {
        this.neck = neck;
    }

    public double getPectoral() {
        return pectoral;
    }

    public void setPectoral(double pectoral) {
        this.pectoral = pectoral;
    }

    public double getShoulder() {
        return shoulder;
    }

    public void setShoulder(double shoulder) {
        this.shoulder = shoulder;
    }

    public double getLeftBiceps() {
        return leftBiceps;
    }

    public void setLeftBiceps(double leftBiceps) {
        this.leftBiceps = leftBiceps;
    }

    public double getRightBiceps() {
        return rightBiceps;
    }

    public void setRightBiceps(double rightBiceps) {
        this.rightBiceps = rightBiceps;
    }

    public double getAbs() {
        return abs;
    }

    public void setAbs(double abs) {
        this.abs = abs;
    }

    public double getLeftLeg() {
        return leftLeg;
    }

    public void setLeftLeg(double leftLeg) {
        this.leftLeg = leftLeg;
    }

    public double getRightLeg() {
        return rightLeg;
    }

    public void setRightLeg(double rightLeg) {
        this.rightLeg = rightLeg;
    }

    public double getLeftCalves() {
        return leftCalves;
    }

    public void setLeftCalves(double leftCalves) {
        this.leftCalves = leftCalves;
    }

    public double getRightCalves() {
        return rightCalves;
    }

    public void setRightCalves(double rightCalves) {
        this.rightCalves = rightCalves;
    }

    public String getStringFormatDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate, Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    public void setDate(Date startDate) {
        this.date = startDate;
    }

    public void setDate(String stringDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate, Locale.getDefault());
        try {
            this.date = simpleDateFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "User{" +
                "formatDate='" + formatDate + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", fat=" + fat +
                ", height=" + height +
                ", neck=" + neck +
                ", pectoral=" + pectoral +
                ", shoulder=" + shoulder +
                ", leftBiceps=" + leftBiceps +
                ", rightBiceps=" + rightBiceps +
                ", abs=" + abs +
                ", leftLeg=" + leftLeg +
                ", rightLeg=" + rightLeg +
                ", leftCalves=" + leftCalves +
                ", rightCalves=" + rightCalves +
                ", date=" + date +
                '}';
    }
}
