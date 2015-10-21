package com.edgar.module.sys.repository.querydsl;

import com.edgar.module.sys.repository.domain.TestTable;
import com.mysema.query.sql.ColumnMetadata;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import javax.annotation.Generated;

import static com.mysema.query.types.PathMetadataFactory.forVariable;


/**
 * QTestTable is a Querydsl query type for TestTable
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QTestTable extends com.mysema.query.sql.RelationalPathBase<TestTable> {

    private static final long serialVersionUID = 1358567829;

    public static final QTestTable testTable = new QTestTable("test_table");

    public final DateTimePath<java.sql.Timestamp> createdTime = createDateTime("createdTime", java.sql.Timestamp.class);

    public final StringPath dictName = createString("dictName");

    public final StringPath parentCode = createString("parentCode");

    public final NumberPath<Integer> sorted = createNumber("sorted", Integer.class);

    public final StringPath testCode = createString("testCode");

    public final DateTimePath<java.sql.Timestamp> updatedTime = createDateTime("updatedTime", java.sql.Timestamp.class);

    public final com.mysema.query.sql.PrimaryKey<TestTable> primary = createPrimaryKey(testCode);

    public QTestTable(String variable) {
        super(TestTable.class, forVariable(variable), "null", "test_table");
        addMetadata();
    }

    public QTestTable(String variable, String schema, String table) {
        super(TestTable.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QTestTable(Path<? extends TestTable> path) {
        super(path.getType(), path.getMetadata(), "null", "test_table");
        addMetadata();
    }

    public QTestTable(PathMetadata<?> metadata) {
        super(TestTable.class, metadata, "null", "test_table");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdTime, ColumnMetadata.named("created_time").ofType(93).withSize(19).notNull());
        addMetadata(dictName, ColumnMetadata.named("dict_name").ofType(12).withSize(32).notNull());
        addMetadata(parentCode, ColumnMetadata.named("parent_code").ofType(12).withSize(32).notNull());
        addMetadata(sorted, ColumnMetadata.named("sorted").ofType(4).withSize(10).notNull());
        addMetadata(testCode, ColumnMetadata.named("test_code").ofType(12).withSize(32).notNull());
        addMetadata(updatedTime, ColumnMetadata.named("updated_time").ofType(93).withSize(19).notNull());
    }

}

