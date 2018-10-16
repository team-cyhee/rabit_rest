package com.cyhee.rabit.model.main;

import com.cyhee.rabit.model.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.Comparator;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainInfo {
    private Integer likeNum;
    private Integer commentNum;
    private Page<Comment> comments;
    private Date lastUpdated;

    public static class DateSort implements Comparator<MainInfo> {
        @Override
        public int compare(MainInfo o1, MainInfo o2) {
            return o2.getLastUpdated().compareTo(o1.getLastUpdated());
        }
    }
}
