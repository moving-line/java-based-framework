package user.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.RowMapper;
import user.domain.User;

import java.util.List;

public class UserDao {
    private static final String INSERT_INTO_USER = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
    private static final String UPDATE_VALUE_USER = "UPDATE USERS SET password=?, name=?, email=? WHERE userId=?";
    private static final String SELECT_FROM_USER = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
    private static final String SELECT_FROM_ALL_USER = "SELECT * FROM USERS";

    private final JdbcTemplate template;

    public UserDao(JdbcTemplate template) {
        this.template = template;
    }

    public void insert(User user) {
        PreparedStatementSetter pss = pstmt -> {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
        };

        template.update(INSERT_INTO_USER, pss);
    }

    public void insert2(User user) {
        template.update(INSERT_INTO_USER, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public void update(User user) {
        PreparedStatementSetter pss = pstmt -> {
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getUserId());
        };

        template.update(UPDATE_VALUE_USER, pss);
    }

    public void update2(User user) {
        JdbcTemplate template = new JdbcTemplate();
        template.update(UPDATE_VALUE_USER, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }

    public User findByUserId(String userId) {
        PreparedStatementSetter pss = pstmt -> pstmt.setString(1, userId);
        RowMapper<User> rm = rs -> new User(
                rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email")
        );

        return template.queryForObject(SELECT_FROM_USER, rm, pss);
    }

    public User findByUserId2(String userId) {
        RowMapper<User> rm = rs -> new User(
                rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email")
        );

        return template.queryForObject(SELECT_FROM_USER, rm, userId);
    }

    public List<User> findAll() {
        PreparedStatementSetter pss = pstmt -> {};
        RowMapper<User> rm = rs -> new User(
                rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email")
        );

        return template.query(SELECT_FROM_ALL_USER, rm, pss);
    }

    public List<User> findAll2() {
        RowMapper<User> rm = rs -> new User(
                rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email")
        );

        return template.query(SELECT_FROM_ALL_USER, rm);
    }
}

