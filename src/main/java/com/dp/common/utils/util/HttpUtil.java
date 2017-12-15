package com.dp.common.utils.util;


import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class HttpUtil {

    public static final int TIMEOUT_CONNECT = 3000;
    public static final int TIMEOUT_READ = 3000;

    /**
     * 向指定URL发送GET方法的请求
     * @param url  发送请求的URL
     * @param param  请求参数，请求参数应该是name1=value1&name2=value2的形式
     * @return URL所代表远程资源的响应
     * @throws Exception 
     */

    public static String sendGet(String url, String param) throws Exception {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            if (StringUtils.isNotBlank(param)) {
                url = url + "?" + param;
            }
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setConnectTimeout(TIMEOUT_CONNECT);//连接主机超时
            conn.setReadTimeout(TIMEOUT_READ);//读取超时
            conn.setUseCaches(false);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
            conn.connect();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return result.toString();
    }

    /**
     * 向指定URL发送POST方法的请求
     * @param url 发送请求的URL
     * @param param 请求参数，请求参数应该是name1=value1&name2=value2的形式
     * @return URL所代表远程资源的响应
     * @throws Exception 
     */
    public static String sendPost(String url, String param) throws Exception {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setDoOutput(true);//默认false
            conn.setDoInput(true);//默认true
            conn.setConnectTimeout(TIMEOUT_CONNECT);
            conn.setReadTimeout(TIMEOUT_READ);
            conn.setRequestMethod("POST");//默认get
            conn.setUseCaches(false);//默认true
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
            out = new PrintWriter(conn.getOutputStream());//会调用connect()方法
            out.print(param);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        }
        return result.toString();
    }


    /**
     * 向指定URL发送带文件的POST方法的请求
     * @param url 发送请求的URL
     * @param params 请求参数，请求参数应该是name1=value1&name2=value2的形式
     * @param fileItemList 请求参数，上传的文件信息
     * @return URL所代表远程资源的响应
     */
    public static String sendPostWithFile(String url, String params, List<FileItem> fileItemList) throws Exception {
        StringBuilder result = new StringBuilder();
        String uuid = UuidUtil.generator();
        String BOUNDARY = "----WZQUN" + uuid;
        DataInputStream in = null;//用于读文件
        DataOutputStream out = null;//用于输出http请求
        BufferedReader reader = null;//用户获取http响应
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setConnectTimeout(TIMEOUT_CONNECT);
            conn.setReadTimeout(TIMEOUT_READ);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
            conn.setRequestProperty("Content-Type","multipart/form-data; boundary=" + BOUNDARY);
            out = new DataOutputStream(conn.getOutputStream());
            // text
            if (StringUtils.isNotBlank(params)) {
                StringBuilder sb = new StringBuilder();
                String[] paramArr = params.split("&");
                for (String param : paramArr) {
                    String[] arr = param.split("=");
                    String name = arr[0];
                    String value = arr[1];
                    sb.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    sb.append("Content-Disposition: form-data; name=\""+ name + "\"\r\n\r\n");
                    sb.append(value);
                }
                out.write(sb.toString().getBytes());
            }
            // files
            if (fileItemList != null) {
                for (FileItem fileItem : fileItemList) {
                    InputStream inputStream = fileItem.getInputStream();
                    String fileName = fileItem.getFileName();
                    String fieldName = fileItem.getFieldName();
                    String contentType = "application/octet-stream";
                    if (fileName.endsWith(".png")) {
                        contentType = "image/png";
                    } else if(fileName.endsWith(".bmp")) {
                        contentType = "image/bmp";
                    } else if(fileName.endsWith(".gif")) {
                        contentType = "image/gif";
                    }  else if(fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".jpe")) {
                        contentType = "image/jpeg";
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    sb.append("Content-Disposition: form-data; name=\"" + fieldName + "\"; filename=\""+fileName+"\"\r\n");
                    sb.append("Content-Type: "+contentType+"\r\n\r\n");
                    out.write(sb.toString().getBytes());
                    in = new DataInputStream(inputStream);
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                }
            }
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            // 读取返回数据
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
        return result.toString();
    }


    public static class FileItem {
        private String fieldName;
        private String fileName;
        private InputStream inputStream;
        public FileItem(String fieldName, String fileName, InputStream inputStream) {
            this.fieldName = fieldName;
            this.fileName = fileName;
            this.inputStream = inputStream;
        }
        public String getFieldName() {
            return fieldName;
        }
        public String getFileName() {
            return fileName;
        }
        public InputStream getInputStream() {
            return inputStream;
        }
    }

    // 提供主方法，测试发送GET请求和POST请求
    public static void main(String args[]) throws Exception {
    }
}
