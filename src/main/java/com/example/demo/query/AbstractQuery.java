package com.example.demo.query;

import com.example.demo.common.AbstractManager;
import com.example.demo.core.CommandContext;
import com.example.demo.impl.Context;
import com.example.demo.interceptor.Command;
import com.example.demo.interceptor.CommandExecutor;

import java.io.Serializable;

public abstract class AbstractQuery<T extends Query<?,?>,U> extends ListQueryParameterObject implements Command<Object>, Query<T,U>, Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SORTORDER_ASC = "asc";
    public static final String SORTORDER_DESC = "desc";

    private static enum ResultType{
        LIST, LIST_PAGE, SIGNLE_RESULT, COUNT
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

    public T asc(){
        return direction(Direction.ASCENDING);
    }

    public T direction(Direction direction){
       if(orderProperty == null){
           throw new RuntimeException("sss");
       }
       addOrder(orderProperty.getName(),direction.getName(),nullHandlingOnOrder);
       orderProperty = null;
       nullHandlingOnOrder  = null;
       return (T) this;

    }

    protected void addOrder(String colum, String sortOrder,NullHandlingOnOrder nullHandlingOnOrder){

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

}
