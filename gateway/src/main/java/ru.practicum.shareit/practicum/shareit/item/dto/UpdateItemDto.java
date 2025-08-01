package ru.practicum.shareit.practicum.shareit.item.dto;

import lombok.Data;

@Data
public class UpdateItemDto {
    private String name;
    private String description;
    private Boolean available;

    public boolean hasName() {
        return !(name == null || name.isBlank());
    }

    public boolean hasDescription() {
        return !(description == null || description.isBlank());
    }

    public boolean hasAvailable() {
        return Boolean.FALSE.equals(available);
    }
}
