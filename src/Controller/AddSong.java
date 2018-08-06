package Controller;

import Model.User;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

@WebServlet("/add_song")
@MultipartConfig
public class AddSong extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
    private static String process_String(String str){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0;i<str.length();++i){
            if(str.charAt(i)=='\'')stringBuilder.append("\'");
            stringBuilder.append(str.charAt(i));
        }
        return stringBuilder.toString();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part filePart = req.getPart("file");
        System.out.println(filePart.getSubmittedFileName());
        File copied = new File("..\\..\\..\\web\\Users\\" + req.getParameter("filepath") + "\\" + Paths.get(filePart.getSubmittedFileName()).getFileName().toString());
        try (InputStream inputStream = filePart.getInputStream();
             OutputStream out = new BufferedOutputStream(
                        new FileOutputStream(copied))) {

            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = inputStream.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
        }
        try (InputStream input = filePart.getInputStream()) {
            ContentHandler handler = new DefaultHandler();
            Metadata metadata = new Metadata();
            Parser parser = new Mp3Parser();
            ParseContext parseCtx = new ParseContext();
            parser.parse(input, handler, metadata, parseCtx);
            // Retrieve the necessary info from metadata
            // Names - title, xmpDM:artist etc. - mentioned below may differ based
            String title = metadata.get("title");
            String artists = metadata.get("xmpDM:artist");
            System.out.println(title + " " + artists);
            try(Connection connection = DriverManager.getConnection(User.getCONNECTION(),User.getUSERNAME(),User.getPASSWORD());
                Statement statement = connection.createStatement())
            {
                String full_name = process_String(filePart.getSubmittedFileName());
                String insertion = "insert into user_" + req.getParameter ("get_id") + " (full_name,title,artist) values "+ "('" + full_name.substring(0,full_name.indexOf(".mp3")) + "\'," +
                        "\'" + process_String(title) + "\'"+ ",\'" + process_String(artists) + "')";
                statement.executeUpdate(insertion);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("View/User_Page/main/user.jsp");
            requestDispatcher.forward(req, resp);//передаем управление MyView

        } catch (TikaException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("View/User_Page/main/user.jsp");
        requestDispatcher.forward(req, resp);//передаем управление MyView
    }
}