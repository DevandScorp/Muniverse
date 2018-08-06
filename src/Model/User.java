package Model;

import Controller.SqlEditor;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.sql.*;
import java.util.Objects;



@Entity(name = "Users")
public class User implements Serializable {
    @Id
    @GeneratedValue
    int id;
    @Id
    @NotNull
    private String nickname;
    @Id
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String filepath;

    public User(String nickname, String email, String password) {

        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", filepath='" + filepath + '\'' +
                ", USERNAME='" + USERNAME + '\'' +
                ", PASSWORD='" + PASSWORD + '\'' +
                ", CONNECTION='" + CONNECTION + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(nickname, user.nickname) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(filepath, user.filepath) &&
                Objects.equals(USERNAME, user.USERNAME) &&
                Objects.equals(PASSWORD, user.PASSWORD) &&
                Objects.equals(CONNECTION, user.CONNECTION);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nickname, email, password);
    }

    public static String getUSERNAME() {
        return USERNAME;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static String getCONNECTION() {
        return CONNECTION;
    }


    public enum Existence {
        NICKNAME,
        EMAIL,
        DO_NOT_EXIST
    }

    @Transient
    private static final String USERNAME = "root";
    @Transient
    private static final String PASSWORD = "Bb14337107916408";
    @Transient
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/MUniverse?serverTimezone=UTC&verifyServerCertificate=false&useSSL=false";


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filename) {
        this.filepath = filename;
    }

    public User() {
    }

    public Existence exists() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String email_ = "\'" + email + "\'";
        String nickname_ = "\'" + nickname + "\'";
        try (Connection connection = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery("select * from users where nickname = " + nickname_);
            if (result.next()) {
                return Existence.NICKNAME;
            }

            ResultSet result1 = statement.executeQuery("select * from users where email = " + email_);
            if (result1.next()) {
                return Existence.EMAIL;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Existence.DO_NOT_EXIST;
    }
}

