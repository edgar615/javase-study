package com.edgar.module.sys.repository.querydsl;

import com.edgar.module.sys.repository.domain.Sequence;
import com.mysema.query.sql.ColumnMetadata;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import javax.annotation.Generated;

import static com.mysema.query.types.PathMetadataFactory.forVariable;


/**
 * QSequence is a Querydsl query type for Sequence
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSequence extends com.mysema.query.sql.RelationalPathBase<Sequence> {

    private static final long serialVersionUID = 1016145256;

    public static final QSequence sequence = new QSequence("sequence");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath stub = createString("stub");

    public final com.mysema.query.sql.PrimaryKey<Sequence> primary = createPrimaryKey(id);

    public QSequence(String variable) {
        super(Sequence.class, forVariable(variable), "null", "sequence");
        addMetadata();
    }

    public QSequence(String variable, String schema, String table) {
        super(Sequence.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QSequence(Path<? extends Sequence> path) {
        super(path.getType(), path.getMetadata(), "null", "sequence");
        addMetadata();
    }

    public QSequence(PathMetadata<?> metadata) {
        super(Sequence.class, metadata, "null", "sequence");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(id, ColumnMetadata.named("id").ofType(-5).withSize(19).notNull());
        addMetadata(stub, ColumnMetadata.named("stub").ofType(1).withSize(1).notNull());
    }

}

