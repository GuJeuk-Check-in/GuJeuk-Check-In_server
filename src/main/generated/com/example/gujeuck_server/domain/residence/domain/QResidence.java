package com.example.gujeuck_server.domain.residence.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QResidence is a Querydsl query type for Residence
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QResidence extends EntityPathBase<Residence> {

    private static final long serialVersionUID = 843411199L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QResidence residence = new QResidence("residence");

    public final com.example.gujeuck_server.global.entity.QBaseIdEntity _super = new com.example.gujeuck_server.global.entity.QBaseIdEntity(this);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final com.example.gujeuck_server.domain.organ.domain.QOrgan organ;

    public final NumberPath<Integer> residenceIndex = createNumber("residenceIndex", Integer.class);

    public final StringPath residenceName = createString("residenceName");

    public QResidence(String variable) {
        this(Residence.class, forVariable(variable), INITS);
    }

    public QResidence(Path<? extends Residence> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QResidence(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QResidence(PathMetadata metadata, PathInits inits) {
        this(Residence.class, metadata, inits);
    }

    public QResidence(Class<? extends Residence> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.organ = inits.isInitialized("organ") ? new com.example.gujeuck_server.domain.organ.domain.QOrgan(forProperty("organ")) : null;
    }

}

