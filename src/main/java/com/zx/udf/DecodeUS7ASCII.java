package com.zx.udf;

import org.apache.flink.table.functions.ScalarFunction;

import java.nio.charset.Charset;
import java.util.stream.IntStream;

/**
 * @ClassName DecodeUS7ASCII
 * @Description Flink自定义函数解码US7ASCII
 * @Author zhaoxudong
 * @Date 2023/3/15
 **/
public class DecodeUS7ASCII extends ScalarFunction {

    public static String eval(String str){
        try{
            char[] inputCharArray = str.toCharArray();
            byte[] inputBytes = new byte[inputCharArray.length];
            IntStream.range(0, inputCharArray.length).forEach(i -> {
                inputBytes[i] = (byte) inputCharArray[i];
            });

            return new String(inputBytes, Charset.forName("GB18030"));
        } catch (Exception e){
            return " ";
        }
    }
}
