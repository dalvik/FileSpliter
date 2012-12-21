package com.drovik.sky.ui;

import java.io.*;   
import java.util.*;   
public class FengGeHeBing {   
    static int nM=1024*1024;   
    static public void fenGe(File fenGeFile,File cunMuLu,int daXiao){   
        FileInputStream fis=null;   
        try{   
            if(!cunMuLu.isDirectory()){   
                cunMuLu.mkdirs();   
            }   
            nM=nM*daXiao;   
            int length=(int)fenGeFile.length();   
            int num=length/nM;   
            int yu=length%nM;   
            System.out.println("feGenFile.length:"+length);   
            fis=new FileInputStream(fenGeFile);   
            //byte[] yByte=new byte[length];   
            //fis.read(yByte);   
            int wenJianShu=0;   
            File[] fl=new File[num+1];   
            int begin=0;   
            for(int i=0;i<num;i++){   
                fl[i]=new File(cunMuLu.getAbsolutePath()+"\\"+(i+1)+".tem");   
                if(!fl[i].isFile()){   
                    fl[i].createNewFile();   
                }   
                FileOutputStream fos=new FileOutputStream(fl[i]);   
                byte[] bl=new byte[nM];   
                fis.read(bl);   
                //fos.write(yByte,begin,daXiao*1024*1024);   
                fos.write(bl);   
                begin=begin+daXiao*1024*1024;   
                fos.close();   
                //System.out.println(fl[i].getName()+"  length"+fl[i].length());   
            }   
            if(yu!=0){   
                fl[num]=new File(cunMuLu.getAbsolutePath()+"\\"+(num+1)+".tem");   
                if(!fl[num].isFile()){   
                    fl[num].createNewFile();   
                }   
                FileOutputStream fyu=new FileOutputStream(fl[num]);   
                byte[] bl=new byte[yu];   
                fis.read(bl);   
                //fyu.write(yByte,length-yu,yu);   
                fyu.write(bl);   
                fyu.close();   
                //System.out.println(fl[num].getName()+"  length"+fl[num].length());   
            }   
        }catch(Exception e){   
            e.printStackTrace();   
        }  finally{   
            //fis.close();   
        }   
    }   
    public static void heBing(File[] f,File cunDir,String hz) {   
        try {   
            //File[] fl = f.listFiles();   
            File heBingFile = new File(cunDir.getAbsoluteFile()+"\\heBing"+hz);   
            if (!heBingFile.isFile()) {   
                heBingFile.createNewFile();   
            }   
            FileOutputStream fos = new FileOutputStream(heBingFile);   
            for (int i = 0; i < f.length; i++) {   
                FileInputStream fis = new FileInputStream(f[i]);   
                int len = (int) f[i].length();   
                byte[] bRead = new byte[len];   
                fis.read(bRead);   
                fos.write(bRead);   
                fis.close();   
            }   
            fos.close();   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
    }   
    public static void main(String[] args){   
        File fenGeFile=new File("d:\\¡¶½ðÓ¹È«¼¯¡·µä²Ø°æv2.1.exe");   
        File ccMuLu=new File("d:\\fenGe\\");   
        int daXiao=3;   
        //fenGe(fenGeFile,ccMuLu,daXiao);   
        File heBingFile=new File("d:\\fenGe\\");   
        //heBing(heBingFile);   
    }   
}  