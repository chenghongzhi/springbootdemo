package com.example.controller.api;

import com.example.model.ReportFiles;
import com.example.service.ReportFilesService;
import com.example.util.FileUtil;
import com.example.util.ResultJSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/document")
public class DocumentApiController extends BaseController{

    @Autowired
    private ReportFilesService reportFilesService;
    @Value("${filepath}")
    private String path;
    private Logger log = LoggerFactory.getLogger(DocumentApiController.class);

    @PostMapping(value = "/upload")
    public ResultJSON add(HttpServletRequest request, @RequestParam(value = "files", required = false) MultipartFile file ) throws IllegalStateException, IOException {
        ResultJSON json =new  ResultJSON();
        if (file==null) {
            return json;
        } else {
            //获取上传文件真实名称
            String realName = file.getOriginalFilename();
            //获取上传文件名
            String fileName = file.getOriginalFilename();
            //2.判断文件后缀名是否符合要求
            String fileNameSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            switch (fileNameSuffix) {
                case "docx":
                    break;
                case "doc":
                    break;
                case "zip":
                    break;
                case "rar":
                    break;
                default:
                    json.failure("文件类型不正确,只能上传文档和压缩文件，请重试");
                    log.error("文件类型不正确，请重试");
                    return json;
            }
            fileName = UUID.randomUUID().toString() + "."+fileNameSuffix;
            File targetFile = new File(path + fileName);
            if (!targetFile.getParentFile().exists()) {
                //不存在创建文件夹
                targetFile.getParentFile().mkdirs();
            }
            try {
                //6.将上传文件写到服务器上指定的文件
                file.transferTo(targetFile);
                ReportFiles reportFiles = new ReportFiles();
                reportFiles.setInTime(new Date());
                reportFiles.setFileName(realName);
//                reportFiles.setReportId(reportId);
                reportFiles.setFileCode(UUID.randomUUID().toString());
                reportFiles.setFilePath(path + fileName);
                reportFiles.setFileUrl("/api/v1/document/download/" + reportFiles.getFileCode());
                reportFiles.setSubmitterName(getUser().getUsername());
                reportFilesService.insert(reportFiles);
                json.success(reportFiles.getId());
            } catch (IOException e) {
                e.printStackTrace();
                json.failure("上传文件失败");
            }
        }
        return json;
    }

    @GetMapping("/download/{code}")
    public ResultJSON download(HttpServletResponse response, @PathVariable String code) throws UnsupportedEncodingException {
        ResultJSON json =new  ResultJSON();
        ReportFiles reportFiles =reportFilesService.selectByCode(code);
        if (reportFiles != null) {
            File file = new File(reportFiles.getFilePath());
            String fileName = reportFiles.getFileName();
            if (file.exists()) {//判断文件是否存在
                //判断浏览器是否为火狐
                response.setContentType("application/force-download");
                response.setHeader("Content-Disposition", "attachment;fileName=" +new String(reportFiles.getFileName().getBytes("UTF-8"),"iso-8859-1"));
                byte[] buff = new byte[1024];
                //创建输入流（读文件）输出流（写文件）
                BufferedInputStream bis = null;
                OutputStream os = null;
                try {
                    os = response.getOutputStream();
                    bis = new BufferedInputStream(new FileInputStream(file));
                    int i = bis.read(buff);
                    while (i != -1) {
                        os.write(buff, 0, buff.length);
                        os.flush();
                        i = bis.read(buff);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (os != null) {
                        try {
                            os.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                return json.failure("文件不存在！！！");
            }
            return json.success("下载成功");
        } else {
            return json.failure("下载失败");
        }
    }

    @PostMapping("/delete")
    public ResultJSON deleteFile(Integer id){
        ResultJSON json = new ResultJSON();
        if (reportFilesService.selectById(id)!=null) {
            reportFilesService.delete(id);
            json.success();
        } else {
            json.failure("文件不存在，删除失败");
        }
        return json;
    }
}
