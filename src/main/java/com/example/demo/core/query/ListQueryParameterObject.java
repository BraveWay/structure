package com.example.demo.core.query;

public class ListQueryParameterObject {

    protected int maxResults = Integer.MAX_VALUE;
    protected int firstResult;
    protected Object parameter;
    protected String databaseType;

    public ListQueryParameterObject() {
    }

    public ListQueryParameterObject(int maxResults, int firstResult, Object parameter) {
        this.maxResults = maxResults;
        this.firstResult = firstResult;
        this.parameter = parameter;
    }

    public int getFirstResult(){
        return firstResult;
    }

    public int getFirstRow(){
        return firstResult + 1;
    }

    public int getLastRow(){
        if(maxResults == Integer.MAX_VALUE){
            return maxResults;
        }
        return firstResult + maxResults + 1;
    }

    public int getMaxResults(){
        return maxResults;
    }

    public void setFirstResult(int firstResult){
        this.firstResult = firstResult;
    }

    public void setMaxResults(int maxResults){
        this.maxResults = maxResults;
    }

    public void setParameter(Object parameter){
        this.parameter = parameter;
    }

    public String getOrderBy(){
        //the default order column
        return "RES.ID_ asc";
    }

    public String getOrderByColumns(){
        return getOrderBy();
    }

    public void setDatabaseType(String databaseType){
        this.databaseType = databaseType;
    }

    public String getDatabaseType(){
        return databaseType;
    }
}
