package com.edgar.module.sys.repository.querydsl;

import com.edgar.module.sys.repository.domain.SysUser;
import com.mysema.query.sql.ColumnMetadata;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;

import static com.mysema.query.types.PathMetadataFactory.forVariable;


/**
 * QSysUser is a Querydsl query type for SysUser
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSysUser extends com.mysema.query.sql.RelationalPathBase<SysUser> {

    private static final long serialVersionUID = 1714647473;

    public static final QSysUser sysUser = new QSysUser("sys_user");

    public final DatePath<java.sql.Date> birthday = createDate("birthday", java.sql.Date.class);

    public final DateTimePath<java.sql.Timestamp> createdTime = createDateTime("createdTime", java.sql.Timestamp.class);

    public final StringPath email = createString("email");

    public final BooleanPath enabled = createBoolean("enabled");

    public final StringPath fullName = createString("fullName");

    public final BooleanPath isRoot = createBoolean("isRoot");

    public final StringPath password = createString("password");

    public final StringPath salt = createString("salt");

    public final DateTimePath<java.sql.Timestamp> updatedTime = createDateTime("updatedTime", java.sql.Timestamp.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public final StringPath username = createString("username");

    public final com.mysema.query.sql.PrimaryKey<SysUser> primary = createPrimaryKey(userId);

    public QSysUser(String variable) {
        super(SysUser.class, forVariable(variable), "null", "sys_user");
        addMetadata();
    }

    public QSysUser(String variable, String schema, String table) {
        super(SysUser.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QSysUser(Path<? extends SysUser> path) {
        super(path.getType(), path.getMetadata(), "null", "sys_user");
        addMetadata();
    }

    public QSysUser(PathMetadata<?> metadata) {
        super(SysUser.class, metadata, "null", "sys_user");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(birthday, ColumnMetadata.named("birthday").ofType(91).withSize(10));
        addMetadata(createdTime, ColumnMetadata.named("created_time").ofType(93).withSize(19).notNull());
        addMetadata(email, ColumnMetadata.named("email").ofType(12).withSize(64));
        addMetadata(enabled, ColumnMetadata.named("enabled").ofType(-7));
        addMetadata(fullName, ColumnMetadata.named("full_name").ofType(12).withSize(32).notNull());
        addMetadata(isRoot, ColumnMetadata.named("is_root").ofType(-7));
        addMetadata(password, ColumnMetadata.named("password").ofType(12).withSize(64).notNull());
        addMetadata(salt, ColumnMetadata.named("salt").ofType(12).withSize(64));
        addMetadata(updatedTime, ColumnMetadata.named("updated_time").ofType(93).withSize(19).notNull());
        addMetadata(userId, ColumnMetadata.named("user_id").ofType(4).withSize(10).notNull());
        addMetadata(username, ColumnMetadata.named("username").ofType(12).withSize(16).notNull());
    }

}

