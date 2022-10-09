package com.ahao.controller.graphsController;

import com.ahao.pojo.ChinaTotal;
import com.ahao.pojo.NocvData;
import com.ahao.service.ChinaTotalService;
import com.ahao.service.NewsService;
import com.ahao.service.NocvService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class NocvDataController {

    @Autowired
    private NocvService nocvService;
    @Autowired
    private ChinaTotalService chinaTotalService;
    @Autowired
    private NewsService newsService;

    //跳转到主控页 并顺带更新最新中国地图数据以主控页展示
    @RequestMapping("/")
    public String chinaTopData(Model model){
        ChinaTotal chinaTotal = chinaTotalService.queryNew();
        model.addAttribute("chinaTotal",chinaTotal);
        return "index";
    }

    //疫情地图
    @RequestMapping("/queryMap")
    @ResponseBody
    public List<NocvData> getNocvData(){

        Jedis jedis = new Jedis("127.0.0.1");
        // 0. 判断是否连接
        if (jedis!=null){
            List<String> nocvJsonData = jedis.lrange("nocvdata", 0, 33);
            // 1. redis有缓存
            if (nocvJsonData.size()==34){
                ArrayList<NocvData> list = new ArrayList<>();
                // 1.2. 遍历redis并返回前台
                for (String str : nocvJsonData){
                    JSONObject jsonObject = JSONObject.parseObject(str);
                    String name = jsonObject.getString("name");
                    Integer value = Integer.valueOf(jsonObject.getString("value"));
                    list.add(new NocvData(name,value));
                }
                return list;
            } else {
                // 3. 查数据库
                List<NocvData> list = nocvService.queryNewAll();
                jedis.del("nocvdata");
                for (NocvData nocvData : list){
                    // 4. 更新redis缓存
                    jedis.lpush("nocvdata", JSONObject.toJSONString(nocvData));
                }
                return list;
            }
        }
        //  连接redis失败,直接查数据库
        List<NocvData> list = nocvService.queryNewAll();
        System.out.println("redis连接失败,直接查数据库！");
        return list;
    }

    //饼状图
    @RequestMapping("/queryPie")
    @ResponseBody
    public List<NocvData> getNocvDataPie(){
        return nocvService.queryNewAll();
    }

    //柱状图数据
    @RequestMapping("/queryBar")
    @ResponseBody
    public HashMap<Object, Object> getNocvDateBar(){
        return nocvService.queryToBar();
    }

    //导出表格
    @RequestMapping("/outPortExcelCity")
    @ResponseBody
    public void outPortExcelCity(HttpServletResponse response){

        // 1.1  响应字符集
        response.setCharacterEncoding("UTF8");
        // 1.2  从数据库中获取数据（最新的34个）
        List<NocvData> list = nocvService.queryNewAll();
        HSSFWorkbook sheets = new HSSFWorkbook();

        // 2.1  创建Excel中的第一页
        HSSFSheet sheet = sheets.createSheet("中国省份疫情数据");
        // 2.2  创建字段
        HSSFRow firstRow = sheet.createRow(0);
        firstRow.createCell(0).setCellValue("省份名称");
        firstRow.createCell(1).setCellValue("确诊人数");

        // 3  遍历库中获取的数据 并 插入Excel
        for (NocvData nocvData: list){
            HSSFRow currentRow = sheet.createRow(sheet.getLastRowNum() + 1);
            currentRow.createCell(0).setCellValue(nocvData.getName());
            currentRow.createCell(1).setCellValue(nocvData.getValue());
        }

        // 4  建立输出流
        OutputStream stream = null;

        // 5  尝试输出
        try{
            // 6  设置Excel的名称
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String("中国疫情数据表".getBytes(),"iso-8859-1") + ".xls");
            stream = response.getOutputStream();
            sheets.write(stream);
            stream.flush();
        }catch(Exception e){
            e.printStackTrace();

        } finally {
            try {
                if(stream != null){
                    stream.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}