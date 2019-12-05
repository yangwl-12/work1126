package com.yang.controller;

import com.yang.entity.Album;
import com.yang.entity.Chapter;
import com.yang.service.AlbumService;
import com.yang.service.ChapterService;
import org.apache.commons.io.FilenameUtils;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;
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
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private AlbumService albumService;

    //根据albumId查询
    @RequestMapping("findAll")
    @ResponseBody
    public Map<String,Object> findAll(Integer page,Integer rows,String fid){
        HashMap<String, Object> hashMap = new HashMap<>();
        List<Chapter> all = chapterService.findAll();
        Integer size = all.size();
        Integer total = size%rows==0?size/rows:size/rows+1;
        List<Chapter> allf = chapterService.findAllf(page, rows, fid);
        hashMap.put("rows",allf);
        hashMap.put("page",page);
        hashMap.put("total",total);
        hashMap.put("records",size);

        return hashMap;
    }

    @RequestMapping("allmethod")
    @ResponseBody
    public Map allmethod(Chapter chapter, String fid, String oper, MultipartFile path, HttpServletRequest request) throws IOException {
        HashMap hashMap= new HashMap<>();
        if(oper.equals("del")){
            chapterService.removeOne(chapter.getId());
            hashMap.put("chapterId",chapter.getId());
            hashMap.put("status",200);
            return hashMap;

        }else if(oper.equals("add")){
            Album one = albumService.findOne(fid);
            Integer episode = one.getEpisode();
            episode+=1;
            one.setEpisode(episode);
            albumService.modifyOne(one);
            String sid= UUID.randomUUID().toString().replace("-","");
            chapter.setId(sid);
            chapter.setAlbumId(fid);
           chapter.setFabushjian(new Date());
            chapterService.addOne(chapter);
            hashMap.put("chapterId",sid);
            hashMap.put("status",200);

            return hashMap;

        }else{
            chapterService.updateOne(chapter);
            hashMap.put("status",200);
            hashMap.put("albumId",chapter.getId());
            return hashMap;
        }
    }


    @RequestMapping("upload")
    @ResponseBody
    public void upload(MultipartFile path,String pid,HttpServletRequest request) throws IOException, TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException {
       // System.out.println(path.getOriginalFilename());
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
        //获取文件名
        String[] split = uri.split("/");
        String sname = split[split.length-1];
        File file = new File(dataDay, sname);
        System.out.println(file);
        long length = file.length();
        String size = length/1024/1024 + "MB";
        MP3File read = (MP3File) AudioFileIO.read(file);
        MP3AudioHeader mp3AudioHeader = read.getMP3AudioHeader();
        //获取当前音频有多少秒
        int trackLength = mp3AudioHeader.getTrackLength();
        String min = trackLength/60+"分";
        String sed = trackLength%30+"秒";

        Chapter chapter = new Chapter();
        chapter.setPath(uri);
        chapter.setId(pid);
        chapter.setChapterSize(size);
        chapter.setChapter_time(min+sed);
        chapter.setName(path.getOriginalFilename());
        chapterService.updateOne(chapter);

    }

}
