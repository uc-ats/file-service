package io.avinash.ats.fileservice.parser.impl;

import io.avinash.ats.fileservice.parser.FileParser;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DocParser implements FileParser {
    @Override
    public String parse(byte[] content) {
        String text = null;
        try (InputStream is = new ByteArrayInputStream(content);
             HWPFDocument doc = new HWPFDocument(is)) {
            StringBuilder sb = new StringBuilder();
            Range range = doc.getRange();
            for (int i = 0; i < range.numParagraphs(); i++) {
                Paragraph para = range.getParagraph(i);
                sb.append(para.text().trim()).append("\n");
            }
            text = sb.toString();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return text;
    }
}
