package io.avinash.ats.fileservice.parser.impl;

import io.avinash.ats.fileservice.parser.FileParser;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.IOException;

public class PdfParser implements FileParser {

    @Override
    public String parse(byte[] content) {
        String text = null;
        try(PDDocument document = Loader.loadPDF(content)){
            PDFTextStripper stripper = new PDFTextStripper();
            text = stripper.getText(document);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return text;
    }
}
