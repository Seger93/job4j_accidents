package ru.job4j.accidents.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class Rule {
    @EqualsAndHashCode.Include
    private int id;

    private String name;
}