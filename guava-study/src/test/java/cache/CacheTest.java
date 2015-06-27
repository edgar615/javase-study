package cache;

import coll.Person;
import com.google.common.base.Predicate;
import com.google.common.base.Ticker;
import com.google.common.cache.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Callables;
import jdk.nashorn.internal.ir.annotations.Immutable;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by edgar on 15-6-27.
 */
public class CacheTest {

    public class PersonService {
        private Person person1;
        private Person person2;
        private Person person3;
        private Person person4;
        private List<Person> personList;

        public PersonService() {
            person1 = new Person("Wilma", "Flintstone", 30, "F");
            person2 = new Person("Fred", "Flintstone", 32, "M");
            person3 = new Person("Betty", "Rubble", 31, "F");
            person4 = new Person("Barney", "Rubble", 33, "M");
            personList = Lists.newArrayList(person1, person2, person3, person4);
        }

        public Person getPersonByFirstName(String firstname) {
            System.out.println("find " + firstname);
            return Iterables.find(personList, new Predicate<Person>() {
                @Override
                public boolean apply(Person input) {
                    return firstname.equals(input.getFirstname());
                }
            });
        }
    }

    @Test
    public void test() {
        PersonService personService = new PersonService();
        LoadingCache<String, Person> personLoadingCache =
                CacheBuilder.newBuilder()
//                        .concurrencyLevel(10)
                        //缓存项在给定时间内没有被写访问（创建或覆盖），则回收。如果认为缓存数据总是在固定时候后变得陈旧不可用，这种回收方式是可取的。
                        .expireAfterWrite(5L, TimeUnit.SECONDS)
                                //缓存项在给定时间内没有被读/写访问，则回收。请注意这种缓存的回收顺序和基于大小回收一样。
//                        .expireAfterAccess(5L, TimeUnit.SECONDS)
//                        .refreshAfterWrite(5L,TimeUnit.SECONDS)
                        .maximumSize(5000L)
//                        .removalListener(new
//                                TradeAccountRemovalListener())
                        .ticker(Ticker.systemTicker())
                        .build(new CacheLoader<String, Person>() {
                            @Override
                            public Person load(String key) throws
                                    Exception {
                                return

                                        personService.getPersonByFirstName(key);
                            }
                        });
    }

    @Test
    public void testLoad() throws ExecutionException, InterruptedException {
        PersonService personService = new PersonService();
        LoadingCache<String, Person> personLoadingCache =
                CacheBuilder.newBuilder()
//                        .concurrencyLevel(10)
                        .expireAfterWrite(5L, TimeUnit.SECONDS)
//                        .expireAfterAccess(5L, TimeUnit.SECONDS)
//                        .refreshAfterWrite(5L,TimeUnit.SECONDS)
                        .maximumSize(5000L)
//                        .removalListener(new
//                                TradeAccountRemovalListener())
                        .ticker(Ticker.systemTicker())
                        .build(new CacheLoader<String, Person>() {
                            @Override
                            public Person load(String key) throws
                                    Exception {
                                return

                                        personService.getPersonByFirstName(key);
                            }
                        });
        personLoadingCache.get("Betty");
        personLoadingCache.get("Betty");
        TimeUnit.SECONDS.sleep(6);
        personLoadingCache.get("Betty");
        personLoadingCache.get("Edgar");
    }

    @Test
    public void testLoadUnchecked() throws ExecutionException, InterruptedException {
        PersonService personService = new PersonService();
        LoadingCache<String, Person> personLoadingCache =
                CacheBuilder.newBuilder()
                        .expireAfterWrite(5L, TimeUnit.SECONDS)
                        .maximumSize(5000L)
                        .ticker(Ticker.systemTicker())
                        .build(new CacheLoader<String, Person>() {
                            @Override
                            public Person load(String key) {
                                return

                                        personService.getPersonByFirstName(key);
                            }
                        });
        personLoadingCache.getUnchecked("Edgar");
    }

    @Test
    public void testLoadAll() throws ExecutionException, InterruptedException {
        PersonService personService = new PersonService();
        LoadingCache<String, Person> personLoadingCache =
                CacheBuilder.newBuilder()
                        .expireAfterWrite(5L, TimeUnit.SECONDS)
                        .maximumSize(5000L)
                        .ticker(Ticker.systemTicker())
                        .build(new CacheLoader<String, Person>() {
                            @Override
                            public Person load(String key) {
                                return

                                        personService.getPersonByFirstName(key);
                            }

                            @Override
                            public Map<String, Person> loadAll(Iterable<? extends String> keys) throws Exception {
                                return super.loadAll(keys);
                            }
                        });
        personLoadingCache.getAll(ImmutableList.of("Betty", "Barney"));
    }

