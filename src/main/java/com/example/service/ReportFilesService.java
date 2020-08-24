package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mapper.ReportFilesMapper;
import com.example.model.ReportFiles;
import com.example.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReportFilesService {
    @Autowired
    private ReportFilesMapper reportFilesMapper;

    public void insert(ReportFiles reportFiles){
        reportFilesMapper.insert(reportFiles);
    }

    public ReportFiles selectById(Integer id){
        return reportFilesMapper.selectById(id);
    }

    public void delete(Integer id){
        String filePath = this.selectById(id).getFilePath();
        FileUtil fileUtil = new FileUtil();
        fileUtil.removeFile(filePath);
        reportFilesMapper.deleteById(id);
    }

    public IPage<ReportFiles> selectAllFiles(Integer pageNo){
        Page<ReportFiles> page = new Page<>(pageNo,10);
        QueryWrapper<ReportFiles> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ReportFiles::getReportId,0);
        return reportFilesMapper.selectPage(page,wrapper);
    }

    public ReportFiles selectByCode(String code){
        QueryWrapper<ReportFiles> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ReportFiles::getFileCode,code);
        return reportFilesMapper.selectOne(wrapper);
    }

}
