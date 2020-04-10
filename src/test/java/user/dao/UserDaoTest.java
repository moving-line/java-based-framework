package user.dao;

import core.jdbc.ConnectionManager;
import core.jdbc.JdbcTemplate;
import core.test.BaseTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import user.domain.User;

import java.util.List;

public class UserDaoTest extends BaseTest {
    @Before
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("db.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @Test
    public void crud() {
        User expected = new User("userId", "password", "name", "javajigi@email.com");
        UserDao userDao = new UserDao(new JdbcTemplate());
        userDao.insert(expected);
        User actual = userDao.findByUserId(expected.getUserId());
        softly.assertThat(actual).isEqualTo(expected);

        expected.update(new User("userId", "password2", "name2", "sanjigi@email.com"));
        userDao.update(expected);
        actual = userDao.findByUserId(expected.getUserId());
        softly.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void crud2() {
        User expected = new User("userId2", "password", "name", "javajigi@email.com");
        UserDao userDao = new UserDao(new JdbcTemplate());
        userDao.insert2(expected);
        User actual = userDao.findByUserId2(expected.getUserId());
        softly.assertThat(actual).isEqualTo(expected);

        expected.update(new User("userId2", "password2", "name2", "sanjigi@email.com"));
        userDao.update2(expected);
        actual = userDao.findByUserId2(expected.getUserId());
        softly.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void findAll() {
        UserDao userDao = new UserDao(new JdbcTemplate());
        List<User> users = userDao.findAll();
        softly.assertThat(users).hasSize(2);
    }

    @Test
    public void findAll2() {
        UserDao userDao = new UserDao(new JdbcTemplate());
        List<User> users = userDao.findAll2();
        softly.assertThat(users).hasSize(2);
    }
}