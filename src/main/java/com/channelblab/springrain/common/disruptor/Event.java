package com.channelblab.springrain.common.disruptor;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：dengyi(A.K.A Bear)
 * @date ：Created in 2023/6/20 15:14
 * @description：
 * @modified By：
 */
@Data
public class Event {

    private EventType eventType;

    private String message;

    private Object params;

    public static void main(String[] args) {

//        Integer aa=0;
//        Double bb=0.00;
//        System.out.println(aa.getClass() == bb.getClass());


        List<Integer> l1 = new ArrayList<>();
        List<Double> l2 = new ArrayList<>();
        System.out.println("args = " );
        Class<? extends List> aClass = l1.getClass();
        Class<? extends List> bClass = l1.getClass();
        System.out.println( l1.getClass()==l2.getClass());

        System.out.println(l1 instanceof List);




    }

}
