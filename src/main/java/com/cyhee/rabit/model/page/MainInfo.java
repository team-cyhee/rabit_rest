package com.cyhee.rabit.model.page;

import com.cyhee.rabit.model.cmm.ContentType;
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
	private Long order;
    private ContentType type;
    private Integer likeNum;
    private Integer commentNum;
    private Integer companionNum;
    private Date createDate;
    private Date lastUpdated;
    private boolean liked;

    public static class DateSort implements Comparator<MainInfo> {
        @Override
        public int compare(MainInfo o1, MainInfo o2) {
            return o2.getCreateDate().compareTo(o1.getCreateDate());
        }
    }
}
