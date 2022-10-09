package com.ahao;

import com.ahao.pojo.News;
import com.ahao.pojo.NocvData;
import com.ahao.pojo.Role;
import com.ahao.service.ClassService;
import com.ahao.service.NewsService;
import com.ahao.service.NocvService;
import com.ahao.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.List;
import java.util.Map;

@SpringBootTest
class BiliYqYcvSqlApplicationTests {

    @Autowired
    private ClassService classService;

    @Test
    void test() throws IOException {
        FileReader fileReader = new FileReader("D:\\test.txt");
        int i;
        while((i=fileReader.read())!=-1){
            System.out.print((char) i);
        }
        fileReader.close();
    }

    @Test
    void test0() throws IOException {
        FileWriter fileWriter = new FileWriter("D:\\test.txt", true);
        fileWriter.write("\r\n");  //回车换行
        fileWriter.write("乾坤未定 \r\n");
        fileWriter.write("你我皆是黑马！ \r\n");
        fileWriter.close();
    }

    @Test
    void test1() throws IOException {
        BufferedInputStream in =new BufferedInputStream(new FileInputStream("C:\\Users\\25427\\Desktop\\桌面\\IMG_2531.PNG"));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("D:\\touxiang.png"));
        int i = 0;
        long beginTime = System.currentTimeMillis();
        while ((i = in.read())!=-1){
            out.write(i);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("花费了"+(endTime-beginTime)+"毫秒");
        in.close();
        out.close();
    }

    @Autowired
    private RoleService roleService;
    @Test
    void test2(){
        List<Map<String, Object>> maps = roleService.listMaps();
        List<Role> list = roleService.list();
        System.out.println(maps);
        System.out.println("=====================");
        System.out.println(list);
    }

    @Autowired
    private NewsService newsService;
    @Test
    void test3(){
        List<News> news = newsService.showNews3();
        news.forEach(System.out::println);
    }

    @Autowired
    private NocvService nocvService;
    @Test
    void test4(){
        List<NocvData> list = nocvService.queryNewAll();
        System.out.println(list);
        System.out.println("====================");
        list.forEach(System.out::println);
    }


}
