package Model;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.Serializable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

import Controller.Music_Tag_Handler;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
@Named("user")
@SessionScoped
public class User_Bean implements Serializable {
    @Inject
    User user;
    private String JSTags;
    private String PlayerTags;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJSTags() {
        return this.JSTags;
    }

    public void setJSTags() {
        this.JSTags = Music_Tag_Handler.getJSTag(user.getNickname());
    }

    public String getPlayerTags() {
        return Music_Tag_Handler.getPlayerTag(user.getFilepath());
    }

    public void setPlayerTags() {
        PlayerTags = Music_Tag_Handler.getPlayerTag(user.getFilepath());
    }

    public String getNickname() {
        return user.getNickname();
    }

    public void setNickname(String nickname) {
        user.setNickname(nickname);
    }

    public String getEmail() {
        return user.getEmail();
    }

    public void setEmail(String email) {
        user.setEmail(email);
    }

    public String getPassword() {
        return user.getPassword();
    }

    public void setPassword(String password) {
        user.setPassword(password);
    }

    public String getFilepath() {
        return user.getFilepath();
    }

    public void setFilepath(String filename) {
        user.setFilepath(filename);
    }
    public String getJS() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        StringBuilder stringBuilder = new StringBuilder("");
        try (Connection connection = DriverManager.getConnection(User.getCONNECTION(), User.getUSERNAME(), User.getPASSWORD());
             Statement statement = connection.createStatement()) {
            String stat = "select * from user_" + getId();
            ResultSet result = statement.executeQuery(stat);
            while (result.next()) {
                String title = result.getString("title");
                String artist = result.getString("artist");
                String filename = result.getString("full_name");
                if (title.length() != 0 && artist.length() != 0) {
                    stringBuilder.append("{\n" +
                            "                \"name\": \"" + title + "\",\n" +
                            "                \"artist\": \"" + artist + "\",\n" +
                            "" +
                            "                \"url\": \"http://localhost:8080/" + "Users/" +  getNickname() + "/"+ filename + ".mp3" + "\",\n" +
                            "                \"cover_art_url\": \"../album-art/we-are-but-hunks-of-wood.jpg\"\n" +
                            "            },\n");
                } else {
                    stringBuilder.append("{\n" +
                            "                \"name\": \"" + filename + "\",\n" +
                            "                \"artist\": \"" + artist + "\",\n" +
                            "                \"url\": \"http://localhost:8080/" + "Users/" +  getNickname() + "/"+ filename + ".mp3" + "\",\n" +
                            "                \"cover_art_url\": \"../album-art/we-are-but-hunks-of-wood.jpg\"\n" +
                            "            },\n");
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
        public  String getPlayer() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        StringBuilder stringBuilder = new StringBuilder("");
        try (Connection connection = DriverManager.getConnection(User.getCONNECTION(), User.getUSERNAME(), User.getPASSWORD());
             Statement statement = connection.createStatement()) {
            String stat = "select * from user_" + getId();
            ResultSet result = statement.executeQuery(stat);
            int i = 0;
            while (result.next()) {
                String title = result.getString("title");
                String artist = result.getString("artist");
//                System.out.println(title);
//                System.out.println(artist);

                if (title.length()!= 0 && artist.length()!= 0) {
                    stringBuilder.append("<div class=\"player\">\n" +
                            "            <img src=\"http://localhost:8080/View/User_Page/album-art/we-are-but-hunks-of-wood.jpg\" class=\"album-art\"/>\n" +
                            "            <div class=\"meta-container\">\n" +
                            "                <div class=\"song-title\">" + title + "</div>\n" +
                            "                <div class=\"song-artist\">" + artist + "</div>\n" +
                            "\n" +
                            "                <div class=\"time-container\">\n" +
                            "                    <div class=\"current-time\">\n" +
                            "                        <span class=\"amplitude-current-minutes\" amplitude-song-index=\"" + i + "\"></span>:<span class=\"amplitude-current-seconds\" amplitude-song-index=\"" + i + "\"></span>\n" +
                            "                    </div>\n" +
                            "\n" +
                            "                    <div class=\"duration\">\n" +
                            "                        <span class=\"amplitude-duration-minutes\" amplitude-song-index=\"" + i + "\">3</span>:<span class=\"amplitude-duration-seconds\" amplitude-song-index=\"" + i + "\">30</span>\n" +
                            "                    </div>\n" +
                            "                </div>\n" +
                            "                <progress class=\"amplitude-song-played-progress\" amplitude-song-index=\"" + i + "\" id=\"song-played-progress-" + (i+1) + "\"></progress>\n" +
                            "                <div class=\"control-container\">\n" +
                            "                    <div class=\"amplitude-prev\">\n" +
                            "\n" +
                            "                    </div>\n" +
                            "                    <div class=\"amplitude-play-pause\" amplitude-song-index=\"" + i + "\">\n" +
                            "\n" +
                            "                    </div>\n" +
                            "                    <div class=\"amplitude-next\">\n" +
                            "\n" +
                            "                    </div>\n" +
                            "                </div>\n" +
                            "            </div>\n" +
                            "        </div>");
                } else {
                    stringBuilder.append("<div class=\"player\">\n" +
                            "            <img src=\"http://localhost:8080/View/User_Page/album-art/we-are-but-hunks-of-wood.jpg\" class=\"album-art\"/>\n" +
                            "            <div class=\"meta-container\">\n" +
                            "                <div class=\"song-title\">" + result.getString("full_name") + "</div>\n" +
                            "                <div class=\"song-artist\">" + artist + "</div>\n" +
                            "\n" +
                            "                <div class=\"time-container\">\n" +
                            "                    <div class=\"current-time\">\n" +
                            "                        <span class=\"amplitude-current-minutes\" amplitude-song-index=\"" + i + "\"></span>:<span class=\"amplitude-current-seconds\" amplitude-song-index=\"" + i + "\"></span>\n" +
                            "                    </div>\n" +
                            "\n" +
                            "                    <div class=\"duration\">\n" +
                            "                        <span class=\"amplitude-duration-minutes\" amplitude-song-index=\"" + i + "\">3</span>:<span class=\"amplitude-duration-seconds\" amplitude-song-index=\"" + i + "\">30</span>\n" +
                            "                    </div>\n" +
                            "                </div>\n" +
                            "                <progress class=\"amplitude-song-played-progress\" amplitude-song-index=\"" + i + "\" id=\"song-played-progress-" + (i+1) + "\"></progress>\n" +
                            "                <div class=\"control-container\">\n" +
                            "                    <div class=\"amplitude-prev\">\n" +
                            "\n" +
                            "                    </div>\n" +
                            "                    <div class=\"amplitude-play-pause\" amplitude-song-index=\"" + i + "\">\n" +
                            "\n" +
                            "                    </div>\n" +
                            "                    <div class=\"amplitude-next\">\n" +
                            "\n" +
                            "                    </div>\n" +
                            "                </div>\n" +
                            "            </div>\n" +
                            "        </div>");

                }
                stringBuilder.append(" <script type = \"text/javascript\">\n" +
                        "        \t\n" +
                        "\t\t\tdocument.getElementById('song-played-progress-" + (i+1) + "').addEventListener('click', function( e ){\n" +
                        "\t\t\t  if( Amplitude.getActiveIndex() == "+i+" ){\n" +
                        "\t\t\t    var offset = this.getBoundingClientRect();\n" +
                        "\t\t\t    var x = e.pageX - offset.left;\n" +
                        "\n" +
                        "\t\t\t    Amplitude.setSongPlayedPercentage( ( parseFloat( x ) / parseFloat( this.offsetWidth) ) * 100 );\n" +
                        "\t\t\t  }\n" +
                        "\t\t\t});\n" +
                        "        </script>");
                ++i;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    @Override
    public int hashCode() {
        return user.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + user.getId() +
                ", nickname='" + user.getNickname() + '\'' +
                ", email='" + user.getEmail() + '\'' +
                ", password='" + user.getPassword() + '\'' +
                ", filepath='" + user.getFilepath() + '\'' +
                ", USERNAME='" + User.getUSERNAME() + '\'' +
                ", PASSWORD='" + User.getPASSWORD() + '\'' +
                ", CONNECTION='" + User.getCONNECTION() + '\'' +
                '}';
    }
}
