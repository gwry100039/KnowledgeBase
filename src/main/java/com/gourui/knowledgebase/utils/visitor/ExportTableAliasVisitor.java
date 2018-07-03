package com.gourui.knowledgebase.utils.visitor;


import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectTableReference;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleASTVisitorAdapter;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExportTableAliasVisitor extends OracleASTVisitorAdapter {
    //    private Map<String, SQLTableSource> aliasMap = new HashMap<String, SQLTableSource>();
    private Map<String, String> aliasMap = new HashMap<>();
    private List<String> tableList = new ArrayList<>();

    @Override
    public boolean visit(SQLExprTableSource x) {
        String alias = x.getAlias();
        if (alias != null) {
            aliasMap.put(alias.toUpperCase(), x.getName().getSimpleName().toUpperCase());
            tableList.add(x.getName().getSimpleName().toUpperCase());
        }
        return super.visit(x);
    }

    @Override
    public boolean visit(OracleSelectTableReference x) {
        String alias = x.getAlias();
        aliasMap.put(alias.toUpperCase(), x.getName().getSimpleName().toUpperCase());
        tableList.add(x.getName().getSimpleName().toUpperCase());
        return super.visit(x);
    }

    public Map<String, String> getAliasMap() {
        return aliasMap;
    }

    public List<String> getTableList() {
        return tableList;
    }
}
