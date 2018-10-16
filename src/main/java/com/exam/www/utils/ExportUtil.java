package com.exam.www.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.jxls.area.Area;
import org.jxls.builder.AreaBuilder;
import org.jxls.builder.xls.XlsCommentAreaBuilder;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;
import org.jxls.util.TransformerFactory;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace;

/**
 * Created by Administrator on 2017/8/16.
 * 导出工具类
 */
public class ExportUtil {
    public static final org.slf4j.Logger logger = LoggerFactory.getLogger(ExportUtil.class);
    private static final String contextPath = "/file";

//    public static void htmlToWord() throws Exception {
//        InputStream bodyIs = new FileInputStream("f:\\1.html");
//        InputStream cssIs = new FileInputStream("f:\\1.css");
//        String body = this.getContent(bodyIs);
//        String css = this.getContent(cssIs);
//        //拼一个标准的HTML格式文档
//        String content = "<html><head><style>" + css + "</style></head><body>" + body + "</body></html>";
//        InputStream is = new ByteArrayInputStream(content.getBytes("GBK"));
//        OutputStream os = new FileOutputStream("f:\\1.doc");
//        this.inputStreamToWord(is, os);
//    }

    /**
     * 根据Doc模板生成word文件
     * @param dataMap 需要填入模板的数据
     * @param templateDir 模板文件所在文件夹
     * @param templateFilename 模板文件名
     */
    public static String exportDoc(Map<String,Object> dataMap, String templateDir, String templateFilename,
                                   HttpServletRequest request, HttpServletResponse response){
        Configuration configure = new Configuration();
        configure.setDefaultEncoding("utf-8");

        //加载需要装填的模板
        Template template=null;

        try {
            //加载模板文件
            configure.setDirectoryForTemplateLoading(new File(templateDir));
//设置模板装置方法和路径，FreeMarker支持多种模板装载方法。可以重servlet，classpath,数据库装载。
            //        configure.setClassForTemplateLoading(this.getClass(), "/testDoc");
            //设置对象包装器
//        configure.setObjectWrapper(new DefaultObjectWrapper());
            //设置异常处理器
//        configure.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);

            template = configure.getTemplate(templateFilename);

            String fileUpLoadDisk = PropertiesUtils.getProperty("fileUpLoadDisk");
            String format = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss.ss").format(new Date());
            String destFilePath = fileUpLoadDisk + "\\export\\" + format + ".doc";

            File outFile=new File(destFilePath);
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
            template.process(dataMap, out);
            out.close();

            String contextPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            String retUrl = contextPath + "/file/export/" + format + ".doc";
            return retUrl;
        } catch (Exception e) {
            e.printStackTrace();
            String fullError = getFullStackTrace(e);
            logger.error("exportDoc->e:"+fullError);
            return "";
        }
    }

    /**
     * 导出文本
     * */
    public static String exportTxt(String content,
                                   HttpServletRequest request, HttpServletResponse response){

        String fileUpLoadDisk = PropertiesUtils.getProperty("fileUpLoadDisk");
        String format = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss.ss").format(new Date());
        String destFilePath = fileUpLoadDisk + "\\export\\" + format + ".txt";

        File file = new File(destFilePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try {
            FileOutputStream fop = new FileOutputStream(file);
            byte[] contentInBytes = content.getBytes();
            fop.write(contentInBytes);
            fop.flush();
            fop.close();

//            FileWriter fileWritter = new FileWriter(file.getName(),true);
//            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
//            bufferWritter.write(content);
//            bufferWritter.close();

            String contextPath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath();
            String retUrl = contextPath + "/file/export/" + format + ".txt";
            return retUrl;
        }catch (Exception e){
            e.printStackTrace();
            String fullError = getFullStackTrace(e);
            logger.error("exportTxt->e:"+fullError);
            return "";
        }
    }

    /**
     * @描述 根据excel模板路径导出单个sheet的excel
     * @param context
     * @param srcFilePath
     * @param request
     * @param response
     * @返回值 java.lang.String
     * @创建人 Administrator
     * @创建时间 2017/10/17
     */
    public static String exportExcel(Context context, String srcFilePath,
                                     HttpServletRequest request, HttpServletResponse response){

        String fileUpLoadDisk = PropertiesUtils.getProperty("fileUpLoadDisk");
        String format = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss.ss").format(new Date());
//        String filePath = FilePathHelper.getPath(fileUpLoadDisk + fileUploadPath + logoPath);
        String destFilePath = fileUpLoadDisk + "\\export\\" + format + ".xls";

        File file = new File(destFilePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(srcFilePath);
            os = new FileOutputStream(destFilePath);
            JxlsHelper.getInstance().processTemplate(is, os, context);
//            JxlsHelper.getInstance().processTemplateAtCell(is, os, context, "Result!A1");

            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }

            String contextPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
//            String imgUrl = contextPath + "/export/" + format + ".xls";
            String retUrl = contextPath + "/file/export/" + format + ".xls";
            return retUrl;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("exportExcel->e:"+e.toString());
            return "";
        }
    }

    /**
     * @描述 导出多个sheet的excel
     * @param srcFilePath
     * @param listSheetHeads
     * @param contexts
     * @param request
     * @param response
     * @返回值 java.lang.String
     * @创建人 Administrator
     * @创建时间 2017/10/17
     */
    public static String exportMultipleSheetsExcel(String srcFilePath, List<String> listSheetHeads,
                                                   List<Context> contexts,
                                                   HttpServletRequest request, HttpServletResponse response){

        String fileUpLoadDisk = PropertiesUtils.getProperty("fileUpLoadDisk");
        String format = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss.ss").format(new Date());
        String destFilePath = fileUpLoadDisk + "\\export\\" + format + ".xls";

        File file = new File(destFilePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        InputStream is = null;
        FileOutputStream out = null;
        try {
            is = new FileInputStream(srcFilePath);
            out = new FileOutputStream(destFilePath);

            Transformer transformer = TransformerFactory.createTransformer(is, out);
            AreaBuilder areaBuilder = new XlsCommentAreaBuilder(transformer);
            List<Area> xlsAreaList = areaBuilder.build();
            if(xlsAreaList==null || xlsAreaList.size()==0){
                return "";
            }

            for(int i=0;i<xlsAreaList.size();i++){
                Area xlsArea = xlsAreaList.get(i);
                Context context = contexts.get(i);
                xlsArea.applyAt(new CellRef(listSheetHeads.get(i)), context);
            }

            transformer.write();

            if(is!=null){
                is.close();
            }
            if(out!=null){
                out.close();
            }
            //返回导出的excel路径Url
            String contextPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            String retUrl = contextPath + "/file/export/" + format + ".xls";
            return retUrl;
        }catch (Exception e){
            e.printStackTrace();
            String fullError = getFullStackTrace(e);
            logger.error("exportMultipleSheetsExcel->e:"+fullError);
            return "";
        }
    }
}
