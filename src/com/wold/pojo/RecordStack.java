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
	
	 //��ջ
    public void push(T t) {
        ll.addFirst(t);
    }
 
    //��ջ
    public T pop() {
        return ll.removeFirst();
    }
 
    //ջ��Ԫ��
    public T peek() {
        T t = null;
        //ֱ��ȡԪ�ػᱨ�쳣����Ҫ���ж��Ƿ�Ϊ��
        if (!ll.isEmpty())
            t = ll.getFirst();
        return t;
    }
 
    //ջ��
    public boolean isEmpty() {
        return ll.isEmpty();
    }
    
    //���ջ
    public void clear() {
    	ll.clear();
    }
    //��ȡջ��С
    public int getSize() {
    	return ll.size();
    }
}
