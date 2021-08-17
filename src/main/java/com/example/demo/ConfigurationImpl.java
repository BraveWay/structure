package com.example.demo;

import com.example.demo.persistence.cache.EntityCache;
import com.example.demo.persistence.cache.EntityCacheImpl;
import com.example.demo.common.*;
import com.example.demo.core.DbSqlSessionFactory;
import com.example.demo.persistence.entity.data.impl.MybatisUserDataManager;
import com.example.demo.util.ReflectUtil;
import com.example.demo.core.SessionFactory;
import com.example.demo.impl.GenericManagerFactory;
import com.example.demo.impl.cfg.CommandExecutorImpl;
import com.example.demo.interceptor.*;
import com.example.demo.service.ServiceImpl;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

public class ConfigurationImpl {

    private static Logger log = LoggerFactory.getLogger(ConfigurationImpl.class);

    protected SqlSessionFactory sqlSessionFactory;
    protected TransactionFactory transactionFactory;

    protected CommandContextFactory commandContextFactory;

    protected String databaseType;
    protected String jdbcDriver = "org.h2.Driver";
    protected String jdbcUrl = "jdbc:h2:tcp://localhost/~/activiti";
    protected String jdbcUsername = "sa";
    protected String jdbcPassword = "";
    protected String dataSourceJndiName;
    protected boolean isDbHistoryUsed = true;
    protected int jdbcMaxActiveConnections;
    protected int jdbcMaxIdleConnections;
    protected int jdbcMaxCheckoutTime;
    protected int jdbcMaxWaitTime;
    protected boolean jdbcPingEnabled;
    protected String jdbcPingQuery;
    protected int jdbcPingConnectionNotUsedFor;
    protected int jdbcDefaultTransactionIsolationLevel;

    protected List<SessionFactory> customSessionFactories;
    protected DbSqlSessionFactory dbSqlSessionFactory;
    protected Map<Class<?>, SessionFactory> sessionFactories;

    protected DataSource dataSource;

    public static final String DEFAULT_MYBATIS_MAPPING_FILE = "config/mybatis-conf.xml";

    protected CommandExecutor commandExecutor;

    //DATA MANAGERS ////////////////////
    protected UserDataManager userDataManager;


    //ENTITY MANAGERS ///////////////////////////////////////
    protected UserEntityManager userEntityManager;

    //SERVICES
    protected UserService userService = new UserServiceImpl(this);

    protected CommandConfig defaultCommandConfig;

    protected List<CommandInterceptor> customPreCommandInterceptors;
    protected List<CommandInterceptor> customPostCommandInterceptors;

    protected List<CommandInterceptor> commandInterceptors;

    protected CommandInterceptor commandInvoker;

   public void init(){
       initDataSource();
       initTransactionFactory();
       initSqlSessionFactory();
       initCommandContextFactory();
       initCommandExecutors();
       initServices();

       initSessionFactories();
       initDataManagers();
       initEntityManagers();
    }

    private void initDataSource(){
        if (dataSource == null) {
            if (dataSourceJndiName != null) {
                try {
                    dataSource = (DataSource) new InitialContext().lookup(dataSourceJndiName);
                } catch (Exception e) {
                    e.printStackTrace();;
                }

            } else if (jdbcUrl != null) {
                if ((jdbcDriver == null) || (jdbcUsername == null)) {
                    throw new RuntimeException("DataSource or JDBC properties have to be specified in a process engine configuration");
                }

                log.debug("initializing datasource to db: {}", jdbcUrl);

                PooledDataSource pooledDataSource = new PooledDataSource(ReflectUtil.getClassLoader(), jdbcDriver, jdbcUrl, jdbcUsername, jdbcPassword);

                if (jdbcMaxActiveConnections > 0) {
                    pooledDataSource.setPoolMaximumActiveConnections(jdbcMaxActiveConnections);
                }
                if (jdbcMaxIdleConnections > 0) {
                    pooledDataSource.setPoolMaximumIdleConnections(jdbcMaxIdleConnections);
                }
                if (jdbcMaxCheckoutTime > 0) {
                    pooledDataSource.setPoolMaximumCheckoutTime(jdbcMaxCheckoutTime);
                }
                if (jdbcMaxWaitTime > 0) {
                    pooledDataSource.setPoolTimeToWait(jdbcMaxWaitTime);
                }
                if (jdbcPingEnabled) {
                    pooledDataSource.setPoolPingEnabled(true);
                    if (jdbcPingQuery != null) {
                        pooledDataSource.setPoolPingQuery(jdbcPingQuery);
                    }
                    pooledDataSource.setPoolPingConnectionsNotUsedFor(jdbcPingConnectionNotUsedFor);
                }
                if (jdbcDefaultTransactionIsolationLevel > 0) {
                    pooledDataSource.setDefaultTransactionIsolationLevel(jdbcDefaultTransactionIsolationLevel);
                }
                dataSource = pooledDataSource;
            }

            if (dataSource instanceof PooledDataSource) {
                // ACT-233: connection pool of Ibatis is not properly
                // initialized if this is not called!
                ((PooledDataSource) dataSource).forceCloseAll();
            }
        }

    }

