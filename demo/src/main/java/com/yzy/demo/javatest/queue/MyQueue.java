package com.yzy.demo.javatest.queue;

import com.yzy.demo.javatest.link.LNode;
/**
 * 链表的方式设计队列的实现
 * 先进先出、后进后出
 * */
public class MyQueue<T> {
    //头部结点
    LNode<T> head = new LNode();
    //当前结点
    LNode<T> cur = new LNode();
    //队尾结点
    LNode<T> end = new LNode<>();
    public MyQueue(){
        //头部结点初始化
        cur = head;
    }
    /**
     * 判断栈是否为空
     * */
    public boolean empty(){
        return head.next == null;
    }
    /**
     * 查看队列中的元素数量
     * */
    public int size(){
        int i = 0;
        LNode<T> tmp = head.next;
        while (tmp != null){
            tmp  = tmp.next;
            i++;
        }
        return i;
    }
    /**
     * 入队列 链表新增 (引用对象的方式来建立联系)
     * */
    public void push(T t){
        //栈实现
        LNode<T> tmp = new LNode();
        tmp.data = t;
        //链表 引用设计
        cur.next = tmp;
        cur = tmp;
        end.data = t;
    }
    /**
     * 出队列， 链表删除头部结点+1位置结点
     * 链表删除原理:前驱结点.next = 后继结点
     * */
    public T pop(){
        LNode<T> tmp = head.next;
        if (tmp!=null){
            head.next = tmp.next;
            System.out.println("出队列:"+tmp.data);
            return tmp.data;
        }
        System.out.println("队列已经空了");
        return null;
    }

    /**
     * 不出队列，取得队头元素
     * */
    public T top(){
        if (head.next!= null){
            return head.next.data;
        }
        System.out.println("队列已经空了");
        return null;
    }

    /**
     * 不出队列，取得队尾元素
     * */
    public T end(){
        if (end.data!= null){
            return end.data;
        }
        System.out.println("队列已经空了");
        return null;
    }

    /**
     * 测试
     * */
    public static void main(String[] args) {
        MyQueue<Integer> myQueue = new MyQueue<>();
        boolean flag = true;
        //入队列
        System.out.println("开始入队列==============>>>>");
        for (int i=0;i<=10;i++){
            //偶数入队列
            if (i%2==0){
                System.out.println("入队列:"+i);
                myQueue.push(i);
            }
        }
        //获取队列内总数
        System.out.println("获取队列内总数:"+ myQueue.size());
        //判断队列是否为空
        System.out.println("判断队列是否为空:"+ myQueue.empty());
        //查询队头数据
        System.out.println("查询队头数据:"+myQueue.top());
        //查询队尾数据
        System.out.println("查询队尾数据:"+myQueue.end());
        //出队列
        while (flag){
            boolean index = myQueue.pop() == null?true:false;
            if (index){
                flag = false;
            }
        }
        //获取队列内总数
        System.out.println("获取队列内总数:"+ myQueue.size());
        //判断队列是否为空
        System.out.println("判断队列是否为空:"+ myQueue.empty());
    }

}
