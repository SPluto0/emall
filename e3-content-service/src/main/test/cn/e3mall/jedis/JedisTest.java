package cn.e3mall.jedis;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2019/8/14 17:29
 */
public class JedisTest {
    @Test
    public void testJedis()throws Exception{
        //创建一个连接redis对象，参数：host、port
        Jedis jedis = new Jedis("192.168.25.250",6379);
        //直接使用jedis 操作redis。所有jedis的命令都对应一个方法。
        jedis.set("test123","my first jedis test");
        String s = jedis.get("test123");
        System.out.println(s);
        //关闭连接
        jedis.close();
    }

    @Test
    public void testJedisPool()throws Exception {
        //创建一个连接池对象，两个参数host、port
        JedisPool jedisPool = new JedisPool("192.168.25.250",6379);
        //从连接池获得一个连接，就是一个jedis对象
        Jedis jedis = jedisPool.getResource();
        //使用Jedis操作redis
        String s = jedis.get("test123");
        System.out.println(s);
        //关闭连接，每次使用完毕后关闭连接。连接池回收资源。
        jedis.close();
        //关闭连接池。
        jedisPool.close();
    }

    @Test
    public void testJedisCluster()throws Exception{
        //创建一个JedisCluster对象。有一个参数nodes是一个set类型。set中包含若干个HostAndPort对象
        //创建一个连接池对象，两个参数host、port
        Set<HostAndPort> node = new HashSet<>();
        //从连接池获得一个连接，就是一个jedis对象
        node.add(new HostAndPort("192.168.25.250",7001));
        node.add(new HostAndPort("192.168.25.250",7002));
        node.add(new HostAndPort("192.168.25.250",7003));
        node.add(new HostAndPort("192.168.25.250",7004));
        node.add(new HostAndPort("192.168.25.250",7005));
        node.add(new HostAndPort("192.168.25.250",7006));
        //直接使用JedisCluster对象操作redis。
        JedisCluster jedisCluster = new JedisCluster(node);
        jedisCluster.set("a","123");
        System.out.println(jedisCluster.get("a"));
        //关闭JedisCluster对象
        jedisCluster.close();
    }
}