    public void initTransactionFactory() {
        transactionFactory = new JdbcTransactionFactory();
    }

    private  void initSqlSessionFactory(){

        if (sqlSessionFactory == null) {
            InputStream inputStream = null;
            try {
                inputStream = getMyBatisXmlConfigurationStream();

                Environment environment = new Environment("default", transactionFactory, dataSource);
                Reader reader = new InputStreamReader(inputStream);
                Properties properties = new Properties();
//                properties.put("prefix", databaseTablePrefix);
//                String wildcardEscapeClause = "";
//                if ((databaseWildcardEscapeCharacter != null) && (databaseWildcardEscapeCharacter.length() != 0)) {
//                    wildcardEscapeClause = " escape '" + databaseWildcardEscapeCharacter + "'";
//                }
//                properties.put("wildcardEscapeClause", wildcardEscapeClause);
                //set default properties
                properties.put("limitBefore" , "");
                properties.put("limitAfter" , "");
                properties.put("limitBetween" , "");
                properties.put("limitOuterJoinBetween" , "");
                properties.put("limitBeforeNativeQuery" , "");
                properties.put("orderBy" , "order by ${orderByColumns}");
                properties.put("blobType" , "BLOB");
                properties.put("boolValue" , "TRUE");

                if (databaseType != null) {
                    properties.load(getResourceAsStream("org/activiti/db/properties/"+databaseType+".properties"));
                }

                Configuration configuration = initMybatisConfiguration(environment, reader, properties);
                sqlSessionFactory = new DefaultSqlSessionFactory(configuration);
                System.out.println(sqlSessionFactory);

            } catch (Exception e) {
                throw new RuntimeException("Error while building ibatis SqlSessionFactory: " + e.getMessage(), e);
            } finally {
//                IoUtil.closeSilently(inputStream);
            }
        }
    }

    protected InputStream getResourceAsStream(String resource) {
        return ReflectUtil.getResourceAsStream(resource);
    }

