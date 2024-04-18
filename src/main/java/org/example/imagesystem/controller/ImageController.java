package org.example.imagesystem.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    private static final String BASE_DIR = "/home/images/avatar";


    @PostMapping("/avatar/upload")
    public String upload(@RequestParam MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            File uploadFile = new File(BASE_DIR + fileName);
            if (!uploadFile.getParentFile().exists()) {
                uploadFile.getParentFile().mkdirs();
            }
            file.transferTo(uploadFile);
            System.out.println("File uploaded successfully to: " + uploadFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 自己拼接一下下载的接口url，参数就是上传的文件名称
        return "http://localhost:8081/api/image/download?fileName=" + fileName;
    }


    @GetMapping("/avatar/download")
    public void download(@RequestParam String fileName, HttpServletResponse response) {
        //  新建文件流，从磁盘读取文件流
        try (FileInputStream fis = new FileInputStream(BASE_DIR + fileName);
             BufferedInputStream bis = new BufferedInputStream(fis);
             OutputStream os = response.getOutputStream()) {    //  OutputStream 是文件写出流，讲文件下载到浏览器客户端
            // 新建字节数组，长度是文件的大小，比如文件 6kb, bis.available() = 1024 * 6
            byte[] bytes = new byte[bis.available()];
            // 从文件流读取字节到字节数组中
            bis.read(bytes);
            // 重置 response
            response.reset();
            // 设置 response 的下载响应头
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));  // 注意，这里要设置文件名的编码，否则中文的文件名下载后不显示
            // 写出字节数组到输出流
            os.write(bytes);
            // 刷新输出流
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
