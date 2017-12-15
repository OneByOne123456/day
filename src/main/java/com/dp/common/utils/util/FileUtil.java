package com.dp.common.utils.util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/27.
 */
public class FileUtil {

    private FileUtil() {}


    public static String getDateDir(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
    }


    /** 计算文件大小 */
    public static String getSize(long b) {
        if (b < 1024) {
            return b + "b";
        } else if ((1024 <= b) && (b < (1024 * 1024))) {
            return StringUtils.substringBefore(Math.ceil(b/(1024)) + "", ".") + "kb";
        } else if ((1024*1024 <= b) && (b < (1024 * 1024 * 1024))) {
            return StringUtils.substringBefore(Math.ceil(b/(1024*1024)) + "", ".") + "M";
        }
        return "0kb";
    }



    /**
     * @Desc 将上传上来的文件，压缩一般和原文件一起存盘，返回 缩略图路径_原图路径
     */
    public static String pressMultipartFile(MultipartFile multipartFile, String dir, int w, int h) throws IOException {
        String suffix = StringUtils.substringAfterLast(multipartFile.getOriginalFilename(),".");
        File dirs = new File(dir);
        if (!dirs.exists()) {
            dirs.mkdirs();
        }
        String id = UuidUtil.generator();
        String path = dir + "/" + id + "." + suffix;
        //按比例压缩
        Image img = ImageIO.read(multipartFile.getInputStream());
        int width = img.getWidth(null);
        int height = img.getHeight(null);
        if (width / height > w / h) {
            h = (int) (height * w / width);
        } else {
            w = (int) (width * h / height);
        }
        BufferedImage image = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB );
        image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
        File destFile = new File(path);
        FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out); // 可以正常实现bmp、png、gif转jpg
        encoder.encode(image); // JPEG编码
        out.close();
        //处理原文件
        String id2 = UuidUtil.generator();
        String path2 = dir + "/" + id2 + "." + suffix;
        multipartFile.transferTo(new File(path2));

        return path + "_" + path2;
    }
}
