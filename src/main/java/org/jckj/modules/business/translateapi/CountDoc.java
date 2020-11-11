package org.jckj.modules.business.translateapi;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.*;
import java.util.List;

public class CountDoc {


    public static void countLength() throws IOException {
        File file = new File("D:\\1.docx");
        try {
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument xdoc = new XWPFDocument(fis);

            List<XWPFParagraph> paragraphs = xdoc.getParagraphs();

            int count = 0;
            int i = 1;
            for (XWPFParagraph xwpfParagraph:paragraphs) {
                int linLength = 0;
                String lineStr = "";
                List<XWPFRun> xwpfRuns = xwpfParagraph.getRuns();
                for (XWPFRun xwpfRun : xwpfRuns) {
                    linLength +=  xwpfRun.toString().trim().length();
                    lineStr += xwpfRun.toString();
                    count += xwpfRun.toString().trim().length();
                }
                System.out.println("第"+i+"行内容：'"+lineStr+"'      长度："+linLength);
                i++;
            }
            System.out.println("文章总行数："+paragraphs.size() +" 行");
            System.out.println("文章总字数："+count);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void countLengths(InputStream inputStream) throws IOException {
        try {
            XWPFDocument xdoc = new XWPFDocument(inputStream);

            List<XWPFParagraph> paragraphs = xdoc.getParagraphs();

            int count = 0;
            int i = 1;
            for (XWPFParagraph xwpfParagraph:paragraphs) {
                int linLength = 0;
                String lineStr = "";
                List<XWPFRun> xwpfRuns = xwpfParagraph.getRuns();
                for (XWPFRun xwpfRun : xwpfRuns) {
                    linLength +=  xwpfRun.toString().trim().length();
                    lineStr += xwpfRun.toString();
                    count += xwpfRun.toString().trim().length();
                }
                System.out.println("第"+i+"行内容：'"+lineStr+"'      长度："+linLength);
                i++;
            }
            System.out.println("文章总行数："+paragraphs.size() +" 行");
            System.out.println("文章总字数："+count);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String readStream(InputStream in) {
        try {
//<1>创建字节数组输出流，用来输出读取到的内容
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //<2>创建缓存大小
            byte[] buffer = new byte[1024]; // 1KB
            //每次读取到内容的长度
            int len = -1;
            //<3>开始读取输入流中的内容
            while ((len = in.read(buffer)) != -1) { //当等于-1说明没有数据可以读取了
                baos.write(buffer, 0, len);   //把读取到的内容写到输出流中
            }
//<4> 把字节数组转换为字符串
            String content = baos.toString();
            //<5>关闭输入流和输出流
            in.close();
            baos.close();
            //<6>返回字符串结果
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return  e.getMessage();
        }
    }
    public static void main(String[] args) throws Exception {
        CountDoc.countLength();
    }


}

