package com.gourui.knowledgebase.utils;

import com.gourui.knowledgebase.utils.sqlparser.OracleSqlParser;

public class TestSqlParser {
    public static void main(String[] args) {

/*
create table dasfsdaf as select a.aaa,b.bbb,ccc from tablesa a
left join tableb b on a.aaa=b.bbb
where a.aaa=b.bbb;

INSERT INTO MKTG_H_EXEC_RESULT_FACT
(THE_DATE, AREA_ID, SCENE_ID, PID_TEST, MKTG_CNT, MKTG_SUC_CNT
, TASK_CNT, TASK_F_CNT, TASK_F_SUC_CNT, CON_CNT, CON_SUC_CNT, CONSTANT)
SELECT TRUNC(SYSDATE) AS columna , T1.AREA_ID
, RTRIM(TO_CHAR(T2.PID))
, case when t2.pid='1' then t1.area_id else t2.pid end
, SUM(T1.MKTG_CNT), SUM(T1.MKTG_SUC_CNT)  AS columnb
, SUM(T1.TASK_CNT), SUM(T1.TASK_F_CNT)
, SUM(T1.TASK_F_SUC_CNT), SUM(T1.CON_CNT)
, SUM(T1.CON_SUC_CNT)
, 'CONSTANT'
FROM MKTG_H_EXEC_SOURCE T1, DMN_MKTG_PLAN_TYPE T2
WHERE T1.THE_DATE = TRUNC(SYSDATE)
AND T1.SCENE_ID = T2.SCENE_ID
GROUP BY T1.AREA_ID, RTRIM(TO_CHAR(T2.PID));

CREATE TABLE MKTG_H_EXEC_RESULT_FACT AS
SELECT TRUNC(SYSDATE) AS THE_DATE , T1.AREA_ID
, RTRIM(TO_CHAR(T2.PID)) SCENE_ID
, case when t2.pid='1' then t1.area_id else t2.pid end PID_TEST
, SUM(T1.MKTG_CNT) MKTG_CNT
, SUM(T1.MKTG_SUC_CNT)  AS MKTG_SUC_CNT
, SUM(T1.TASK_CNT) TASK_CNT
, SUM(T1.TASK_F_CNT) TASK_F_CNT
, SUM(T1.TASK_F_SUC_CNT) TASK_F_SUC_CNT
, SUM(T1.CON_CNT) CON_CNT
, SUM(T1.CON_SUC_CNT) CON_SUC_CNT
, 'CONSTANT' CONSTANT
FROM MKTG_H_EXEC_SOURCE T1, DMN_MKTG_PLAN_TYPE T2
WHERE T1.THE_DATE = TRUNC(SYSDATE)
AND T1.SCENE_ID = T2.SCENE_ID
GROUP BY T1.AREA_ID, RTRIM(TO_CHAR(T2.PID));

create table  instable compress  as select a.name,'sadasdsa',b.dsadas b ,b.c from testtable
a left join tableb b on a.t = b.t
where a.c = b.c;

TODO 今后需要加入子查询的识别  以及  With子句的识别
 */
        String sql = "CREATE TABLE MKTG_H_EXEC_RESULT_FACT AS\n" +
                "SELECT TRUNC(SYSDATE) AS THE_DATE , T1.AREA_ID\n" +
                ", RTRIM(TO_CHAR(T2.PID)) SCENE_ID\n" +
                ", case when t2.pid='1' then t1.area_id else t2.pid end PID_TEST\n" +
                ", SUM(T1.MKTG_CNT) MKTG_CNT\n" +
                ", SUM(T1.MKTG_SUC_CNT)  AS MKTG_SUC_CNT\n" +
                ", SUM(T1.TASK_CNT) TASK_CNT\n" +
                ", SUM(T1.TASK_F_CNT) TASK_F_CNT\n" +
                ", SUM(T1.TASK_F_SUC_CNT) TASK_F_SUC_CNT\n" +
                ", SUM(T1.CON_CNT) CON_CNT\n" +
                ", SUM(T1.CON_SUC_CNT) CON_SUC_CNT\n" +
                ", 'CONSTANT' CONSTANT\n" +
                "FROM MKTG_H_EXEC_SOURCE T1, DMN_MKTG_PLAN_TYPE T2\n" +
                "WHERE T1.THE_DATE = TRUNC(SYSDATE)\n" +
                "AND T1.SCENE_ID = T2.SCENE_ID\n" +
                "GROUP BY T1.AREA_ID, RTRIM(TO_CHAR(T2.PID));";
        OracleSqlParser osp = new OracleSqlParser(sql);
    }
}


//api文档查看  http://tool.oschina.net/apidocs/apidoc?api=druid0.26