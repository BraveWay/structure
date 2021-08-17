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

    public static enum ResultType{
        LIST, LIST_PAGE, SINGLE_RESULT, COUNT
    }

    protected transient CommandExecutor commandExecutor;
    protected transient CommandContext commandContext;

    protected String databaseType;

    protected String orderBy;

    protected ResultType resultType;

    protected QueryProperty orderProperty;

    public static enum NullHandlingOnOrder{
        NULLS_FIRST,NULL_LAST
    }

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

    @Override
    public T orderBy(QueryProperty property){
        this.orderProperty = property;
        return (T) this;
    }

    public T orderBy(QueryProperty property,NullHandlingOnOrder nullHandlingOnOrder){
        orderBy(property);
        this.nullHandlingOnOrder = nullHandlingOnOrder;
        return (T) this;
    }

    @Override
    public T asc(){
        return direction(Direction.ASCENDING);
    }

    @Override
    public T desc(){return direction(Direction.DESCENDING);}

    public T direction(Direction direction){
       if(orderProperty == null){
           throw new RuntimeException("sss");
       }
       addOrder(orderProperty.getName(),direction.getName(),nullHandlingOnOrder);
       orderProperty = null;
       nullHandlingOnOrder  = null;
       return (T) this;
    }

    @Override
    public U singleResult() {
        this.resultType = ResultType.SINGLE_RESULT;
        if(commandExecutor != null){
            return (U) commandExecutor.execute(this);
        }
        return executeSingleResult(Context.getCommandContext());
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

    @Override
    public List<U> list() {
        this.resultType = ResultType.LIST;
        if(commandExecutor != null){
            return  (List<U>) commandExecutor.execute(this);
        }
        return executeList(Context.getCommandContext(),null);
    }



    protected void addOrder(String colum, String sortOrder, NullHandlingOnOrder nullHandlingOnOrder){

    }

    public long count(){
        this.resultType = ResultType.COUNT;
        if(commandExecutor != null){
            return (Long) commandExecutor.execute(this);
        }
        return executeCount(Context.getCommandContext());
    }

    public Object execute(CommandContext commandContext){
        return executeCount(commandContext);
    }

    public abstract  long executeCount(CommandContext commandContext);

    public abstract  List<U> executeList(CommandContext commandContext, Page page);

}
