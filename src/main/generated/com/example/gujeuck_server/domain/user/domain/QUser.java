package com.example.gujeuck_server.domain.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1626900978L;

    public static final QUser user = new QUser("user");

    public final EnumPath<com.example.gujeuck_server.domain.user.domain.enums.Age> age = createEnum("age", com.example.gujeuck_server.domain.user.domain.enums.Age.class);

    public final NumberPath<Long> allUserCount = createNumber("allUserCount", Long.class);

    public final StringPath birthYMD = createString("birthYMD");

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final EnumPath<com.example.gujeuck_server.domain.user.domain.enums.Gender> gender = createEnum("gender", com.example.gujeuck_server.domain.user.domain.enums.Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath phone = createString("phone");

    public final BooleanPath privacyAgreed = createBoolean("privacyAgreed");

    public final StringPath residence = createString("residence");

    public final StringPath userId = createString("userId");

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

