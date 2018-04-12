package org.template.cloud.service.service.mongo;


import org.template.cloud.service.domain.bean.mongo.Transcript;

import java.util.Date;
import java.util.List;

public interface TranscriptService {
    void insert(Transcript t);

    void insert(List<Transcript> transcripts);

    List<Transcript> findByUserIdAndDateBetween(String userId, Date start, Date end);
}
