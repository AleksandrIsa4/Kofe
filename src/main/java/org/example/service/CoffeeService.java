package org.example.service;

import org.example.entity.Coffee;
import org.example.model.CoffeeType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CoffeeService {

    Coffee getCoffeeId(long id);

    List<Coffee> getAll(Pageable pageable);

    Coffee orderCoffee(CoffeeType type, long sugarSize);

    void fillCoffeeMachine(long tankWaterVolume,long tankMilkVolume,long tankSugar);
}
