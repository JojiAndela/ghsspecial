package com.jojitoon.jesusme.ghsspecial;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JESUS ME on 7/15/2017
 */

public class DocParser {


    private static final String LOGTAG = "JOJITOON";

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String STANZA = "stanza";


    private DocData currentHymn = null;
    private String currentTag = null;
    private List<DocData> hymns = new ArrayList<>();

    public List<DocData> parseXML(Context context) {

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            InputStream stream = context.getResources().openRawResource(R.raw.doc);
            xpp.setInput(stream, null);

            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    handleStartTag(xpp.getName());
                } else if (eventType == XmlPullParser.END_TAG) {
                    currentTag = null;
                } else if (eventType == XmlPullParser.TEXT) {
                    handleText(xpp.getText());
                }
                eventType = xpp.next();
            }

        } catch (Resources.NotFoundException e) {
            Log.d(LOGTAG, e.getMessage());
        } catch (XmlPullParserException e) {
            Log.d(LOGTAG, e.getMessage());
        } catch (IOException e) {
            Log.d(LOGTAG, e.getMessage());
        }

        return hymns;
    }

    private void handleText(String text) {


        if (currentHymn != null && currentTag != null) {


            if (currentTag.equals(ID)) {

                currentHymn.setId(text);
            } else if (currentTag.equals(TITLE)) {
                currentHymn.setTitle(text);
            } else if (currentTag.equals(STANZA)) {
                currentHymn.setStanza(text);
            }


        }
    }

    private void handleStartTag(String name) {
        if (name.equals("doctrine")) {
            currentHymn = new DocData();
            hymns.add(currentHymn);
        } else {
            currentTag = name;
        }
    }
}

