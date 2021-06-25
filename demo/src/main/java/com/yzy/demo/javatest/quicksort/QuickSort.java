package com.yzy.demo.javatest.quicksort;

import com.alibaba.fastjson.JSON;

import java.util.Random;


/**
 * 快速排序-三路快排
 * @author yangzhenyu
 * */
class QuickData{
    //<基准值所处位置索引
    private int lt;
    //>基准值所处位置索引
    private int rt;

    public QuickData(int lt, int rt) {
        this.lt = lt;
        this.rt = rt;
    }
    public int getLt() {
        return lt;
    }
    public int getRt() {
        return rt;
    }

}
public class QuickSort {

    /**
     * 获取一个区间的随机数
     *
     * @param min 最小
     * @param max 最大
     * @return 返回一个随机数
     * @author yangzhenyu
     */
    public static int nextInt(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }
    /**
     * 创建测试数据
     * */
    public static int [] createTestData(int num){
        int [] data = new int[num];
        for (int i = 0;i<num;i++){
            data[i] = nextInt(0,10000);
        }
        return data;
    }
    /**
     * 分区
     * @param arr 被排序的数组
     * @param l 起始位置
     * @param r 末尾位置
     * @return QuickData 分区索引位置
     * @author yangzhenyu
     * */
    public static  QuickData partition(int [] arr, int l, int r){
        // 基准值为数组的零号元素
        int p = arr[l];
        // 左区间的初始值: L
        int lt = l;
        // 右区间的初始值: R+1
        int gt = r + 1;
        for (int index= l + 1; index < gt;){
            if(arr[index] == p){
                // 当前index指向的元素等于p
                index++;
            } else if(arr[index] > p){
                // 当前index指向的元素大于p，将gt-1处的元素与当前索引处的元素交换位置，gt--
                int tem = arr[index];
                arr[index] = arr[gt-1];
                arr[gt-1] = tem;
                gt--;
            }else{
                // 当前i指向的元素小于p，将lt+1处的元素与当前索引处的元素交换位置，lt+1，i+1
                int tem = arr[index];
                arr[index] = arr[lt+1];
                arr[lt+1] = tem;
                lt++;
                index++;
            }
        }
        // i走向gt处，除了基准值外的元素，其余的空间已经分区完毕，交换基准值与lt处的元素，lt-1，最终得到我们需要的三个区间
        int tem = arr[l];
        arr[l] = arr[lt];
        arr[lt] = tem;
        lt--;
        System.out.println(String.format("三路快排后的数组:%s,lt:【%d】,rt:【%d】",JSON.toJSONString(arr),lt,gt));
        return new QuickData(lt,gt);
    }

    /**
     * 快速排序-三路快排
     * @param arr 被排序的数组
     * @param l 起始位置
     * @param r 末尾位置
     * @author yangzhenyu
     * */
    public static void quickSort(int[] arr, int l, int r){
        if(l >= r){
            return;
        }
        QuickData obj = partition(arr, l, r);
        // 递归执行: 将没有大于p,和小于p区间的元素在进行三路快排
        quickSort(arr,l,obj.getLt());
        quickSort(arr,obj.getRt(),r);
    }
    public static void main(String[] args) {
        //01 创建测试数据
        int[] testData = createTestData(17);
        System.out.printf("快速排序前的数组:%s\n",JSON.toJSONString(testData));
        //02 快速排序-三路快排
        quickSort(testData,0,testData.length-1);
        //03 快速排序后的结果
        System.out.printf("快速排序后的数组:%s\n",JSON.toJSONString(testData));
    }
}
