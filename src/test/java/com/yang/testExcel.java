package com.yang;

import com.alibaba.excel.EasyExcel;
import com.yang.entity.DemoData;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class testExcel {
    @Test
    public void test1(){
        //创建表格对象
        HSSFWorkbook workbook= new HSSFWorkbook();
        //创建页对象
        HSSFSheet sheet = workbook.createSheet("学生信息");
        //创建行的对象
        HSSFRow row = sheet.createRow(0);
        //创建单元格的对象
        HSSFCell cell = row.createCell(0);
        //将表格对象写入磁盘
        try {
            workbook.write(new File("D://test123.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private List<DemoData> data(){
        DemoData demoData1 = new DemoData("Rxx", new Date(), 1.0, "Rxx");
        DemoData demoData2 = new DemoData("Rxx", new Date(), 1.0, "Rxx");
        DemoData demoData3 = new DemoData("Rxx", new Date(), 1.0, "Rxx");
        DemoData demoData4 = new DemoData("Rxx", new Date(), 1.0, "Rxx");
        List<DemoData> demoData = Arrays.asList(demoData1, demoData2, demoData3, demoData4);
        return demoData;
    }
    String fileName = "D://"+new Date().getTime()+"Demodata.xlsx";
    @Test
    public void test3(){
        //1.链式调用
        EasyExcel.write(fileName,DemoData.class).sheet().doWrite(data());
        //写法2、\
//        EasyExcel.write(fileName,DemoData.class).sheet().doWrite(data());
//        ExcelWriter build = EasyExcel.write(fileName,DemoData.class).build();
//        WriteSheet sheet = EasyExcel.writerSheet("test111").build();
//        build.write(data(),sheet);
//        build.finish();


    }

    @Test
    public void test4(){
         //new DemoDataListener() 每次都需要创建新的DemoDataListener 在Spring框架中使用时需要注意
         EasyExcel.read("D://1575271667719Demodata.xlsx",DemoData.class,new DemoDataListener()).sheet().doRead();

//        ExcelReader excelReader = EasyExcel.read("D://1575271667719Demodata.xlsx", DemoData.class, new DemoDataListener()).build();
//        ReadSheet readSheet = EasyExcel.readSheet(0).build();
//        excelReader.read(readSheet);
//        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
//        excelReader.finish();

    }

}
