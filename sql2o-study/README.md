sql2o-study
===========

sql2o的学习，计划在dbutils和sql2o之间选择一个作为DAO的实现

**sql2o依赖于JDK1.7+**

## 1. 基本查询
<pre>
Sql2o sql2o = new Sql2o(dataSource);
try (Connection connection = sql2o.open()) {
    List<SysUser> sysUsers = connection.createQuery("select user_id as userId, username, password, full_name as fullName from sys_user")
            .executeAndFetch(SysUser.class);
    System.out.println(sysUsers);
}
</pre>

## 2. 查询结果
### 2.1 返回POJO
- 返回POJO的集合

<pre>
Sql2o sql2o = new Sql2o(dataSource);
try (Connection connection = sql2o.open()) {
    List<SysUser> sysUsers = connection.createQuery("select user_id as userId, username, password, full_name as fullName from sys_user")
            .executeAndFetch(SysUser.class);
    System.out.println(sysUsers);
}
</pre>

- 返回第一行数据的POJO对象

<pre>
Sql2o sql2o = new Sql2o(dataSource);
try (Connection connection = sql2o.open()) {
    SysUser sysUser = connection.createQuery("select user_id as userId, username, password, full_name as fullName from sys_user")
            .executeAndFetchFirst(SysUser.class);
    System.out.println(sysUser);
}
</pre>

### 2.2 返回Scalar
- 返回第一行数据的特定字段

<pre>
Sql2o sql2o = new Sql2o(dataSource);
try (Connection connection = sql2o.open()) {
    Object username = connection.createQuery("select username from sys_user order by user_id desc").executeScalar();
    System.out.println(username);
}
</pre>

<pre>
Sql2o sql2o = new Sql2o(dataSource);
try (Connection connection = sql2o.open()) {
    String username = connection.createQuery("select username from sys_user order by user_id desc").executeScalar(String.class);
    System.out.println(username);
}
</pre>

<pre>
Sql2o sql2o = new Sql2o(dataSource);
try (Connection connection = sql2o.open()) {
    Long count = connection.createQuery("select count(*) from sys_user").executeScalar(Long.class);
    System.out.println(count);
}
</pre>

- 返回特定字段的列表

<pre>
Sql2o sql2o = new Sql2o(dataSource);
try (Connection connection = sql2o.open()) {
    List<String> usernameList = connection.createQuery("select username from sys_user order by user_id desc").executeScalarList(String.class);
    System.out.println(usernameList);
}
</pre>

### 2.3 返回Table对象
<pre>
Sql2o sql2o = new Sql2o(dataSource);
try (Connection connection = sql2o.open()) {
    Table table = connection.createQuery("select * from sys_user").executeAndFetchTable();
    System.out.println(table.getName());
    System.out.println(table.rows());
    System.out.println(table.columns());
}
</pre>


### 2.4 返回Map对象的集合（实际上返回的是Table）
<pre>
Sql2o sql2o = new Sql2o(dataSource);
try (Connection connection = sql2o.open()) {
    List<Map<String, Object>> result = connection.createQuery("select * from sys_user").executeAndFetchTable().asList();
    System.out.println(result.get(0).get("username"));
}
</pre>

### 2.5 自定义返回的结果
具体使用见ResultSetHandler和Converter的用法，不做详细介绍


## 3. 懒加载

<pre>
Sql2o sql2o = new Sql2o(dataSource);
final String sql = "select user_id as userId, username, password, full_name as fullName from sys_user";
final int BATCH_SIZE = 5;
List<SysUser> batch = new ArrayList<SysUser>(BATCH_SIZE);
try (Connection connection = sql2o.open()) {
   try (ResultSetIterable<SysUser> sysUsers = connection.createQuery(sql)
            .executeAndFetchLazy(SysUser.class)) {
       for (SysUser sysUser : sysUsers) {
           System.out.println(batch.size());
           if (batch.size() == BATCH_SIZE) {
               // 批量处理数据

               batch.clear();
           }
           batch.add(sysUser);
       }
   }
}
</pre>

## 4. 自定义结果集的映射
<pre>
Sql2o sql2o = new Sql2o(dataSource);
try (Connection connection = sql2o.open()) {
    List<SysUser> sysUsers = connection.createQuery("select user_id, username, password, full_name from sys_user")
            .addColumnMapping("user_id", "userId")
            .addColumnMapping("full_name", "fullName")
            .executeAndFetch(SysUser.class);
    System.out.println(sysUsers);
}
</pre>

- **设置默认的映射**

