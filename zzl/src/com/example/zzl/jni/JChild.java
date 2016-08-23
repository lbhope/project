package com.example.zzl.jni;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class JChild extends JParent {

	public native String getNativeString();

	public String key = "jason";

	public Animal child = new Cat();

	public static int count = 9;

	public native static String getStringFromC();

	public native String getString2FromC(int i);

	public native String accessField();

	public native void accessStaticField();

	public native void accessMethod();

	public native void accessStaticMethod();

	public native Date accessConstructor();

	public native void accessNonvirtualMethod();

	public native String chineseChars(String in);

	public native void giveArray(int[] array);

	public native int[] getArray(int len);

	public native void localRef();

	public native void createGlobalRef();

	public native String getGlobalRef();

	public native void deleteGlobalRef();

	public native void exeception();

	public native void cached();

	public native static void initIds();

	public void print(JChild t) {
		String text = getStringFromC();
		System.out.println(text);

		text = t.getString2FromC(6);
		System.out.println(text);

		System.out.println("key修改前：" + t.key);
		t.accessField();
		System.out.println("key修改后：" + t.key);

		System.out.println("count修改前：" + count);
		t.accessStaticField();
		System.out.println("count修改后：" + count);

		t.accessMethod();
		t.accessStaticMethod();

		t.accessConstructor();

		t.accessNonvirtualMethod();

		System.out.println(t.chineseChars("NDK编程"));

		int[] array = { 9, 100, 10, 37, 5, 10 };
		// 排序
		t.giveArray(array);
		for (int i : array) {
			System.out.println(i);
		}

		// ----------
		int[] array2 = t.getArray(10);
		System.out.println("------------");
		for (int i : array2) {
			System.out.println(i);
		}

		System.out.println("------08-17------");
		t.createGlobalRef();
		System.out.println(t.getGlobalRef());
		// 用完之后释放
		t.deleteGlobalRef();
		System.out.println("释放完了...");
		// System.out.println(t.getGlobalRef());

		try {
			t.exeception();
		} catch (Exception e) {
			System.out.println("发生异常：" + e.getMessage());
		}

		System.out.println("--------异常发生之后-------");

		try {
			t.exeception();
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println(e.getMessage());
		}

		// 不断调用cached方法
		for (int i = 0; i < 100; i++) {
			t.cached();
		}
	}

	// 产生指定范围的随机数
	public int genRandomInt(int max) {
		System.out.println("genRandomInt 执行了...");
		return new Random().nextInt(max);
	}

	// 产生UUID字符串
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	@Override
	public void sayHi() {
		System.out.println("method sayHi in JChild ....");
	}
}