    @Test
    public void testCallable() throws ExecutionException, InterruptedException {
        PersonService personService = new PersonService();
        Cache<String, Person> personLoadingCache =
                CacheBuilder.newBuilder()
                        .expireAfterWrite(5L, TimeUnit.SECONDS)
                        .maximumSize(5000L)
                        .ticker(Ticker.systemTicker()).build();
        personLoadingCache.get("Betty", new Callable<Person>() {
            @Override
            public Person call() throws Exception {
                return personService.getPersonByFirstName("Betty");
            }
        });

        personLoadingCache.get("Barney", Callables.returning(personService.getPersonByFirstName("Barney")));
    }

    @Test
    public void testInvalidate() throws ExecutionException, InterruptedException {
        PersonService personService = new PersonService();
        LoadingCache<String, Person> personLoadingCache =
                CacheBuilder.newBuilder()
                        .expireAfterWrite(5L, TimeUnit.SECONDS)
                        .maximumSize(5000L)
                        .ticker(Ticker.systemTicker())
                        .build(new CacheLoader<String, Person>() {
                            @Override
                            public Person load(String key) {
                                return

                                        personService.getPersonByFirstName(key);
                            }

                            @Override
                            public Map<String, Person> loadAll(Iterable<? extends String> keys) throws Exception {
                                return super.loadAll(keys);
                            }
                        });
        personLoadingCache.get("Betty");
        personLoadingCache.invalidate("Betty");
        personLoadingCache.get("Betty");
    }

    //refresh
    @Test
    public void testRefresh() throws ExecutionException, InterruptedException {
        PersonService personService = new PersonService();
        LoadingCache<String, Person> personLoadingCache =
                CacheBuilder.newBuilder()
                        .expireAfterWrite(5L, TimeUnit.SECONDS)
                        .maximumSize(5000L)
                        .ticker(Ticker.systemTicker())
                        .build(new CacheLoader<String, Person>() {
                            @Override
                            public Person load(String key) {
                                return

                                        personService.getPersonByFirstName(key);
                            }

                            @Override
                            public Map<String, Person> loadAll(Iterable<? extends String> keys) throws Exception {
                                return super.loadAll(keys);
                            }
                        });
        personLoadingCache.get("Betty");
        personLoadingCache.refresh("Betty");
    }

    //reload
    @Test
    public void testRefreshAfterWrite() throws ExecutionException, InterruptedException {
        PersonService personService = new PersonService();
        LoadingCache<String, Person> personLoadingCache =
                CacheBuilder.newBuilder()
                        .expireAfterWrite(5L, TimeUnit.SECONDS)
                        .refreshAfterWrite(3L, TimeUnit.SECONDS)
                        .maximumSize(5000L)
                        .ticker(Ticker.systemTicker())
                        .build(new CacheLoader<String, Person>() {
                            @Override
                            public Person load(String key) {
                                return

                                        personService.getPersonByFirstName(key);
                            }

                            @Override
                            public Map<String, Person> loadAll(Iterable<? extends String> keys) throws Exception {
                                return super.loadAll(keys);
                            }
                        });
        personLoadingCache.get("Betty");
        TimeUnit.SECONDS.sleep(6);
    }

    //显式插入
    @Test
    public void testPut() throws ExecutionException, InterruptedException {
        PersonService personService = new PersonService();
        LoadingCache<String, Person> personLoadingCache =
                CacheBuilder.newBuilder()
                        .expireAfterWrite(5L, TimeUnit.SECONDS)
                        .maximumSize(5000L)
                        .ticker(Ticker.systemTicker())
                        .build(new CacheLoader<String, Person>() {
                            @Override
                            public Person load(String key) {
                                return

                                        personService.getPersonByFirstName(key);
                            }

                            @Override
                            public Map<String, Person> loadAll(Iterable<? extends String> keys) throws Exception {
                                return super.loadAll(keys);
                            }
                        });
        personLoadingCache.put("Betty", new Person("", "", 0, "F"));
        personLoadingCache.asMap().put("Barney", new Person("", "", 0, "F"));
        personLoadingCache.get("Betty");
        personLoadingCache.get("Barney");
    }

    //基于容量的回收
    @Test
    public void testMaxSize() throws ExecutionException, InterruptedException {
        PersonService personService = new PersonService();
        LoadingCache<String, Person> personLoadingCache =
                CacheBuilder.newBuilder()
                        .expireAfterWrite(5L, TimeUnit.SECONDS)
                        .maximumSize(1L)
                        .ticker(Ticker.systemTicker())
                        .build(new CacheLoader<String, Person>() {
                            @Override
                            public Person load(String key) {
                                return

                                        personService.getPersonByFirstName(key);
                            }

                            @Override
                            public Map<String, Person> loadAll(Iterable<? extends String> keys) throws Exception {
                                return super.loadAll(keys);
                            }
                        });
        personLoadingCache.get("Betty");
        personLoadingCache.get("Barney");
        personLoadingCache.get("Betty");
    }

