package com.example.gujeuck_server.domain.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1490676173L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final com.example.gujeuck_server.global.entity.QBaseIdEntity _super = new com.example.gujeuck_server.global.entity.QBaseIdEntity(this);

    public final EnumPath<com.example.gujeuck_server.domain.user.domain.enums.Age> age = createEnum("age", com.example.gujeuck_server.domain.user.domain.enums.Age.class);

    public final NumberPath<Long> allUserCount = createNumber("allUserCount", Long.class);

    public final StringPath birthYMD = createString("birthYMD");

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final EnumPath<com.example.gujeuck_server.domain.user.domain.enums.Gender> gender = createEnum("gender", com.example.gujeuck_server.domain.user.domain.enums.Gender.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    public final com.example.gujeuck_server.domain.organ.domain.QOrgan organ;

    public final StringPath phone = createString("phone");

    public final BooleanPath privacyAgreed = createBoolean("privacyAgreed");

    public final StringPath residence = createString("residence");

    public final StringPath userId = createString("userId");

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.organ = inits.isInitialized("organ") ? new com.example.gujeuck_server.domain.organ.domain.QOrgan(forProperty("organ")) : null;
    }

}

