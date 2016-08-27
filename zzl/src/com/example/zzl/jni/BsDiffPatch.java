package com.example.zzl.jni;

/**
 * 增量更新
 * 
 * @author libin
 * 
 */
public class BsDiffPatch {
	/**
	 * 合并
	 * 
	 * @param oldfile
	 *            旧文件
	 * @param newfile
	 *            新文件
	 * @param patchfile
	 *            差分文件
	 */
	public native static void diff(String oldfile, String newfile,
			String patchfile);

	public native static void patch(String oldfile, String newfile,
			String patchfile);
}
