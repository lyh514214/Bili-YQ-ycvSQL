package com.ahao.reids;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.ZAddParams;

import java.util.HashMap;
import java.util.Map;

public class reidsTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1");
        jedis.flushDB();
        //String
        jedis.set("ahao","666");
        //Hash
        jedis.hset("ahaoHash","test1","98");
        jedis.hset("ahaoHash","test2","99");
        jedis.hset("ahaoHash","test3","100");
        jedis.hset("ahaoHash","test4","97");
        //List
        jedis.lpush("ahaoList","111","222","333","444","111");
        //Set
        jedis.sadd("ahaoSet1","555","666","777","888","555");
        //sorted set
        jedis.zadd("ahaoSet2",75,"ccc");
        jedis.zadd("ahaoSet2",86,"bbb");
        jedis.zadd("ahaoSet2",90,"AAA");
        jedis.zadd("ahaoSet2",100,"ahao");
        jedis.zadd("ahaoSet2",87,"BBB");

    }
}
