package com.cyhee.rabit.model.follow;

import com.cyhee.rabit.model.cmm.TimestampEntity;
import com.cyhee.rabit.model.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
public class Follow extends TimestampEntity {

    @ManyToMany
    @JoinColumn(name="follower_id", foreignKey = @ForeignKey(name = "FK_USER_FOLLOWER"))
    @OnDelete(action= OnDeleteAction.CASCADE)
    private User Follower;

    @ManyToMany
    @JoinColumn(name="following_id", foreignKey = @ForeignKey(name = "FK_USER_FOLLOWING"))
    @OnDelete(action= OnDeleteAction.CASCADE)
    private User Following;

    @Column(nullable=false)
    private FollowStatus status = FollowStatus.ACTIVE;
}
