package com.yang.controller;

import com.yang.entity.Album;
import com.yang.service.AlbumService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;


    @RequestMapping("findAllf")
    @ResponseBody
    public Map<String,Object> findAllf(Integer page,Integer rows){
        HashMap<String ,Object> hashMap =  new HashMap<>();
        List<Album> allf = albumService.findAllf(page, rows);
        List<Album> all = albumService.findAll();
        Integer size = all.size();
        Integer total=size%rows==0?size/rows:size/rows+1;

        hashMap.put("rows",allf);
        hashMap.put("page",page);
        hashMap.put("total",total);
        hashMap.put("records",size);
        return hashMap;
    }

    @RequestMapping("allmethod")
    @ResponseBody
    public Map allmethod(Album album, String pid, String[] id, String oper, MultipartFile path, HttpServletRequest request) throws IOException {
        HashMap hashMap= new HashMap<>();
        if(oper.equals("del")){
            albumService.removeOne(album.getId());
            hashMap.put("albumId",album.getId());
            hashMap.put("status",200);
            return hashMap;

        }else if(oper.equals("add")){

            String sid= UUID.randomUUID().toString().replace("-","");
            album.setId(sid);
            album.setReleasetime(new Date());
           albumService.addOne(album);
            hashMap.put("albumId",sid);
            hashMap.put("status",200);
            return hashMap;

        }else{

            albumService.modifyOne(album);
            hashMap.put("status",200);
            hashMap.put("albumId",album.getId());

            return hashMap;
        }
    }


    @RequestMapping("upload")
    @ResponseBody
    public void upload(MultipartFile cover,String pid,HttpServletRequest request) throws IOException {
        System.out.println("dadas");
        String realPath = request.getSession().getServletContext().getRealPath("/upload");
        //修改文件名
        String newFileNamePrefix=new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date())+
                UUID.randomUUID().toString().replace("-","");
        String newFileNameSuffix="."+ FilenameUtils.getExtension(cover.getOriginalFilename());
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
        cover.transferTo(new File(dataDay,newFileName));
//      网络路径：http://Ip:端口/项目名/文件存放位置
        String http = request.getScheme();
        String localhost = InetAddress.getLocalHost().toString();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        String uri = http+"://"+localhost.split("/")[1]+":"+serverPort+contextPath+
                "/upload/"+dataDayString+"/"+newFileName;
        //System.out.println(uri);
        Album album = new Album();
        album.setCover(uri);
        album.setId(pid);
        albumService.modifyOne(album);


    }





    //
}
