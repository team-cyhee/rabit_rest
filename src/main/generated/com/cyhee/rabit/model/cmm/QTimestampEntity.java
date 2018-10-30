package com.cyhee.rabit.model.cmm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTimestampEntity is a Querydsl query type for TimestampEntity
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QTimestampEntity extends EntityPathBase<TimestampEntity> {

    private static final long serialVersionUID = 980478032L;

    public static final QTimestampEntity timestampEntity = new QTimestampEntity("timestampEntity");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final DateTimePath<java.util.Date> createDate = createDateTime("createDate", java.util.Date.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final DateTimePath<java.util.Date> lastUpdated = createDateTime("lastUpdated", java.util.Date.class);

    public QTimestampEntity(String variable) {
        super(TimestampEntity.class, forVariable(variable));
    }

    public QTimestampEntity(Path<? extends TimestampEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTimestampEntity(PathMetadata metadata) {
        super(TimestampEntity.class, metadata);
    }

}

