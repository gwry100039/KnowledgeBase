package com.gourui.knowledgebase.utils;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleASTVisitorAdapter;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;
import com.alibaba.druid.sql.parser.ParserException;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.util.JdbcConstants;
import com.gourui.knowledgebase.utils.visitor.ExportTableAliasVisitor;
import com.sun.deploy.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OracleSqlParser {
    private String sql;
    private OracleSchemaStatVisitor visitor;
    private ExportTableAliasVisitor visitor1;
    private List<Map<String, String>> aliasMapList;
    private List<List<String>> tableListList;
    private List<Map<String, String>> stMapList;
    private List<String> simpleStStrList;

    public OracleSqlParser(String sql) {
        this.sql = sql;
        String dbType = JdbcConstants.ORACLE;

        //格式化输出
        String result = SQLUtils.format(sql, dbType);
        System.out.println(result); // 缺省大写格式

        List<SQLStatement> stmtList = null;
        aliasMapList = new ArrayList<>();
        tableListList = new ArrayList<>();
        stMapList = new ArrayList<>();
        simpleStStrList = new ArrayList<>();
        try {
            stmtList = SQLUtils.parseStatements(result, dbType);
            //解析出的独立语句的个数
            System.out.println("size is:" + stmtList.size());
            for (int i = 0; i < stmtList.size(); i++) {

                SQLStatement stmt = stmtList.get(i);

                stMapList.add(initStMapList(stmt));
                Map<String, String> aliasMap = initAliasMap(stmt);
                aliasMapList.add(aliasMap);
                if (stmt instanceof SQLInsertStatement)
                    initColumnMap((SQLInsertStatement) stmt, aliasMap);
                if (stmt instanceof SQLCreateTableStatement)
                    initColumnMap((SQLCreateTableStatement) stmt, aliasMap);
            }
        } catch (ParserException e) {
            this.sql = null;
            System.err.println(e.getMessage());
        }
    }

    private Map<String, String> initStMapList(SQLStatement stmt) {
        visitor = new OracleSchemaStatVisitor();
        stmt.accept(visitor);

        Map<String, String> sourceTargetTableMap = new HashMap<>();
        String targetTableName = "";
        //find target table name
        for (Map.Entry<TableStat.Name, TableStat> entry : visitor.getTables().entrySet()) {
            String tableName = entry.getKey().getName();
            String type = entry.getValue().toString();
            if (type.equals("Create") || type.equals("Insert")) {
                targetTableName = tableName;
                break;
            }
        }
        //set map
        List<String> sourceTableList = new ArrayList<>();
        for (Map.Entry<TableStat.Name, TableStat> entry : visitor.getTables().entrySet()) {
            String tableName = entry.getKey().getName();
            String type = entry.getValue().toString();
            if (!(type.equals("Create") || type.equals("Insert"))) {
                sourceTargetTableMap.put(tableName, targetTableName);
                sourceTableList.add(tableName);
            }
        }
        return sourceTargetTableMap;
        /*stMapList.add(sourceTargetTableMap);
        //format: A,B=>tablec
        simpleStStrList.add(StringUtils.join(sourceTableList, ",") + "=>" + targetTableName);*/
    }

    private Map<String, String> initAliasMap(SQLStatement stmt) {
        visitor1 = new ExportTableAliasVisitor();
        stmt.accept(visitor1);
        System.out.println("aliasMap1 : " + visitor1.getAliasMap());
        System.out.println("tableList : " + visitor1.getTableList());
        return visitor1.getAliasMap();
        /*aliasMapList.add(visitor1.getAliasMap());
        tableListList.add(visitor1.getTableList());*/
    }

    /*
    insert 语句：
    首先用selectItemList确定顺序，和insert里面的list对应起来，不允许只写一个insert。
    然后用一个visitor拿到单个SQLExpr下面的所有column
    这样一来，每个SQLselectItem都会拿到一个list，然后通过index对应到insert的字段上去，得到一个多对一的映射关系
     */
    private void initColumnMap(SQLInsertStatement stmt, Map<String, String> aliasMap) {
        SQLSelect select = stmt.getQuery();
        SQLSelectQueryBlock queryBlock = select.getQueryBlock();
        List<SQLSelectItem> selectItems = queryBlock.getSelectList();
        String targetTableName = stmt.getTableName().getSimpleName();

        List<SQLExpr> insertColumns = stmt.getColumns();//拿到isnert的list

        for ( int i=0 ; i < selectItems.size() ; i++) {
            SQLSelectItem sqlSelectItem = selectItems.get(i);
            ExtractColumnsVisitor extractColumnsVisitor = new ExtractColumnsVisitor();
            sqlSelectItem.getExpr().accept(extractColumnsVisitor);
            List<SQLPropertyExpr> columnList = extractColumnsVisitor.getColumnList();

            SQLIdentifierExpr sqlIdentifierExpr = (SQLIdentifierExpr) (insertColumns.get(i));
            String targetColumnName = sqlIdentifierExpr.getName();
            for( SQLPropertyExpr expr : columnList) {
                String tableName = aliasMap.get(expr.getOwnernName().toUpperCase());
                String columnName = expr.getName();
                System.out.println(tableName + '.' + columnName + "=>" + targetTableName + '.' + targetColumnName);
            }
        }
    }

    /*
    create table 语句：
    递归方式与Insert雷同，但映射关系需要通过每一个SQLselectItem的alias来确定，如果没有别名，则一定是SQLPropertyExpr，直接用getname
     */
    private void initColumnMap(SQLCreateTableStatement stmt, Map<String, String> aliasMap) {
        SQLSelect select = stmt.getSelect();
        SQLSelectQueryBlock queryBlock = select.getQueryBlock();
        List<SQLSelectItem> selectItems = queryBlock.getSelectList();
        String targetTableName = stmt.getTableSource().toString();

        for ( int i=0 ; i < selectItems.size() ; i++) {
            SQLSelectItem sqlSelectItem = selectItems.get(i);
            ExtractColumnsVisitor extractColumnsVisitor = new ExtractColumnsVisitor();
            sqlSelectItem.getExpr().accept(extractColumnsVisitor);
            List<SQLPropertyExpr> columnList = extractColumnsVisitor.getColumnList();

            String targetColumnName = sqlSelectItem.getAlias();
            for( SQLPropertyExpr expr : columnList) {
                String tableName = aliasMap.get(expr.getOwnernName().toUpperCase());
                String columnName = expr.getName();
                if(targetColumnName == null) {
                    targetColumnName = columnName;
                }
                System.out.println(tableName + '.' + columnName + "=>" + targetTableName + '.' + targetColumnName);
            }
        }
    }

    public List<Map<String, String>> getAliasMapList() {
        return aliasMapList;
    }

    public List<List<String>> getTableListList() {
        return tableListList;
    }

    public List<Map<String, String>> getStMapList() {
        return stMapList;
    }

    public List<String> getSimpleStStrList() {
        return simpleStStrList;
    }

    private class ExtractColumnsVisitor extends OracleASTVisitorAdapter {

        private List<SQLPropertyExpr> columnList = new ArrayList<>();

        @Override
        public boolean visit(SQLPropertyExpr e) {
            columnList.add(e);
            return true;
        }

        public List<SQLPropertyExpr> getColumnList() {
            return columnList;
        }

    }
}
