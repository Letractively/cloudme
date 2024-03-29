package de.moritzpetersen.homepage.dataload;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.google.inject.Inject;

import de.moritzpetersen.homepage.domain.Entry;
import de.moritzpetersen.homepage.util.DateUtil;

public class DataLoader {
    private static final Logger LOG = Logger.getLogger(DataLoader.class.getName());
    @Inject
    private EntryHandler entryHandler;

    public void load(InputStream in) {
        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            parser.parse(in, new DefaultHandler() {
                private final DateFormat dateFormat = DateUtil.defaultFormat();
                private Entry entry;
                private StringBuilder chars;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes)
                        throws SAXException {
                    if ("h_entry".equals(qName)) {
                        entry = new Entry();
                    }
                    chars = new StringBuilder();
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    chars.append(ch, start, length);
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if ("h_entry".equals(qName)) {
                        entryHandler.handle(entry);
                        return;
                    }
                    String value = chars.toString().trim();
                    if ("id".equals(qName)) {
                        entry.setOldId(value);
                    }
                    else if ("title".equals(qName)) {
                        entry.setTitle(value);
                    }
                    else if ("content".equals(qName)) {
                        entry.setContent(value);
                    }
                    else if ("url".equals(qName)) {
                        entry.setUrl(value);
                    }
                    else if ("date".equals(qName)) {
                        try {
                            entry.setDate(dateFormat.parse(value));
                        }
                        catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else if ("origin".equals(qName)) {
                        entry.setOrigin(value);
                    }
                }
            });
        }
        catch (ParserConfigurationException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
        catch (SAXException e) {
            throw new InvalidDataException();
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
