package com.gourui.knowledgebase.utils;


import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectTableReference;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleASTVisitorAdapter;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExportTableAliasVisitor extends OracleASTVisitorAdapter {
//    private Map<String, SQLTableSource> aliasMap = new HashMap<String, SQLTableSource>();
    private Map<String, String> aliasMap = new HashMap<String, String>();
    private List<String> tableList = new ArrayList<>();

    @Override
    public boolean visit(OracleSelectTableReference x) {
        String alias = x.getAlias();
        aliasMap.put(alias, x.getName().getSimpleName());
        tableList.add(x.getName().getSimpleName());
        return true;
    }

    public Map<String, String> getAliasMap() {
        return aliasMap;
    }

    public List<String> getTableList() {
        return tableList;
    }
}
