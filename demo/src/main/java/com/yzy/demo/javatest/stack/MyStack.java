package com.yzy.demo.javatest.stack;

import com.yzy.demo.javatest.link.LNode;
/**
 * 链表的方式设计 栈的实现，先进后出、后进先出，
 * */
public class MyStack<T> {
    //头部结点
    private LNode<T> head;
    public MyStack(){
        //头部结点初始化
        head = new LNode<>();
        head.data = null;
        head.next = null;
    }
    /**
     * 判断栈是否为空
     * */
    public boolean empty(){
        return head.next == null;
    }
    /**
     * 查看栈中的元素数量
     * */
    public int size(){
        int i = 0;
        LNode<T> p = head.next;
        while (p != null){
            p  = p.next;
            i++;
        }
        return i;
    }
    /**
     * 入栈 链表新增 (引用对象的方式来建立联系)
     * */
    public void push(T t){
        //栈实现
        LNode<T> p = new LNode();
        p.data = t;
        //链表 逆序设计
        p.next = head.next;
        head.next = p;
    }
    /**
     * 出栈， 链表删除头部结点+1位置结点
     * 链表删除原理:前驱结点.next = 后继结点
     * */
    public T pop(){
        LNode<T> p = head.next;
        if (p!=null){
            head.next = p.next;
            System.out.println("出栈:"+p.data);
            return p.data;
        }
        System.out.println("栈已经空了");
        return null;
    }

    /**
     * 不出栈，取得栈顶元素
     * */
    public T top(){
        if (head.next!= null){
            return head.next.data;
        }
        System.out.println("栈已经空了");
        return null;
    }

    /**
     * 测试
     * */
    public static void main(String[] args) {
        MyStack<Integer> myStack = new MyStack<>();
        boolean flag = true;
        //入栈
        System.out.println("开始入栈==============>>>>");
        for (int i=0;i<=5;i++){
            //奇数入栈
            if (i%2!=0){
                System.out.println("入栈:"+i);
                myStack.push(i);
            }
        }
        //获取栈内总数
        System.out.println("获取栈内总数:"+ myStack.size());
        //判断栈是否为空
        System.out.println("判断栈是否为空:"+ myStack.empty());
        //查询栈顶数据
        System.out.println("查询栈顶数据:"+myStack.top());
        //出栈
        while (flag){
            boolean index = myStack.pop() == null?true:false;
            if (index){
                flag = false;
            }
        }
        //获取栈内总数
        System.out.println("获取栈内总数:"+ myStack.size());
        //判断栈是否为空
        System.out.println("判断栈是否为空:"+ myStack.empty());
    }
}
