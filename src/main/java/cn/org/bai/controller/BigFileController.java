package cn.org.bai.controller;

import cn.novelweb.tool.upload.local.pojo.UploadFileParam;
import cn.org.bai.annotation.Login;
import cn.org.bai.config.SystemException;
import cn.org.bai.constant.SysConstant;
import cn.org.bai.constant.UrlConstant;
import cn.org.bai.model.dto.AddFileDto;
import cn.org.bai.model.dto.GetFileDto;
import cn.org.bai.model.entity.Files;
import cn.org.bai.model.response.ErrorCode;
import cn.org.bai.model.response.RestResponse;
import cn.org.bai.model.response.RestResponses;
import cn.org.bai.model.response.Result;
import cn.org.bai.service.IFileService;
import cn.org.bai.utils.EmptyUtils;
import cn.org.bai.utils.EncodingUtils;
import cn.org.bai.utils.InputStreamUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(UrlConstant.API + "/file")
@RequiredArgsConstructor
public class BigFileController {

    private final IFileService fileService;

    /**
     * 获取文件列表
     *
     * @param pageNo   当前页
     * @param pageSize 分页大小
     * @return {@link RestResponse}<{@link List}<{@link GetFileDto}>>
     * @throws IOException ioexception
     */
    @GetMapping(value = "/list")
    public RestResponse<List<GetFileDto>> getFileList(@RequestParam Integer pageNo, @RequestParam Integer pageSize) throws IOException {
        return RestResponses.newResponseFromResult(fileService.getFileList(pageNo, pageSize));
    }

    /**
     * 普通上传方式上传文件：用于小文件的上传，等待时间短，不会产生配置数据
     *
     * @param file 文件
     * @return {@link RestResponse}<{@link String}>
     */
    @Login
    @PostMapping("/upload")
    public RestResponse<String> uploadFiles(MultipartFile file) {
        if (file.isEmpty()) {
            return RestResponses.newFailResponse(ErrorCode.INVALID_PARAMETER, "文件不能为空");
        }
        return RestResponses.newResponseFromResult(fileService.uploadFiles(file));
    }

    /**
     * 断点续传完成后上传文件信息进行入库操作
     *
     * @param dto           dto
     * @param bindingResult 绑定的结果
     * @return {@link RestResponse}<{@link String}>
     */
    @PostMapping("/add")
    public RestResponse<String> addFile(@RequestBody AddFileDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return RestResponses.newFailResponse(ErrorCode.INVALID_PARAMETER, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return RestResponses.newResponseFromResult(fileService.addFile(dto));
    }

    /**
     * 检查文件MD5（文件MD5若已存在进行秒传）
     *
     * @param md5      md5
     * @param fileName 文件名称
     * @return {@link RestResponse}<{@link Object}>
     */
    @GetMapping(value = "/check-file")
    public RestResponse<Object> checkFileMd5(String md5, String fileName) {
        return RestResponses.newResponseFromResult(fileService.checkFileMd5(md5, fileName));
    }

    /**
     * 断点续传方式上传文件：用于大文件上传
     *
     * @param param   参数
     * @param request 请求
     * @return {@link RestResponse}<{@link Object}>
     */
    @Login
    @PostMapping(value = "/breakpoint-upload", consumes = "multipart/*", headers = "content-type=multipart/form-data", produces = "application/json;charset=UTF-8")
    public RestResponse<Object> breakpointResumeUpload(UploadFileParam param, HttpServletRequest request) {
        return RestResponses.newResponseFromResult(fileService.breakpointResumeUpload(param, request));
    }

    /**
     * 图片/PDF查看
     *
     * @param id id
     * @return {@link ResponseEntity}<{@link byte[]}>
     * @throws IOException ioexception
     */
    @GetMapping(value = "/view/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> viewFilesImage(@PathVariable String id) throws IOException {
        Result<Files> fileDetails = fileService.getFileDetails(id);
        if (fileDetails.isSuccess()) {
            if (!EmptyUtils.basicIsEmpty(fileDetails.getData().getSuffix()) && !SysConstant.IMAGE_TYPE.contains(fileDetails.getData().getSuffix())) {
                throw new SystemException(ErrorCode.FILE_ERROR.getCode(), "非图片/PDF类型请先下载");
            }
        } else {
            throw new SystemException(fileDetails.getErrorCode().getCode(), fileDetails.getDescription());
        }
        InputStream inputStream = fileService.getFileInputStream(id);
        return new ResponseEntity<>(InputStreamUtils.inputStreamToByte(inputStream), HttpStatus.OK);
    }

    /**
     * 文件下载
     *
     * @param id       id
     * @param isSource 是否使用原文件名
     * @param request  请求
     * @param response 响应
     */
    @GetMapping(value = "/download/{id}")
    public void viewFilesImage(@PathVariable String id, @RequestParam(required = false) Boolean isSource, HttpServletRequest request, HttpServletResponse response) {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            Result<Files> fileDetails = fileService.getFileDetails(id);
            if (!fileDetails.isSuccess()) {
                throw new SystemException(fileDetails.getErrorCode().getCode(), fileDetails.getDescription());
            }
            String filename = (!EmptyUtils.basicIsEmpty(isSource) && isSource) ? fileDetails.getData().getFileName() : fileDetails.getData().getFilePath();
            inputStream = fileService.getFileInputStream(id);
            response.setHeader("Content-Disposition", "attachment;filename=" + EncodingUtils.convertToFileName(request, filename));
            // 获取输出流
            outputStream = response.getOutputStream();
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            log.error("文件下载出错", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