    //基于容量的回收——权重函数
    @Test
    public void testWeight() throws ExecutionException, InterruptedException {
        PersonService personService = new PersonService();
        LoadingCache<String, Person> personLoadingCache =
                CacheBuilder.newBuilder()
                        .expireAfterWrite(5L, TimeUnit.SECONDS)
                        .maximumSize(10L)
                        .ticker(Ticker.systemTicker())
//                        .weigher(new Weigher<String, Person>() {
//                            @Override
//                            public int weigh(String key, Person value) {
//                                return 5;
//                            }
//                        })
                        .build(new CacheLoader<String, Person>() {
                            @Override
                            public Person load(String key) {
                                return

                                        personService.getPersonByFirstName(key);
                            }

                            @Override
                            public Map<String, Person> loadAll(Iterable<? extends String> keys) throws Exception {
                                return super.loadAll(keys);
                            }
                        });
        personLoadingCache.get("Betty");
        personLoadingCache.get("Barney");
        personLoadingCache.get("Betty");
    }

    //监听器
    @Test
    public void testListener() throws ExecutionException, InterruptedException {
        RemovalListener<String, Person> personRemovalListener = new RemovalListener<String, Person>() {
            @Override
            public void onRemoval(RemovalNotification<String, Person> notification) {
                System.out.println(notification.getKey());
                System.out.println(notification.getValue());
            }
        };
        PersonService personService = new PersonService();
        LoadingCache<String, Person> personLoadingCache =
                CacheBuilder.newBuilder()
                        .expireAfterWrite(5L, TimeUnit.SECONDS)
                        .maximumSize(5000L)
                        .removalListener(personRemovalListener)
                        .ticker(Ticker.systemTicker())
                        .build(new CacheLoader<String, Person>() {
                            @Override
                            public Person load(String key) throws
                                    Exception {
                                return

                                        personService.getPersonByFirstName(key);
                            }
                        });
        personLoadingCache.get("Betty");
        personLoadingCache.invalidate("Betty");
    }

    //异步监听器
    @Test
    public void testAsyncListener() throws ExecutionException, InterruptedException {
        RemovalListener<String, Person> personRemovalListener = new RemovalListener<String, Person>() {
            @Override
            public void onRemoval(RemovalNotification<String, Person> notification) {
                System.out.println(notification.getKey());
                System.out.println(notification.getValue());
            }
        };
        ExecutorService service = Executors.newCachedThreadPool();
        PersonService personService = new PersonService();
        LoadingCache<String, Person> personLoadingCache =
                CacheBuilder.newBuilder()
                        .expireAfterWrite(5L, TimeUnit.SECONDS)
                        .maximumSize(5000L)
                        .removalListener(RemovalListeners.asynchronous(personRemovalListener, service))
                        .ticker(Ticker.systemTicker())
                        .build(new CacheLoader<String, Person>() {
                            @Override
                            public Person load(String key) throws
                                    Exception {
                                return

                                        personService.getPersonByFirstName(key);
                            }
                        });
        personLoadingCache.get("Betty");
        personLoadingCache.invalidate("Betty");
    }

    //统计
    @Test
    public void testStat() throws ExecutionException, InterruptedException {
        PersonService personService = new PersonService();
        LoadingCache<String, Person> personLoadingCache =
                CacheBuilder.newBuilder()
                        .expireAfterWrite(5L, TimeUnit.SECONDS)
                        .maximumSize(5000L)
                        .ticker(Ticker.systemTicker())
                        .recordStats()
                        .build(new CacheLoader<String, Person>() {
                            @Override
                            public Person load(String key) throws
                                    Exception {
                                return

                                        personService.getPersonByFirstName(key);
                            }
                        });
        personLoadingCache.get("Betty");
        System.out.println(personLoadingCache.stats().hitCount());
        System.out.println(personLoadingCache.stats().hitRate());
        System.out.println(personLoadingCache.stats().evictionCount());
        personLoadingCache.get("Betty");
        System.out.println(personLoadingCache.stats().hitCount());
        System.out.println(personLoadingCache.stats().hitRate());
        System.out.println(personLoadingCache.stats().evictionCount());
        TimeUnit.SECONDS.sleep(6);
        System.out.println(personLoadingCache.stats().hitCount());
        System.out.println(personLoadingCache.stats().hitRate());
        System.out.println(personLoadingCache.stats().evictionCount());
    }

    //测试多线程get
    @Test
    public void testGet() throws ExecutionException, InterruptedException {
        PersonService personService = new PersonService();
        LoadingCache<String, Person> personLoadingCache =
                CacheBuilder.newBuilder()
                        .expireAfterWrite(5L, TimeUnit.SECONDS)
                        .maximumSize(5000L)
                        .ticker(Ticker.systemTicker())
                        .recordStats()
                        .build(new CacheLoader<String, Person>() {
                            @Override
                            public Person load(String key) throws
                                    Exception {
                                return

                                        personService.getPersonByFirstName(key);
                            }
                        });
        ExecutorService service = Executors.newCachedThreadPool();
        CyclicBarrier barrier = new CyclicBarrier(9);
        for (int i = 0; i < 10; i ++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        barrier.await();
                        personLoadingCache.get("Betty");
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        TimeUnit.SECONDS.sleep(5);
    }
}
