package io.avinash.ats.fileservice.constants;

import io.avinash.ats.fileservice.parser.FileParser;
import io.avinash.ats.fileservice.parser.impl.DefaultParser;
import io.avinash.ats.fileservice.parser.impl.DocParser;
import io.avinash.ats.fileservice.parser.impl.DocxParser;
import io.avinash.ats.fileservice.parser.impl.PdfParser;

public enum FileExtension {
    PDF("pdf", new PdfParser()),
    DOC("doc", new DocParser()),
    DOCX("docx", new DocxParser()),
    TXT("txt", new DefaultParser()),
    RTF("rtf", new DefaultParser());

    private final String extension;
    private final FileParser parser;

    FileExtension(String extension, FileParser parser) {
        this.extension = extension;
        this.parser = parser;
    }

    public FileParser getParser() {
        return parser;
    }

    public String getExtension() {
        return extension;
    }

    public static FileExtension from(String ext) {
        for (FileExtension fe : values()) {
            if (fe.extension.equalsIgnoreCase(ext)) {
                return fe;
            }
        }
        return null;
    }
}
