package com.gourui.knowledgebase.utils;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;
import com.alibaba.druid.sql.parser.ParserException;
import com.alibaba.druid.util.JdbcConstants;

import java.util.List;
import java.util.Map;

public class OracleSqlParser {
    private String sql;
    private OracleSchemaStatVisitor visitor;
    private ExportTableAliasVisitor visitor1;

    public OracleSqlParser(String sql) {
        this.sql = sql;
        String dbType = JdbcConstants.ORACLE;

        //格式化输出
        String result = SQLUtils.format(sql, dbType);
        System.out.println(result); // 缺省大写格式

        List<SQLStatement> stmtList = null;
        try {
            stmtList = SQLUtils.parseStatements(result, dbType);
            //解析出的独立语句的个数
            System.out.println("size is:" + stmtList.size());
            for (int i = 0; i < stmtList.size(); i++) {

                SQLStatement stmt = stmtList.get(i);

                visitor = new OracleSchemaStatVisitor();
                stmt.accept(visitor);
                //获取操作方法名称,依赖于表名称
                System.out.println("Manipulation : " + visitor.getTables());
                //获取字段名称
                System.out.println("fields : " + visitor.getColumns());
                //获取关联关系
                System.out.println("relationship : " + visitor.getRelationships());

                visitor1 = new ExportTableAliasVisitor();
                stmt.accept(visitor1);
                System.out.println("aliasMap1 : " + visitor1.getAliasMap());
                System.out.println("tablelist : " + visitor1.getTableList());
            }
        } catch (ParserException e) {
            this.sql = null;
            System.err.println(e.getMessage());
        }
    }

    public Map<String, String> getAliasMap() {
        return visitor1.getAliasMap();
    }

    public List<String> getTableList() {
        return visitor1.getTableList();
    }
}
