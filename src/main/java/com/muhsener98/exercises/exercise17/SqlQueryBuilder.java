package com.muhsener98.exercises.exercise17;

import java.util.*;

public class SqlQueryBuilder {

    @Input("ids")
    private List<String> ids;
    @Input("limit")
    private Integer limit;
    @Input("tableName")
    private String tableName;
    @Input("columnNames")
    private List<String> columnNames;

    public SqlQueryBuilder(List<String> ids, Integer limit, String tableName, List<String> columnNames) {
        this.ids = ids;
        this.limit = limit;
        this.tableName = tableName;
        this.columnNames = columnNames;
    }

    @Operation("SelectBuilder")
    public String selectStatementBuilder(@Input("tableName") String tableName,  @Input("columnNames") List<String> columnNames) {
        String columnsString = columnNames.isEmpty() ? "*" : String.join(",", columnNames);

        return String.format("SELECT %s FROM %s", columnsString, tableName);
    }

    @Operation("WhereClauseBuilder")
    public String addWhereClause(@DependsOn("SelectBuilder") String query, @Input("ids") List<String> ids) {
        if (ids.isEmpty())
            return query;

        return String.format("%s WHERE id IN %s", query, String.join(",", ids));
    }


    @FinalResult
    public String addLimit(@DependsOn("WhereClauseBuilder") String query,  @Input("limit") Integer limit) {
        if (limit == null || limit == 0)
            return query;

        if (limit < 0)
            throw new IllegalArgumentException("limit cannot be negative");

        return String.format("%s LIMIT %d" , query , limit);
    }
}
