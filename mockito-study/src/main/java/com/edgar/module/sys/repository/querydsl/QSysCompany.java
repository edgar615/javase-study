package com.edgar.module.sys.repository.querydsl;

import com.edgar.module.sys.repository.domain.SysCompany;
import com.mysema.query.sql.ColumnMetadata;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.BooleanPath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import javax.annotation.Generated;

import static com.mysema.query.types.PathMetadataFactory.forVariable;


/**
 * QSysCompany is a Querydsl query type for SysCompany
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSysCompany extends com.mysema.query.sql.RelationalPathBase<SysCompany> {

    private static final long serialVersionUID = 2114528471;

    public static final QSysCompany sysCompany = new QSysCompany("sys_company");

    public final StringPath companyCode = createString("companyCode");

    public final NumberPath<Integer> companyId = createNumber("companyId", Integer.class);

    public final StringPath companyName = createString("companyName");

    public final BooleanPath isDel = createBoolean("isDel");

    public final com.mysema.query.sql.PrimaryKey<SysCompany> primary = createPrimaryKey(companyId);

    public QSysCompany(String variable) {
        super(SysCompany.class, forVariable(variable), "null", "sys_company");
        addMetadata();
    }

    public QSysCompany(String variable, String schema, String table) {
        super(SysCompany.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QSysCompany(Path<? extends SysCompany> path) {
        super(path.getType(), path.getMetadata(), "null", "sys_company");
        addMetadata();
    }

    public QSysCompany(PathMetadata<?> metadata) {
        super(SysCompany.class, metadata, "null", "sys_company");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(companyCode, ColumnMetadata.named("company_code").ofType(12).withSize(8).notNull());
        addMetadata(companyId, ColumnMetadata.named("company_id").ofType(4).withSize(10).notNull());
        addMetadata(companyName, ColumnMetadata.named("company_name").ofType(12).withSize(64).notNull());
        addMetadata(isDel, ColumnMetadata.named("is_del").ofType(-7).notNull());
    }

}

