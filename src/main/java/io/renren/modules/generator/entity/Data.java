package io.renren.modules.generator.entity;

import io.swagger.models.auth.In;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Data {

    public static HashMap<Integer, Double> map1 = new HashMap();
    public static HashMap<Integer, Double> map2 = new HashMap();
    public static HashMap<Integer, Double> map3 = new HashMap();
    public static HashMap<Integer, Double> map4 = new HashMap();
    public static HashMap<Integer, Double> map5 = new HashMap();
    public static HashMap<Integer, Double> map6 = new HashMap();

//    public static void main(String[] args) {
//        String fileName = "E:\\File\\array.txt";
//
//        //total是返回一共有多少条数据
//        Integer total = initData(fileName);
//        //
//        for (int i = 0;i<total ;i++){
//            //你说你每次for循环都要用到6个数据
//            //那么使用的方法是
//            Double data1 = map1.get(i);
//            Double data2 = map2.get(i);
//            Double data3 = map3.get(i);
//            Double data4 = map4.get(i);
//            Double data5 = map5.get(i);
//            Double data6 = map6.get(i);
//
//            //你现在可以用这6个数据做你的操作了
//
//        }
//    }

    /**
     * 初始化数据
     */
    public static Integer initData(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        int line = 0;
        try {
            System.out.println("开始将txt文件中数据读取到map中：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;

            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                //System.out.println("line " + line + ": " + tempString);

                String[] array = tempString.split("\\t");

                map1.put(line, Double.valueOf(array[0]));
                map2.put(line, Double.valueOf(array[1]));
                map3.put(line, Double.valueOf(array[2]));
                map4.put(line, Double.valueOf(array[3]));
                map5.put(line, Double.valueOf(array[4]));
                map6.put(line, Double.valueOf(array[5]));

                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        System.out.println("读取完毕！一共读取了：" + line + "条数据");

        return line;
    }


}
