package io.avinash.ats.fileservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.avinash.ats.fileservice.constants.FileExtension;
import io.avinash.ats.fileservice.model.ResumeData;
import org.apache.tika.Tika;
import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

@Service
public class FileParsingService {
    private final Tika tika = new Tika();
    private final ObjectMapper mapper = new ObjectMapper();

    public String detectExtension(byte[] content){
        try {
            String mimeType = tika.detect(content);
            MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
            MimeType mime = allTypes.forName(mimeType);
            String extension = mime.getExtension();
            return extension.replace(".", "");
        } catch (MimeTypeException e) {
            throw new RuntimeException(e);
        }
    }

    public String toJson(Object resume){
        String json = null;
        try {
            json = mapper.writeValueAsString(resume);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    public ResumeData toResumeData(String json){
        ResumeData data = null;
        try {
            data = mapper.readValue(json, new TypeReference<ResumeData>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    public String cleanText(String text) {
        if (text == null) return "";
        return text.replaceAll("\\p{Cntrl}", " ") // remove control chars
                .replaceAll("\\s+", " ")       // collapse multiple spaces
                .trim();
    }

    public String parse(String filename, byte[] content){
        String ext = detectExtension(content);
        FileExtension fileExt = FileExtension.from(ext);
        String rawText = fileExt.getParser().parse(content);

        ResumeData resume = new ResumeData();
        resume.setFileName(filename);
        resume.setText(cleanText(rawText));

        ResumeData.MetadataInfo meta = new ResumeData.MetadataInfo();
        meta.setFormat(ext);
        meta.setSizeKb(content.length / 1024.0);
        resume.setMetadata(meta);

        ResumeData.Sections sections = new ResumeData.Sections();
        sections.setEducation(null);
        resume.setSections(sections);

        ResumeData.Entities entities = new ResumeData.Entities();
        entities.setCertifications(Collections.emptyList());
        entities.setDates(Collections.emptyList());
        entities.setEmails(Collections.emptyList());
        entities.setNames(Collections.emptyList());
        entities.setPhones(Collections.emptyList());
        entities.setOrganizations(Collections.emptyList());
        entities.setLocations(Collections.emptyList());
        entities.setSkillPhrases(Collections.emptyList());
        entities.setTitles(Collections.emptyList());
        resume.setEntities(entities);

        resume.setNormalizedSkills(Collections.emptyList());

        return toJson(resume);
    }
}
