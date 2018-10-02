package com.cyhee.rabit.model.follow;

import com.cyhee.rabit.model.cmm.RadioStatus;
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

    @ManyToOne
    @JoinColumn(name="follower_id", foreignKey = @ForeignKey(name = "FK_USER_FOLLOWER"))
    @OnDelete(action= OnDeleteAction.CASCADE)
    private User follower;

    @ManyToOne
    @JoinColumn(name="followee_id", foreignKey = @ForeignKey(name = "FK_USER_FOLLOWEE"))
    @OnDelete(action= OnDeleteAction.CASCADE)
    private User followee;

    @Column(nullable=false)
    private RadioStatus status = RadioStatus.ACTIVE;
}
