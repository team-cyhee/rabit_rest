package com.cyhee.rabit.model.follow;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFollow is a Querydsl query type for Follow
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFollow extends EntityPathBase<Follow> {

    private static final long serialVersionUID = 1465133232L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFollow follow = new QFollow("follow");

    public final com.cyhee.rabit.model.cmm.QTimestampEntity _super = new com.cyhee.rabit.model.cmm.QTimestampEntity(this);

    //inherited
    public final DateTimePath<java.util.Date> createDate = _super.createDate;

    public final com.cyhee.rabit.model.user.QUser followee;

    public final com.cyhee.rabit.model.user.QUser follower;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.util.Date> lastUpdated = _super.lastUpdated;

    public final EnumPath<com.cyhee.rabit.model.cmm.RadioStatus> status = createEnum("status", com.cyhee.rabit.model.cmm.RadioStatus.class);

    public QFollow(String variable) {
        this(Follow.class, forVariable(variable), INITS);
    }

    public QFollow(Path<? extends Follow> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFollow(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFollow(PathMetadata metadata, PathInits inits) {
        this(Follow.class, metadata, inits);
    }

    public QFollow(Class<? extends Follow> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.followee = inits.isInitialized("followee") ? new com.cyhee.rabit.model.user.QUser(forProperty("followee")) : null;
        this.follower = inits.isInitialized("follower") ? new com.cyhee.rabit.model.user.QUser(forProperty("follower")) : null;
    }

}

