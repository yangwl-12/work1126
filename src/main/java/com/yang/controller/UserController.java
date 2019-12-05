package com.yang.controller;

import com.yang.entity.*;
import com.yang.service.*;
import com.yang.util.Code;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("findSex")
    public Map<String,Object> findSex(){
        Map<String, Object> map = new HashMap<>();
        map.put("man_oneDay",userService.findSex("sex = '男' and DATE_SUB(NOW(),INTERVAL 1 day) < createTime"));
        map.put("woman_oneDay",userService.findSex("sex = '女' and DATE_SUB(NOW(),INTERVAL 1 day) < createTime"));
        map.put("man_oneWeek",userService.findSex("sex = '男' and DATE_SUB(NOW(),INTERVAL 7 day) < createTime"));
        map.put("woman_oneWeek",userService.findSex("sex = '女' and DATE_SUB(NOW(),INTERVAL 7 day) < createTime"));
        map.put("man_oneMonth",userService.findSex("sex = '男' and DATE_SUB(NOW(),INTERVAL 30 day) < createTime"));
        map.put("woman_oneMonth",userService.findSex("sex = '女' and DATE_SUB(NOW(),INTERVAL 30 day) < createTime"));
        map.put("man_oneYear",userService.findSex("sex = '男' and DATE_SUB(NOW(),INTERVAL 365 day) < createTime"));
        map.put("woman_oneYear",userService.findSex("sex = '女' and DATE_SUB(NOW(),INTERVAL 365 day) < createTime"));
        return map;
    }

    @RequestMapping("showUsersLocation")
    public List<MapVO> showUsersLocation(){
        List<MapVO> mapVOS = userService.selectByLoction();
        return mapVOS;
    }



    //查询所有
    @RequestMapping("findAll")
    @ResponseBody
    public Map<String, Object> findAll(Integer page, Integer rows) {

        HashMap<String, Object> hashMap = new HashMap<>();
        List<User> all = userService.findAll();
        Integer size = all.size();
        Integer total = size % rows == 0 ? size / rows : size / rows + 1;
        List<User> allf = userService.findAllf(page, rows);
        //System.out.println(allf);
        hashMap.put("rows", allf);
        hashMap.put("page", page);
        hashMap.put("total", total);
        hashMap.put("records", size);
        return hashMap;
    }


    @RequestMapping("allmethod")
    @ResponseBody
    public Map allmethod(User user, String pid, String[] id, String oper, MultipartFile avatar, HttpServletRequest request) throws IOException {
        HashMap hashMap= new HashMap<>();
        if(oper.equals("del")){
           userService.removeOne(user.getId());
            hashMap.put("picId",user.getId());
            hashMap.put("status",200);
            return hashMap;

        }else if(oper.equals("add")){

            String sid= UUID.randomUUID().toString().replace("-","");
            user.setId(sid);
            user.setCreateTime(new Date());
            userService.addOne(user);
            hashMap.put("picId",sid);
            hashMap.put("status",200);
            return hashMap;

        }else{
            User one = userService.findOne(user.getId());
            if(user.getAvatar().equals("")||user.getAvatar().equals(null)){
                user.setAvatar(one.getAvatar());
            }
            userService.updateOne(user);

            hashMap.put("status",200);
            hashMap.put("picId",user.getId());

            return hashMap;
        }
    }

    @RequestMapping("upload")
    @ResponseBody
    public void upload(MultipartFile avatar,String pid,HttpServletRequest request) throws IOException {
        //ystem.out.println("dadas");
        String realPath = request.getSession().getServletContext().getRealPath("/upload");
        //修改文件名
        String newFileNamePrefix=new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date())+
                UUID.randomUUID().toString().replace("-","");
        String newFileNameSuffix="."+ FilenameUtils.getExtension(avatar.getOriginalFilename());
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
        avatar.transferTo(new File(dataDay,newFileName));
