package org.example.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.dto.Views;
import org.example.model.CoffeeType;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "coffee")
@DiscriminatorColumn(name="coffee_type")
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class Coffee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Summary.class)
    @Column(name = "id")
    Long id;

    @JsonView(Views.Summary.class)
    @Column(name = "cup_size")
    final long cupSize = 200;

    @Column(name = "milk_size")
    long milkSize;

    @JsonView(Views.Summary.class)
    @Column(name = "sugar_size")
    long sugarSize;

    @Column(name = "water_size")
    long waterSize;

    @JsonView(Views.Summary.class)
    @Column(name = "coffee_type",insertable = false,updatable = false)
    @Enumerated(EnumType.STRING)
    CoffeeType coffeeType;

    @JsonView(Views.Summary.class)
    @Column(name = "created_on")
    LocalDateTime createdOn;
}
