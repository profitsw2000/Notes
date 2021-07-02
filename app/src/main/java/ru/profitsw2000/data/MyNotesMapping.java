package ru.profitsw2000.data;

import java.security.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class MyNotesMapping {
    public static class Fields{
        public final static String PICTURE = "picture";
        public final static String DATE = "date";
        public final static String TITLE = "title";
        public final static String DESCRIPTION = "description";
        public final static String TEXT = "text";
    }

    public static MyNotes toMyNotes(String id, Map<String, Object> doc) {
        long indexPic = (long) doc.get(Fields.PICTURE);
        Timestamp timeStamp = (Timestamp)doc.get(Fields.DATE);
        MyNotes answer = new MyNotes((String) doc.get(Fields.TITLE),
                PictureIndexConverter.getPictureByIndex((int) indexPic),
                (String) doc.get(Fields.DESCRIPTION),
                timeStamp.getTimestamp(),
                (String) doc.get(Fields.TEXT));
        answer.setId(id);
        return answer;
    }
    public static Map<String, Object> toDocument(MyNotes cardData){
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.TITLE, cardData.getTitle());
        answer.put(Fields.PICTURE,
                PictureIndexConverter.getIndexByPicture(cardData.getPicture()));
        answer.put(Fields.DESCRIPTION, cardData.getDescription());
        answer.put(Fields.DATE, cardData.getDate());
        answer.put(Fields.TEXT, cardData.getText());
        return answer;
    }
}
