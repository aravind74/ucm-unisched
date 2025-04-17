package com.project.unischedapi.dao;

import com.project.unischedapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;

@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int insertUser(User user) {
        String sql = "INSERT INTO User (EmailID, PasswordHash, UserRoleID, CreatedAt, LastUpdatedBy, LastUpdated, IsActive) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmailId());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getRoleId());
            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            ps.setString(5, "system");
            ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            ps.setBoolean(7, true);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    // Fetch user by email
    public User getUserLogin(String email) {
        String sql = "SELECT * FROM User WHERE EmailID = ? AND IsActive = TRUE";

        return jdbcTemplate.queryForObject(sql, new Object[]{email}, (rs, rowNum) -> {
            User user = new User();
            user.setUserId(rs.getInt("UserID"));
            user.setEmailId(rs.getString("EmailID"));
            user.setPassword(rs.getString("PasswordHash"));
            user.setRoleId(rs.getInt("UserRoleID"));
            user.setRoleName(getRoleNameById(rs.getInt("UserRoleID")));
            user.setIsActive(rs.getBoolean("IsActive"));
            return user;
        });
    }

    private String getRoleNameById(int roleId) {
        String sql = "SELECT RoleName FROM UserRole WHERE UserRoleID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{roleId}, String.class);
    }

}
