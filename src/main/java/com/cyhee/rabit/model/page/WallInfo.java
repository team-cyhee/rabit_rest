package com.cyhee.rabit.model.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WallInfo {
    private Long id;
    private String username;
    private Integer followerNum;
    private Integer followeeNum;
    private List<String> goalContents;
}
