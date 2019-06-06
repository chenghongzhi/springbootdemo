package com.example.util;

import com.example.model.Report;
import com.example.model.StudentReport;
import com.example.service.ReportService;
import com.example.service.StudentReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;


/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Component
public class FileUtil {

  private Logger log = LoggerFactory.getLogger(FileUtil.class);

  @Autowired
  private ReportService reportService;
  @Autowired
  private StudentReportService studentReportService;

  // 上传文件
  public String uploadFile(MultipartFile file, String fileName,String filePath){
    try {
      //文件名的前缀
      String fileNamePrefix = fileName.substring(0 , fileName.lastIndexOf("."));
      //获取上传文件名
      String fileNameSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
      fileName = fileNamePrefix + "-" + System.currentTimeMillis() + "." + fileNameSuffix;
      File targetFile=new File(filePath+fileName);
      if (!targetFile.getParentFile().exists()) {
        targetFile.getParentFile().mkdirs();
      }
      //6.将上传文件写到服务器上指定的文件
      file.transferTo(targetFile);
      log.info("文件上传成功");
      return fileName;
    }catch (Exception e){
      e.printStackTrace();
      log.error(e.getMessage());
    }
    return "文件上传失败";
  }

  // 下载文件
  public String downloadFile(HttpServletResponse response, String code) throws UnsupportedEncodingException {
    StudentReport studentReport=studentReportService.selectByCode(code);
    String fullPath = studentReport.getFilepath();
    String fileName=fullPath.substring(fullPath.lastIndexOf("/")+1);
      File file = new File(fullPath);
      if (file.exists()) {
        response.setContentType("application/force-download");
        // 设置强制下载不打开
        response.addHeader("Content-Disposition","attachment;fileName=" +new String(fileName.getBytes("UTF-8"),"iso-8859-1"));
        response.setCharacterEncoding("utf-8");
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
          fis = new FileInputStream(file);
          bis = new BufferedInputStream(fis);
          OutputStream os = response.getOutputStream();
          int i = bis.read(buffer);
          while (i != -1) {
            os.write(buffer, 0, i);
            i = bis.read(buffer);
          }
          return "下载成功";
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          if (bis != null) {
            try {
              bis.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
          if (fis != null) {
            try {
              fis.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        }
      }
//    }
    return "下载失败";
  }

  public void removeFile(Integer id){
    List<StudentReport> studentReportList=studentReportService.selectByReportId(id);
    studentReportList.forEach(studentReport -> {
      File file=new File(studentReport.getFilepath());
      if (file.exists()) {
        file.delete();
      }
    });
  }
}
