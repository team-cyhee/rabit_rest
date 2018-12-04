package com.cyhee.rabit.model.notice;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QNotice is a Querydsl query type for Notice
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QNotice extends EntityPathBase<Notice> {

    private static final long serialVersionUID = 1649778384L;

    public static final QNotice notice = new QNotice("notice");

    public final com.cyhee.rabit.model.cmm.QTimestampEntity _super = new com.cyhee.rabit.model.cmm.QTimestampEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.util.Date> createDate = _super.createDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.util.Date> lastUpdated = _super.lastUpdated;

    public final EnumPath<com.cyhee.rabit.model.cmm.ContentStatus> status = createEnum("status", com.cyhee.rabit.model.cmm.ContentStatus.class);

    public final StringPath title = createString("title");

    public QNotice(String variable) {
        super(Notice.class, forVariable(variable));
    }

    public QNotice(Path<? extends Notice> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNotice(PathMetadata metadata) {
        super(Notice.class, metadata);
    }

}

