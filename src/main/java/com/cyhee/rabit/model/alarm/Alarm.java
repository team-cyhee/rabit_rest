package com.cyhee.rabit.model.alarm;

import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.cmm.TimestampEntity;
import com.cyhee.rabit.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@AllArgsConstructor
@NoArgsConstructor
public class Alarm extends TimestampEntity {

	@ManyToOne(optional=false)
	@JoinColumn(name="owner_id", foreignKey = @ForeignKey(name = "FK_USER_OWNER"))
	@OnDelete(action=OnDeleteAction.CASCADE)
	private User owner;

	@ManyToOne(optional=false)
	@JoinColumn(name="author_id", foreignKey = @ForeignKey(name = "FK_USER_OWNER"))
	@OnDelete(action=OnDeleteAction.CASCADE)
	private User author;

	@Column
	@Enumerated(EnumType.STRING)
	private ContentType target;

	@Column
	@Enumerated(EnumType.STRING)
	private ContentType action;

	@Column
	private Long actionId;
}
