package ren.lingpai.lpagile.part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ren.lingpai.lpagile.entity.RequestFileParam;
import ren.lingpai.lpagile.entity.RequestFormParam;
import ren.lingpai.lpagile.entity.RequestParam;
import ren.lingpai.lputil.collection.CollectionUtil;
import ren.lingpai.lputil.file.FileUtil;
import ren.lingpai.lputil.io.StreamUtil;
import ren.lingpai.lputil.string.StringUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lrp on 17-4-21.
 */
public class UploadPart {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadPart.class);

    private static ServletFileUpload servletFileUpload;

    /**
     * 初始化servletFileUpload
     * 文件地址
     * 文件大小限制
     * @param servletContext
     */
    public static void init(ServletContext servletContext){
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        servletFileUpload = new ServletFileUpload
                (new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD,repository));
        int uploadLimit = ConfigPart.getAppUploadLimit();
        if(uploadLimit != 0){
            servletFileUpload.setFileSizeMax(uploadLimit * 1024 * 1024);
        }
    }

    /**
     * 判断请求是否为multiPart类型
     * @param request
     * @return
     */
    public static boolean isMultipart(HttpServletRequest request){
        return ServletFileUpload.isMultipartContent(request);
    }

    /**
     * 创建请求对象
     * @param request
     * @return
     * @throws IOException
     */
    public static RequestParam createParam(HttpServletRequest request) throws IOException{
        List<RequestFormParam> formParamList = new ArrayList<RequestFormParam>();
        List<RequestFileParam> fileParamList = new ArrayList<RequestFileParam>();
        try {
            Map<String,List<FileItem>> fileItemListMap = servletFileUpload.parseParameterMap(request);
            if(CollectionUtil.isNotEmpty(fileItemListMap)){
                for(Map.Entry<String,List<FileItem>> fileItemEntry : fileItemListMap.entrySet()){
                    String fieldName = fileItemEntry.getKey();
                    List<FileItem> fileItemList = fileItemEntry.getValue();
                    if(CollectionUtil.isNotEmpty(fileItemList)){
                        for(FileItem fileItem : fileItemList){
                            if(fileItem.isFormField()){//表单参数
                                String fieldValue = fileItem.getString("UTF-8");
                                formParamList.add(new RequestFormParam(fieldName,fieldValue));
                            }else {//文件参数
                                String fileName = FileUtil.getRealFileName(new String(fileItem.getName().getBytes(),"UTF-8"));
                                if(StringUtil.isNotEmpty(fileName)){
                                    long fileSize = fileItem.getSize();
                                    String contentType = fileItem.getContentType();
                                    InputStream inputStream = fileItem.getInputStream();
                                    fileParamList.add(new RequestFileParam(fieldName,fileName,fileSize,contentType,inputStream));
                                }
                            }
                        }
                    }
                }
            }
        }catch (FileUploadException e){
            LOGGER.error("------[UploadPart:createParam create param failure]------",e);
            throw new RuntimeException(e);
        }
        return new RequestParam(formParamList,fileParamList);
    }

    /**
     * 上传文件
     * @param basePath
     * @param fileParam
     */
    public static void uploadFile(String basePath,RequestFileParam fileParam){
        try{
            if(null != fileParam){
                String filePath = basePath + fileParam.getFileName();
                FileUtil.createFile(filePath);
                InputStream inputStream = new BufferedInputStream(fileParam.getInputStream());
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
                StreamUtil.copyStream(inputStream,outputStream);
            }
        }catch(Exception e){
            LOGGER.error("------[UploadPart:uploadFile upload file failure]------",e);
            throw new RuntimeException(e);
        }
    }

    public static void uploadFile(String basePath,List<RequestFileParam> fileParamList){
        try {
            if(CollectionUtil.isNotEmpty(fileParamList)){
                for (RequestFileParam fileParam : fileParamList){
                    uploadFile(basePath,fileParam);
                }
            }
        }catch (Exception e){
            LOGGER.error("------[UploadPart:uploadFile uploadFile upload file failure]------",e);
            throw new RuntimeException(e);
        }
    }

}
