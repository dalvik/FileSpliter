package com.drovik.sky.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class SplitFile implements Runnable {

	private OnChangeListener changeListener;
	
	private String splitFileName;
	
	private String saveFilePath;
	
	private int singleFileLength;
	
	private long totalSpliteSize = 0;
	
	private Object lock = new Object();
	
	public SplitFile(String splitFileName, String saveFilePath, int singleFileLength){
		this.splitFileName = splitFileName;
		this.saveFilePath = saveFilePath;
		this.singleFileLength = singleFileLength;
	}
	
	
	@Override
	public void run() {
		File splteFile;
		long srcFileLength = 0;
		splteFile = new File(splitFileName);
		srcFileLength = splteFile.length();
		int threadNum = (int) (srcFileLength / singleFileLength);
		if(srcFileLength%singleFileLength !=0) {
			threadNum += 1;
		}
		System.out.println("### thread numbers = " + threadNum);
		File savePath = new File(saveFilePath); 
		if(!savePath.exists()) {
			savePath.mkdirs();
		}
		if(changeListener!= null) {
			changeListener.initTotalSize(srcFileLength);
		}
		for(long i=0;i<threadNum;i++) {
			long position = 0l;
			try {
				position = i*singleFileLength;
				new Thread(new SplitWorkThread(new RandomAccessFile(splitFileName, "r"), position, singleFileLength, savePath + File.separator + i + "_" + splitFileName.substring(splitFileName.lastIndexOf(File.separator) + 1))).start();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		while(true) {
			if(totalSpliteSize >= srcFileLength) {
				System.out.println("### totalSpliteSize = " + totalSpliteSize + " srcFileLength = "+ srcFileLength);
				if(changeListener!= null) {
					changeListener.onComplete();
				}
				break;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private class SplitWorkThread implements Runnable {
		
		private RandomAccessFile accessFile;
		
		private long startPosition;
		
		private int blockDataLength;
		
		private String desName;
		
		public SplitWorkThread(RandomAccessFile accessFile, long startPosition, int blockDataLength, String desName) {
			this.accessFile = accessFile;
			this.startPosition = startPosition;
			this.blockDataLength = blockDataLength;
			this.desName = desName;
		}
		
		@Override
		public void run() {
			FileOutputStream fos = null;
			int l = 1024 * 50;
			byte[] tmp = new byte[l];
			try {
				accessFile.seek(startPosition);
				fos = new FileOutputStream(desName, false);
				int readLeng = 0;
				while((readLeng = accessFile.read(tmp, 0, l)) != -1 && blockDataLength>0) {
					fos.write(tmp, 0, readLeng);
					fos.flush();
					blockDataLength -= readLeng;
					synchronized (lock) {
						totalSpliteSize += readLeng;
						if(changeListener!= null) {
							changeListener.updateProgress(totalSpliteSize);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public void setOnStateChangeListener(OnChangeListener changeListener) {
		this.changeListener = changeListener;
	}
	
	public interface OnChangeListener {
		public void initTotalSize(long total);
		public void updateProgress(long value);
		public void onComplete();
	}
}
