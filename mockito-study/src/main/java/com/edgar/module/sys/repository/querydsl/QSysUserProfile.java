package com.edgar.module.sys.repository.querydsl;

import com.edgar.module.sys.repository.domain.SysUserProfile;
import com.mysema.query.sql.ColumnMetadata;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import javax.annotation.Generated;

import static com.mysema.query.types.PathMetadataFactory.forVariable;


/**
 * QSysUserProfile is a Querydsl query type for SysUserProfile
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSysUserProfile extends com.mysema.query.sql.RelationalPathBase<SysUserProfile> {

    private static final long serialVersionUID = 1387435704;

    public static final QSysUserProfile sysUserProfile = new QSysUserProfile("sys_user_profile");

    public final StringPath language = createString("language");

    public final NumberPath<Integer> profileId = createNumber("profileId", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<SysUserProfile> primary = createPrimaryKey(profileId);

    public QSysUserProfile(String variable) {
        super(SysUserProfile.class, forVariable(variable), "null", "sys_user_profile");
        addMetadata();
    }

    public QSysUserProfile(String variable, String schema, String table) {
        super(SysUserProfile.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QSysUserProfile(Path<? extends SysUserProfile> path) {
        super(path.getType(), path.getMetadata(), "null", "sys_user_profile");
        addMetadata();
    }

    public QSysUserProfile(PathMetadata<?> metadata) {
        super(SysUserProfile.class, metadata, "null", "sys_user_profile");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(language, ColumnMetadata.named("language").ofType(12).withSize(16).notNull());
        addMetadata(profileId, ColumnMetadata.named("profile_id").ofType(4).withSize(10).notNull());
        addMetadata(userId, ColumnMetadata.named("user_id").ofType(4).withSize(10));
    }

}

