package org.example.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.Views;
import org.example.entity.Coffee;
import org.example.service.CoffeeService;
import org.example.model.CoffeeType;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/coffee")
public class CoffeeController {

    private final CoffeeService coffeeService;

    @Operation(summary = "Создать новое кофе")
    @PostMapping("/order")
    @JsonView(Views.Summary.class)
    public Coffee postCoffee(@RequestParam CoffeeType type,@RequestParam long sugarSize) {
        log.info("CoffeeController.postCoffee()");
        return coffeeService.orderCoffee(type, sugarSize);
    }

    @Operation(summary = "Получить кофе по id")
    @GetMapping(value = "/get/{id}")
    @JsonView(Views.Summary.class)
    public Coffee getCoffeeId(@RequestParam long id)  {
        log.info("CoffeeController.getCoffeeId()");
        return coffeeService.getCoffeeId(id);
    }

    @Operation(summary = "Получить все кофе")
    @GetMapping(value = "/get")
    @JsonView(Views.Summary.class)
    public List<Coffee> getCoffeeAll(Pageable pageable)  {
        log.info("CoffeeController.getCoffeeAll()");
        return coffeeService.getAll(pageable);
    }

    @Operation(summary = "Пополнить кофемашину")
    @PostMapping("/fill_tanker")
    @JsonView(Views.Summary.class)
    public void postTanker(@RequestParam(defaultValue = "0") @PositiveOrZero(message = "Необходимо указать положительное значение") long tankWaterVolume,
                           @RequestParam(defaultValue = "0") @PositiveOrZero(message = "Необходимо указать положительное значение") long tankMilkVolume,
                           @RequestParam(defaultValue = "0") @PositiveOrZero(message = "Необходимо указать положительное значение") long tankSugar)  {
        log.info("CoffeeController.postTanker()");
        coffeeService.fillCoffeeMachine(tankWaterVolume,tankMilkVolume,tankSugar);
    }
}