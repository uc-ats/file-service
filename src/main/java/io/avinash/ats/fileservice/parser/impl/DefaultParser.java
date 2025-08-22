package io.avinash.ats.fileservice.parser.impl;

import io.avinash.ats.fileservice.parser.FileParser;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DefaultParser implements FileParser {
    @Override
    public String parse(byte[] content) {
        BodyContentHandler handler = new BodyContentHandler(-1); // -1 = unlimited
        Metadata metadata = new Metadata();
        try (InputStream stream = new ByteArrayInputStream(content)) {
            AutoDetectParser parser = new AutoDetectParser();
            parser.parse(stream, handler, metadata);
        } catch (IOException | SAXException | TikaException e) {
            throw new RuntimeException(e);
        }
        return handler.toString();
    }
}
