package com.yzy.demo.javatest.tree;

import com.alibaba.fastjson.JSON;
import com.yzy.demo.javatest.maopao.MaoPaoUtil;

import java.util.Random;

/**
 * 将整数数组放到二叉树中
 * @author yangzhenyu
 * */
public class TreeCreate {

    /**
     * 获取一个区间的随机数
     *
     * @param min 最小
     * @param max 最大
     * @return 返回一个随机数
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
            data[i] = nextInt(0,1000);
        }
        return data;
    }
    /**
     * @param arr 有序数组
     * @param start 第一个索引位置
     * @param end 最后一个索引位置
     * 实现思路:取数组中间元素作为根节点，将数组分成左右两部分，对数据的两部分分别使用
     * 递归的方式分别构建左右子树
     * */
    public static BitNode arrayToTree(int [] arr,int start,int end){
        BitNode bitNode = null;
        if (end>=start){
            bitNode = new BitNode();
            int mid  = (start+end+1)/2;
            //树的根节点为中间的元素
            bitNode.data = arr[mid];
            //递归左部分数组构建左子树
            bitNode.lChild = arrayToTree(arr,start,mid-1);
            //递归右部分数组构建右子树
            bitNode.rChild = arrayToTree(arr,mid+1,end);
        }else {
            bitNode = null;
        }
        return bitNode;
    }

    /**
     * 打印顺序: 左孩子末尾左结点右结点-> 终端结点 -> 右孩子末尾左结点右结点
     * */
    public static  void printTree(BitNode root){
        if (root == null){
            return;
        }
        //遍历左结点
        if (root.lChild != null){
            printTree(root.lChild);
        }

        System.out.printf("结点值:%s\n",root.data);
        //遍历右结点
        if (root.rChild != null){
            printTree(root.rChild);
        }

    }
    public static void main(String[] args) {
        //01 创建测试数据
        int[] testData = createTestData(17);
        //02 将无序数组转成有序数组
        MaoPaoUtil.maoPao(testData);
        //查看数组是否有序
        String data = JSON.toJSONString(testData);
        System.out.println("有序数组:"+data);
        //03 把有序数组放到二叉树中
        BitNode bitNode = arrayToTree(testData, 0, testData.length-1);
        //04 遍历二叉树
        printTree(bitNode);
    }
}
