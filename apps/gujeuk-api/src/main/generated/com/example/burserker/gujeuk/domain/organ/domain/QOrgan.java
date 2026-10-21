package com.example.burserker.gujeuk.domain.organ.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrgan is a Querydsl query type for Organ
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrgan extends EntityPathBase<Organ> {

    private static final long serialVersionUID = -2051239257L;

    public static final QOrgan organ = new QOrgan("organ");

    public final com.example.burserker.gujeuk.global.entity.QBaseIdEntity _super = new com.example.burserker.gujeuk.global.entity.QBaseIdEntity(this);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath organName = createString("organName");

    public final StringPath password = createString("password");

    public QOrgan(String variable) {
        super(Organ.class, forVariable(variable));
    }

    public QOrgan(Path<? extends Organ> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrgan(PathMetadata metadata) {
        super(Organ.class, metadata);
    }

}

