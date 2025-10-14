package com.example.gujeuck_server.domain.purpose.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPurpose is a Querydsl query type for Purpose
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPurpose extends EntityPathBase<Purpose> {

    private static final long serialVersionUID = 79417630L;

    public static final QPurpose purpose1 = new QPurpose("purpose1");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath purpose = createString("purpose");

    public final StringPath purposeImage = createString("purposeImage");

    public QPurpose(String variable) {
        super(Purpose.class, forVariable(variable));
    }

    public QPurpose(Path<? extends Purpose> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPurpose(PathMetadata metadata) {
        super(Purpose.class, metadata);
    }

}

