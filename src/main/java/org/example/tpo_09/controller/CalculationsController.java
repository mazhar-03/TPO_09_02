package org.example.tpo_09.controller;

import org.example.tpo_09.exceptions.InvalidDataException;
import org.example.tpo_09.exceptions.InvalidGenderException;
import org.example.tpo_09.model.BmiDto;
import org.example.tpo_09.model.BmrDto;
import org.example.tpo_09.service.CalculationsService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculations")
public class CalculationsController {

    private final CalculationsService calcService;

    public CalculationsController(CalculationsService calcService) {
        this.calcService = calcService;
    }

    @GetMapping(
            value    = "/BMI",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.TEXT_PLAIN_VALUE
            }
    )
    public ResponseEntity<?> getBmi(
            @RequestParam double weight,
            @RequestParam double height,
            @RequestHeader(
                    name         = "Accept",
                    defaultValue = MediaType.APPLICATION_JSON_VALUE
            )
            String accept
    ) {
        // Plain-text
        if (accept.contains(MediaType.TEXT_PLAIN_VALUE)) {
            // bmiPlain throws my custom InvalidDataException if invalid
            double raw = calcService.bmiPlain(weight, height);
            String formatted = String.format("%.2f", raw);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(formatted);
        }

        // JSON / XML
        BmiDto dto = calcService.calculateBmi(weight, height);
        MediaType contentType = accept.contains(MediaType.APPLICATION_XML_VALUE)
                ? MediaType.APPLICATION_XML
                : MediaType.APPLICATION_JSON;
        return ResponseEntity
                .ok()
                .contentType(contentType)
                .body(dto);
    }

    @GetMapping(
            value    = "/BMR/{gender}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.TEXT_PLAIN_VALUE
            }
    )
    public ResponseEntity<?> getBmr(
            @PathVariable String gender,
            @RequestParam  double weight,
            @RequestParam  double height,
            @RequestParam  int    age,
            @RequestHeader(
                    name         = "Accept",
                    defaultValue = MediaType.APPLICATION_JSON_VALUE
            )
            String accept
    ) {
        // service.calculateBmr will throw my custom exceptions
        // Plain-text
        if (accept.contains(MediaType.TEXT_PLAIN_VALUE)) {
            int bmr = calcService.bmrPlain(gender, weight, height, age);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(bmr + " kcal");
        }

        // JSON / XML
        BmrDto dto = calcService.calculateBmr(gender, weight, height, age);
        MediaType contentType = accept.contains(MediaType.APPLICATION_XML_VALUE)
                ? MediaType.APPLICATION_XML
                : MediaType.APPLICATION_JSON;
        return ResponseEntity
                .ok()
                .contentType(contentType)
                .body(dto);
    }
}
