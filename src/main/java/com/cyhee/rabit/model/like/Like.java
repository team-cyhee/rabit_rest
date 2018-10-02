
package com.cyhee.rabit.model.like;

import com.cyhee.rabit.model.cmm.ContentType;
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
public class Like extends TimestampEntity {
	
	@ManyToOne(optional=false)
	@JoinColumn(name="author_id", foreignKey = @ForeignKey(name = "FK_LIKE_AUTHOR"))
	@OnDelete(action=OnDeleteAction.CASCADE)
	private User author;
	
	@Column
	@Enumerated(EnumType.STRING)
	private ContentType type;
	
	@Column
	private Long parentId;

	@Column(nullable=false)
	private RadioStatus status = RadioStatus.ACTIVE;
}
