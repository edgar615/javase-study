package com.edgar.module.sys.repository.querydsl;

import com.edgar.module.sys.repository.domain.I18nMessage;
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
 * QI18nMessage is a Querydsl query type for I18nMessage
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QI18nMessage extends com.mysema.query.sql.RelationalPathBase<I18nMessage> {

    private static final long serialVersionUID = -259441406;

    public static final QI18nMessage i18nMessage = new QI18nMessage("i18n_message");

    public final DateTimePath<java.sql.Timestamp> createdTime = createDateTime("createdTime", java.sql.Timestamp.class);

    public final NumberPath<Integer> i18nId = createNumber("i18nId", Integer.class);

    public final StringPath i18nKey = createString("i18nKey");

    public final StringPath i18nValueEn = createString("i18nValueEn");

    public final StringPath i18nValueZhCn = createString("i18nValueZhCn");

    public final StringPath i18nValueZhTw = createString("i18nValueZhTw");

    public final BooleanPath isRoot = createBoolean("isRoot");

    public final DateTimePath<java.sql.Timestamp> updatedTime = createDateTime("updatedTime", java.sql.Timestamp.class);

    public final com.mysema.query.sql.PrimaryKey<I18nMessage> primary = createPrimaryKey(i18nId);

    public QI18nMessage(String variable) {
        super(I18nMessage.class, forVariable(variable), "null", "i18n_message");
        addMetadata();
    }

    public QI18nMessage(String variable, String schema, String table) {
        super(I18nMessage.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QI18nMessage(Path<? extends I18nMessage> path) {
        super(path.getType(), path.getMetadata(), "null", "i18n_message");
        addMetadata();
    }

    public QI18nMessage(PathMetadata<?> metadata) {
        super(I18nMessage.class, metadata, "null", "i18n_message");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdTime, ColumnMetadata.named("created_time").ofType(93).withSize(19).notNull());
        addMetadata(i18nId, ColumnMetadata.named("i18n_id").ofType(4).withSize(10).notNull());
        addMetadata(i18nKey, ColumnMetadata.named("i18n_key").ofType(12).withSize(64).notNull());
        addMetadata(i18nValueEn, ColumnMetadata.named("i18n_value_en").ofType(12).withSize(64).notNull());
        addMetadata(i18nValueZhCn, ColumnMetadata.named("i18n_value_zh_cn").ofType(12).withSize(64).notNull());
        addMetadata(i18nValueZhTw, ColumnMetadata.named("i18n_value_zh_tw").ofType(12).withSize(64).notNull());
        addMetadata(isRoot, ColumnMetadata.named("is_root").ofType(-7));
        addMetadata(updatedTime, ColumnMetadata.named("updated_time").ofType(93).withSize(19).notNull());
    }

}

