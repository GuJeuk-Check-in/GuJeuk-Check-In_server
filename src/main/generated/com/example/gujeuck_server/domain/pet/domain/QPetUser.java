package com.example.gujeuck_server.domain.pet.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPetUser is a Querydsl query type for PetUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPetUser extends EntityPathBase<PetUser> {

    private static final long serialVersionUID = 783535306L;

    public static final QPetUser petUser = new QPetUser("petUser");

    public final com.example.gujeuck_server.global.entity.QBaseIdEntity _super = new com.example.gujeuck_server.global.entity.QBaseIdEntity(this);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    public final StringPath phone = createString("phone");

    public QPetUser(String variable) {
        super(PetUser.class, forVariable(variable));
    }

    public QPetUser(Path<? extends PetUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPetUser(PathMetadata metadata) {
        super(PetUser.class, metadata);
    }

}

