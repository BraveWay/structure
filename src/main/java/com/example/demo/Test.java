package com.example.demo;


public class Test {

    public static void main(String[] args) {
//        //1.创建sqlsessionFactory
//        SqlSessionFactory sqlSessionFactory = DBtools.getSqlSessionFactory();
//
//        //2.创建SqlSession
//        SqlSession session = sqlSessionFactory.openSession();
//        //3.session 中创建相应的接口代理类，即mapper对象
//        //UserDao userBeanMapper = session.getMapper(UserDao.class);
//        //System.out.println(userBeanMapper.findById(1));
//
//        DbSqlSessionFactory sessionFactory = new DbSqlSessionFactory();

//        UserDataManager userManager = new MybatisUserDataManager(session,sessionFactory);
//
//        MybatisStudentDataManager studentManager = new MybatisStudentDataManager(session,sessionFactory);
//
//        User user = userManager.findById("11");
//        System.out.println(user.getClass().getName());
//        System.out.println(userManager.findById("11"));
//        System.out.println(studentManager.findById("1"));
//
//        ConfigurationImpl impl = new ConfigurationImpl();
//        impl.init();

//        System.out.println(userManager.findUserByName("asdfasdf"));

        //User user =  session.selectOne("com.example.demo.dao.UserDao.selectOne",1);

        //System.out.println(user);


        try {
//            System.out.println(userBeanMapper.deleteUserByName("jun"));
//            UserBean u1 = new UserBean();
//            u1.setAge(28);
//            u1.setName("king");
//            u1.setId(3);
//            System.out.println(userBeanMapper.insertUser(u1));
//            session.commit();//一定要提交，不然所有增删改操作不会生效的
//            System.out.println(userBeanMapper.queryAll());
        }catch (Exception e){
//            session.rollback();//回滚
        }

    }
}
