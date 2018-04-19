package org.template.cloud.bean.module;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Data
@Document(collection = "transcript")
public class Transcript {
    @Id
    String id;
    String userId;
    Date date;
    Integer score;
}
