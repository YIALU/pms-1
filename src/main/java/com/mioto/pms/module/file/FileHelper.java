package com.mioto.pms.module.file;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.mioto.pms.exception.BasicException;
import com.mioto.pms.module.device.model.Device;
import com.mioto.pms.module.file.model.FileInfo;
import com.mioto.pms.module.file.service.FileService;
import com.mioto.pms.result.SystemTip;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Optional;

/**
 * @author admin
 * @date 2021-06-30 16:06
 */
@Component
public class FileHelper {
    @Value("${file.path}")
    private String filePath;
    @Resource
    private FileService fileService;
    /**
     * 创建文件对象
     * @param realPath 文件完整路径
     * @param fileName 文件名
     * @return
     */
    public FileInfo buildFileInfo(String realPath, String fileName){
        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(IdUtil.simpleUUID());
        fileInfo.setName(fileName);
        fileInfo.setFilePath(realPath.substring(filePath.length()));
        fileInfo.setCreateTime(new Date());
        return fileInfo;
    }

    /**
     * 创建文件名
     * @param fileName
     * @return
     */
    public String createNewFileName(String fileName){
        return StrBuilder.create()
                .append(DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT))
                .append(StrUtil.DOT)
                .append(getFileSuffix(fileName))
                .toString();
    }

    /**
     * 创建文件目录
     * @return
     */
    public String createDirPath(){
        String dirPath = StrBuilder.create().append(filePath).append(StrUtil.SLASH).append(DateUtil.today()).toString();
        FileUtil.mkdir(dirPath);
        return dirPath;
    }

    /**
     * 获取文件名后缀
     * @param fileName
     * @return
     */
    public String getFileSuffix(String fileName){
        return Optional.ofNullable(fileName)
                .filter(name -> name.lastIndexOf(StrUtil.DOT) > 0 && name.lastIndexOf(StrUtil.DOT) < name.length())
                .map(name -> name.substring(name.lastIndexOf(StrUtil.DOT) + 1))
                .orElse(StrUtil.EMPTY);
    }

    /**
     * 根据文件类型普安段是否为图片
     * @param fileType
     * @return
     */
    public boolean isImage(String fileType){
        switch (fileType){
            case "jpg":
            case "png":
            case "gif":
            case "tif":
            case "bmp":
                return true;
            default:
                return false;
        }
    }

    /**
     * 根据文件类型普安段是否为视频
     * @param fileType
     * @return
     */
    public boolean isVideo(String fileType){
        switch (fileType){
            case "rmvb":
            case "flv":
            case "mp4":
            case "mpg":
            case "wmv":
            case "wav":
            case "avi":
                return true;
            default:
                return false;
        }
    }

    /**
     * 文件下载
     * @param response
     * @param fileInfo
     */
    public void download(HttpServletResponse response, FileInfo fileInfo) {
        File file = new File(filePath + fileInfo.getFilePath());
        if (file.exists()) {
            OutputStream out = null;
            try {
                String contentType = "application/x-msdownload";
                response.setHeader("Content-Disposition", "attachment;filename=" + URLUtil.encode(fileInfo.getName()));
                response.setContentType(contentType);
                out = response.getOutputStream();
                FileUtil.writeToStream(file,out);
            } catch (IOException exception) {
                exception.printStackTrace();
            }finally {
                IoUtil.close(out);
            }
        }else {
            throw new BasicException(SystemTip.FILE_NOT_EXIST);
        }
    }

    /**
     * 设备二维码生成方法
     * @param device
     * @throws IOException
     */
    public void createCode(Device device) {
        String codePath = "/code/";
        File file = new File(filePath+codePath+device.getDeviceId()+".jpg");
        File touch = FileUtil.touch(file);
        //生成二维码图片，第一个参数为二维码扫码内容
        QrCodeUtil.generate(device.getName(), 200, 300, touch);
        //二维码底部文字内容
        String pressText=device.getDeviceId();
        //计算文字开始的位置
        //x开始的位置：（图片宽度-字体大小*字的个数）/2
        int startX = (200 - (10 * pressText.length())) / 2;
        //y开始的位置：图片高度-（图片高度-图片宽度）/2
        int startY = 300 - (300 - 200) / 3;
        try {
            Image src = ImageIO.read(touch);
            int imageW = src.getWidth(null);
            int imageH = src.getHeight(null);
            BufferedImage image = new BufferedImage(imageW, imageH, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, imageW, imageH, null);
            g.setColor(Color.black);
            g.setFont(new Font(null, 4, 20));
            g.drawString(pressText, startX, startY);
            g.dispose();
            ImageIO.write(image, "jpg",touch);
            FileInfo fileInfo = new FileInfo();
            fileInfo.setCreateTime(new Date());
            fileInfo.setName(touch.getName());
            fileInfo.setFilePath(codePath+ file.getName());
            fileInfo.setId(IdUtil.simpleUUID());
            int result = fileService.insertIgnoreNull(fileInfo);
            if (result > 0) {
                device.setCode(fileInfo.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
