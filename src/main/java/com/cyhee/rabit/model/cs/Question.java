package com.cyhee.rabit.model.cs;

import com.cyhee.rabit.model.cmm.ContentStatus;
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
public class Question extends TimestampEntity {

	@ManyToOne(optional=false)
	@JoinColumn(name="author_id", foreignKey = @ForeignKey(name = "FK_USER_QUESTION"))
	@OnDelete(action= OnDeleteAction.CASCADE)
	private User author;

	@Column(columnDefinition = "TEXT")
	private String content;

	@Column(nullable=false)
	private ContentStatus status = ContentStatus.ACTIVE;
}
