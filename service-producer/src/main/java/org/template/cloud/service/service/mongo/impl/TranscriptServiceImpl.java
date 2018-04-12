package org.template.cloud.service.service.mongo.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.template.cloud.service.dao.mongo.TranscriptRepository;
import org.template.cloud.service.domain.bean.mongo.Transcript;
import org.template.cloud.service.service.mongo.TranscriptService;

import java.util.Date;
import java.util.List;

@Service
public class TranscriptServiceImpl implements TranscriptService {

    @Autowired
    TranscriptRepository transcriptRepository;

    @Override
    public void insert(Transcript t) {
        transcriptRepository.insert(t);
    }

    @Override
    public void insert(List<Transcript> transcripts) {
        transcriptRepository.insert(transcripts);
    }

    @Override
    public List<Transcript> findByUserIdAndDateBetween(String userId, Date start, Date end) {
        return transcriptRepository.findByUserIdAndDateBetween(userId, start, end);
    }
}
