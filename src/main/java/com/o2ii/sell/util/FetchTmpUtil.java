package com.o2ii.sell.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class FetchTmpUtil {

    public static File fetchTmpFile(String url, String dir){
        try {
            URL u = new URL(url);
            HttpURLConnection  conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("POST");
            conn.connect();
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            String content_disposition = conn.getHeaderField("content-disposition");
            //微信服务器生成的文件名称
            String file_name ="";
            String[] content_arr = content_disposition.split(";");
            if(content_arr.length  == 2){
                String tmp = content_arr[1];
                int index = tmp.indexOf("\"");
                file_name =tmp.substring(index+1, tmp.length()-1);
            }

            //生成不同文件名称
            File file = new File(file_name);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            byte[] buf = new byte[2048];
            int length = bis.read(buf);
            while(length != -1){
                bos.write(buf, 0, length);
                length = bis.read(buf);
            }
            bos.close();
            bis.close();
            return file;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
