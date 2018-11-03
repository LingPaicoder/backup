package ren.lingpai.lpagile.part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ren.lingpai.lpagile.entity.RequestFormParam;
import ren.lingpai.lpagile.entity.RequestParam;
import ren.lingpai.lputil.codec.CodecUtil;
import ren.lingpai.lputil.collection.CollectionUtil;
import ren.lingpai.lputil.io.StreamUtil;
import ren.lingpai.lputil.string.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 封装对Param对象的获取
 * Created by lrp on 17-4-22.
 */
public final class RequestPart {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestPart.class);

    /**
     * 创建请求对象
     * @param request
     * @return
     * @throws IOException
     */
    public static RequestParam createParam(HttpServletRequest request) throws IOException{
        List<RequestFormParam> formParamList = new ArrayList<RequestFormParam>();
        formParamList.addAll(parseParameterNames(request));
        formParamList.addAll(parseInputStream(request));
        return new RequestParam(formParamList);
    }

    private static List<RequestFormParam> parseParameterNames(HttpServletRequest request){
        List<RequestFormParam> formParamList = new ArrayList<RequestFormParam>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()){
            String fieldName = paramNames.nextElement();
            String[] fieldValues = request.getParameterValues(fieldName);
            if(CollectionUtil.isNotEmpty(fieldValues)){
                Object fieldValue;
                if(1 == fieldValues.length){
                    fieldValue = fieldValues[0];
                }else {
                    StringBuilder sb = new StringBuilder("");
                    for(int i = 0; i < fieldValues.length; i ++){
                        sb.append(fieldValues[i]);
                        if(i != (fieldValues.length - 1)){
                            sb.append(StringUtil.SEPARATOR);
                        }
                    }
                    fieldValue = sb.toString();
                }
                formParamList.add(new RequestFormParam(fieldName,fieldValue));
            }
        }

        LOGGER.info("------[RequestPart:parseParameterNames]end------formParamList details:");
        if(formParamList.size() > 0){
            for (int i = 0 ; i < formParamList.size() ; i ++){
                LOGGER.info("------[" + formParamList.get(i).getFieldName() + "→" + formParamList.get(i).getFieldValue() + "]------");
            }
        }else {
            LOGGER.info("------[formParamList is empty]------");
        }

        return formParamList;
    }

    private static List<RequestFormParam> parseInputStream(HttpServletRequest request) throws IOException{
        List<RequestFormParam> formParamList = new ArrayList<RequestFormParam>();
        String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
        if(StringUtil.isNotEmpty(body)){
            String[] kvs = StringUtil.splitString(body,"&");
            if(CollectionUtil.isNotEmpty(kvs)){
                for(String kv : kvs){
                    String[] array = StringUtil.splitString(kv,"=");
                    if(CollectionUtil.isNotEmpty(array) && (2 == array.length)){
                        String fieldName = array[0];
                        String fieldValue = array[1];
                        formParamList.add(new RequestFormParam(fieldName,fieldValue));
                    }
                }
            }
        }

        LOGGER.info("------[RequestPart:parseInputStream]end------formParamList details:");
        if(formParamList.size() > 0){
            for (int i = 0 ; i < formParamList.size() ; i ++){
                LOGGER.info("------[" + formParamList.get(i).getFieldName() + "→" + formParamList.get(i).getFieldValue() + "]------");
            }
        }else {
            LOGGER.info("------[formParamList is empty]------");
        }

        return formParamList;
    }

}
