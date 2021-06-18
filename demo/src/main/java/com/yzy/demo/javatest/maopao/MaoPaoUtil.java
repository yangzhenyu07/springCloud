package com.yzy.demo.javatest.maopao;
/**
 * 冒泡算法
 * @author yangzhenyu
 * */
public class MaoPaoUtil {
    //冒泡算法优化1 外层循环优化
    //原理:如果用一个flag来判断一下，当前数组是否已经有序，如果有序就退出循环，这样可以明显的提高冒泡排序的性能。
    public static void maoPao(int []str) {
        for (int j = 1; j < str.length; j++) {
            // 设定一个标记，若为true，则表示此次循环没有进行交换，也就是待排序列已经有序，排序已经完成。
            boolean flag = true;
            for (int i = 0; i < str.length-j; i++) {
                if (str[i]>str[i+1]) {
                    int tem = str[i];
                    str[i] = str[i+1];
                    str[i+1]=tem;
                    flag = false;
                }
            }
            if(flag) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        int []str = {789,2,435,45436,575,123,32,45546,32};
        //冒泡算法优化1
        MaoPaoUtil.maoPao(str);
        for (int i = 0; i < str.length; i++) {
            System.out.println(str[i]+",");
        }
        str = null;
    }

}
