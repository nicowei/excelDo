package main.java;
//import java.util.*;
import java.lang.System;
public class testMD5 {
	public static void main(String[] args) {
		System.out.println(MD5.stringMd5(""));
		String y="111";
		System.out.println(y);
		String[] xx=y.split(".");
		
		System.out.println(y.substring(0,y.indexOf(".")>0?y.indexOf("."):y.length()));
		for(int i=0;i<xx.length;i++){
			
			System.out.println(xx[i]);
		}
	}

}
