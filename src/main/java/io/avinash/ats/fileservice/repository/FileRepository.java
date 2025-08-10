package io.avinash.ats.fileservice.repository;

import io.avinash.ats.fileservice.model.entity.AtsFile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepository extends MongoRepository<AtsFile, String> {

}
