package com.cyhee.rabit.model.page;

import com.cyhee.rabit.model.file.FileInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WallInfo {
    private Long id;
    private String username;
    private String introduction;
    private FileInfo profilePhoto;
    private Integer followerNum;
    private Integer followeeNum;
    private Integer goalLogNum;
    private boolean following;
    private boolean self;
}
