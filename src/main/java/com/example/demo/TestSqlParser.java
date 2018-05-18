package com.example.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectTableReference;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleASTVisitorAdapter;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;
import com.alibaba.druid.util.JdbcConstants;

public class TestSqlParser {
    public static void main(String[] args) {
//        String sql = "insert into instable select a,'sadasdsa',dsadas b ,c from testtable" +
//                " a left join tableb b on a.t = b.t ";
        String sql = "Create table  instable compress  as select a,'sadasdsa',dsadas b ,c from testtable" +
                " a left join tableb b on a.t = b.t " +
                " where a.c = b.c";
        String dbType = JdbcConstants.ORACLE;

        //格式化输出
        String result = SQLUtils.format(sql, dbType);
        System.out.println(result); // 缺省大写格式
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);

        //解析出的独立语句的个数
        System.out.println("size is:" + stmtList.size());
        for (int i = 0; i < stmtList.size(); i++) {

            SQLStatement stmt = stmtList.get(i);
            OracleSchemaStatVisitor visitor = new OracleSchemaStatVisitor();
            ExportTableAliasVisitor visitor1 = new ExportTableAliasVisitor();
            stmt.accept(visitor);
            stmt.accept(visitor1);
            //获取操作方法名称,依赖于表名称
            System.out.println("Manipulation : " + visitor.getTables());
            //获取字段名称
            System.out.println("fields : " + visitor.getColumns());
            //获取关联关系
            System.out.println("aliasMap : " + visitor.getRelationships());


            SQLTableSource tableSource = visitor1.getAliasMap().get("a");
            System.out.println(tableSource);
        }

    }
}

//if (statement instanceof SQLSelectStatement) {
//        SQLSelect select = ((SQLSelectStatement) statement).getSelect();
//        SQLSelectQueryBlock query = (SQLSelectQueryBlock) select.getQuery();
//        System.out.println(query.getSelectList());//这里打印的就是name，age
//        if(query.getFrom() instanceof SQLSubqueryTableSource){
//        SQLSubqueryTableSource ssts = (SQLSubqueryTableSource)query.getFrom();
//        MySqlSelectQueryBlock mssqb = (MySqlSelectQueryBlock) ssts.getSelect().getQuery();
//        System.out.println(mssqb.getSelectList());//这里打印的就是子查询的*
//        }


//api文档查看  http://tool.oschina.net/apidocs/apidoc?api=druid0.26