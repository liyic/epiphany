package com.brmayi.epiphany.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 		文件处理工具类
 * 
 *            
 *            .==.       .==.
 *           //'^\\     //^'\\
 *          // ^^\(\__/)/^ ^^\\
 *         //^ ^^ ^/6  6\ ^^^ \\
 *        //^ ^^ ^/( .. )\^ ^^ \\
 *       // ^^  ^/\|v""v|/\^^ ^ \\
 *      // ^^/\/  / '~~' \ \/\^ ^\\
 *      ----------------------------------------
 *      HERE BE DRAGONS WHICH CAN CREATE MIRACLE
 *       
 *      @author 静儿(987489055@qq.com)
 *
 */
public class EpiphanyFileUtil {
	private final static Logger LOGGER = LoggerFactory.getLogger(EpiphanyFileUtil.class); 
	
	/**
	 * 或缺文件路径
	 * @param filePath 文件根路径
	 * @return 加上当前时间目录的路径
	 */
	public static String getPath(String filePath) {
		SimpleDateFormat sdfForDir = new SimpleDateFormat("yyyyMMdd");
		Calendar date = Calendar.getInstance();
		String dateFormat = sdfForDir.format(date.getTime());
	    return new StringBuilder(filePath).append("/").append(dateFormat).append("/").toString();
    }
	
	/**
	 * 创建磁盘目录
	 * @param filePath 文件根路径
	 * @return 加上当前时间目录的路径
	 */
	public static String createPath(String filePath) {
		SimpleDateFormat sdfForDir = new SimpleDateFormat("yyyyMMdd");
		Calendar date = Calendar.getInstance();
		String dateFormat = sdfForDir.format(date.getTime());
		StringBuffer path = new StringBuffer(filePath).append("/").append(dateFormat);
	    File f = new File(path.toString());
	    if(f.exists()) {
	    	String[] children = f.list();
            for(String c : children) {
            	new File(f, c).delete();
            }
	    } else {
	    	f.mkdirs();
	    }
	    return path.append("/").toString();
    }
	
    
    /**
     * 将json数据写入磁盘文件，分批写释放内存
     * @param path 文件全路径
     * @param content 写入内容
     */
	public static void writeToFile(String path, String content) {
    	try (RandomAccessFile randomFile = new RandomAccessFile(path, "rw");){// 打开一个随机访问文件流，按读写方式
            // 文件长度，字节数
            long fileLength = randomFile.length();
            //将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.write(content.getBytes("UTF8"));
        } catch (IOException e) {
        	LOGGER.error("写入全量专辑文件失败", e);
        }
    }
}