    public InputStream getMyBatisXmlConfigurationStream() throws IOException {

        ClassPathResource resource = new ClassPathResource(DEFAULT_MYBATIS_MAPPING_FILE);
        InputStream inputStream = resource.getInputStream();

        return inputStream;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Configuration initMybatisConfiguration(Environment environment, Reader reader, Properties properties) {
        XMLConfigBuilder parser = new XMLConfigBuilder(reader, "", properties);
        Configuration configuration =  parser.parse();

        if(databaseType != null) {
            configuration.setDatabaseId(databaseType);
        }

        configuration.setEnvironment(environment);

//        initMybatisTypeHandlers(configuration);
//        initCustomMybatisMappers(configuration);

//        configuration = parseMybatisConfiguration(configuration, parser);
        return configuration;
    }



    public SqlSessionFactory sqlSessionFactory(){
       return sqlSessionFactory;
    }

    public Map<Class<?>,SessionFactory>  getSessionFactories(){
       return sessionFactories;
    }

    public UserEntityManager getUserEntityManager(){
       return userEntityManager;
    }

    public ConfigurationImpl setUserEntityManager(UserEntityManager userEntityManagerEntityManager){
       this.userEntityManager = userEntityManager;
       return this;
    }

    public void initCommandExecutors() {
        initDefaultCommandConfig();
        initCommandInvoker();
        initCommandInterceptors();
        initCommandExecutor();
    }

    public void initDefaultCommandConfig() {
        if (defaultCommandConfig == null) {
            defaultCommandConfig = new CommandConfig();
        }
    }

    public void initCommandInvoker(){
        commandInvoker = new CommandInvoker();
    }

    public void initCommandExecutor() {
        if (commandExecutor == null) {
            CommandInterceptor first = initInterceptorChain(commandInterceptors);
            commandExecutor = new CommandExecutorImpl(getDefaultCommandConfig(), first);
        }
    }

    public CommandConfig getDefaultCommandConfig() {
        return defaultCommandConfig;
    }

    public void setDefaultCommandConfig(CommandConfig defaultCommandConfig) {
        this.defaultCommandConfig = defaultCommandConfig;
    }

    public CommandInterceptor initInterceptorChain(List<CommandInterceptor> chain) {
        if (chain == null || chain.isEmpty()) {
            throw new RuntimeException("报错了");
//            throw new ActivitiException("invalid command interceptor chain configuration: " + chain);
        }
        for (int i = 0; i < chain.size() - 1; i++) {
            chain.get(i).setNext(chain.get(i + 1));
        }
        return chain.get(0);
    }

    public void initCommandContextFactory() {
        if (commandContextFactory == null) {
            commandContextFactory = new CommandContextFactory();
        }
        commandContextFactory.setConfigurationImpl(this);
    }

    public UserService getUserService() {
        return userService;
    }

    public ConfigurationImpl setTaskService(UserService userService) {
        this.userService = userService;
        return this;
    }

    public void initServices() {
        initService(userService);
    }

    public void initService(Object service) {
        if (service instanceof ServiceImpl) {
            ((ServiceImpl) service).setCommandExecutor(commandExecutor);
        }
    }


    public void initCommandInterceptors() {
        if (commandInterceptors == null) {
            commandInterceptors = new ArrayList<CommandInterceptor>();
            if (customPreCommandInterceptors != null) {
                commandInterceptors.addAll(customPreCommandInterceptors);
            }
            commandInterceptors.addAll(getDefaultCommandInterceptors());
            if (customPostCommandInterceptors != null) {
                commandInterceptors.addAll(customPostCommandInterceptors);
            }
            commandInterceptors.add(commandInvoker);
        }
    }

    public Collection<? extends CommandInterceptor> getDefaultCommandInterceptors() {
        List<CommandInterceptor> interceptors = new ArrayList<CommandInterceptor>();
//        interceptors.add(new LogInterceptor());


        if (commandContextFactory != null) {
            interceptors.add(new CommandContextInterceptor(commandContextFactory, this));
        }

        return interceptors;
    }

    public void initDataManagers() {
       if(userDataManager == null){
           userDataManager = new MybatisUserDataManager(this);
       }
    }

    public void initEntityManagers(){
       if(userEntityManager == null){
           userEntityManager = new UserEntityManagerImpl(this,userDataManager);
       }
    }

    public void initSessionFactories() {
        if (sessionFactories == null) {
            sessionFactories = new HashMap<Class<?>, SessionFactory>();

            initDbSqlSessionFactory();

            addSessionFactory(new GenericManagerFactory(EntityCache.class, EntityCacheImpl.class));
        }

        if (customSessionFactories != null) {
            for (SessionFactory sessionFactory : customSessionFactories) {
                addSessionFactory(sessionFactory);
            }
        }
    }

    public void initDbSqlSessionFactory() {
        if (dbSqlSessionFactory == null) {
            dbSqlSessionFactory = createDbSqlSessionFactory();
        }
        dbSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
        addSessionFactory(dbSqlSessionFactory);
    }

    public DbSqlSessionFactory createDbSqlSessionFactory() {
        return new DbSqlSessionFactory();
    }

    public void addSessionFactory(SessionFactory sessionFactory) {
        sessionFactories.put(sessionFactory.getSessionType(), sessionFactory);
    }
}
