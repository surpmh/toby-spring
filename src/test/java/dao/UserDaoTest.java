package dao;

import domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {
    private UserDao userDao;
    private User user1;
    private User user2;
    private User user3;
    @Autowired
    ApplicationContext context;

    @BeforeEach
    void setUp() {
        this.userDao = context.getBean("awsUserDao", UserDao.class);
        this.user1 = new User("1", "name1", "1234");
        this.user2 = new User("2", "name2", "1234");
        this.user3 = new User("3", "name3", "1234");
    }

    @Test
    void addAndGetTest() throws SQLException {
        userDao.deleteAll();
        assertEquals(0, userDao.getCount());

        userDao.add(user1);
        assertEquals(1, userDao.getCount());

        User user = userDao.findById(user1.getId());
        assertEquals("name1", user.getName());
        assertEquals("1234", user.getPassword());
    }

    @Test
    void getCountTest() throws SQLException {
        User user1 = new User("1", "name1", "123");
        User user2 = new User("2", "name2", "123");
        User user3 = new User("3", "name3", "123");
        userDao.deleteAll();
        assertEquals(0, userDao.getCount());
        userDao.add(user1);
        assertEquals(1, userDao.getCount());
        userDao.add(user2);
        assertEquals(2, userDao.getCount());
        userDao.add(user3);
        assertEquals(3, userDao.getCount());
    }

    @Test
    void findByIdTest() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            userDao.findById("30");
        });
    }

    @Test
    void getAllTest() throws SQLException {
        userDao.deleteAll();
        List<User> users = userDao.getAll();
        assertEquals(0, users.size());
        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);
        users = userDao.getAll();
        assertEquals(3, users.size());
    }
}