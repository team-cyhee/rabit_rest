package com.cyhee.rabit.model.goallog;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGoalLog is a Querydsl query type for GoalLog
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGoalLog extends EntityPathBase<GoalLog> {

    private static final long serialVersionUID = -1831463690L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGoalLog goalLog = new QGoalLog("goalLog");

    public final com.cyhee.rabit.model.cmm.QTimestampEntity _super = new com.cyhee.rabit.model.cmm.QTimestampEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.util.Date> createDate = _super.createDate;

    public final ListPath<com.cyhee.rabit.model.file.FileInfo, com.cyhee.rabit.model.file.QFileInfo> file = this.<com.cyhee.rabit.model.file.FileInfo, com.cyhee.rabit.model.file.QFileInfo>createList("file", com.cyhee.rabit.model.file.FileInfo.class, com.cyhee.rabit.model.file.QFileInfo.class, PathInits.DIRECT2);

    public final com.cyhee.rabit.model.goal.QGoal goal;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.util.Date> lastUpdated = _super.lastUpdated;

    public final EnumPath<com.cyhee.rabit.model.cmm.ContentStatus> status = createEnum("status", com.cyhee.rabit.model.cmm.ContentStatus.class);

    public QGoalLog(String variable) {
        this(GoalLog.class, forVariable(variable), INITS);
    }

    public QGoalLog(Path<? extends GoalLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGoalLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGoalLog(PathMetadata metadata, PathInits inits) {
        this(GoalLog.class, metadata, inits);
    }

    public QGoalLog(Class<? extends GoalLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.goal = inits.isInitialized("goal") ? new com.cyhee.rabit.model.goal.QGoal(forProperty("goal"), inits.get("goal")) : null;
    }

}

