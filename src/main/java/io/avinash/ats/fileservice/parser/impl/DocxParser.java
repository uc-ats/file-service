package io.avinash.ats.fileservice.parser.impl;

import io.avinash.ats.fileservice.parser.FileParser;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

public class DocxParser implements FileParser {
    @Override
    public String parse(byte[] content) {
        try(InputStream is = new ByteArrayInputStream(content)) {
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(is);
            MainDocumentPart docPart = wordMLPackage.getMainDocumentPart();
            // Use docx4j's internal JAXB context
            JAXBContext context = Context.jc;
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter writer = new StringWriter();
            marshaller.marshal(docPart.getJaxbElement(), writer);
            return writer.toString();
            // Better: docPart.getContent() → traverse paragraphs for cleaner text
        } catch (IOException | Docx4JException | JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
