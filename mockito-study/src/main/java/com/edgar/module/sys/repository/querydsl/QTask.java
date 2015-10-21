package com.edgar.module.sys.repository.querydsl;

import com.edgar.module.sys.repository.domain.Task;
import com.mysema.query.sql.ColumnMetadata;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import javax.annotation.Generated;

import static com.mysema.query.types.PathMetadataFactory.forVariable;


/**
 * QTask is a Querydsl query type for Task
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QTask extends com.mysema.query.sql.RelationalPathBase<Task> {

    private static final long serialVersionUID = -844771668;

    public static final QTask task = new QTask("task");

    public final StringPath assignee = createString("assignee");

    public final NumberPath<Integer> assigneeId = createNumber("assigneeId", Integer.class);

    public final NumberPath<Long> assigneeTime = createNumber("assigneeTime", Long.class);

    public final StringPath comment = createString("comment");

    public final NumberPath<Integer> companyId = createNumber("companyId", Integer.class);

    public final NumberPath<Integer> taskId = createNumber("taskId", Integer.class);

    public final StringPath taskName = createString("taskName");

    public final StringPath tracker = createString("tracker");

    public final NumberPath<Integer> trackerId = createNumber("trackerId", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<Task> primary = createPrimaryKey(taskId);

    public QTask(String variable) {
        super(Task.class, forVariable(variable), "null", "task");
        addMetadata();
    }

    public QTask(String variable, String schema, String table) {
        super(Task.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QTask(Path<? extends Task> path) {
        super(path.getType(), path.getMetadata(), "null", "task");
        addMetadata();
    }

    public QTask(PathMetadata<?> metadata) {
        super(Task.class, metadata, "null", "task");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(assignee, ColumnMetadata.named("assignee").ofType(12).withSize(32));
        addMetadata(assigneeId, ColumnMetadata.named("assignee_id").ofType(4).withSize(10));
        addMetadata(assigneeTime, ColumnMetadata.named("assignee_time").ofType(3).withSize(14));
        addMetadata(comment, ColumnMetadata.named("comment").ofType(12).withSize(128));
        addMetadata(companyId, ColumnMetadata.named("company_id").ofType(4).withSize(10));
        addMetadata(taskId, ColumnMetadata.named("task_id").ofType(4).withSize(10).notNull());
        addMetadata(taskName, ColumnMetadata.named("task_name").ofType(12).withSize(32));
        addMetadata(tracker, ColumnMetadata.named("tracker").ofType(12).withSize(32));
        addMetadata(trackerId, ColumnMetadata.named("tracker_id").ofType(4).withSize(10));
    }

}

