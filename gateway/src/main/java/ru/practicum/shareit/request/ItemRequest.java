package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * TODO Sprint add-item-requests.
 */


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {

    private long id;
    private String description;
    private User requestor;
    private LocalDateTime created;
    @Builder.Default
    private List<Item> responsesList = new ArrayList<>();


}
