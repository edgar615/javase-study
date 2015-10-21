package com.edgar.module.sys.repository.querydsl;

import com.edgar.module.sys.repository.domain.SysDict;
import com.mysema.query.sql.ColumnMetadata;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import javax.annotation.Generated;

import static com.mysema.query.types.PathMetadataFactory.forVariable;


/**
 * QSysDict is a Querydsl query type for SysDict
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSysDict extends com.mysema.query.sql.RelationalPathBase<SysDict> {

    private static final long serialVersionUID = 1714131356;

    public static final QSysDict sysDict = new QSysDict("sys_dict");

    public final DateTimePath<java.sql.Timestamp> createdTime = createDateTime("createdTime", java.sql.Timestamp.class);

    public final StringPath dictCode = createString("dictCode");

    public final StringPath dictName = createString("dictName");

    public final StringPath parentCode = createString("parentCode");

    public final NumberPath<Integer> sorted = createNumber("sorted", Integer.class);

    public final DateTimePath<java.sql.Timestamp> updatedTime = createDateTime("updatedTime", java.sql.Timestamp.class);

    public final com.mysema.query.sql.PrimaryKey<SysDict> primary = createPrimaryKey(dictCode);

    public QSysDict(String variable) {
        super(SysDict.class, forVariable(variable), "null", "sys_dict");
        addMetadata();
    }

    public QSysDict(String variable, String schema, String table) {
        super(SysDict.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QSysDict(Path<? extends SysDict> path) {
        super(path.getType(), path.getMetadata(), "null", "sys_dict");
        addMetadata();
    }

    public QSysDict(PathMetadata<?> metadata) {
        super(SysDict.class, metadata, "null", "sys_dict");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdTime, ColumnMetadata.named("created_time").ofType(93).withSize(19).notNull());
        addMetadata(dictCode, ColumnMetadata.named("dict_code").ofType(12).withSize(32).notNull());
        addMetadata(dictName, ColumnMetadata.named("dict_name").ofType(12).withSize(32).notNull());
        addMetadata(parentCode, ColumnMetadata.named("parent_code").ofType(12).withSize(32).notNull());
        addMetadata(sorted, ColumnMetadata.named("sorted").ofType(4).withSize(10).notNull());
        addMetadata(updatedTime, ColumnMetadata.named("updated_time").ofType(93).withSize(19).notNull());
    }

}

