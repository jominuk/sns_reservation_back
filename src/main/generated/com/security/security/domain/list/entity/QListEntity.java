package com.security.security.domain.list.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QListEntity is a Querydsl query type for ListEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QListEntity extends EntityPathBase<ListEntity> {

    private static final long serialVersionUID = 197708313L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QListEntity listEntity = new QListEntity("listEntity");

    public final com.security.security.domain.base.QBaseEntity _super = new com.security.security.domain.base.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deactivatedAt = _super.deactivatedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    //inherited
    public final BooleanPath isActive = _super.isActive;

    public final com.security.security.domain.mamber.entity.QMemberEntity member;

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QListEntity(String variable) {
        this(ListEntity.class, forVariable(variable), INITS);
    }

    public QListEntity(Path<? extends ListEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QListEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QListEntity(PathMetadata metadata, PathInits inits) {
        this(ListEntity.class, metadata, inits);
    }

    public QListEntity(Class<? extends ListEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.security.security.domain.mamber.entity.QMemberEntity(forProperty("member")) : null;
    }

}

