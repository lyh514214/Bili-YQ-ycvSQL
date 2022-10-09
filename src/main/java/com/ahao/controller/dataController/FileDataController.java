package com.ahao.controller.dataController;

import com.ahao.pojo.NocvData;
import com.ahao.service.NocvService;
import com.ahao.vo.DataView;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@Controller
public class FileDataController {

    @Autowired
    private NocvService nocvService;

    @RequestMapping("/getFileData")
    @ResponseBody
    public DataView getFileData(@RequestParam("file") MultipartFile file) {
        DataView dataView = new DataView();

        //文件为空
        if (file.isEmpty()) {
            dataView.setMsg("文件为空");
            return dataView;
        }

        try {
            //获取文件实例  如果是xls，使用HSSFWorkbook；如果是xlsx，使用XSSFWorkbook
            HSSFWorkbook sheets = new HSSFWorkbook(file.getInputStream());
            HSSFSheet sheet = sheets.getSheetAt(0);
            //实体类集合
            ArrayList<NocvData> list = new ArrayList<>();
            HSSFRow row = null;

            for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
                //获取每一行数据
                row = sheet.getRow(i);
                //每次插入队列都要重新new一个NocvData~
                NocvData nocvData = new NocvData();
                nocvData.setName(row.getCell(0).getStringCellValue());
                nocvData.setValue((int) row.getCell(1).getNumericCellValue());
                list.add(nocvData);
            }
            //保存到数据库
            nocvService.saveBatch(list);
            dataView.setCode(200);
            dataView.setMsg("插入数据成功");
            return dataView;
        } catch (IOException e) {
            e.printStackTrace();
        }
        dataView.setCode(500);
        dataView.setMsg("插入数据失败");
        return dataView;
    }
}
