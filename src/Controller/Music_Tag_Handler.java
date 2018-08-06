package Controller;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.*;

public class Music_Tag_Handler {
    public static String getPlayerTag(String nickname){
        File file = new File("http://localhost:8080/"+ nickname);
        if(!file.exists()) System.out.println("does not exist");
        File[] files = file.listFiles();
        StringBuilder stringBuilder = new StringBuilder();
        for(File file1:files){
            try(InputStream input = new FileInputStream(new File(file.getAbsolutePath()))) {
                ContentHandler handler = new DefaultHandler();
                Metadata metadata = new Metadata();
                Parser parser = new Mp3Parser();
                ParseContext parseCtx = new ParseContext();
                parser.parse(input, handler, metadata, parseCtx);
                // Retrieve the necessary info from metadata
                // Names - title, xmpDM:artist etc. - mentioned below may differ based
                String title = metadata.get("title");
                String artists = metadata.get("xmpDM:artist");
                if(title.length()!=0 && artists.length()!=0){
                    stringBuilder.append("<div class=\"player\">\n" +
                            "            <img src=\"http://localhost:8080/View/User_Page/album-art/we-are-but-hunks-of-wood.jpg\" class=\"album-art\"/>\n" +
                            "            <div class=\"meta-container\">\n" +
                            "                <div class=\"song-title\">" +  title + "</div>\n" +
                            "                <div class=\"song-artist\">" + artists + "</div>\n" +
                            "\n" +
                            "                <div class=\"time-container\">\n" +
                            "                    <div class=\"current-time\">\n" +
                            "                        <span class=\"amplitude-current-minutes\" amplitude-song-index=\"0\"></span>:<span class=\"amplitude-current-seconds\" amplitude-song-index=\"0\"></span>\n" +
                            "                    </div>\n" +
                            "\n" +
                            "                    <div class=\"duration\">\n" +
                            "                        <span class=\"amplitude-duration-minutes\" amplitude-song-index=\"0\">3</span>:<span class=\"amplitude-duration-seconds\" amplitude-song-index=\"0\">30</span>\n" +
                            "                    </div>\n" +
                            "                </div>\n" +
                            "                <progress class=\"amplitude-song-played-progress\" amplitude-song-index=\"0\" id=\"song-played-progress-1\"></progress>\n" +
                            "                <div class=\"control-container\">\n" +
                            "                    <div class=\"amplitude-prev\">\n" +
                            "\n" +
                            "                    </div>\n" +
                            "                    <div class=\"amplitude-play-pause\" amplitude-song-index=\"0\">\n" +
                            "\n" +
                            "                    </div>\n" +
                            "                    <div class=\"amplitude-next\">\n" +
                            "\n" +
                            "                    </div>\n" +
                            "                </div>\n" +
                            "            </div>\n" +
                            "        </div>");
                }
                else{
                    stringBuilder.append("<div class=\"player\">\n" +
                            "            <img src=\"http://localhost:8080/View/User_Page/album-art/we-are-but-hunks-of-wood.jpg\" class=\"album-art\"/>\n" +
                            "            <div class=\"meta-container\">\n" +
                            "                <div class=\"song-title\">" +  file1.getName().substring(0,file.getName().indexOf(".")) + "</div>\n" +
                            "                <div class=\"song-artist\">" + artists + "</div>\n" +
                            "\n" +
                            "                <div class=\"time-container\">\n" +
                            "                    <div class=\"current-time\">\n" +
                            "                        <span class=\"amplitude-current-minutes\" amplitude-song-index=\"0\"></span>:<span class=\"amplitude-current-seconds\" amplitude-song-index=\"0\"></span>\n" +
                            "                    </div>\n" +
                            "\n" +
                            "                    <div class=\"duration\">\n" +
                            "                        <span class=\"amplitude-duration-minutes\" amplitude-song-index=\"0\">3</span>:<span class=\"amplitude-duration-seconds\" amplitude-song-index=\"0\">30</span>\n" +
                            "                    </div>\n" +
                            "                </div>\n" +
                            "                <progress class=\"amplitude-song-played-progress\" amplitude-song-index=\"0\" id=\"song-played-progress-1\"></progress>\n" +
                            "                <div class=\"control-container\">\n" +
                            "                    <div class=\"amplitude-prev\">\n" +
                            "\n" +
                            "                    </div>\n" +
                            "                    <div class=\"amplitude-play-pause\" amplitude-song-index=\"0\">\n" +
                            "\n" +
                            "                    </div>\n" +
                            "                    <div class=\"amplitude-next\">\n" +
                            "\n" +
                            "                    </div>\n" +
                            "                </div>\n" +
                            "            </div>\n" +
                            "        </div>");
                }



            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (TikaException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }
    public static String getJSTag(String nickname){
        File file = new File("..\\..\\..\\web\\Users\\"+ nickname);
        File[] files = file.listFiles();
        StringBuilder stringBuilder = new StringBuilder();
        for(File file1:files){
            try(InputStream input = new FileInputStream(new File(file.getAbsolutePath()))) {
                ContentHandler handler = new DefaultHandler();
                Metadata metadata = new Metadata();
                Parser parser = new Mp3Parser();
                ParseContext parseCtx = new ParseContext();
                parser.parse(input, handler, metadata, parseCtx);
                // Retrieve the necessary info from metadata
                // Names - title, xmpDM:artist etc. - mentioned below may differ based
                String title = metadata.get("title");
                String artists = metadata.get("xmpDM:artist");
                String album = metadata.get("xmpDM:album");
                if(title.length()!=0 && artists.length()!=0){
                    stringBuilder.append("{\n" +
                            "                \"name\": \"" + title + "\",\n" +
                            "                \"artist\": \"" + artists + "\",\n" +
                            "                // \"album\": \"" + album + "\",\n" +
                            "                \"url\": \"http://localhost:8080/" + nickname +"\",\n" +
                            "                \"cover_art_url\": \"../album-art/we-are-but-hunks-of-wood.jpg\"\n" +
                            "            },\n");
                }
                else{
                    stringBuilder.append("{\n" +
                            "                \"name\": \"" + file1.getName().substring(0,file.getName().indexOf(".mp3")) + "\",\n" +
                            "                \"artist\": \"" + artists + "\",\n" +
                            "                // \"album\": \"" + album + "\",\n" +
                            "                \"url\": \"http://localhost:8080/" + nickname +"\",\n" +
                            "                \"cover_art_url\": \"../album-art/we-are-but-hunks-of-wood.jpg\"\n" +
                            "            },\n");
                }



            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (TikaException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }
}
