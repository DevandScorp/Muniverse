package Controller;

import Model.User;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;

public class SqlEditor {
    public static void write(User user){
        try{
            EntityManagerFactory unit = Persistence.createEntityManagerFactory("NewPersistenceUnit");
            EntityManager entityManager = unit.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
            unit.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public static void createMusicTable(User user) throws ClassNotFoundException {
       Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(User.getCONNECTION(),User.getUSERNAME(),User.getPASSWORD());
           Statement statement = connection.createStatement()){
          statement.executeUpdate("create TABLE if not exists "+"user_" + Integer.toString(user.getId())+ "(id MEDIUMINT not null auto_increment,full_name char(255)NOT NULL ,title char(255),artist char(255),primary key (id))");
      }
       catch (SQLException e) {
           e.printStackTrace();
       }
    }

}
