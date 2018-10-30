package com.cyhee.rabit.model.goal;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGoal is a Querydsl query type for Goal
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGoal extends EntityPathBase<Goal> {

    private static final long serialVersionUID = 1039295024L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGoal goal = new QGoal("goal");

    public final com.cyhee.rabit.model.cmm.QTimestampEntity _super = new com.cyhee.rabit.model.cmm.QTimestampEntity(this);

    public final com.cyhee.rabit.model.user.QUser author;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.util.Date> createDate = _super.createDate;

    public final NumberPath<Integer> doTimes = createNumber("doTimes", Integer.class);

    public final EnumPath<GoalUnit> doUnit = createEnum("doUnit", GoalUnit.class);

    public final DateTimePath<java.util.Date> endDate = createDateTime("endDate", java.util.Date.class);

    public final ListPath<com.cyhee.rabit.model.file.FileInfo, com.cyhee.rabit.model.file.QFileInfo> files = this.<com.cyhee.rabit.model.file.FileInfo, com.cyhee.rabit.model.file.QFileInfo>createList("files", com.cyhee.rabit.model.file.FileInfo.class, com.cyhee.rabit.model.file.QFileInfo.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.util.Date> lastUpdated = _super.lastUpdated;

    public final QGoal parent;

    public final DateTimePath<java.util.Date> startDate = createDateTime("startDate", java.util.Date.class);

    public final EnumPath<com.cyhee.rabit.model.cmm.ContentStatus> status = createEnum("status", com.cyhee.rabit.model.cmm.ContentStatus.class);

    public QGoal(String variable) {
        this(Goal.class, forVariable(variable), INITS);
    }

    public QGoal(Path<? extends Goal> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGoal(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGoal(PathMetadata metadata, PathInits inits) {
        this(Goal.class, metadata, inits);
    }

    public QGoal(Class<? extends Goal> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new com.cyhee.rabit.model.user.QUser(forProperty("author")) : null;
        this.parent = inits.isInitialized("parent") ? new QGoal(forProperty("parent"), inits.get("parent")) : null;
    }

}

