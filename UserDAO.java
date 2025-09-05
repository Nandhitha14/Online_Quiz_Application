package com.example.quizapp.dao;

import com.example.quizapp.model.User;
import com.example.quizapp.util.Database;
import com.example.quizapp.util.PasswordUtil;

import java.sql.*;

public class UserDAO {

    public boolean register(String username, String email, char[] password) throws Exception {
        String salt = PasswordUtil.generateSalt();
        String hash = PasswordUtil.hashPassword(password, salt);

        String sql = "INSERT INTO users(username,email,password_hash,salt,is_admin) VALUES(?,?,?,?,0)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, hash);
            stmt.setString(4, salt);
            stmt.executeUpdate();
            return true;
        }
    }

    public User login(String username, char[] password) throws Exception {
        String sql = "SELECT * FROM users WHERE username=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String salt = rs.getString("salt");
                String expectedHash = rs.getString("password_hash");
                String givenHash = PasswordUtil.hashPassword(password, salt);
                if (givenHash.equals(expectedHash)) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getInt("is_admin") == 1
                    );
                }
            }
        }
        return null;
    }
}
