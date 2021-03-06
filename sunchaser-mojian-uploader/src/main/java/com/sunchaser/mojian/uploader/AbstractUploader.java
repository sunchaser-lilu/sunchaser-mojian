package com.sunchaser.mojian.uploader;

import cn.hutool.core.io.FileTypeUtil;
import com.google.common.base.Preconditions;
import com.sunchaser.mojian.uploader.autoconfigure.UploaderProperties;
import com.sunchaser.mojian.uploader.support.FileNameGenerator;
import com.sunchaser.mojian.uploader.support.FileUriGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
@DependsOn(value = "uploaderProperties")
public abstract class AbstractUploader implements Uploader {
    protected FileTypeEnum fileTypeEnum;

    protected UploaderProperties uploaderProperties;

    private FileNameGenerator fileNameGenerator;

    private FileUriGenerator fileUriGenerator;

    @Autowired
    public void setFileNameGenerator(FileNameGenerator fileNameGenerator) {
        this.fileNameGenerator = fileNameGenerator;
    }

    @Autowired
    public void setFileUriGenerator(FileUriGenerator fileUriGenerator) {
        this.fileUriGenerator = fileUriGenerator;
    }

    @Autowired
    public void setUploaderProperties(UploaderProperties uploaderProperties) {
        this.uploaderProperties = uploaderProperties;
    }

    public void setFileTypeEnum(FileTypeEnum fileTypeEnum) {
        this.fileTypeEnum = fileTypeEnum;
    }

    @Override
    public String upload(MultipartFile multipartFile) throws Exception {
        String type = FileTypeUtil.getType(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
        Preconditions.checkArgument(this.getFileTypeEnum().getFileExtNameList().contains(type), "文件格式有误");
        String fileUri = this.fileUriGenerator.generateFileUri(multipartFile, this.fileNameGenerator);
        return doUpload(multipartFile, fileUri);
    }

    protected abstract String doUpload(MultipartFile multipartFile, String fileUri) throws Exception;
}