<pre>
Map<String, String> colMaps = new HashMap<String,String>();
colMaps.put("DUE_DATE", "dueDate");
colMaps.put("DESC", "description");
colMaps.put("E_MAIL", "email");
colMaps.put("SHORT_DESC", "shortDescription");
sql2o.setDefaultColumnMappings(colMaps);
</pre>

## 5. 查询参数
<pre>
Sql2o sql2o = new Sql2o(dataSource);
try (Connection connection = sql2o.open()) {
    List<SysUser> sysUsers = connection.createQuery("select user_id, username, password, full_name from sys_user where username = :username")
            .addColumnMapping("user_id", "userId")
            .addColumnMapping("full_name", "fullName")
            .addParameter("username", "root")
            .executeAndFetch(SysUser.class);
    System.out.println(sysUsers);
}
</pre>

<pre>
Sql2o sql2o = new Sql2o(dataSource);
try (Connection connection = sql2o.open()) {
    List<SysUser> sysUsers = connection.createQueryWithParams("select user_id, username, password, full_name from sys_user where username = :p1", "root")
            .addColumnMapping("user_id", "userId")
            .addColumnMapping("full_name", "fullName")
            .executeAndFetch(SysUser.class);
    System.out.println(sysUsers);
}
</pre>

## 6. Update
<pre>
Sql2o sql2o = new Sql2o(dataSource);
try (Connection connection = sql2o.open()) {
    int result = connection.createQuery("update sys_user set full_name = :fullName where user_id = :userId")
            .addParameter("fullName", "Edgar")
            .addParameter("userId", 2454)
            .executeUpdate().getResult();
    System.out.println(result);
}
</pre>

## 7. Delete
<pre>
Sql2o sql2o = new Sql2o(dataSource);
try (Connection connection = sql2o.open()) {
    int result = connection.createQuery("delete from sys_user where user_id = :userId")
            .addParameter("userId", 2454)
            .executeUpdate().getResult();
    System.out.println(result);
}
</pre>

## 8. Insert
<pre>
Sql2o sql2o = new Sql2o(dataSource);
try (Connection connection = sql2o.open()) {
    int result = connection.createQuery("insert into sys_user(user_id, username, password, full_name) values(:userId, :username, :password, :fullName)")
            .addParameter("userId", 2454)
            .addParameter("username", 2454)
            .addParameter("password", 2454)
            .addParameter("fullName", 2454)
            .executeUpdate().getResult();
    System.out.println(result);
}
</pre>

- 返回自动生成的主键

<pre>
Sql2o sql2o = new Sql2o(dataSource);
try (Connection connection = sql2o.open()) {
    Object result = connection.createQuery("insert into test(name) values(:name)")
            .addParameter("name", "Test")
            .executeUpdate().getKey();
    System.out.println(result);
}
</pre>

- 使用POJO

<pre>
Sql2o sql2o = new Sql2o(dataSource);
SysUser sysUser = new SysUser();
sysUser.setUserId(9999);
sysUser.setUsername("9999");
sysUser.setPassword("9999");
sysUser.setFullName("9999");
try (Connection connection = sql2o.open()) {
    int result = connection.createQuery("insert into sys_user(user_id, username, password, full_name) values(:userId, :username, :password, :fullName)")
            .bind(sysUser)
            .executeUpdate().getResult();
    System.out.println(result);
}
</pre>

## 9. 事务
事务默认会在connection的close方法中回滚，所以必须在方法的最后使用commit提交
<pre>
String sql = "insert into sys_user(user_id, username, password, full_name) values(:userId, :username, :password, :fullName)";
Sql2o sql2o = new Sql2o(dataSource);
SysUser sysUser = new SysUser();
sysUser.setUserId(9999);
sysUser.setUsername("9999");
sysUser.setPassword("9999");
sysUser.setFullName("9999");
try (Connection connection = sql2o.beginTransaction()) {
     connection.createQuery(sql)
            .bind(sysUser)
            .executeUpdate().getResult();
    connection.createQuery(sql)
            .bind(sysUser)
            .executeUpdate().getResult();
    connection.commit();
}
</pre>

**If you don’t explisitely call commit() or rollback() on the Connection object, the transaction will automatically be rolled back when exiting the try-with-resources block.**

## 10. 批量操作
<pre>
Sql2o sql2o = new Sql2o(dataSource);
try (Connection connection = sql2o.beginTransaction()) {
    Query query = connection.createQuery("insert into test(name) values(:name)");
    for (int i = 0; i < 100; i ++) {
        query.addParameter("name", "test" + i).addToBatch();
    }
    int [] result = query.executeBatch().getBatchResult();
    System.out.println(result.length);
}
</pre>