//      网络路径：http://Ip:端口/项目名/文件存放位置
        String http = request.getScheme();
        String localhost = InetAddress.getLocalHost().toString();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        String uri = http+"://"+localhost.split("/")[1]+":"+serverPort+contextPath+
                "/upload/"+dataDayString+"/"+newFileName;
        //System.out.println(uri);
        User user = new User();
        user.setAvatar(uri);
        user.setId(pid);
       userService.updateOne(user);

    }

    @RequestMapping("upload1")
    @ResponseBody
    public void upload1(MultipartFile avatar,String pid,HttpServletRequest request) throws IOException {
        Boolean flag=true;
        if(avatar.getOriginalFilename().equals("")||avatar.getOriginalFilename().equals(null)){
            flag=false;
        }
        if(flag){
            String realPath = request.getSession().getServletContext().getRealPath("/upload");
            //修改文件名
            String newFileNamePrefix=new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date())+
                    UUID.randomUUID().toString().replace("-","");
            String newFileNameSuffix="."+ FilenameUtils.getExtension(avatar.getOriginalFilename());
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
            avatar.transferTo(new File(dataDay,newFileName));
//      网络路径：http://Ip:端口/项目名/文件存放位置
            String http = request.getScheme();
            String localhost = InetAddress.getLocalHost().toString();
            int serverPort = request.getServerPort();
            String contextPath = request.getContextPath();
            String uri = http+"://"+localhost.split("/")[1]+":"+serverPort+contextPath+
                    "/upload/"+dataDayString+"/"+newFileName;
            //System.out.println(uri);
            User user = new User();
            user.setAvatar(uri);
            user.setId(pid);
            userService.updateOne(user);
        }
    }

    //登录
    @RequestMapping("login")
    public Map login(String phone,String password){
        User oneByPhone = userService.findOneByPhone(phone);
        HashMap<String, Object> hashMap = new HashMap<>();
        try{
            if(oneByPhone!=null){
              if(!oneByPhone.getPassWord().equals(password)){
                  hashMap.put("status","-200");
                  hashMap.put("message","密码错误！");
                  return hashMap;
              }else {
                  hashMap.put("status","200");
                  hashMap.put("message","");
                  return hashMap;

              }
            }hashMap.put("status","200");
            hashMap.put("user",oneByPhone);
            return hashMap;
        }
        catch(Exception e){
                e.printStackTrace();
                hashMap.put("status","-200");
                hashMap.put("message",e.getStackTrace()) ;
                 return hashMap;
        }

//        if(oneByPhone!=null) {
//            if(!oneByPhone.getPassWord().equals(password)){
//                return "密码错误！";
//            }return "登录成功";
//        }return "账户不存在！";
    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    //验证码
    @RequestMapping("code")
    public Map code1(String phone){
        HashMap<Object, Object> hashMap = new HashMap<>();
        try {
            Integer mobile_code = (int) ((Math.random() * 9 + 1) * 100000);
            stringRedisTemplate.setStringSerializer(new StringRedisSerializer());
            stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
            System.out.println(phone);
            stringRedisTemplate.opsForValue().set(phone, String.valueOf(mobile_code),60, TimeUnit.SECONDS);
            Code.code(phone, mobile_code);
            hashMap.put("status", "200");
            hashMap.put("message", "成功");
            return hashMap;
        }catch (Exception e){
            hashMap.put("status","-200");
            hashMap.put("message","失败！");
            return hashMap;
        }

    }

    //判断验证码
    @RequestMapping("changeCode")
    public Map chaneCode(String code){
        HashMap<Object, Object> hashMap = new HashMap<>();
            if (code=="123"){
                hashMap.put("status","200");

                return hashMap;
            }else {
                hashMap.put("status", "-200");
                hashMap.put("message", "错误！");
                return hashMap;
            }
    }

    //补充个人信息
    @RequestMapping("addOne")
    public  Map addOne(User user){
        HashMap<Object, Object> hashMap = new HashMap<>();
        try {
            userService.addOne(user);
            hashMap.put("status","200");
            hashMap.put("user",user);
            return hashMap;
        }catch (Exception e)
        {
            hashMap.put("status","-200");
            hashMap.put("message","失败！");
            return hashMap;

        }


    }
    @Autowired
    private PictureService pictureService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ClassworkService classworkService;
    @Autowired
    private CountService countService;
    @Autowired
    private GuruService guruService;
    //查询全部
    @RequestMapping("findAllFront")
    public Map findAllFront(){

        List<Picture> all = pictureService.findAll();
        List<Article> all1 = articleService.findAll();
        List<Album> all2 = albumService.findAll();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("pic",all);
        hashMap.put("art",all1);
        hashMap.put("album",all2);
        return hashMap;

    }
    //根据ID查询文章
    @RequestMapping("findArticleById")
    public Map findArticleById(String id){
        HashMap<Object, Object> hashMap = new HashMap<>();
        try {
            Article one = articleService.findOne(id);
            hashMap.put("status","200");
            hashMap.put("art",one);
            return hashMap;
        }catch (Exception e){
            hashMap.put("status","-200");
            hashMap.put("message","失败");
            return hashMap;
        }
    }
    //根据Id查询专辑
    @RequestMapping("findAlbumById")
    public Map findAlbumById(String id){
        HashMap<Object, Object> hashMap = new HashMap<>();
        try {
            Album one = albumService.findOne(id);
            hashMap.put("status","200");
            hashMap.put("art",one);
            return hashMap;
        }catch (Exception e){
            hashMap.put("status","-200");
            hashMap.put("message","失败");
            return hashMap;
        }
    }
    //展示功课
    @RequestMapping("findAllClasswork")
    public Map findAllClasswork(){
        HashMap<Object, Object> hashMap = new HashMap<>();
        try {
            List<Classwork> all = classworkService.findAll();
            hashMap.put("status","200");
            hashMap.put("class",all);
            return hashMap;
        }catch (Exception e){
            hashMap.put("status","-200");
            hashMap.put("message","错误！");
            return hashMap;
        }

    }
    //添加功课
    @RequestMapping("addOneClasswork")
    public Map addOne(Classwork classwork){
        HashMap<Object, Object> hashMap = new HashMap<>();
        try {
            classworkService.addOne(classwork);
            hashMap.put("status","200");
            hashMap.put("class",classwork);
            return hashMap;
        }catch (Exception e){
            hashMap.put("status","-200");
            hashMap.put("message","错误！");
            return hashMap;
        }
    }
    //删除功课
    @RequestMapping("deleteOneClasswork")
    public Map<Object, Object> deleteOne(String id){
        HashMap<Object, Object> hashMap = new HashMap<>();
        try {
            classworkService.deleteOne(id);
            hashMap.put("status","200");
            hashMap.put("class",classworkService.findOne(id));
            return hashMap;
        }catch (Exception e){
            hashMap.put("status","-200");
            hashMap.put("message","错误！");
            return hashMap;
        }
    }
    //展示计数器
    @RequestMapping("findAllCount")
    public Map<Object, Object> findAll(){
        HashMap<Object, Object> hashMap = new HashMap<>();
        try {
            List<Count> all = countService.findAll();
            hashMap.put("status","200");
            hashMap.put("class",all);
            return hashMap;
        }catch (Exception e){
            hashMap.put("status","-200");
            hashMap.put("message","错误！");
            return hashMap;
        }
    }
    //添加计数器
    @RequestMapping("addOneCount")
    public Map<Object, Object> addOneCount(Count count){
        HashMap<Object, Object> hashMap = new HashMap<>();
        try {
            countService.addOne(count);
            hashMap.put("status","200");
            hashMap.put("class",count);
            return hashMap;
        }catch (Exception e){
            hashMap.put("status","-200");
            hashMap.put("message","错误！");
            return hashMap;
        }
    }
    //删除计数器
    @RequestMapping("deleteOneCount")
    public Map deleteOneCount(String id){

        Count one = countService.findOne(id);
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("status",200);
        hashMap.put("delete",one);
        countService.deleteOne(id);
       return hashMap;
    }
    //修改个人信息
    @RequestMapping("updateOneUser")
    public Map updateOne(User user){
        userService.updateOne(user);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status",200);
        hashMap.put("update",user);
        return hashMap;
    }

    //展示上师列表
    @RequestMapping("findAllguru")
    public Map findAllguru(){
        List<Guru> all = guruService.findAll();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status",200);
        hashMap.put("Guru",all);
        return hashMap;
    }










}