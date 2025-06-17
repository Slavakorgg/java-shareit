package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.request.ItemRequest;

@Data
@Builder
public class NewItemDto {
    @NotBlank(message = "название предмета не может быть пустым")
    private String name;

    @NotBlank(message = "описание не должно быть пустым")
    private String description;

    @NotNull(message = "не установлен статус доступности бронирования")
    private Boolean available;

    private ItemRequest request;
}
