package com.yang.controller;

import com.yang.entity.Article;
import com.yang.service.ArticleService;
import com.yang.util.HttpUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;


    //查询所有
    @RequestMapping("findAll")
    @ResponseBody
    public Map<String,Object> findAll(Integer page,Integer rows){
        HashMap<String, Object> hashMap = new HashMap<>();
        List<Article> all = articleService.findAll();
        Integer size = all.size();
        Integer total=size%rows==0?size/rows:size/rows+1;
        List<Article> allf = articleService.findAllf(page, rows);
        //System.out.println(allf);
        hashMap.put("rows",allf);
        hashMap.put("page",page);
        hashMap.put("total",total);
        hashMap.put("records",size);
        return hashMap;

    }

    @RequestMapping("update")
    @ResponseBody
    public void upadteArt(MultipartFile articleImg,HttpServletRequest request,HttpSession session,Article article){
            if(articleImg.getOriginalFilename().equals("")||articleImg.getOriginalFilename().equals(null)){
                articleService.modifyOne(article);
            }else{
                String dir = "/upload/articleImg";
                String httpUrl = HttpUtil.getHttpUrl(articleImg, request, session, dir);
                article.setCover(httpUrl);
                article.setCreate_time(new Date());
                articleService.modifyOne(article);
            }

    }
    @RequestMapping("allmethod")
    @ResponseBody
    public Map allmethod(Article article, String pid, String[] id, String oper, MultipartFile path, HttpServletRequest request) throws IOException {
        HashMap hashMap= new HashMap<>();
        if(oper.equals("del")){
            //pictureService.removeOne(picture.getId());
            articleService.removeOne(article.getId());
            hashMap.put("picId",article.getId());
            hashMap.put("status",200);
            return hashMap;

        }else if(oper.equals("add")){

            String sid= UUID.randomUUID().toString().replace("-","");
            article.setId(sid);
            article.setCreate_time(new Date());
            articleService.addOne(article);
            hashMap.put("picId",sid);
            hashMap.put("status",200);
            return hashMap;

        }else{

            articleService.modifyOne(article);
            hashMap.put("status",200);
            hashMap.put("picId",article.getId());

            return hashMap;
        }
    }


    @RequestMapping("uploadImg")
    @ResponseBody
    public Map uploadImg(MultipartFile imgFile, HttpSession session, HttpServletRequest request){
        HashMap hashMap = new HashMap();
        String dataDayString=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dir = "/upload/articleImg";
        try {
            String httpUrl = HttpUtil.getHttpUrl(imgFile, request, session, dir);
            hashMap.put("error",0);
            hashMap.put("url",httpUrl);
        } catch (Exception e) {
            hashMap.put("error",1);
            hashMap.put("message","上传错误");
            e.printStackTrace();
        }
        return hashMap;
    }

    @RequestMapping("showAllImgs")
    @ResponseBody
    public Map showAllImgs(HttpSession session,HttpServletRequest request){
        // 1. 获取文件夹绝对路径
        String dataDayString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String realPath = session.getServletContext().getRealPath("/upload/articleImg/"+dataDayString);
        // 2. 准备返回的Json数据
        HashMap hashMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        // 3. 获取目标文件夹
        File file = new File(realPath);
        File[] files = file.listFiles();
        // 4. 遍历文件夹中的文件
        for (File file1 : files) {
            // 5. 文件属性封装
            HashMap fileMap = new HashMap();
            fileMap.put("is_dir",false);
            fileMap.put("has_file",false);
            fileMap.put("filesize",file1.length());
            fileMap.put("is_photo",true);
            // 获取文件后缀 | 文件类型
            String extension = FilenameUtils.getExtension(file1.getName());
            fileMap.put("filetype",extension);
            fileMap.put("filename",file1.getName());
            // 获取文件上传时间 1. 截取时间戳 2. 创建格式转化对象 3. 格式类型转换
            String s = file1.getName().split("_")[0];
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = simpleDateFormat.format(new Date(Long.valueOf(s)));
            fileMap.put("datetime",format);
            arrayList.add(fileMap);
        }
        hashMap.put("file_list",arrayList);
        hashMap.put("total_count",arrayList.size());
        // 返回路径为 项目名 + 文件夹路径
        hashMap.put("current_url",request.getContextPath()+"/upload/articleImg/"+dataDayString+"/");
        return hashMap;
    }


    @RequestMapping("upload")
    @ResponseBody
    public void upload(MultipartFile articleImg,HttpServletRequest request,HttpSession session,Article article) throws IOException {
        String dir = "/upload/articleImg";
        String httpUrl = HttpUtil.getHttpUrl(articleImg, request, session, dir);

//        String realPath = request.getSession().getServletContext().getRealPath("/upload");
//        //修改文件名
//        String newFileNamePrefix=new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date())+
//                UUID.randomUUID().toString().replace("-","");
//        String newFileNameSuffix="."+ FilenameUtils.getExtension(path.getOriginalFilename());
//        //新的文件名
//        String newFileName=newFileNamePrefix+newFileNameSuffix;
//        //创建日期目录
//        String dataDayString=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//        String truePath="img/"+dataDayString;
//        File dataDay = new File(realPath, dataDayString);
//        if(!dataDay.exists()){
//            dataDay.mkdirs();
//        }
//        //System.out.println(dataDayString);
//        path.transferTo(new File(dataDay,newFileName));
////      网络路径：http://Ip:端口/项目名/文件存放位置
//        String http = request.getScheme();
//        String localhost = InetAddress.getLocalHost().toString();
//        int serverPort = request.getServerPort();
//        String contextPath = request.getContextPath();
//        String uri = http+"://"+localhost.split("/")[1]+":"+serverPort+contextPath+
//                "/upload/"+dataDayString+"/"+newFileName;
//        //System.out.println(uri);
            article.setId(UUID.randomUUID().toString());
            article.setCover(httpUrl);
            article.setCreate_time(new Date());
            articleService.addOne(article);



    }



}
