package com.pflb.education.atexample.dto;

import java.util.Objects;

public class Car {
    public Integer id;
    public String mark;
    public EngineType engineType;
    public String model;
    public Double price;

    public enum EngineType{
        Diesel,
        CNG,
        Hydrogenic,
        Electric,
        PHEV,
        Gasoline
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", mark='" + mark + '\'' +
                ", engineType=" + engineType +
                ", model='" + model + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car =  (Car) o;
        return Integer.compare(car.id, id) == 0
                && Double.compare(car.price, price) == 0
                && mark.equals(car.mark)
                && engineType == car.engineType
                && model.equals(car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mark, engineType, model, price);
    }
}