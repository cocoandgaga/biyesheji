package com.zxj.finalexam.controller;

import com.zxj.common.utils.R;
import com.zxj.common.utils.RRException;
import com.zxj.finalexam.dao.AttachmentDao;
import com.zxj.finalexam.dao.DeptUserDao;
import com.zxj.finalexam.entity.Attachment;
import com.zxj.finalexam.entity.DeptUserEntity;
import com.zxj.finalexam.service.AttachmentService;
import com.zxj.finalexam.utils.FileTransProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;


@Controller
@RequestMapping("finalexam/file")
public class FileTransportController {

    @Autowired
    private FileTransProvider fileTransProvider;
    @Autowired
    private AttachmentDao attachmentDao;
    @Autowired
    private DeptUserDao deptUserDao;

    @RequestMapping("/upload")
    @ResponseBody
    public R upload(MultipartHttpServletRequest request) throws IOException {
        List<String> urls = new ArrayList<>();
        MultiValueMap<String, MultipartFile> fileMap = request.getMultiFileMap();
        if (!fileMap.isEmpty()) {
            for (Map.Entry<String, List<MultipartFile>> entity : fileMap.entrySet()) {
                List<MultipartFile> fileList = entity.getValue();
                for (MultipartFile file : fileList) {
                    //获取文件的二进制码
                    if (file.isEmpty()) {
                        throw new RRException("上传文件不能为空");
                    }
                    String filename = file.getOriginalFilename();
                    String url = fileTransProvider.upload(file.getInputStream(), Objects.requireNonNull(filename));
                    urls.add(url);
                }
            }
        }
        if (!urls.isEmpty()) {
            return R.ok().put("urls", urls);
        } else {
            return R.error("上传失败");
        }
    }


    @RequestMapping("/upload-attachment")
    @ResponseBody
    @Transactional
    public R uploadAttachment(MultipartHttpServletRequest request) throws IOException {
        List<String> urls = new ArrayList<>();
        List<String> uids = new ArrayList<>();
        MultiValueMap<String, MultipartFile> fileMap = request.getMultiFileMap();
        if (!fileMap.isEmpty()) {
            for (Map.Entry<String, List<MultipartFile>> entity : fileMap.entrySet()) {
                List<MultipartFile> fileList = entity.getValue();
                for (MultipartFile file : fileList) {
                    //获取文件的二进制码
                    if (file.isEmpty()) {
                        throw new RRException("上传文件不能为空");
                    }
                    String filename = file.getOriginalFilename();
                    Long creator = (Long) request.getSession().getAttribute("creator");
                    DeptUserEntity deptUserEntity = deptUserDao.getDeptName(creator);
                    Attachment attachment = new Attachment();
                    attachment.setDeptId(deptUserEntity.getDeptId());
                    attachment.setCreator(creator);
                    attachment.setGmtCreate(new Date());
                    attachment.setFileName(filename);
                    String url = fileTransProvider.upload(file.getInputStream(), Objects.requireNonNull(filename));
                    attachment.setUrl(url);
                    attachmentDao.insert(attachment);
                    uids.add(attachment.getId());
                    urls.add(url);
                }
            }
        }
        if (!urls.isEmpty()) {
            return R.ok().put("urls", urls).put("uids", uids);
        } else {
            return R.error("上传失败");
        }
    }
}
