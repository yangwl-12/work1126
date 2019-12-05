package com.yang.timer;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.yang.entity.Picture;
import com.yang.entity.Picture1;
import com.yang.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class timerTask {
    @Autowired
    private PictureService pictureService;

    @Scheduled(cron = "0 0 0 ? * SUN")
    public void scheduled() throws IOException {
        System.out.println("123123");
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
        String fileName = "D://"+new Date().getTime()+"DemoData.xlsx";
        ExcelWriter build = EasyExcel.write(fileName, Picture1.class).build();
        // String : 页名称  Int : 第几页    可以同时指定
        WriteSheet sheet = EasyExcel.writerSheet("用户信息").build();
        build.write(picture1s,sheet);
        build.finish();


    }

}


