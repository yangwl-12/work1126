package com.yang.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.yang.entity.Picture;
import com.yang.entity.Picture1;
import com.yang.service.PictureService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("pic")
public class PictureController {
    @Autowired
    private PictureService pictureService;

    //查询所欲
    @RequestMapping("findAll")
    @ResponseBody
    public List<Picture> findAll(){
        return  pictureService.findAll();
    }

    @ResponseBody
    @RequestMapping("findAll1")
    public Map<String,Object> findAll1(String searchField, String searchString, String searchOper, Integer page, Integer rows, Boolean _search){
        //不做搜索处理
        Map<String,Object> map=new HashMap<>();
        List<Picture> lists = null;
        Long totalCounts = null;
        if(_search){
            //根据搜索的条件查询集合
            lists = pictureService.selectSearch(searchField,searchString,searchOper,page,rows);
            //根据搜索的条件查询条数
            totalCounts = pictureService.findTotalSearch(searchField,searchString,searchOper);
            }else {
            //进行分页查询
            lists = pictureService.findAllByPage(page,rows);
            //获取数据的总条数
            Integer size = pictureService.findAll().size();
            totalCounts = size.longValue();
        }
        Long totalPage = totalCounts%rows==0?totalCounts/rows:totalCounts/rows+1;
        map.put("rows",lists);
        map.put("total",totalPage);
        map.put("page",page);
        map.put("records",totalCounts);
        return map;
    }

    @RequestMapping("allmethod")
    @ResponseBody
    public Map allmethod(Picture picture,String pid, String[] id,String oper, MultipartFile path, HttpServletRequest request) throws IOException {
        HashMap hashMap= new HashMap<>();
        if(oper.equals("del")){
            //pictureService.removeOne(picture.getId());
            pictureService.deleteAll(Arrays.asList(id));
            hashMap.put("picId",picture.getId());
            hashMap.put("status",200);
            return hashMap;

        }else if(oper.equals("add")){

            String sid= UUID.randomUUID().toString().replace("-","");
            picture.setId(sid);
            picture.setCreate_time(new Date());
            pictureService.addOne(picture);
            hashMap.put("picId",sid);
            hashMap.put("status",200);
            return hashMap;

        }else{
            Picture one = pictureService.findOne(picture.getId());
            if(picture.getPath().equals("")||picture.getPath().equals(null)){
                picture.setPath(one.getPath());
            }
            pictureService.modifyOne(picture);
            hashMap.put("status",200);
            hashMap.put("picId",picture.getId());

            return hashMap;
        }
    }

