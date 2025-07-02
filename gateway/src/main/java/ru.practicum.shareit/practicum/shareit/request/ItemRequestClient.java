package ru.practicum.shareit.practicum.shareit.request;


import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.shareit.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.practicum.shareit.request.dto.NewItemRequestDto;


public class ItemRequestClient extends BaseClient {

    public ItemRequestClient(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public ResponseEntity<Object> create(NewItemRequestDto newItemRequestDto, Long userId) {
        return post("", userId, newItemRequestDto);
    }

    public ResponseEntity<Object> getByUser(Long userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> getById(Long requestId) {
        return get("/" + requestId);
    }
}
