package com.framework.core;

import java.io.*;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.junit.Test;

/**
 * http://www.cnblogs.com/mingforyou/archive/2013/08/26/3282922.html
 * Created by HR on 2017/9/5.
 */
public class ExcelTest {

    public void createExcel(OutputStream os) throws WriteException, IOException {
        //创建工作薄
        WritableWorkbook workbook = Workbook.createWorkbook(os);
        //创建新的一页
        WritableSheet sheet = workbook.createSheet("First Sheet", 0);
        //创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
        Label xuexiao = new Label(0, 0, "学校");
        sheet.addCell(xuexiao);
        Label zhuanye = new Label(1, 0, "专业");
        sheet.addCell(zhuanye);
        Label jingzhengli = new Label(2, 0, "专业竞争力");
        sheet.addCell(jingzhengli);

        Label qinghua = new Label(0, 1, "清华大学");
        sheet.addCell(qinghua);
        Label jisuanji = new Label(1, 1, "计算机专业");
        sheet.addCell(jisuanji);
        Label gao = new Label(2, 1, "高");
        sheet.addCell(gao);

        Label beida = new Label(0, 2, "北京大学");
        sheet.addCell(beida);
        Label falv = new Label(1, 2, "法律专业");
        sheet.addCell(falv);
        Label zhong = new Label(2, 2, "中");
        sheet.addCell(zhong);

        Label ligong = new Label(0, 3, "北京理工大学");
        sheet.addCell(ligong);
        Label hangkong = new Label(1, 3, "航空专业");
        sheet.addCell(hangkong);
        Label di = new Label(2, 3, "低");
        sheet.addCell(di);

        //把创建的内容写入到输出流中，并关闭输出流
        workbook.write();
        workbook.close();
        os.close();
    }

    @Test
    public void testOutFile() {

        String fileName = "D:\\TEST.xls";

        File file = new File(fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);

            createExcel(fos);

            System.out.println(file.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testReadXLS() {

        jxl.Workbook readwb = null;

        try

        {
            //构建Workbook对象, 只读Workbook对象

            //直接从本地文件创建Workbook

            InputStream is = new FileInputStream("D:/TEST.xls");

            readwb = Workbook.getWorkbook(is);

            //Sheet的下标是从0开始

            //获取第一张Sheet表

            Sheet readsheet = readwb.getSheet(0);

            //获取Sheet表中所包含的总列数

            int rsColumns = readsheet.getColumns();

            //获取Sheet表中所包含的总行数

            int rsRows = readsheet.getRows();

            //获取指定单元格的对象引用

            for (int i = 0; i < rsRows; i++) {

                for (int j = 0; j < rsColumns; j++)

                {

                    Cell cell = readsheet.getCell(j, i);

                    System.out.print(cell.getContents() + " ");

                }

                System.out.println();

            }


            //利用已经创建的Excel工作薄,创建新的可写入的Excel工作薄

            jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(new File(

                    "D:/TESTBACKUP.xls"), readwb);

            //读取第一张工作表

            jxl.write.WritableSheet ws = wwb.getSheet(0);

            //获得第一个单元格对象

            jxl.write.WritableCell wc = ws.getWritableCell(0, 0);

            //判断单元格的类型, 做出相应的转化

            if (wc.getType() == CellType.LABEL) {

                Label l = (Label) wc;

                l.setString("新姓名");

            }

            //写入Excel对象

            wwb.write();

            wwb.close();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            readwb.close();

        }
    }

}
