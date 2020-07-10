package com.wold.pojo;

import java.util.LinkedList;
/**
 * �����¼
 * @author WOLD
 *
 * @param <T>
 */
public class RecordStack <T>{
	private LinkedList<T> ll=new LinkedList<>();
	
	 //入栈
    public void push(T t) {
        ll.addFirst(t);
    }
 
    //出栈
    public T pop() {
        return ll.removeFirst();
    }
 
    //栈顶元素
    public T peek() {
        T t = null;
        //ֱ判断非空
        if (!ll.isEmpty())
            t = ll.getFirst();
        return t;
    }
 
    //栈空
    public boolean isEmpty() {
        return ll.isEmpty();
    }
    
    //清楚栈
    public void clear() {
    	ll.clear();
    }
    //获取栈大小
    public int getSize() {
    	return ll.size();
    }
}
