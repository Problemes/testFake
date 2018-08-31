package com.framework.core;

import com.framework.Util.Base64Util;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


/**
 * IO流信息操作
 * http://blog.csdn.net/yczz/article/details/38761237
 * Created by HR on 2017/9/6.
 */
public class IOTest {

    /** Test IOUtils start
     * https://www.cnblogs.com/xwb583312435/p/9015772.html
     * https://blog.csdn.net/zhaoyanjun6/article/details/55051917
     */


   /* public static long copyLarge(Reader input, Writer output, char [] buffer) throws IOException {
        long count = 0;
        int n = 0;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }*/

    @Test
    public void testIOUtils_readLines (){

        try {
//            InputStream in = IOUtils.toInputStream("钮祜禄", "utf-8");
            InputStream in = new FileInputStream("D:\\zzz\\test.xml");
            List<String> lines = IOUtils.readLines(in);
            for (String line : lines){
                System.out.println(line);
            }

        }catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Test //从一个流中读取一个byte长度的字节数组
    public void testIOUtils_read(){

        byte[] bytes = new byte[12];
        try {
            InputStream in = IOUtils.toInputStream("NAMES:Lilei, Hanmeimei, 钮祜禄, 那尔布", "UTF-8");
            IOUtils.read(in, bytes);
            System.out.println(new String(bytes, "UTF-8"));

            byte[] longBytes = new byte[50];

            IOUtils.read(in, longBytes, 3, 40);//offset是buffer的偏移值，length是读取的长度

            String base64En = Base64Util.encryptBASE64(longBytes);
            System.out.println(base64En);
            System.out.println(new String(Base64Util.decryptBASE64(base64En), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test //从一个流中读取指定长度的流，如果读取的长度不够，就会抛出异常
    public void testIOUtils_readFully() throws IOException {

        byte[] bytes = new byte[100]; //length 太长，实际读出来44个字节，和bytes长度不符，会报EOFException
        InputStream in = IOUtils.toInputStream("NAMES:Lilei, Hanmeimei, 钮祜禄, 那尔布", "UTF-8");
        IOUtils.readFully(in, bytes);
        System.out.println(new String(bytes));
    }

    @Test //跳过指定长度的流， 如果跳过的长度大于流的长度，报错：EOFException
    public void testIOUtils_skipFully() throws IOException {

        InputStream in = IOUtils.toInputStream("NAMES:Lilei, Hanmeimei, 钮祜禄, 那尔布", "UTF-8");
        IOUtils.skipFully(in, 45);
    }

    @Test //跳过指定长度的流， 如果跳过的长度大于流的长度，报错：EOFException
    public void testIOUtils_newUrl() throws IOException, URISyntaxException {

        String urlStr = "http://www.baidu.com";
        String urlContent = IOUtils.toString(new URI(urlStr), "utf-8");

        System.out.println(urlContent);
    }


}
