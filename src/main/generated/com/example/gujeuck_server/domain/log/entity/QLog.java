package com.example.gujeuck_server.domain.log.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLog is a Querydsl query type for Log
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLog extends EntityPathBase<Log> {

    private static final long serialVersionUID = -1926511906L;

    public static final QLog log = new QLog("log");

    public final EnumPath<com.example.gujeuck_server.domain.user.entity.enums.Age> age = createEnum("age", com.example.gujeuck_server.domain.user.entity.enums.Age.class);

    public final NumberPath<Integer> femaleCount = createNumber("femaleCount", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> maleCount = createNumber("maleCount", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath phone = createString("phone");

    public final BooleanPath privacyAgreed = createBoolean("privacyAgreed");

    public final StringPath purpose = createString("purpose");

    public final StringPath visitDate = createString("visitDate");

    public final NumberPath<Integer> year = createNumber("year", Integer.class);

    public QLog(String variable) {
        super(Log.class, forVariable(variable));
    }

    public QLog(Path<? extends Log> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLog(PathMetadata metadata) {
        super(Log.class, metadata);
    }

}

