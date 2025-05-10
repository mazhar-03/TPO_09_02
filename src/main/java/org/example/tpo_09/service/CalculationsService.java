package org.example.tpo_09.service;


import org.example.tpo_09.model.BmiDto;
import org.example.tpo_09.model.BmrDto;
import org.springframework.stereotype.Service;

@Service
public class CalculationsService {

    public BmiDto calculateBmi(double weight, double height) {
        if (weight <= 0 || height <= 0) {
            throw new IllegalArgumentException(
                    "invalid data, weight and height parameters must be positive numbers"
            );
        }
        double h = height / 100.0;
        double rawBmi = weight / (h * h);
        int bmiInt = (int) rawBmi;
        String type = decision(rawBmi);

        BmiDto b = new BmiDto();
        b.setWeight(weight);
        b.setHeight(height);
        b.setBmi(bmiInt);
        b.setType(type);
        return b;
    }

    public double bmiPlain(double weight, double height) {
        // for text/plain: two-decimal format
        double h = height / 100.0;
        return weight / (h * h);
    }

    public BmrDto calculateBmr(
            String sex, double weight, double height, int age
    ) {
        if (weight <= 0 || height <= 0 || age <= 0) {
            throw new IllegalStateException(
                    "invalid data, weight, height and age parameters must be positive numbers"
            );
        }
        double raw = switch (sex.toLowerCase()) {
            case "man" -> 88.362 + 13.397 * weight
                    + 4.799 * height
                    - 5.677 * age;
            case "woman" -> 447.593 + 9.247 * weight
                    + 3.098 * height
                    - 4.330 * age;
            default -> throw new UnsupportedOperationException("invalid gender data");
        };
        int bmrInt = (int) raw;
        BmrDto b = new BmrDto();
        b.setGender(sex);
        b.setWeight(weight);
        b.setHeight(height);
        b.setAge(age);
        b.setBmr(bmrInt);
        return b;
    }

    public int bmrPlain(String gender, double weight, double height, int age) {
        return calculateBmr(gender, weight, height, age).getBmr();
    }

    private String decision(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 25) {
            return "Normal";
        } else if (bmi < 30) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }
}
