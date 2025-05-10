package org.example.tpo_09.controller;

import org.example.tpo_09.model.BmiDto;
import org.example.tpo_09.model.BmrDto;
import org.example.tpo_09.service.CalculationsService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
            String mimeType
    ) {
        MediaType jsonType  = MediaType.APPLICATION_JSON;
        MediaType xmlType   = MediaType.APPLICATION_XML;
        MediaType plainType = MediaType.TEXT_PLAIN;

        // plain-text response
        if (mimeType.contains(plainType.toString())) {
            Optional<Double> resultOpt = (weight > 0 && height > 0)
                    ? Optional.of(calcService.bmiPlain(weight, height))
                    : Optional.empty();

            return resultOpt
                    .map(value -> ResponseEntity
                            .ok()
                            .contentType(plainType)
                            .body(String.format("%.2f", value)))
                    .orElseGet(() -> ResponseEntity
                            .badRequest()
                            .header("Reason",
                                    "invalid data, weight and height parameters must be positive numbers")
                            .build());
        }

        // JSON or XML response
        MediaType responseType = mimeType.contains(xmlType.toString()) ? xmlType : jsonType;
        Optional<BmiDto> dtoOpt = (weight > 0 && height > 0)
                ? Optional.of(calcService.calculateBmi(weight, height))
                : Optional.empty();

        return dtoOpt
                .map(dto -> ResponseEntity
                        .ok()
                        .contentType(responseType)
                        .body(dto))
                .orElseGet(() -> ResponseEntity
                        .badRequest()
                        .header("Reason",
                                "invalid data, weight and height parameters must be positive numbers")
                        .build());
    }

    @GetMapping(
            value    = "/BMR/{sex}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.TEXT_PLAIN_VALUE
            }
    )
    public ResponseEntity<?> getBmr(
            @PathVariable("sex") String sexParam,
            @RequestParam             double weight,
            @RequestParam             double height,
            @RequestParam             int    age,
            @RequestHeader(
                    name         = "Accept",
                    defaultValue = MediaType.APPLICATION_JSON_VALUE
            )
            String mimeType
    ) {
        MediaType jsonType  = MediaType.APPLICATION_JSON;
        MediaType xmlType   = MediaType.APPLICATION_XML;
        MediaType plainType = MediaType.TEXT_PLAIN;

        // gender must be "man" or "woman"
        if (!"man".equalsIgnoreCase(sexParam) && !"woman".equalsIgnoreCase(sexParam)) {
            return ResponseEntity
                    .badRequest()
                    .header("Reason", "invalid gender data")
                    .build();
        }

        // plain-text response
        if (mimeType.contains(plainType.toString())) {
            Optional<Integer> resultOpt = (weight > 0 && height > 0 && age > 0)
                    ? Optional.of(calcService.bmrPlain(sexParam, weight, height, age))
                    : Optional.empty();

            return resultOpt
                    .map(value -> ResponseEntity
                            .ok()
                            .contentType(plainType)
                            .body(value + " kcal"))
                    .orElseGet(() -> ResponseEntity
                            .status(499)
                            .header("Reason",
                                    "invalid data, weight, height and age parameters must be positive numbers")
                            .build());
        }

        // JSON or XML response
        MediaType responseType = mimeType.contains(xmlType.toString()) ? xmlType : jsonType;
        Optional<BmrDto> dtoOpt = (weight > 0 && height > 0 && age > 0)
                ? Optional.of(calcService.calculateBmr(sexParam, weight, height, age))
                : Optional.empty();

        return dtoOpt
                .map(dto -> ResponseEntity
                        .ok()
                        .contentType(responseType)
                        .body(dto))
                .orElseGet(() -> ResponseEntity
                        .status(499)
                        .header("Reason",
                                "invalid data, weight, height and age parameters must be positive numbers")
                        .build());
    }
}
