package com.edgar.module.sys.repository.querydsl;

import com.edgar.module.sys.repository.domain.SysJob;
import com.mysema.query.sql.ColumnMetadata;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.BooleanPath;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import javax.annotation.Generated;

import static com.mysema.query.types.PathMetadataFactory.forVariable;


/**
 * QSysJob is a Querydsl query type for SysJob
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSysJob extends com.mysema.query.sql.RelationalPathBase<SysJob> {

    private static final long serialVersionUID = -83246825;

    public static final QSysJob sysJob = new QSysJob("sys_job");

    public final StringPath clazzName = createString("clazzName");

    public final DateTimePath<java.sql.Timestamp> createdTime = createDateTime("createdTime", java.sql.Timestamp.class);

    public final StringPath cron = createString("cron");

    public final BooleanPath enabled = createBoolean("enabled");

    public final NumberPath<Integer> jobId = createNumber("jobId", Integer.class);

    public final StringPath jobName = createString("jobName");

    public final DateTimePath<java.sql.Timestamp> updatedTime = createDateTime("updatedTime", java.sql.Timestamp.class);

    public final com.mysema.query.sql.PrimaryKey<SysJob> primary = createPrimaryKey(jobId);

    public QSysJob(String variable) {
        super(SysJob.class, forVariable(variable), "null", "sys_job");
        addMetadata();
    }

    public QSysJob(String variable, String schema, String table) {
        super(SysJob.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QSysJob(Path<? extends SysJob> path) {
        super(path.getType(), path.getMetadata(), "null", "sys_job");
        addMetadata();
    }

    public QSysJob(PathMetadata<?> metadata) {
        super(SysJob.class, metadata, "null", "sys_job");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(clazzName, ColumnMetadata.named("clazz_name").ofType(12).withSize(64).notNull());
        addMetadata(createdTime, ColumnMetadata.named("created_time").ofType(93).withSize(19).notNull());
        addMetadata(cron, ColumnMetadata.named("cron").ofType(12).withSize(32));
        addMetadata(enabled, ColumnMetadata.named("enabled").ofType(-7).notNull());
        addMetadata(jobId, ColumnMetadata.named("job_id").ofType(4).withSize(10).notNull());
        addMetadata(jobName, ColumnMetadata.named("job_name").ofType(12).withSize(32).notNull());
        addMetadata(updatedTime, ColumnMetadata.named("updated_time").ofType(93).withSize(19).notNull());
    }

}

