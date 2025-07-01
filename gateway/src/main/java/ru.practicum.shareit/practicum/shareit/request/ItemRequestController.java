package ru.practicum.shareit.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.practicum.shareit.request.dto.NewItemRequestDto;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {
    private final ItemRequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody NewItemRequestDto newItemRequestDto,
                                 @RequestHeader("X-Sharer-User-Id") Long userId){
        return requestClient.create(newItemRequestDto,userId);
    }

    @GetMapping
    public ResponseEntity<Object> getRequestsByUser(@RequestHeader("X-Sharer-User-Id") Long userId){
        return requestClient.getByUser(userId);
    }
    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequestById(@PathVariable Long requestId) {
        return requestClient.getById(requestId);
    }

}
