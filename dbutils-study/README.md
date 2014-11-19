# dbutils-study

## 1. 基础查询

	<pre>
	@Test
	public void testBasicQuery() throws SQLException {
	    ResultSetHandler<Object[]> rh = new ResultSetHandler<Object[]>() {
	        @Override
	        public Object[] handle(ResultSet resultSet) throws SQLException {
	            if (!resultSet.next()) {
	                return null;
	            }
	            ResultSetMetaData metaData = resultSet.getMetaData();
	            int cols = metaData.getColumnCount();
	            Object[] result = new Object[cols];
	            for (int i = 0; i < cols; i++) {
	                Object r = resultSet.getObject(i + 1);
	                result[i] = r;
	            }
	            return result;
	        }
	    };
	    QueryRunner runner = new QueryRunner(dataSource);
	    Object[] result = runner.query("select * from sys_user where username= ? ", rh, "localhost");
	    System.out.println(result);
	}
	</pre>


## 2. DbUtils提供的ResultSetHandler实现类
### 2.1 读取某个字段
#### ScalarHandler
ScalarHandler返回第一行数据某个字段的值。它提供了3个构造函数
<pre>
ScalarHandler()
ScalarHandler(int columnIndex)
ScalarHandler(String columnName)
</pre>
如果指定了具体的字段名称，则返回对应的值。否则按照指定的索引返回字段，默认的索引值为1 
<pre>
public void testScalarListHandlerGetUsername() throws SQLException {
    QueryRunner runner = new QueryRunner(dataSource);
    ResultSetHandler<String> sysUserResultSetHandler = new ScalarHandler<String>();
    String result = runner.query("select username from sys_user ", sysUserResultSetHandler);
    System.out.println(result);
}
</pre>
* 使用默认索引1
<pre>
@Test
public void testScalarListHandlerDefault() throws SQLException {
    QueryRunner runner = new QueryRunner(dataSource);
    ResultSetHandler<Integer> sysUserResultSetHandler = new ScalarHandler<Integer>();
    Integer result = runner.query("select * from sys_user ", sysUserResultSetHandler);
    System.out.println(result);
}
</pre>
* 使用索引值2
<pre>
@Test
public void testScalarListHandlerIndex2() throws SQLException {
    QueryRunner runner = new QueryRunner(dataSource);
    ResultSetHandler<String> sysUserResultSetHandler = new ScalarHandler<String>(2);
    String result = runner.query("select * from sys_user ", sysUserResultSetHandler);
    System.out.println(result);
}
</pre>
* 使用字段名username
<pre>
@Test
public void testScalarListHandlerUsername() throws SQLException {
    QueryRunner runner = new QueryRunner(dataSource);
    ResultSetHandler<String> sysUserResultSetHandler = new ScalarHandler<String>("username");
    String result = runner.query("select * from sys_user ", sysUserResultSetHandler);
    System.out.println(result);
}
</pre>
#### ColumnListHandler
ColumnListHandler返回某个字段的集合。它提供了3个构造函数
<pre>
ColumnListHandler()
ColumnListHandler(int columnIndex)
ColumnListHandler(String columnName)
</pre>
如果指定了具体的字段名称，则返回对应的值。否则按照指定的索引返回字段，默认的索引值为1 
* 使用默认索引1
<pre>
@Test
public void testColumnListResultDefault() throws SQLException {
    QueryRunner runner = new QueryRunner(dataSource);
    ResultSetHandler<List<Integer>> sysUserResultSetHandler = new ColumnListHandler<Integer>();
    List<Integer> result = runner.query("select * from sys_user ", sysUserResultSetHandler);
    Assert.assertNotNull(result);
    Assert.assertNotNull(result.get(1));
    System.out.println(result);
    System.out.println(result.size());
}
</pre>
* 使用索引值2
<pre>
@Test
public void testColumnListResultIndex2() throws SQLException {
    QueryRunner runner = new QueryRunner(dataSource);
    ResultSetHandler<List<String>> sysUserResultSetHandler = new ColumnListHandler<String>(2);
    List<String> result = runner.query("select * from sys_user ", sysUserResultSetHandler);
    Assert.assertNotNull(result);
    Assert.assertNotNull(result.get(1));
    System.out.println(result);
    System.out.println(result.size());
}
</pre>
* 使用字段名username
<pre>
@Test
public void testColumnListResultUsername() throws SQLException {
    QueryRunner runner = new QueryRunner(dataSource);
    ResultSetHandler<List<String>> sysUserResultSetHandler = new ColumnListHandler<String>("username");
    List<String> result = runner.query("select * from sys_user ", sysUserResultSetHandler);
    Assert.assertNotNull(result);
    Assert.assertNotNull(result.get(1));
    System.out.println(result);
    System.out.println(result.size());
}
</pre>

