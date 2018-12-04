package com.cyhee.rabit.model.report;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReport is a Querydsl query type for Report
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QReport extends EntityPathBase<Report> {

    private static final long serialVersionUID = 34602832L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReport report = new QReport("report");

    public final com.cyhee.rabit.model.cmm.QTimestampEntity _super = new com.cyhee.rabit.model.cmm.QTimestampEntity(this);

    //inherited
    public final DateTimePath<java.util.Date> createDate = _super.createDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.util.Date> lastUpdated = _super.lastUpdated;

    public final com.cyhee.rabit.model.user.QUser reporter;

    public final EnumPath<com.cyhee.rabit.model.cmm.ContentStatus> status = createEnum("status", com.cyhee.rabit.model.cmm.ContentStatus.class);

    public final NumberPath<Long> targetId = createNumber("targetId", Long.class);

    public final EnumPath<com.cyhee.rabit.model.cmm.ContentType> targetType = createEnum("targetType", com.cyhee.rabit.model.cmm.ContentType.class);

    public final EnumPath<ReportType> type = createEnum("type", ReportType.class);

    public QReport(String variable) {
        this(Report.class, forVariable(variable), INITS);
    }

    public QReport(Path<? extends Report> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReport(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReport(PathMetadata metadata, PathInits inits) {
        this(Report.class, metadata, inits);
    }

    public QReport(Class<? extends Report> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.reporter = inits.isInitialized("reporter") ? new com.cyhee.rabit.model.user.QUser(forProperty("reporter")) : null;
    }

}

