package com.example.todolist.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDo is a Querydsl query type for Do
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDo extends EntityPathBase<Do> {

    private static final long serialVersionUID = 2118615580L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDo do$ = new QDo("do$");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final BooleanPath status = createBoolean("status");

    public QDo(String variable) {
        this(Do.class, forVariable(variable), INITS);
    }

    public QDo(Path<? extends Do> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDo(PathMetadata metadata, PathInits inits) {
        this(Do.class, metadata, inits);
    }

    public QDo(Class<? extends Do> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