### 2.2 每行数据以Object[]对象的形式返回
#### ArrayHandler
ArrayHandler以Object[]的形式返回第一行数据。它提供了2个构造函数
<pre>
ArrayHandler()
ArrayHandler(RowProcessor convert)
</pre>
测试代码
<pre>
@Test
public void testArrayResult() throws SQLException {
    QueryRunner runner = new QueryRunner(dataSource);
    ResultSetHandler<Object[]> sysUserResultSetHandler = new ArrayHandler();
    Object[] result = runner.query("select * from sys_user ", sysUserResultSetHandler);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.length > 0);
}
</pre>
#### ArrayListHandler
ArrayListHandler以Object[]的形式返回数据的集合。它提供了2个构造函数
<pre>
ArrayListHandler()
ArrayListHandler(RowProcessor convert)
</pre>
测试代码
<pre>
@Test
public void testArrayListResult() throws SQLException {
    QueryRunner runner = new QueryRunner(dataSource);
    ResultSetHandler<List<Object[]>> sysUserResultSetHandler = new ArrayListHandler();
    List<Object[]> result = runner.query("select * from sys_user ", sysUserResultSetHandler);
    Assert.assertNotNull(result);
    System.out.println(result.get(0).length);
    Assert.assertFalse(result.isEmpty());
}
</pre>

### 2.3 每行数据以map对象的形式返回
#### MapHandler
MapHandler以{字段名 : 字段值}的map形式返回第一行数据。它提供了2个构造函数
<pre>
MapHandler()
MapHandler(RowProcessor convert)
</pre>
测试代码
<pre>
@Test
public void testMapHandler() throws SQLException {
    QueryRunner runner = new QueryRunner(dataSource);
    ResultSetHandler<Map<String, Object>> sysUserResultSetHandler = new MapHandler();
    Map<String, Object> result = runner.query("select * from sys_user ", sysUserResultSetHandler);
    Assert.assertNotNull(result);
    Assert.assertNotNull(result.get("full_name"));
    System.out.println(result);
    System.out.println(result.size());
}
</pre>
#### MapListHandler
MapListHandler以{字段名 : 字段值}的map形式返回数据的集合。它提供了2个构造函数
<pre>
MapListHandler()
MapListHandler(RowProcessor convert)
</pre>
测试代码
<pre>
@Test
public void testMapListHandler() throws SQLException {
    QueryRunner runner = new QueryRunner(dataSource);
    ResultSetHandler<List<Map<String, Object>>> sysUserResultSetHandler = new MapListHandler();
    List<Map<String, Object>> result = runner.query("select * from sys_user ", sysUserResultSetHandler);
    Assert.assertNotNull(result);
    Assert.assertNotNull(result.get(0).get("full_name"));
    System.out.println(result);
    System.out.println(result.size());
}
</pre>

### 2.4 每行数据以JavaBean的形式返回
#### BeanHandler
BeanHandler以JavaBean的形式返回第一行数据。它提供了2个构造函数
<pre>
BeanHandler(Class<T> type)
BeanHandler(Class<T> type, RowProcessor convert)
</pre>
测试代码
<pre>
@Test
public void testBeanResult() throws SQLException {
    QueryRunner runner = new QueryRunner(dataSource);
    ResultSetHandler<SysUser> sysUserResultSetHandler = new BeanHandler<SysUser>(SysUser.class);
    SysUser sysUser = runner.query("select * from sys_user where username = ?", sysUserResultSetHandler, "root");
    Assert.assertNotNull(sysUser);
    Assert.assertEquals("root", sysUser.getUsername());
}
</pre>
#### BeanListHandler
BeanListHandler以JavaBean形式返回数据的集合。它提供了2个构造函数
<pre>
BeanListHandler(Class<T> type)
BeanListHandler(Class<T> type, RowProcessor convert)
</pre>
测试代码
<pre>
@Test
public void testBeanListResult() throws SQLException {
    QueryRunner runner = new QueryRunner(dataSource);
    ResultSetHandler<List<SysUser>> sysUserResultSetHandler = new BeanListHandler<SysUser>(SysUser.class);
    List<SysUser> result = runner.query("select * from sys_user", sysUserResultSetHandler);
    Assert.assertNotNull(result);
    Assert.assertFalse(result.isEmpty());
}
</pre>

