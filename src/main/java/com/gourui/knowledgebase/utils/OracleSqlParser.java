package com.gourui.knowledgebase.utils;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;
import com.alibaba.druid.sql.parser.ParserException;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.util.JdbcConstants;
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
                    if ( ! (type.equals("Create") || type.equals("Insert")) ) {
                        sourceTargetTableMap.put(tableName, targetTableName);
                        sourceTableList.add(tableName);
                    }
                }
                stMapList.add(sourceTargetTableMap);
                //tablea,tableb=>tablec
                simpleStStrList.add(StringUtils.join(sourceTableList,",") + "=>" + targetTableName);

                visitor1 = new ExportTableAliasVisitor();
                stmt.accept(visitor1);
                System.out.println("aliasMap1 : " + visitor1.getAliasMap());
                System.out.println("tableList : " + visitor1.getTableList());

                aliasMapList.add(visitor1.getAliasMap());
                tableListList.add(visitor1.getTableList());
            }
        } catch (ParserException e) {
            this.sql = null;
            System.err.println(e.getMessage());
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
}
