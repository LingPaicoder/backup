package ren.lingpai.lputil.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Json工具类
 * Created by lrp on 17-2-12.
 */
public final class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 将POJO转为JSON
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String toJson(T obj){
        String json;
        try{
            json = OBJECT_MAPPER.writeValueAsString(obj);
        }catch (Exception e){
            LOGGER.error("conver POJO to JSON failure",e);
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * 将JSON转为POJO
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json , Class<T> type){
        T pojo;
        try {
            pojo = OBJECT_MAPPER.readValue(json,type);
        }catch (Exception e){
            LOGGER.error("conver JSON to POJO failure",e);
            throw new RuntimeException(e);
        }
        return pojo;
    }

}
