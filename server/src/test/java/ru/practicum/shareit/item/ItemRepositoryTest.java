package ru.practicum.shareit.item;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.ArrayList;
import java.util.List;


@DataJpaTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    void beforeEachTest() {
        Item item = new Item();
        User user = new User();
        List<Item> itemList = new ArrayList<>();
        user.setId(1L);
        user.setName("new_user");
        user.setEmail("new_user@example.com");
        item.setOwner(user);
        item.setName("new_item");
        item.setDescription("description_new_item");
        itemList.add(item);
    }

    @Test
    void findAllByOwnerIdTest() {
        List<Item> itemDataList = itemRepository.findByOwner(userRepository.getById(1L));

        Assertions.assertNotNull(itemDataList);
        Assertions.assertEquals(1, itemDataList.size());
    }

    @Test
    void findAllByOwnerIdAndTextTest() {
        List<Item> itemDataList = itemRepository.searchItem("description_new_item");

        Assertions.assertNotNull(itemDataList);
        Assertions.assertEquals(1, itemDataList.size());

    }
}