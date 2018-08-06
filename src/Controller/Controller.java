package Controller;

import Model.User;
import org.apache.commons.io.FilenameUtils;
import org.apache.openejb.jee.Dispatcher;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Enumeration;

@WebServlet("/controller")
@MultipartConfig
public class Controller extends HttpServlet {
    @Inject
    User user;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
    private void downloadImage(HttpServletRequest req) throws IOException, ServletException {
        Part filePart = req.getPart("file");
        InputStream inputStream = filePart.getInputStream();
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        String format = FilenameUtils.getExtension(Paths.get(filePart.getSubmittedFileName()).getFileName().toString());

        File file;
        if(!(file= new File("..\\..\\..\\web\\Users\\"+ user.getNickname())).exists()){
            if(file.mkdir()) System.out.println("Dir created");
        }
        File outPutFile = new File("..\\..\\..\\web\\Users\\"+ user.getNickname() + "\\logo.jpg");
        System.out.println(outPutFile.getAbsolutePath());
        ImageIO.write(bufferedImage,format,outPutFile);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         user.setNickname(req.getParameter("nickname"));
         user.setEmail(req.getParameter("email"));
         user.setPassword(req.getParameter("password"));
         user.setFilepath("..\\..\\..\\Users\\"+ user.getNickname());
        User.Existence existence = null;
        try {
            existence = user.exists();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        RequestDispatcher requestDispatcher;
        if(existence==User.Existence.DO_NOT_EXIST){
            downloadImage(req);
            SqlEditor.write(user);
            try {
                SqlEditor.createMusicTable(user);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            req.setAttribute("warning",existence);
            requestDispatcher = req.getRequestDispatcher("View/User_Page/main/user.jsp");
            requestDispatcher.forward(req,resp);//передаем управление MyView
        }
        else{
            if(existence==User.Existence.NICKNAME){
                req.setAttribute("warning","This nickname is busy");
            }
            else{
                req.setAttribute("warning","This email is busy");

            }
            requestDispatcher = req.getRequestDispatcher("View/index.jsp");
            requestDispatcher.forward(req,resp);//передаем управление MyView
        }

    }
}
