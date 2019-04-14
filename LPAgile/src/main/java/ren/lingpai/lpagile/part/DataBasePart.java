package ren.lingpai.lpagile.part;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ren.lingpai.lputil.clazz.ClassUtil;
import ren.lingpai.lputil.collection.CollectionUtil;

import javax.sql.DataSource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * 数据库操作类 Created by lrp on 17-2-13.
 */
public final class DataBasePart {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataBasePart.class);

    private static final ThreadLocal<Connection> CONNECTION_HOLDER;

    private static final QueryRunner QUERY_RUNNER;

    private static final BasicDataSource DATA_SOURCE;

    static {
        CONNECTION_HOLDER = new ThreadLocal<Connection>();
        QUERY_RUNNER = new QueryRunner();
        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(ConfigPart.getJdbcDriver());
        DATA_SOURCE.setUrl(ConfigPart.getJdbcUrl());
        DATA_SOURCE.setUsername(ConfigPart.getJdbcUsername());
        DATA_SOURCE.setPassword(ConfigPart.getJdbcPassword());

        // 初始连接数
        DATA_SOURCE.setInitialSize(4);
        // 最小空闲数
        DATA_SOURCE.setMinIdle(4);
        // 最大空闲数
        DATA_SOURCE.setMaxIdle(8);
        // 最大连接数
        DATA_SOURCE.setMaxTotal(16);
        // 超时回收时间(以毫秒为单位)
        DATA_SOURCE.setMaxWaitMillis(3 * 1000);
        // 自动回收超时连接
        DATA_SOURCE.setRemoveAbandonedOnMaintenance(true);
        // 超时时间(以秒数为单位)
        DATA_SOURCE.setRemoveAbandonedTimeout(3);

    }

    /**
     * 获取数据源
     */
    public static DataSource getDataSource() {
        return DATA_SOURCE;
    }

    /**
     * 获取数据库连接
     */
    public static Connection getConnection() {
        Connection conn = CONNECTION_HOLDER.get();
        if (null == conn) {
            try {
                conn = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                LOGGER.error("get connection failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
        return conn;
    }

    /**
     * 开启事务
     */
    public static void beginTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                LOGGER.error("begin transaction failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.commit();
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("commit transaction failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("rollback transaction failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * 查询实体
     */
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        T entity;
        Connection conn = null;
        try {
            conn = getConnection();
            entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("query entity failure", e);
            throw new RuntimeException(e);
        } finally {
            if (null != conn) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error("conn close failure", e);
                }
            }
        }
        return entity;
    }

    /**
     * 查询实体列表
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> entityList;
        Connection conn = null;
        try {
            conn = getConnection();
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure", e);
            throw new RuntimeException(e);
        } finally {
            if (null != conn) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error("conn close failure", e);
                }
            }
        }
        return entityList;
    }

    /**
     * 查询并返回单个列值
     */
    public static <T> T query(String sql, Object... params) {
        T obj;
        Connection conn = null;
        try {
            conn = getConnection();
            obj = QUERY_RUNNER.query(conn, sql, new ScalarHandler<T>(), params);
        } catch (SQLException e) {
            LOGGER.error("query failure", e);
            throw new RuntimeException(e);
        } finally {
            if (null != conn) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error("conn close failure", e);
                }
            }
        }
        return obj;
    }

    /**
     * 查询并返回多个列值
     */
    public static <T> List<T> queryList(String sql, Object... params) {
        List<T> list;
        Connection conn = null;
        try {
            conn = getConnection();
            list = QUERY_RUNNER.query(conn, sql, new ColumnListHandler<T>(), params);
        } catch (SQLException e) {
            LOGGER.error("query list failure", e);
            throw new RuntimeException(e);
        } finally {
            if (null != conn) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error("conn close failure", e);
                }
            }
        }
        return list;
    }

    /**
     * 查询并返回多个列值（具有唯一性）
     */
    public static <T> Set<T> querySet(String sql, Object... params) {
        Collection<T> valueList = queryList(sql, params);
        return new LinkedHashSet<T>(valueList);
    }

    /**
     * 查询并返回数组
     */
    public static Object[] queryArray(String sql, Object... params) {
        Object[] resultArray;
        Connection conn = null;
        try {
            conn = getConnection();
            resultArray = QUERY_RUNNER.query(conn, sql, new ArrayHandler(), params);
        } catch (SQLException e) {
            LOGGER.error("query array failure", e);
            throw new RuntimeException(e);
        } finally {
            if (null != conn) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error("conn close failure", e);
                }
            }
        }
        return resultArray;
    }

    /**
     * 查询并返回数组列表
     */
    public static List<Object[]> queryArrayList(String sql, Object... params) {
        List<Object[]> resultArrayList;
        Connection conn = null;
        try {
            conn = getConnection();
            resultArrayList = QUERY_RUNNER.query(conn, sql, new ArrayListHandler(), params);
        } catch (SQLException e) {
            LOGGER.error("query array list failure", e);
            throw new RuntimeException(e);
        } finally {
            if (null != conn) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error("conn close failure", e);
                }
            }
        }
        return resultArrayList;
    }

    /**
     * 查询并返回结果集映射（列名 => 列值）
     */
    public static Map<String, Object> queryMap(String sql, Object... params) {
        Map<String, Object> resultMap;
        Connection conn = null;
        try {
            conn = getConnection();
            resultMap = QUERY_RUNNER.query(conn, sql, new MapHandler(), params);
        } catch (SQLException e) {
            LOGGER.error("query map failure", e);
            throw new RuntimeException(e);
        } finally {
            if (null != conn) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error("conn close failure", e);
                }
            }
        }
        return resultMap;
    }

    /**
     * 查询并返回结果集映射列表（列名 => 列值）
     */
    public static List<Map<String, Object>> queryMapList(String sql, Object... params) {
        List<Map<String, Object>> resultMapList;
        Connection conn = null;
        try {
            conn = getConnection();
            resultMapList = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            LOGGER.error("query map list failure", e);
            throw new RuntimeException(e);
        } finally {
            if (null != conn) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error("conn close failure", e);
                }
            }
        }
        return resultMapList;
    }

    /**
     * 执行更新语句（包括：update、insert、delete）
     */
    public static int update(String sql, Object... params) {
        int rows;
        Connection conn = null;
        try {
            conn = getConnection();
            rows = QUERY_RUNNER.update(conn, sql, params);
        } catch (SQLException e) {
            LOGGER.error("execute update failure", e);
            throw new RuntimeException(e);
        } finally {
            if (null != conn) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error("conn close failure", e);
                }
            }
        }
        return rows;
    }

    /**
     * 插入实体
     */
    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {
        if (CollectionUtil.isEmpty(fieldMap)) {
            LOGGER.error("can not insert entity: fieldMap is empty");
            return false;
        }
        if (entityClass.getSimpleName().length() <= 2) {
            LOGGER.error("can not insert entity: entityName is illegal");
            return false;
        }
        String tableName0 = "t_" + entityClass.getSimpleName();
        String tableName = tableName0.substring(0, tableName0.length() - 2).toLowerCase();
        String sql = "INSERT INTO " + tableName;
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (String fieldName : fieldMap.keySet()) {
            columns.append("m_" + fieldName.toLowerCase()).append(", ");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
        values.replace(values.lastIndexOf(", "), values.length(), ")");
        sql += columns + " VALUES " + values;

        Object[] params = fieldMap.values().toArray();

        return update(sql, params) == 1;
    }

    /**
     * 更新实体
     */
    public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap) {
        if (CollectionUtil.isEmpty(fieldMap)) {
            LOGGER.error("can not update entity: fieldMap is empty");
            return false;
        }
        if (entityClass.getSimpleName().length() <= 2) {
            LOGGER.error("can not update entity: entityName is illegal");
            return false;
        }
        String tableName0 = "t_" + entityClass.getSimpleName();
        String tableName = tableName0.substring(0, tableName0.length() - 2).toLowerCase();
        String sql = "UPDATE " + tableName.toLowerCase() + " SET ";
        StringBuilder columns = new StringBuilder();
        for (String fieldName : fieldMap.keySet()) {
            columns.append("m_" + fieldName.toLowerCase()).append(" = ?, ");
        }
        sql += columns.substring(0, columns.lastIndexOf(", ")) + " WHERE m_id = ?";

        List<Object> paramList = new ArrayList<Object>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        Object[] params = paramList.toArray();

        return update(sql, params) == 1;
    }

    /**
     * 删除实体
     */
    public static <T> boolean deleteEntity(Class<T> entityClass, long id) {
        if (entityClass.getSimpleName().length() <= 2) {
            LOGGER.error("can not delete entity: entityName is illegal");
            return false;
        }
        String tableName0 = "t_" + entityClass.getSimpleName();
        String tableName = tableName0.substring(0, tableName0.length() - 2).toLowerCase();
        String sql = "DELETE FROM " + tableName + " WHERE m_id = ?";
        return update(sql, id) == 1;
    }

    /**
     * 执行 SQL 文件
     */
    public static void executeSqlFile(String filePath) {
        InputStream is = ClassUtil.getClassLoader().getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            String sql;
            while ((sql = reader.readLine()) != null) {
                update(sql);
            }
        } catch (Exception e) {
            LOGGER.error("execute sql file failure", e);
            throw new RuntimeException(e);
        }
    }
}

