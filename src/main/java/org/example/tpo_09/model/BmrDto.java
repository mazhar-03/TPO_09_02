package org.example.tpo_09.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@JacksonXmlRootElement(localName = "BmrResult")
@XmlRootElement(name = "BmrResult")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BmrDto {
    private String gender;

    public int getBmr() {
        return bmr;
    }

    public void setBmr(int bmr) {
        this.bmr = bmr;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    private double weight;
    private double height;
    private int age;
    private int bmr;

}