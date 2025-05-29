package ru.practicum.shareit.item.dto;

import lombok.*;
import org.springframework.validation.annotation.Validated;


/**
 * TODO Sprint add-controllers.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Validated
public class ItemDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;

}
