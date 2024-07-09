package org.example.entity.coffe;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.entity.Coffee;

@AllArgsConstructor
@SuperBuilder
@Entity
@DiscriminatorValue("ESPRESSO")
public class Espresso extends Coffee {
}