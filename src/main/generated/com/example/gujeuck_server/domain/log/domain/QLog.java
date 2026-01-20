package com.example.gujeuck_server.domain.log.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLog is a Querydsl query type for Log
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLog extends EntityPathBase<Log> {

    private static final long serialVersionUID = -1687397569L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLog log = new QLog("log");

    public final com.example.gujeuck_server.global.entity.QBaseIdEntity _super = new com.example.gujeuck_server.global.entity.QBaseIdEntity(this);

    public final com.example.gujeuck_server.domain.admin.domain.QAdmin admin;

    public final EnumPath<com.example.gujeuck_server.domain.user.domain.enums.Age> age = createEnum("age", com.example.gujeuck_server.domain.user.domain.enums.Age.class);

    public final NumberPath<Integer> femaleCount = createNumber("femaleCount", Integer.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Integer> maleCount = createNumber("maleCount", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath phone = createString("phone");

    public final BooleanPath privacyAgreed = createBoolean("privacyAgreed");

    public final StringPath purpose = createString("purpose");

    public final StringPath residence = createString("residence");

    public final com.example.gujeuck_server.domain.user.domain.QUser user;

    public final StringPath visitDate = createString("visitDate");

    public final StringPath visitTime = createString("visitTime");

    public final NumberPath<Integer> year = createNumber("year", Integer.class);

    public QLog(String variable) {
        this(Log.class, forVariable(variable), INITS);
    }

    public QLog(Path<? extends Log> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLog(PathMetadata metadata, PathInits inits) {
        this(Log.class, metadata, inits);
    }

    public QLog(Class<? extends Log> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.admin = inits.isInitialized("admin") ? new com.example.gujeuck_server.domain.admin.domain.QAdmin(forProperty("admin")) : null;
        this.user = inits.isInitialized("user") ? new com.example.gujeuck_server.domain.user.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

