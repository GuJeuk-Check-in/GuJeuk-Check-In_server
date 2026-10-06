package com.example.gujeuck_server.domain.common.funnel.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCheckInFunnelEvent is a Querydsl query type for CheckInFunnelEvent
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCheckInFunnelEvent extends EntityPathBase<CheckInFunnelEvent> {

    private static final long serialVersionUID = 1160574961L;

    public static final QCheckInFunnelEvent checkInFunnelEvent = new QCheckInFunnelEvent("checkInFunnelEvent");

    public final com.example.gujeuck_server.global.entity.QBaseIdEntity _super = new com.example.gujeuck_server.global.entity.QBaseIdEntity(this);

    public final EnumPath<com.example.gujeuck_server.domain.user.domain.enums.Age> ageGroup = createEnum("ageGroup", com.example.gujeuck_server.domain.user.domain.enums.Age.class);

    public final StringPath clientEventId = createString("clientEventId");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> elapsedMsFromStart = createNumber("elapsedMsFromStart", Long.class);

    public final StringPath eventName = createString("eventName");

    public final StringPath failureReason = createString("failureReason");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final BooleanPath isExistingUser = createBoolean("isExistingUser");

    public final DateTimePath<java.time.LocalDateTime> occurredAt = createDateTime("occurredAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> organId = createNumber("organId", Long.class);

    public final StringPath purpose = createString("purpose");

    public final StringPath sessionId = createString("sessionId");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final NumberPath<Long> visitCount = createNumber("visitCount", Long.class);

    public final EnumPath<com.example.gujeuck_server.domain.common.funnel.domain.enums.VisitCountBucket> visitCountBucket = createEnum("visitCountBucket", com.example.gujeuck_server.domain.common.funnel.domain.enums.VisitCountBucket.class);

    public QCheckInFunnelEvent(String variable) {
        super(CheckInFunnelEvent.class, forVariable(variable));
    }

    public QCheckInFunnelEvent(Path<? extends CheckInFunnelEvent> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCheckInFunnelEvent(PathMetadata metadata) {
        super(CheckInFunnelEvent.class, metadata);
    }

}

