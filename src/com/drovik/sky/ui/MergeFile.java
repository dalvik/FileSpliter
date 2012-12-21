package com.drovik.sky.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.drovik.sky.ui.SplitFile.OnChangeListener;

public class MergeFile implements Runnable {

	private String srcPath;
	
	private String desPath;
	
	private OnChangeListener changeListener;
	
	private	long mergeFileLength = 0;
	
	private Object lock = new Object();
	
	public MergeFile(String srcPath, String desPath) {
		this.srcPath = srcPath;
		this.desPath = desPath;
	}
	
	@Override
	public void run() {
		File srcPathDir = new File(srcPath);
		if(!srcPathDir.exists() && srcPathDir.isFile()) {
			return;
		}
		System.out.println("srcPath = " + srcPath +  " " );
		File[] files = srcPathDir.listFiles();
		List<File> fileList = new ArrayList<File>();
		for(File f: files) {
			if(f.isFile()) {
				fileList.add(f);
			}
		}
		Collections.sort(fileList);
		long totalFileLength = 0;
		int skipBytes = 0;
		for(File f: fileList) {
			totalFileLength += f.length();
			RandomAccessFile accessFile;
			try {
				accessFile = new RandomAccessFile(desPath + File.separator + f.getName().substring(f.getName().indexOf("_") +1),"rw");
				new Thread(new MergeFileWork(f, accessFile, skipBytes)).start();
				skipBytes += (int)f.length();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(changeListener!= null) {
			changeListener.initTotalSize(totalFileLength);
		}
		while(true) {
			if(mergeFileLength>=totalFileLength) {
				if(changeListener!= null) {
					changeListener.onComplete();
				}
				System.out.println("break");
				break;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setOnStateChangeListener(OnChangeListener changeListener) {
		this.changeListener = changeListener;
	}
	
	private class MergeFileWork implements Runnable {
		
		private RandomAccessFile accessFile;
		
		private int startPosition;
		
		private File srcFile;
		
		
		public MergeFileWork(File srcFile, RandomAccessFile accessFile, int startPosition) {
			this.accessFile = accessFile;
			this.startPosition = startPosition;
			this.srcFile = srcFile;
			//System.out.println(srcFile.getName() + "  " + srcFile.length() + "  startPosition=" + startPosition);
		}
		
		
		@Override
		public void run() {
			FileInputStream fis = null;
			int l = 500 * 1024;
			byte[] buf = new byte[l];
			int readBytes = 0;
			int writeBytes = 0;
			try {
				accessFile.seek(startPosition);
				fis = new FileInputStream(srcFile);
				while((readBytes = (fis.read(buf, 0, l)))!=-1) {
					accessFile.write(buf, 0, readBytes);
					writeBytes +=readBytes;
					synchronized (lock) {
						mergeFileLength += readBytes;
						if(changeListener!= null) {
							changeListener.updateProgress(mergeFileLength);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				if(fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(accessFile != null) {
					try {
						accessFile.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				System.out.println("startPosition = " + startPosition  + " exit. writeBytes = " + writeBytes);
			}
		}
	}
	
}
