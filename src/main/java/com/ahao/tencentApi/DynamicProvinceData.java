package com.ahao.tencentApi;

import com.ahao.mapper.ChinaTotalMapper;
import com.ahao.pojo.ChinaTotal;
import com.ahao.pojo.Global;
import com.ahao.pojo.NocvData;
import com.ahao.service.GlobalService;
import com.ahao.service.NocvService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Component
public class DynamicProvinceData {


    @Autowired
    private NocvService nocvService;
    @Autowired
    private GlobalService globalService;
    @Autowired
    private ChinaTotalMapper chinaTotalMapper;


    @Scheduled(cron = "0 0 0/4 * * ?")  //每天第4×小时执行一次
//    @Scheduled(fixedDelay = 3600000*4)
    public void getData() throws IOException, ParseException {

        HttpUtils httpUtils = new HttpUtils();
        String str = httpUtils.getData();

        // ==========================================
        // 中国疫情总数数据
        // ==========================================

        JSONObject allJson = JSONObject.parseObject(str);
        JSONObject data = allJson.getJSONObject("data");
        JSONArray areaTreeMap = data.getJSONArray("areaTree");

        JSONObject chinaJson = areaTreeMap.getJSONObject(2);
        String lastUpdateTime = chinaJson.getString("lastUpdateTime");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date updateTime = sdf.parse(lastUpdateTime);
        JSONArray childrenMap = chinaJson.getJSONArray("children");
        Object[] array = childrenMap.toArray();

        ArrayList<NocvData> list = new ArrayList<>();

        for (int i = 0; i < array.length; i++) {

            JSONObject provinceMap = childrenMap.getJSONObject(i);
            //省份名称
            String name = provinceMap.getString("name");
            //确诊病例 = 总数 - 治愈 - 死亡
            JSONObject total = provinceMap.getJSONObject("total");
            Integer confirm = total.getInteger("confirm");
            Integer heal = total.getInteger("heal");
            Integer dead = total.getInteger("dead");

            NocvData nocvData = new NocvData();

            nocvData.setName(name);
            nocvData.setValue(confirm-heal-dead);
            nocvData.setUpdateTime(updateTime);
            list.add(nocvData);
        }

        boolean a = nocvService.saveBatch(list);
        if (a){
            System.out.println("地图数据更新成功");
        }else {
            System.out.println("地图数据更新失败");
        }

        // ==========================================
        // 全球疫情数据
        // ==========================================

        JSONObject dataJson = allJson.getJSONObject("data");
        JSONArray areaTreeMap2 = dataJson.getJSONArray("areaTree");
        Object[] areaArray = areaTreeMap2.toArray();
        ArrayList<Global> list2 = new ArrayList<>();

        for (int i = 0; i < areaArray.length; i++) {

            JSONObject country = areaTreeMap2.getJSONObject(i);
            // 1. 获取当前国家名字
            String countryName = country.getString("name");
            // 2. 获取total包
            JSONObject num = country.getJSONObject("total");
            // 2.1 获取时间字符串 并处理格式
            String overseaLastUpdateTime = country.getString("lastUpdateTime");
            Date updateTime2 = sdf.parse(overseaLastUpdateTime);
            // 2.2 目前确诊人数计算
            Integer value = num.getInteger("confirm") - num.getInteger("heal") - num.getInteger("dead");
            // 3 插入数据库
            Global global = new Global();
            global.setName(countryName);
            global.setValue(value);
            global.setUpdateTime(updateTime2);
            list2.add(global);

        }

        boolean b = globalService.saveBatch(list2);
        if (b){
            System.out.println("全球数据保存成功！");
        }else {
            System.out.println("全球数据保存失败！");
        }

        // ==========================================
        // 中国疫情总数数据
        // ==========================================

        // 第一层
        JSONObject jsonObject = JSONObject.parseObject(str);
        JSONObject keyData = jsonObject.getJSONObject("data");
        String timeString = keyData.getString("overseaLastUpdateTime");
        Date updateTime3 = sdf.parse(timeString);

        //第二层
        JSONObject keyChinaTotal = keyData.getJSONObject("chinaTotal");

        JSONObject keyTotal = keyChinaTotal.getJSONObject("total");

        ChinaTotal chinaTotal = new ChinaTotal();
        chinaTotal.setConfirm(keyTotal.getInteger("confirm"));
        chinaTotal.setInput(keyTotal.getInteger("input"));
        chinaTotal.setSevere(keyTotal.getInteger("severe"));
        chinaTotal.setHeal(keyTotal.getInteger("heal"));
        chinaTotal.setDead(keyTotal.getInteger("dead"));
        chinaTotal.setSuspect(keyTotal.getInteger("suspect"));
        chinaTotal.setUpdateTime(updateTime3);
        int c = chinaTotalMapper.insert(chinaTotal);
        if (c == 1){
            System.out.println("顶部数据更新成功");
        }else{
            System.out.println("顶部总数数据更新失败");
        }

        //保持数据更新到redis缓存
        Jedis jedis = new Jedis("127.0.0.1");
        if (jedis!=null){
            jedis.flushDB();
        }

    }
}
