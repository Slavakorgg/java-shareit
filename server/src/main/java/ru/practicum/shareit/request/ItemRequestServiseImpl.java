package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.NewItemRequestDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemRequestServiseImpl implements ItemRequestService {
    private final ItemRequestRepository requestRepository;
    private final UserRepository userRepository;

    @Override
    public ItemRequestDto create(NewItemRequestDto newItemRequestDto, long userId) {
        ItemRequest itemRequest = RequestMapper.toModel(newItemRequestDto);
        itemRequest.setRequestor(getUserById(userId));
        return RequestMapper.toDto(requestRepository.save(itemRequest));
    }

    @Override
    public List<ItemRequestDto> getByUser(long id) {
        getUserById(id);
        List<ItemRequest> requests = requestRepository.getAllByRequestorId(id);
        return requests.stream()
                .map(RequestMapper::toDto)
                .sorted(Comparator.comparing(ItemRequestDto::getCreated).reversed())
                .toList();
    }

    @Override
    public ItemRequestDto getById(long id) {
        return RequestMapper.toDto(getRequestById(id));
    }


    private User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + id + " не найден"));
    }

    private ItemRequest getRequestById(long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Запрос вещи с id = " + id + " не найден"));

    }
}
