package com.edgar.module.sys.repository.querydsl;

import com.edgar.module.sys.repository.domain.CompanyConfig;
import com.mysema.query.sql.ColumnMetadata;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import javax.annotation.Generated;

import static com.mysema.query.types.PathMetadataFactory.forVariable;


/**
 * QCompanyConfig is a Querydsl query type for CompanyConfig
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QCompanyConfig extends com.mysema.query.sql.RelationalPathBase<CompanyConfig> {

    private static final long serialVersionUID = 496393432;

    public static final QCompanyConfig companyConfig = new QCompanyConfig("company_config");

    public final NumberPath<Integer> companyId = createNumber("companyId", Integer.class);

    public final NumberPath<Integer> configId = createNumber("configId", Integer.class);

    public final StringPath configKey = createString("configKey");

    public final StringPath configValue = createString("configValue");

    public final DateTimePath<java.sql.Timestamp> createdTime = createDateTime("createdTime", java.sql.Timestamp.class);

    public final StringPath description = createString("description");

    public final DateTimePath<java.sql.Timestamp> updatedTime = createDateTime("updatedTime", java.sql.Timestamp.class);

    public final com.mysema.query.sql.PrimaryKey<CompanyConfig> primary = createPrimaryKey(configId);

    public QCompanyConfig(String variable) {
        super(CompanyConfig.class, forVariable(variable), "null", "company_config");
        addMetadata();
    }

    public QCompanyConfig(String variable, String schema, String table) {
        super(CompanyConfig.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QCompanyConfig(Path<? extends CompanyConfig> path) {
        super(path.getType(), path.getMetadata(), "null", "company_config");
        addMetadata();
    }

    public QCompanyConfig(PathMetadata<?> metadata) {
        super(CompanyConfig.class, metadata, "null", "company_config");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(companyId, ColumnMetadata.named("company_id").ofType(4).withSize(10));
        addMetadata(configId, ColumnMetadata.named("config_id").ofType(4).withSize(10).notNull());
        addMetadata(configKey, ColumnMetadata.named("config_key").ofType(12).withSize(16).notNull());
        addMetadata(configValue, ColumnMetadata.named("config_value").ofType(12).withSize(16).notNull());
        addMetadata(createdTime, ColumnMetadata.named("created_time").ofType(93).withSize(19).notNull());
        addMetadata(description, ColumnMetadata.named("description").ofType(12).withSize(128));
        addMetadata(updatedTime, ColumnMetadata.named("updated_time").ofType(93).withSize(19).notNull());
    }

}

