package org.template.cloud.service.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.template.cloud.bean.module.Transcript;

import java.util.Date;
import java.util.List;

@Repository
public interface TranscriptRepository extends MongoRepository<Transcript, String> {
    /**
     * 根据用户ID查询一段时间内的数据
     *
     * @param userId
     * @param start
     * @param end
     * @return
     */
    List<Transcript> findByUserIdAndDateBetween(String userId, Date start, Date end);


}
