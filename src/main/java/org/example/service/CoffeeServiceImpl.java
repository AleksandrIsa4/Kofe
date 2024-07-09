package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exceptions.BadRequestException;
import org.example.exceptions.DataNotFoundException;
import org.example.entity.Coffee;
import org.example.model.CoffeeType;
import org.example.entity.coffe.Americano;
import org.example.entity.coffe.CaffeLatte;
import org.example.entity.coffe.Cappuccino;
import org.example.entity.coffe.Espresso;
import org.example.repository.CoffeeRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CoffeeServiceImpl implements CoffeeService {

    private final CoffeeRepository coffeeRepository;

    private static volatile long tankWaterVolume = 1000L;

    private static volatile long tankMilkVolume = 500L;

    private static volatile long tankSugar = 50L;

    @Override
    public Coffee getCoffeeId(long id){
        return coffeeRepository.findById(id).orElseThrow(()-> new DataNotFoundException("Нет данных с id ="+id));
    }

    @Override
    public List<Coffee> getAll(Pageable pageable){
        return coffeeRepository.findAllBy(pageable);
    }

    private void makeCoffee(Coffee coffee) {
        tankWaterVolume-= coffee.getWaterSize();
        tankMilkVolume-= coffee.getMilkSize();
        tankSugar-= coffee.getSugarSize();
        System.out.println(coffee.getClass().getSimpleName()+" is made");
    }

    @Override
    public Coffee orderCoffee(CoffeeType type, long sugarSize) {
        Coffee coffee = createCoffee(type);
        coffee.setSugarSize(sugarSize);
        checkTankVolume(coffee);
        makeCoffee(coffee);
        coffeeRepository.save(coffee);
        return coffee;
    }

    private void checkTankVolume(Coffee coffee) {
        String text=null;
        boolean checkAllTanks=false;
        if (coffee.getWaterSize() > tankWaterVolume) {
            checkAllTanks=true;
            text="Water tank is empty";
        }
        if (coffee.getMilkSize() > tankMilkVolume) {
            checkAllTanks=true;
            text+=" Milk tank is empty";
        }
        if (coffee.getSugarSize() > tankSugar) {
            checkAllTanks=true;
            text+=" Sugar tank is empty";
        }
        if(checkAllTanks){
            throw new BadRequestException(text);
        }
    }

    private Coffee createCoffee(CoffeeType type) {
        Coffee coffee = null;
        switch (type) {
            case ESPRESSO:
                coffee = Espresso.builder()
                        .milkSize(50L)
                        .waterSize(150L)
                        .build();
                break;
            case AMERICANO:
                coffee = Americano.builder()
                        .milkSize(100L)
                        .waterSize(100L)
                        .build();
                break;
            case CAFFE_LATTE:
                coffee = CaffeLatte.builder()
                        .milkSize(125L)
                        .waterSize(75L)
                        .build();
                break;
            case CAPPUCCINO:
                coffee = Cappuccino.builder()
                        .milkSize(75L)
                        .waterSize(125L)
                        .build();
                break;
        }
        coffee.setCreatedOn(LocalDateTime.now());
        coffee.setCoffeeType(type);
        return coffee;
    }

    @Override
    public void fillCoffeeMachine(long tankWaterVolume,long tankMilkVolume,long tankSugar){
        this.tankWaterVolume+=tankWaterVolume;
        this.tankMilkVolume+=tankMilkVolume;
        this.tankSugar+=tankSugar;
    }
}
