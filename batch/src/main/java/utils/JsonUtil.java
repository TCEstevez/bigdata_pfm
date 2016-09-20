package utils;

import com.google.common.collect.Lists;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import pojo.Question;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class JsonUtil {


    public static String limpiarHtml(String s) {
        return s.replaceAll("\n"," ").replaceAll("\\<[^>]*>", "");
    }

    public static boolean isValuePermited(Attr attr) {
        if (attr == null || attr.getValue().trim().equalsIgnoreCase(""))
            return false;
        return true;
    }

    public static ArrayList<String> parseTags(Attr attr) {
        ArrayList<String> tags=new ArrayList<String>();
        if(isValuePermited(attr)){
            String[] array=attr.getValue().replaceAll("\n"," ").replaceAll("<"," ").replaceAll(">","").trim().split(" ");
            tags= Lists.newArrayList(array);
        }

        return tags;
    }

    public static Question convertStringtoObject(String s){
        Question q = new Question();
        try {

            DocumentBuilderFactory dbf =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(s));

            Document doc = db.parse(is);
            NodeList nodes = doc.getElementsByTagName("row");
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);


                Attr Id = element.getAttributeNode("Id");
                Attr PostTypeId = element.getAttributeNode("PostTypeId");
                Attr CreationDate = element.getAttributeNode("CreationDate");
                Attr AcceptedAnswerId=element.getAttributeNode("AcceptedAnswerId");
                Attr Score = element.getAttributeNode("Score");
                Attr OwnerUserId = element.getAttributeNode("OwnerUserId");
                Attr LastActivityDate = element.getAttributeNode("LastActivityDate");
                Attr ParentId = element.getAttributeNode("ParentId");
                Attr ViewCount = element.getAttributeNode("ViewCount");
                Attr Body = element.getAttributeNode("Body");
                Attr AnswerCount = element.getAttributeNode("AnswerCount");
                Attr CommentCount  = element.getAttributeNode("CommentCount ");
                Attr FavoriteCount = element.getAttributeNode("FavoriteCount");
                Attr Tag = element.getAttributeNode("Tags");


                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

                String body=Body!=null?limpiarHtml(Body.getValue()):"";

                ArrayList<String> tags=new ArrayList<String>();
                tags=parseTags(Tag);
                q = new Question(isValuePermited(Id) ? Long.parseLong(Id.getValue()) : 0,
                        isValuePermited(PostTypeId) ? Integer.parseInt(PostTypeId.getValue()) : 0,
                        isValuePermited(ParentId)? Long.parseLong(ParentId.getValue()) : 0,
                        sdf.parse(CreationDate.getValue()),
                        isValuePermited(Score) ? Long.parseLong(Score.getValue()) : 0,
                        isValuePermited(OwnerUserId) ? Long.parseLong(OwnerUserId.getValue()) : 0,
                        sdf.parse(LastActivityDate.getValue()),
                        isValuePermited(ViewCount) ? Long.parseLong(ViewCount.getValue()) : 0,
                        body,
                        isValuePermited(FavoriteCount) ? Long.parseLong(FavoriteCount.getValue()) : 0,
                        isValuePermited(AnswerCount) ? Long.parseLong(AnswerCount.getValue()) : 0,
                        isValuePermited(CommentCount) ? Long.parseLong(CommentCount.getValue()) : 0,
                        isValuePermited(AcceptedAnswerId) ? Long.parseLong(AcceptedAnswerId.getValue()) : 0,
                        tags);


            }
        } catch (SAXException e) {
            e.printStackTrace();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }catch (ParseException e) {
            e.printStackTrace();

        }

        return q;
    }
}
