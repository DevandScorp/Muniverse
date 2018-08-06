package Controller;

import Model.User;
import Model.User_Bean;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;
import org.apache.catalina.servlets.DefaultServlet;

@WebServlet("/login")
public class Controller_login extends HttpServlet {
    @Inject
    User_Bean user_bean;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        String email_ = "\'" + email + "\'";
        try (Connection connection = DriverManager.getConnection(User.getCONNECTION(), User.getUSERNAME(), User.getPASSWORD());

                 Statement statement = connection.createStatement()) {
                ResultSet result = statement.executeQuery("select * from users where email = " + email_);
                result.next();
                if (result.getString("password").equals(password)) {
                        user_bean.setEmail(email);
                        user_bean.setPassword(password);
                        user_bean.setFilepath(result.getString("filepath"));
                        user_bean.setNickname(result.getString("nickname"));
                        user_bean.setId(Integer.parseInt(result.getString("id")));
                        System.out.println(user_bean);
                        RequestDispatcher requestDispatcher = req.getRequestDispatcher("View/User_Page/main/user.jsp");
                        requestDispatcher.forward(req, resp);
                    }
                    else{
                    req.setAttribute("warning","Wrong email or password");
                    RequestDispatcher requestDispatcher = req.getRequestDispatcher("View/index.jsp");
                    requestDispatcher.forward(req, resp);
                }

            } catch (SQLException e) {
            req.setAttribute("warning","Wrong email or password");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("View/index.jsp");
            requestDispatcher.forward(req, resp);
            }
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