**注意** BeanHandler和BeanListHandler默认使用BeanProcessor将数据转为JavaBean，要求字段名和属性名完全一样，比如full_name的字段，对应JavaBean的属性为full_name。如果我们的属性名为驼峰格式，则需要使用GenerousBeanProcessor
<pre>
@Test
public void testBeanResultHump() throws SQLException {
    QueryRunner runner = new QueryRunner(dataSource);
    ResultSetHandler<SysUser> sysUserResultSetHandler = new BeanHandler<SysUser>(SysUser.class, new BasicRowProcessor(new GenerousBeanProcessor()));
    SysUser sysUser = runner.query("select * from sys_user where username = ?", sysUserResultSetHandler, "root");
    Assert.assertNotNull(sysUser);
    Assert.assertEquals("root", sysUser.getUsername());
    System.out.println(sysUser.getFullName());
}
</pre>

### 2.5 整个结果集以Map的形式返回
#### BeanMapHandler
BeanMapHandler以{字段值 : JavaBean}的map对象返回整个结果集。它提供了4个构造函数
<pre>
BeanMapHandler(Class<V> type)
BeanMapHandler(Class<V> type, RowProcessor convert)
BeanMapHandler(Class<V> type, int columnIndex)
BeanMapHandler(Class<V> type, String columnName)
</pre>
如果指定了具体的字段名称，使用该字段的值作为key。否则按照指定的索引返回字段值作为key，默认的索引值为1 
* 使用默认索引1
<pre>
@Test
public void testBeanMapResultDefault() throws SQLException {
    QueryRunner runner = new QueryRunner(dataSource);
    ResultSetHandler<Map<Integer, SysUser>> sysUserResultSetHandler = new BeanMapHandler<Integer, SysUser>(SysUser.class);
    Map<Integer, SysUser> result = runner.query("select * from sys_user ", sysUserResultSetHandler);
    Assert.assertNotNull(result);
    Assert.assertNotNull(result.get(1));
    System.out.println(result.get(1).getUsername());
    System.out.println(result);
}
</pre>
* 使用索引值2
<pre>
@Test
public void testBeanMapResultIndex2() throws SQLException {
    QueryRunner runner = new QueryRunner(dataSource);
    ResultSetHandler<Map<String, SysUser>> sysUserResultSetHandler = new BeanMapHandler<String, SysUser>(SysUser.class, 2);
    Map<String, SysUser> result = runner.query("select * from sys_user ", sysUserResultSetHandler);
    Assert.assertNotNull(result);
    Assert.assertNotNull(result.get("testname"));
    System.out.println(result.get("testname").getUsername());
    System.out.println(result);
}
</pre>
* 使用字段名username
<pre>
@Test
public void testBeanMapResultUsername() throws SQLException {
    QueryRunner runner = new QueryRunner(dataSource);
    ResultSetHandler<Map<String, SysUser>> sysUserResultSetHandler = new BeanMapHandler<String, SysUser>(SysUser.class, "username");
    Map<String, SysUser> result = runner.query("select * from sys_user ", sysUserResultSetHandler);
    Assert.assertNotNull(result);
    Assert.assertNotNull(result.get("testname"));
    System.out.println(result.get("testname").getUsername());
    System.out.println(result);
}
</pre>

#### KeyedHandler
KeyedHandler以{字段值 : map}的map对象返回整个结果集。它提供了4个构造函数
<pre>
KeyedHandler()
KeyedHandler(RowProcessor convert)
KeyedHandler(int columnIndex)
KeyedHandler(String columnName)
</pre>
如果指定了具体的字段名称，使用该字段的值作为key。否则按照指定的索引返回字段值作为key，默认的索引值为1 
* 使用默认索引1
<pre>
@Test
public void testKeyedHandlerDefault() throws SQLException {
    QueryRunner runner = new QueryRunner(dataSource);
    ResultSetHandler<Map<Integer, Map<String, Object>>> sysUserResultSetHandler = new KeyedHandler<Integer>();
    Map<Integer, Map<String, Object>> result = runner.query("select * from sys_user ", sysUserResultSetHandler);
    Assert.assertNotNull(result);
    Assert.assertNotNull(result.get(1));
    System.out.println(result);
    System.out.println(result.get(1).get("full_name"));
    System.out.println(result.size());
}
</pre>
* 使用索引值2
<pre>
@Test
public void testKeyedHandlerIndex2() throws SQLException {
    QueryRunner runner = new QueryRunner(dataSource);
    ResultSetHandler<Map<String, Map<String, Object>>> sysUserResultSetHandler = new KeyedHandler<String>(2);
    Map<String, Map<String, Object>> result = runner.query("select * from sys_user ", sysUserResultSetHandler);
    Assert.assertNotNull(result);
    Assert.assertNotNull(result.get("testname"));
    System.out.println(result);
    System.out.println(result.get("testname").get("full_name"));
    System.out.println(result.size());
}
</pre>
* 使用字段名username
<pre>
@Test
public void testKeyedHandlerUsername() throws SQLException {
    QueryRunner runner = new QueryRunner(dataSource);
    ResultSetHandler<Map<String, Map<String, Object>>> sysUserResultSetHandler = new KeyedHandler<String>("username");
    Map<String, Map<String, Object>> result = runner.query("select * from sys_user ", sysUserResultSetHandler);
    Assert.assertNotNull(result);
    Assert.assertNotNull(result.get("testname"));
    System.out.println(result);
    System.out.println(result.get("testname").get("full_name"));
    System.out.println(result.size());
}
</pre>


