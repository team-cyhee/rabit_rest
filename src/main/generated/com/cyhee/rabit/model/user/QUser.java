package com.cyhee.rabit.model.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -686713552L;

    public static final QUser user = new QUser("user");

    public final com.cyhee.rabit.model.cmm.QTimestampEntity _super = new com.cyhee.rabit.model.cmm.QTimestampEntity(this);

    public final DatePath<java.util.Date> birth = createDate("birth", java.util.Date.class);

    //inherited
    public final DateTimePath<java.util.Date> createDate = _super.createDate;

    public final StringPath email = createString("email");

    public final ListPath<com.cyhee.rabit.model.file.FileInfo, com.cyhee.rabit.model.file.QFileInfo> files = this.<com.cyhee.rabit.model.file.FileInfo, com.cyhee.rabit.model.file.QFileInfo>createList("files", com.cyhee.rabit.model.file.FileInfo.class, com.cyhee.rabit.model.file.QFileInfo.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath introduction = createString("introduction");

    //inherited
    public final DateTimePath<java.util.Date> lastUpdated = _super.lastUpdated;

    public final StringPath name = createString("name");

    public final StringPath phone = createString("phone");

    public final EnumPath<UserStatus> status = createEnum("status", UserStatus.class);

    public final StringPath username = createString("username");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

