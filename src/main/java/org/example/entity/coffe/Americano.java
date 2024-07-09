package org.example.entity.coffe;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.entity.Coffee;

@AllArgsConstructor
@SuperBuilder
@Entity
@DiscriminatorValue("AMERICANO")
public class Americano extends Coffee {
}