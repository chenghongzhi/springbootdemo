package com.example.util;

import com.example.model.ReportFiles;
import com.example.service.ReportFilesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;


/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Component
public class FileUtil {

  private Logger log = LoggerFactory.getLogger(FileUtil.class);
  @Autowired
  private ReportFilesService reportFilesService;
//  @Autowired
//  private CourseDataService courseDataService;

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

  // 资料下载
  public boolean download(HttpServletResponse response, String code) throws UnsupportedEncodingException {
    ReportFiles reportFiles =reportFilesService.selectByCode(code);
    String fullPath = reportFiles.getFilePath();
    File file = new File(fullPath);
    if (file.exists()) {
      response.setContentType("application/force-download");
      // 设置强制下载不打开
      response.addHeader("Content-Disposition","attachment;fileName=" +new String(reportFiles.getFileName().getBytes("UTF-8"),"iso-8859-1"));
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
        return true;
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
    return false;
  }

  public void removeFile(String filePath){
    File file=new File(filePath);
    if (file.exists()) {
      file.delete();
    }
  }

}
