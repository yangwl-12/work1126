package com.yang.controller;

import com.yang.entity.Guru;
import com.yang.service.GuruService;
import com.yang.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("guru")
public class GuruController {
    @Autowired
    private GuruService guruService;

    @RequestMapping("findAll")
    @ResponseBody
    public List<Guru> findAll() {

        return guruService.findAll();
    }


    @RequestMapping("allmethod")
    @ResponseBody
    public Map allmethod(Guru guru, String pid, String oper, MultipartFile avatar, HttpServletRequest request) throws IOException {
        HashMap hashMap = new HashMap<>();
        if (oper.equals("del")) {
            guruService.removeOne(guru.getId());
            hashMap.put("picId", guru.getId());
            hashMap.put("status", 200);
            return hashMap;

        } else if (oper.equals("add")) {

            String sid = UUID.randomUUID().toString().replace("-", "");
            guru.setId(sid);
            guruService.addOne(guru);
            hashMap.put("picId", sid);
            hashMap.put("status", 200);
            return hashMap;

        } else {

            guruService.modifyOne(guru);
            hashMap.put("status", 200);
            hashMap.put("picId", guru.getId());

            return hashMap;
        }
    }


        @RequestMapping("upload")
        @ResponseBody
        public void upload(MultipartFile avatar,String pid,HttpServletRequest request, HttpSession session) throws IOException {
            String dir = "/upload/articleImg";
            String httpUrl = HttpUtil.getHttpUrl(avatar, request, session, dir);
            Guru guru = new Guru();
            guru.setId(pid);
            guru.setAvatar(httpUrl);
            guruService.modifyOne(guru);

    }
}

