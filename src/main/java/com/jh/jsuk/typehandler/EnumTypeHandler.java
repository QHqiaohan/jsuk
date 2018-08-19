package com.jh.jsuk.typehandler;

import com.jh.jsuk.envm.BaseEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@MappedTypes(BaseEnum.class)
public class EnumTypeHandler<E extends Enum & BaseEnum> extends BaseTypeHandler<BaseEnum> {

    private Class<E> type;

    public EnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BaseEnum parameter, JdbcType jdbcType) throws SQLException {
        if (jdbcType == null) {
            ps.setObject(i, parameter.getKey());
        } else {
            ps.setObject(i, parameter.getKey(), jdbcType.TYPE_CODE);
        }
    }

    @Override
    public BaseEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (null == rs.getString(columnName) && rs.wasNull()) {
            return null;
        }
        return valueOf(type, rs.getObject(columnName));
    }

    @Override
    public BaseEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (null == rs.getString(columnIndex) && rs.wasNull()) {
            return null;
        }
        return valueOf(type, rs.getObject(columnIndex));
    }

    @Override
    public BaseEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (null == cs.getString(columnIndex) && cs.wasNull()) {
            return null;
        }
        return valueOf(type, cs.getObject(columnIndex));
    }

    private BaseEnum valueOf(Class<E> type, Object object) {
        if (object == null)
            return null;
        E[] constants = type.getEnumConstants();
        for (E constant : constants) {
            if (constant.getKey().equals(object)) {
                return constant;
            }
        }
        for (E constant : constants) {
            if (String.valueOf(object).equals(String.valueOf(object))) {
                return constant;
            }
        }
        return null;
    }

}
