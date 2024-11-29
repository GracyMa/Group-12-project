package com.gracyma.onlineshoppingproject.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * upload download
 */
@Slf4j
@Controller
@RequestMapping("")
public class ImageController {

    private String basePath;
    @PostMapping("/upload")
    public String upload(MultipartFile file){
        log.info(file.toString());
        String oriFilename = file.getOriginalFilename();
        String suffix = oriFilename.substring(oriFilename.lastIndexOf('.'));
        String fileName = oriFilename.substring(0, oriFilename.lastIndexOf('.'))+"_"+UUID.randomUUID().toString() + suffix;

        File dir = new File(basePath);
        if(!dir.exists()){
            dir.mkdirs();
        }

        try {
            //file.transferTo(new File("/tmp/reggie_images/{}",file.getName()));
            file.transferTo(new File(basePath+fileName));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "uploaded";
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(basePath+name));

            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            int len =0;
            byte[] bytes =new byte[1024];

            while((len = fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes, 0,len);
                outputStream.flush();
            }
            outputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
