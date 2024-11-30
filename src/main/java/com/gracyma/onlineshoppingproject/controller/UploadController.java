package com.gracyma.onlineshoppingproject.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

@Controller
public class UploadController {

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("fileImage") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "请选择一个文件上传");
            return "upload_pictures";
        }

        try {
            // 检查文件大小，假设限制为2MB
            if (file.getSize() > 2 * 1024 * 1024) {
                redirectAttributes.addFlashAttribute("message", "文件大小超过限制");
                return "upload_pictures";
            }

            // 保存文件到服务器
            file.transferTo(new File("/path/to/uploads/" + file.getOriginalFilename()));
            redirectAttributes.addFlashAttribute("message", "成功上传文件 '" + file.getOriginalFilename() + "'");
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "上传失败：" + e.getMessage());
        }

        return "upload_pictures"; // 上传后重定向到一个状态页面
    }
}