    @RequestMapping("upload")
    @ResponseBody
    public void upload(MultipartFile path,String pid,HttpServletRequest request) throws IOException {
        //ystem.out.println("dadas");
        String realPath = request.getSession().getServletContext().getRealPath("/upload");
        //修改文件名
        String newFileNamePrefix=new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date())+
                UUID.randomUUID().toString().replace("-","");
        String newFileNameSuffix="."+ FilenameUtils.getExtension(path.getOriginalFilename());
        //新的文件名
        String newFileName=newFileNamePrefix+newFileNameSuffix;
        //创建日期目录
        String dataDayString=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String truePath="img/"+dataDayString;
        File dataDay = new File(realPath, dataDayString);
        if(!dataDay.exists()){
            dataDay.mkdirs();
        }
        //System.out.println(dataDayString);
        path.transferTo(new File(dataDay,newFileName));
//      网络路径：http://Ip:端口/项目名/文件存放位置
        String http = request.getScheme();
        String localhost = InetAddress.getLocalHost().toString();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        String uri = http+"://"+localhost.split("/")[1]+":"+serverPort+contextPath+
                "/upload/"+dataDayString+"/"+newFileName;
        //System.out.println(uri);
        Picture picture = new Picture();
        picture.setPath(uri);
        picture.setId(pid);
        pictureService.modifyOne(picture);

    }


    @RequestMapping("upload1")
    @ResponseBody
    public void upload1(MultipartFile path,String pid,HttpServletRequest request) throws IOException {
        Boolean flag=true;
        if(path.getOriginalFilename().equals("")||path.getOriginalFilename().equals(null)){
            flag=false;
        }
        if(flag){
        String realPath = request.getSession().getServletContext().getRealPath("/upload");
        //修改文件名
        String newFileNamePrefix=new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date())+
                UUID.randomUUID().toString().replace("-","");
        String newFileNameSuffix="."+ FilenameUtils.getExtension(path.getOriginalFilename());
        //新的文件名
        String newFileName=newFileNamePrefix+newFileNameSuffix;
        //创建日期目录
        String dataDayString=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String truePath="img/"+dataDayString;
        File dataDay = new File(realPath, dataDayString);
        if(!dataDay.exists()){
            dataDay.mkdirs();
        }
        //System.out.println(dataDayString);
        path.transferTo(new File(dataDay,newFileName));
//      网络路径：http://Ip:端口/项目名/文件存放位置
        String http = request.getScheme();
        String localhost = InetAddress.getLocalHost().toString();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        String uri = http+"://"+localhost.split("/")[1]+":"+serverPort+contextPath+
                "/upload/"+dataDayString+"/"+newFileName;
        //System.out.println(uri);
        Picture picture = new Picture();
        picture.setPath(uri);
        picture.setId(pid);
        pictureService.modifyOne(picture);
        }
    }

    //导出信息
    @RequestMapping("export")
    @ResponseBody
    public void export(HttpServletResponse response) throws IOException {
//        List<Picture> all = pictureService.findAll();
//        //创建Excel工作对象
//        HSSFWorkbook sheets = new HSSFWorkbook();
//        //创建工作表
//        HSSFSheet sheet = sheets.createSheet("轮播图表");
//        //创建标题行
//        HSSFRow row = sheet.createRow(0);
//        String[] title = {"ID","name","path","introduction","href","status","create_time"};
//        //处理单元格对象
//        HSSFCell cell = null;
//        for (int i = 0; i < title.length; i++) {
//            cell = row.createCell(i);//单元格下标
//            cell.setCellValue(title[i]);
//
//        }
//        for (int i = 0; i < all.size(); i++) {
//            //遍历一次创建一行
//            HSSFRow row1 = sheet.createRow(i+1);
//            //每行对应放的数据
//            row1.createCell(0).setCellValue(all.get(i).getId());
//            row1.createCell(1).setCellValue(all.get(i).getName());
//            row1.createCell(2).setCellValue(all.get(i).getPath());
//            row1.createCell(3).setCellValue(all.get(i).getIntroduction());
//            row1.createCell(4).setCellValue(all.get(i).getHref());
//            row1.createCell(5).setCellValue(all.get(i).getStatus());
//            row1.createCell(6).setCellValue(all.get(i).getCreate_time());
//            //创建样式对象
//            CellStyle cellStyle2 = sheets.createCellStyle();
//            //创建日期对象
//            DataFormat dataFormat = sheets.createDataFormat();
//            //设置日期格式
//            cellStyle2.setDataFormat(dataFormat.getFormat("yyy年MM月dd日"));
//            Cell cell1 = row1.createCell(6);
//            cell1.setCellStyle(cellStyle2);
//            cell1.setCellValue(all.get(i).getCreate_time());

//创建输出流
//            String fileName = "轮播图数据.xls";
//            response.setContentType("applicationnd.ms-excel;charset=utf-8");
//            response.setHeader("Content-disposition", "attachment;filename= "+ fileName);
//            ServletOutputStream out = response.getOutputStream();
//            sheets.write(out);
//            out.close();
//            sheets.close();



        List<Picture> all = pictureService.findAll();
        ArrayList<Picture1> picture1s = new ArrayList<>();
        for (Picture picture : all) {
            Picture1 picture1 = new Picture1();
           picture1.setId(picture.getId());
           picture1.setUrl(new URL(picture.getPath()));
           picture1.setName(picture1.getName());
           picture1.setCreate_time(picture.getCreate_time());
           picture1.setHref(picture.getHref());
           picture1.setIntroduction(picture.getIntroduction());
           picture1.setStatus(picture.getStatus());

           picture1s.add(picture1);
        }

        OutputStream outputStream = response.getOutputStream();
        ExcelWriter build = EasyExcel.write(outputStream, Picture1.class).build();
        WriteSheet sheet = EasyExcel.writerSheet("轮播图信息").build();
        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode("轮播图信息.xls","UTF-8"));
        response.setContentType("application/msexcel;charset=UTF-8");//设置类型
        build.write(picture1s, sheet);
        build.finish();

    }

}

