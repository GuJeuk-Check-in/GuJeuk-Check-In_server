package com.example.gujeuck_server.domain.pet.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPet is a Querydsl query type for Pet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPet extends EntityPathBase<Pet> {

    private static final long serialVersionUID = 902476895L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPet pet = new QPet("pet");

    public final com.example.gujeuck_server.global.entity.QBaseIdEntity _super = new com.example.gujeuck_server.global.entity.QBaseIdEntity(this);

    public final NumberPath<Integer> clean = createNumber("clean", Integer.class);

    public final NumberPath<Integer> energy = createNumber("energy", Integer.class);

    public final StringPath equippedHat = createString("equippedHat");

    public final StringPath equippedShirt = createString("equippedShirt");

    public final NumberPath<Integer> happy = createNumber("happy", Integer.class);

    public final NumberPath<Integer> hunger = createNumber("hunger", Integer.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final DatePath<java.time.LocalDate> lastCheckinDate = createDate("lastCheckinDate", java.time.LocalDate.class);

    public final DateTimePath<java.time.Instant> lastSeenAt = createDateTime("lastSeenAt", java.time.Instant.class);

    public final NumberPath<Integer> maxStreak = createNumber("maxStreak", Integer.class);

    public final StringPath name = createString("name");

    public final SetPath<String, StringPath> ownedBuffs = this.<String, StringPath>createSet("ownedBuffs", String.class, StringPath.class, PathInits.DIRECT2);

    public final SetPath<String, StringPath> ownedHats = this.<String, StringPath>createSet("ownedHats", String.class, StringPath.class, PathInits.DIRECT2);

    public final SetPath<String, StringPath> ownedProps = this.<String, StringPath>createSet("ownedProps", String.class, StringPath.class, PathInits.DIRECT2);

    public final SetPath<String, StringPath> ownedShirts = this.<String, StringPath>createSet("ownedShirts", String.class, StringPath.class, PathInits.DIRECT2);

    public final QPetUser petUser;

    public final ListPath<String, StringPath> placedProps = this.<String, StringPath>createList("placedProps", String.class, StringPath.class, PathInits.DIRECT2);

    public final NumberPath<Integer> points = createNumber("points", Integer.class);

    public final EnumPath<com.example.gujeuck_server.domain.pet.domain.enums.PetSpecies> species = createEnum("species", com.example.gujeuck_server.domain.pet.domain.enums.PetSpecies.class);

    public final NumberPath<Integer> streak = createNumber("streak", Integer.class);

    public final NumberPath<Integer> visits = createNumber("visits", Integer.class);

    public final NumberPath<Integer> xp = createNumber("xp", Integer.class);

    public QPet(String variable) {
        this(Pet.class, forVariable(variable), INITS);
    }

    public QPet(Path<? extends Pet> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPet(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPet(PathMetadata metadata, PathInits inits) {
        this(Pet.class, metadata, inits);
    }

    public QPet(Class<? extends Pet> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.petUser = inits.isInitialized("petUser") ? new QPetUser(forProperty("petUser")) : null;
    }

}

