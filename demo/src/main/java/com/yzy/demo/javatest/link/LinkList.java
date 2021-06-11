package com.yzy.demo.javatest.link;
/**
 * 单链表生成
 * */
public class LinkList<T> {
    //头部结点
    LNode head = new LNode();
    //当前结点
    LNode cur = head;
    /**
     * 链表新增
     * */
    public  <T>void push(T msg){
        LNode tmp = new LNode();
        tmp.data = msg;
        cur.next = tmp;
        //通过引用来实现结点递增
        cur = tmp;
    }
    /**
     * 链表删除
     * 原理 前驱结点.next = 后继结点
     * */
    public <T>void delete(T msg){
        //前驱结点
        LNode innerCur = new LNode();
        //后继结点
        LNode next = new LNode();
        for (cur = head.next;cur!=null;cur = cur.next){
            if (msg instanceof Integer){
                if (msg == cur.data){
                    next = cur.next;
                    innerCur.next = next;
                }
            }else {
                System.out.println("不支持这个种类删除");
            }
            innerCur = cur;
        }
    }
    public static void main(String[] args) {
        LinkList<Integer> linkList = new LinkList<>();
        for (int i=0;i<5;i++){
            linkList.push(i);
        }
        for (linkList.cur =linkList. head.next;linkList.cur!=null;linkList.cur = linkList.cur.next){
            System.out.println("删除结点前:"+linkList.cur.data);
        }
        System.out.println("=========================================");

        //删除结点
        linkList.delete(3);
        for (linkList.cur =linkList. head.next;linkList.cur!=null;linkList.cur = linkList.cur.next){
            System.out.println("删除结点后:"+linkList.cur.data);
        }
    }
}
