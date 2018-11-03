package ren.lingpai.lputil.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * 流操作工具类
 * Created by lrp on 17-2-12.
 */
public  final class StreamUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(StreamUtil.class);

    /**
     * 从输入流中读取字符串
     * @param is
     * @return
     */
    public static String getString(InputStream is){
        StringBuilder sb = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while(null != (line = reader.readLine())){
                sb.append(line);
            }
        }catch (Exception e){
            LOGGER.error("get string failure",e);
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    /**
     * 从输入流复制到输出流
     * @param inputStream
     * @param outputStream
     */
    public static void copyStream(InputStream inputStream, OutputStream outputStream){
        try{
            int length;
            byte[] buffer = new byte[4 * 1024];
            while (-1 != (length = inputStream.read(buffer,0,buffer.length))){
                outputStream.write(buffer,0,length);
            }
        }catch (Exception e){
            LOGGER.error("copy stream failure",e);
            throw new RuntimeException(e);
        }finally {
            try {
                inputStream.close();
                outputStream.close();
            }catch (Exception e){
                LOGGER.error("close stream failure",e);
            }
        }
    }

}
