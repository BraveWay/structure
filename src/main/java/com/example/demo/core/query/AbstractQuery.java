package com.example.demo.core.query;

import com.example.demo.core.CommandContext;
import com.example.demo.impl.Context;
import com.example.demo.impl.Page;
import com.example.demo.interceptor.Command;
import com.example.demo.interceptor.CommandExecutor;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractQuery<T extends Query<?,?>,U> extends ListQueryParameterObject implements Command<Object>, Query<T,U>, Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SORTORDER_ASC = "asc";
    public static final String SORTORDER_DESC = "desc";

    public enum ResultType{
        LIST, LIST_PAGE, SINGLE_RESULT, COUNT
    }

    public enum NullHandlingOnOrder{
        NULLS_FIRST,NULL_LAST
    }

    protected transient CommandExecutor commandExecutor;
    protected transient CommandContext commandContext;

    protected String databaseType;

    protected String orderBy;

    protected ResultType resultType;

    protected QueryProperty orderProperty;

    protected NullHandlingOnOrder nullHandlingOnOrder;

    protected AbstractQuery(){
        parameter = this;
    }

    protected AbstractQuery(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    public AbstractQuery(CommandContext commandContext){
        this.commandContext  = commandContext;
    }

    // override super class method
    @Override
    public T asc(){
        return direction(Direction.ASCENDING);
    }

    @Override
    public T desc(){return direction(Direction.DESCENDING);}

    @Override
    @SuppressWarnings("unchecked")
    public T orderBy(QueryProperty property){
        this.orderProperty = property;
        return (T) this;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<U> list() {
        this.resultType = ResultType.LIST;
        if(commandExecutor != null){
            return  (List<U>) commandExecutor.execute(this);
        }
        return executeList(Context.getCommandContext(),null);
    }

    @Override
    public long count(){
        this.resultType = ResultType.COUNT;
        if(commandExecutor != null){
            return (Long) commandExecutor.execute(this);
        }
        return executeCount(Context.getCommandContext());
    }

    @Override
    @SuppressWarnings("unchecked")
    public U singleResult() {
        this.resultType = ResultType.SINGLE_RESULT;
        if(commandExecutor != null){
            return (U) commandExecutor.execute(this);
        }
        return executeSingleResult(Context.getCommandContext());
    }



    @Override
    @SuppressWarnings("unchecked")
    public List<U> listPage(int firstResult, int maxResults) {
        this.firstResult = firstResult;
        this.maxResults = maxResults;
        this.resultType = ResultType.LIST_PAGE;
        if(commandExecutor != null){
         return (List<U>) commandExecutor.execute(this);
        }
        return executeList(Context.getCommandContext(),new Page(firstResult,maxResults));
    }
    // override super class method

    @SuppressWarnings("unchecked")
    public T orderBy(QueryProperty property,NullHandlingOnOrder nullHandlingOnOrder){
        orderBy(property);
        this.nullHandlingOnOrder = nullHandlingOnOrder;
        return (T) this;
    }



    @SuppressWarnings("unchecked")
    public T direction(Direction direction){
       if(orderProperty == null){
           throw new RuntimeException("sss");
       }
       addOrder(orderProperty.getName(),direction.getName(),nullHandlingOnOrder);
       orderProperty = null;
       nullHandlingOnOrder  = null;
       return (T) this;
    }

    private U executeSingleResult(CommandContext commandContext){
        List<U> results = executeList(commandContext,null);
        if(results.size() == 1){
            return results.get(0);
        }else if(results.size() > 1){
            throw new RuntimeException("Query return "+results.size()+"results but only need single");
        }
        return null;
    }




    protected void addOrder(String column, String sortOrder, NullHandlingOnOrder nullHandlingOnOrder){

    }


    public Object execute(CommandContext commandContext){
        if(resultType == ResultType.LIST){
            return executeList(commandContext,null);
        } else if(resultType == ResultType.SINGLE_RESULT){
            return executeSingleResult(commandContext);
        } else if(resultType == ResultType.LIST_PAGE){
            return executeList(commandContext,null);
        } else {
            return executeCount(commandContext);
        }

    }

    public abstract  long executeCount(CommandContext commandContext);

    public abstract  List<U> executeList(CommandContext commandContext, Page page);

}
