package com.cyhee.rabit.model.alarm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlarm is a Querydsl query type for Alarm
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAlarm extends EntityPathBase<Alarm> {

    private static final long serialVersionUID = 2003646678L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlarm alarm = new QAlarm("alarm");

    public final com.cyhee.rabit.model.cmm.QTimestampEntity _super = new com.cyhee.rabit.model.cmm.QTimestampEntity(this);

    public final EnumPath<com.cyhee.rabit.model.cmm.ContentType> action = createEnum("action", com.cyhee.rabit.model.cmm.ContentType.class);

    public final NumberPath<Long> actionId = createNumber("actionId", Long.class);

    public final com.cyhee.rabit.model.user.QUser author;

    //inherited
    public final DateTimePath<java.util.Date> createDate = _super.createDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.util.Date> lastUpdated = _super.lastUpdated;

    public final com.cyhee.rabit.model.user.QUser owner;

    public final EnumPath<com.cyhee.rabit.model.cmm.ContentType> target = createEnum("target", com.cyhee.rabit.model.cmm.ContentType.class);

    public final NumberPath<Long> targetId = createNumber("targetId", Long.class);

    public QAlarm(String variable) {
        this(Alarm.class, forVariable(variable), INITS);
    }

    public QAlarm(Path<? extends Alarm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlarm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlarm(PathMetadata metadata, PathInits inits) {
        this(Alarm.class, metadata, inits);
    }

    public QAlarm(Class<? extends Alarm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new com.cyhee.rabit.model.user.QUser(forProperty("author")) : null;
        this.owner = inits.isInitialized("owner") ? new com.cyhee.rabit.model.user.QUser(forProperty("owner")) : null;
    }

}