## 3. 使用DbUtils插入、修改、删除数据
### 3.1 插入数据
<pre>
@Test
public void testInsert() throws SQLException {
    QueryRunner runner = new QueryRunner(dataSource);
    int result = runner.update("insert into sys_user(user_id, username, password, full_name) values(?, ?, ?, ?)", 2, "test", "test", "test");
    Assert.assertEquals(1, result);
}
</pre>
### 3.2 修改数据
<pre>
@Test
public void testUpdate() throws SQLException {
    QueryRunner runner = new QueryRunner(dataSource);
    int result = runner.update("update sys_user set username = ? where user_id = ?", "test2", 2);
    Assert.assertEquals(1, result);
}
</pre>
### 3.3 删除数据
<pre>
@Test
public void testUpdate() throws SQLException {
    QueryRunner runner = new QueryRunner(dataSource);
    int result = runner.update("update sys_user set username = ? where user_id = ?", "test2", 2);
    Assert.assertEquals(1, result);
}
</pre>
### 3.4 返回自动生成的主键
<pre>
@Test
public void testInsertWithGeneratedKey() throws SQLException {
    QueryRunner runner = new QueryRunner(dataSource);
    ResultSetHandler<Long> r = new ScalarHandler<Long>();
    Long result = runner.insert("insert into test(name) values(?)", r, "test");
    System.out.println(result);
}
</pre>
上面代码中的ResultSetHandler可以用上一章节描述的ResultSetHandler实现
### 3.5 Batch
<pre>
    @Test
    public void testUpdateBatch() throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        Object[][] params = new Object[2][2];
        params[0][0] = "test3";
        params[0][1] = "1";
        params[1][0] = "test4";
        params[1][1] = "2";
        int[] result = runner.batch("update sys_user set username = ? where user_id = ?", params);
        System.out.println(result[0]);
        System.out.println(result[1]);
    }
</pre>
<pre>
@Test
public void testInsertBatch() throws SQLException {
    QueryRunner runner = new QueryRunner(dataSource);
    ResultSetHandler<List<Long>> r = new ColumnListHandler<Long>();
    Object[][] params = new Object[2][1];
    params[0][0] = "test3";
    params[1][0] = "test4";
    List<Long> result = runner.insertBatch("insert into test(name) values(?)", r, params);
    System.out.println(result);
}
</pre>

## 4. 异步执行
可以使用AsyncQueryRunner将对数据库的操作改为异步执行
<pre>
@Test
public void testAsync() throws SQLException, ExecutionException, InterruptedException {
    AsyncQueryRunner runner = new AsyncQueryRunner(Executors.newCachedThreadPool(), new QueryRunner(dataSource));
    Future<Integer> future = runner.update("update sys_user set username = ? where user_id = ?", "test", 1);
    int result = future.get();
    System.out.println(result);
    Assert.assertEquals(1, result);
}
</pre>
## 5. 使用Connection
QueryRunner除了使用DataSource外，还可以直接使用Connection。使用Connection需要自己关闭Connection
<pre>
@Test
public void testBasicQueryWithConn() throws SQLException {
    ResultSetHandler<Object[]> rh ...;
    QueryRunner runner = new QueryRunner();
    Connection conn = null;
    try {
        conn = dataSource.getConnection();
        Object[] result = runner.query(conn, "select * from sys_user where username= ? ", rh, "root");
    } finally {
        DbUtils.close(conn);
    }

}
</pre>
## 6. 事务
DBUtils并未对事务做封装，在实际项目中建议介入其他工具实现事务，如Spring-tx
<pre>
@Test
public void testTransaction() throws SQLException {
    QueryRunner runner = new QueryRunner();
    Connection conn = null;
    try {
        conn = dataSource.getConnection();
        conn.setAutoCommit(false);
        runner.update(conn, "insert into sys_user(user_id, username, password, full_name) values(?, ?, ?, ?)", 2, "test", "test", "test");
        conn.commit();
        throw new RuntimeException("rollback");
    } catch (Exception e) {
        DbUtils.rollback(conn);
    } finally {
        DbUtils.close(conn);
    }
}
</pre>
