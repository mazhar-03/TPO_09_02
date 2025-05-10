package org.example.tpo_09.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@JacksonXmlRootElement(localName = "BmiResult")
@XmlRootElement(name = "BmiResult")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BmiDto {
    private double weight;
    private double height;
    private int bmi;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBmi() {
        return bmi;
    }

    public void setBmi(int bmi) {
        this.bmi = bmi;
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

    private String type;

}
