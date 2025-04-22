package com.project.unischedapi.user.repository;

import com.project.unischedapi.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User findByEmailId(String emailId) {
        String sql = """
                        SELECT u.*, r.RoleName
                        FROM `user` u
                        JOIN userrole r ON u.UserRoleID = r.UserRoleID
                        WHERE u.EmailID = ?
                    """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            User user = new User();
            user.setUserId(rs.getInt("UserID"));
            user.setEmailId(rs.getString("EmailID"));
            user.setPassword(rs.getString("PasswordHash"));
            user.setRoleId(rs.getInt("UserRoleID"));
            user.setRoleName(rs.getString("RoleName")); // from joined table
            user.setCreatedAt(rs.getTimestamp("CreatedAt"));
            user.setLastUpdated(rs.getTimestamp("LastUpdated"));
            user.setLastUpdatedBy(rs.getString("LastUpdatedBy"));
            user.setIsActive(rs.getBoolean("IsActive"));
            return user;
        }, emailId);
    }



    public boolean emailExists(String emailId) {
        String sql = "SELECT COUNT(*) FROM User WHERE EmailID = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, emailId);
        return count != null && count > 0;
    }

    public Integer createUser(User user) {
        String sql = """
            INSERT INTO User (EmailID, PasswordHash, UserRoleID, LastUpdatedBy)
            VALUES (?, ?, ?, ?)
        """;

        jdbcTemplate.update(sql,
                user.getEmailId(),
                user.getPassword(),
                user.getRoleId(),
                user.getLastUpdatedBy());

        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

}
