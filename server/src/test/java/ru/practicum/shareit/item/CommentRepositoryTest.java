package ru.practicum.shareit.item;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.comment.CommentRepository;
import ru.practicum.shareit.comment.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.util.ArrayList;
import java.util.List;


@DataJpaTest
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;

    private List<Item> itemList;
    private List<Comment> commentList;

    @BeforeEach
    void beforeEachTest() {
        User user = new User();
        User booker = new User();
        Item item = new Item();
        Comment comment = new Comment();

        user.setId(1L);
        user.setName("new_user");
        user.setEmail("new_user@example.com");

        booker.setId(2L);
        booker.setName("booker");
        booker.setEmail("booker@example.com");

        item.setId(1L);
        item.setOwner(user);
        item.setName("new_item");
        item.setDescription("description_new_item");

        comment.setId(1L);
        comment.setText("comment text");
        comment.setAuthor(booker);
        comment.setItem(item);

        itemList = new ArrayList<>();
        itemList.add(item);

        commentList = new ArrayList<>();
        commentList.add(comment);
    }

    @Test
    void findAllByItemIdTest() {
        List<Comment> commentDataList = commentRepository.findByItem_id(1);

        Assertions.assertNotNull(commentDataList);
        Assertions.assertEquals(1, commentDataList.size());
        Assertions.assertEquals(commentList.get(0).getId(), commentDataList.get(0).getId());
    }


}