package com.drovik.sky.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


import com.drovik.sky.ui.SplitFile.OnChangeListener;
public class Menu {   
    static File cunDir=new File("f:\\tmp");   
    static File[] fl;   
    static File[] chuanFile;   
    //static File yFile;   
    //static File fMuLu;   
    static  File splitFile;   
    static  File cunMuLu;   
    private JFrame jFrame = null;  //  @jve:decl-index=0:visual-constraint="64,31"   
    private JTabbedPane jTabbedPane = null;   
    private JPanel jPanel = null;   
    private JPanel jPanel1 = null;   
    private JLabel jLabel = null;   
    private JTextField splitFilePathTextField = null;   
    private JButton jButton = null;   
    private JLabel jLabel1 = null;   
    private JTextField splitSaveDir = null;   
    private JButton jButton1 = null;   
    private JLabel jLabel2 = null;   
    private JTextField splitSingleFileSize = null;   
    private JLabel jLabel3 = null;   
    private JButton jButton2 = null;   
    private JButton jButton3 = null;   
    private JLabel splitLable = null;
    private JLabel mergeLable = null;
    private JButton jButton4 = null;   
    private JButton jButton5 = null;   
    private JFileChooser jSplitFileChooser = null;   
    private JFileChooser jFileChooser1 = null;   
    private JScrollPane jScrollPane = null;   
    private JLabel jLabel4 = null;   
    private JTextArea jTextArea = null;   
    private JButton jButton6 = null;   
    private JLabel jLabel41 = null;   
    private JComboBox jComboBox = null;   
    private JTextField jTextField3 = null;   
    private JButton jButton7 = null;
    
    private long totalLength = 0;
    
    private long startTime = 0;
    
    private boolean isMerge = true;
    
    private DecimalFormat df = new DecimalFormat("#");
    
    private OnChangeListener changeListener = new OnChangeListener() {
    	
    	@Override
    	public void initTotalSize(long total) {
    		totalLength = total;
    		//System.out.println("totalLength = " + totalLength);
    	}
		
		@Override
		public void updateProgress(long value) {
			if(totalLength>0) {
				if(isMerge) {
					mergeLable.setText("完成 " + getPercent(value*100) +  " %");
				}else {
					splitLable.setText("完成 " + getPercent(value*100) +  " %");
				}
			}
		}
		
		@Override
		public void onComplete() {
			String useTime = (System.currentTimeMillis() - startTime)/1000 + " s";
			if(isMerge) {
				mergeLable.setText("合并文件：" +formetFileSize(totalLength) + " use : " + useTime);
			} else {
				splitLable.setText("分割文件：" +formetFileSize(totalLength) + " use : " + useTime);
			}
			System.out.println("over! use : " + useTime);
		}
	};
    
    /**  
     * This method initializes jFrame     
     * @return javax.swing.JFrame     
     */  
    private JFrame getJFrame() {   
        if (jFrame == null) {   
            jFrame = new JFrame("分割合并工具");   
            jFrame.setSize(new Dimension(360, 300));   
            jFrame.setContentPane(getJTabbedPane());
            //jFrame.setIconImage(image)
            //String plafName="com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
            try {
				//UIManager.setLookAndFeel(plafName);
				//SwingUtilities.updateComponentTreeUI(jFrame);
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());   
	            SwingUtilities.updateComponentTreeUI(jFrame);   
			} catch (Exception e) {
				e.printStackTrace();
			}  
            jFrame.setVisible(true);   
            jFrame.setResizable(false);
            int windowWidth = jFrame.getWidth();                    //获得窗口宽
            int windowHeight = jFrame.getHeight();                  //获得窗口高
            Toolkit kit = Toolkit.getDefaultToolkit();             //定义工具包
            Dimension screenSize = kit.getScreenSize();            //获取屏幕的尺寸
            int screenWidth = screenSize.width;                    //获取屏幕的宽
            int screenHeight = screenSize.height;                  //获取屏幕的高
            jFrame.setLocation(screenWidth/2-windowWidth/2, screenHeight/2-windowHeight/2);//设置窗口居中显示
            jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);   
        }   
        return jFrame;   
    }   
  
    /**  
     * This method initializes jTabbedPane    
     *    
     * @return javax.swing.JTabbedPane    
     */  
    private JTabbedPane getJTabbedPane() {   
        if (jTabbedPane == null) {   
            jTabbedPane = new JTabbedPane();   
            jTabbedPane.addTab("分割", getJPanel());   
            jTabbedPane.addTab("合并", getJPanel1());   
        }   
        return jTabbedPane;   
    }   
  
    /**  
     * This method initializes jPanel     
     *    
     * @return javax.swing.JPanel     
     */  
    private JPanel getJPanel() {   
        if (jPanel == null) {   
            jLabel3 = new JLabel();   
            jLabel3.setBounds(new Rectangle(283, 106, 51, 18));   
            jLabel3.setText("KB");   
            jLabel2 = new JLabel();   
            jLabel2.setBounds(new Rectangle(11, 103, 87, 28));   
            jLabel2.setText("分割大小");   
            jLabel1 = new JLabel();   
            jLabel1.setBounds(new Rectangle(11, 60, 83, 28));   
            jLabel1.setText("存储目录");   
            GridBagConstraints gridBagConstraints = new GridBagConstraints();   
            gridBagConstraints.gridx = 0;   
            gridBagConstraints.gridy = 0;   
            jLabel = new JLabel();   
            jLabel.setText("源文件");   
            jLabel.setBounds(new Rectangle(11, 16, 76, 33));   
            jPanel = new JPanel();   
            jPanel.setLayout(null);   
            jPanel.add(jLabel, gridBagConstraints);   
            jPanel.add(getJTextField(), null);   
            jPanel.add(getJButton(), null);   
            jPanel.add(jLabel1, null);   
            jPanel.add(getJTextField1(), null);   
            jPanel.add(getJButton1(), null);   
            jPanel.add(getJFileChooser(), null);   
            jPanel.add(jLabel2, null);   
            jPanel.add(getJTextField2(), null);   
            jPanel.add(jLabel3, null);   
            jPanel.add(getJButton2(), null);   
            jPanel.add(getJButton3(), null); 
            jPanel.add(getJSpitLabel(), null);  
        }   
        return jPanel;   
    }   
  
    /**  
     * This method initializes jPanel1    
     *    
     * @return javax.swing.JPanel     
     */  
    private JPanel getJPanel1() {   
        if (jPanel1 == null) {   
            jLabel41 = new JLabel();   
            jLabel41.setBounds(new Rectangle(184, 3, 108, 22));   
            jLabel41.setText("合并后文件后缀：");   
            jPanel1 = new JPanel();   
            jPanel1.setLayout(null);   
            jPanel1.add(getJButton4(), null);   
            jPanel1.add(getJButton5(), null);   
            jPanel1.add(getJFileChooser1(), null);   
            jPanel1.add(getJScrollPane(), null);   
            jPanel1.add(getJButton6(), null);   
            //jPanel1.add(jLabel41, null);   
            //jPanel1.add(getJComboBox(), null);   
            jPanel1.add(getJTextField3(), null);   
            jPanel1.add(getJButton7(), null);   
            jPanel1.add(getJMergeLabel(), null);   
        }   
        return jPanel1;   
    }   
  
    /**  
     * This method initializes jTextField     
     *    
     * @return javax.swing.JTextField     
     */  
    private JTextField getJTextField() {   
        if (splitFilePathTextField == null) {   
            splitFilePathTextField = new JTextField();   
            splitFilePathTextField.setBounds(new Rectangle(105, 16, 165, 25));   
        }   
        return splitFilePathTextField;   
    }   
  
    /**  
     * This method initializes jButton    
     *    
     * @return javax.swing.JButton    
     */  
    private JButton getJButton() {   
        if (jButton == null) {   
            jButton = new JButton("浏览");   
            jButton.setBounds(new Rectangle(285, 16, 35, 30));   
            jButton.addActionListener(new java.awt.event.ActionListener() {   
                public void actionPerformed(java.awt.event.ActionEvent e) {   
                    jSplitFileChooser.setMultiSelectionEnabled(false);   
                    jSplitFileChooser.setFileSelectionMode(0);   
                    int state=jSplitFileChooser.showOpenDialog(null);   
                    if(state==0){   
                        splitFile=jSplitFileChooser.getSelectedFile();   
                        splitFilePathTextField.setText(splitFile.getAbsolutePath());   
                    }   
                }   
            });   
        }   
        return jButton;   
    }   
  
    /**  
     * This method initializes jTextField1    
     *    
     * @return javax.swing.JTextField     
     */  
    private JTextField getJTextField1() {   
        if (splitSaveDir == null) {   
            splitSaveDir = new JTextField();   
            splitSaveDir.setBounds(new Rectangle(105, 60, 165, 25));   
        }   
        return splitSaveDir;   
    }   
  
    /**  
     * This method initializes jButton1   
     *    
     * @return javax.swing.JButton    
     */  
    private JButton getJButton1() {   
        if (jButton1 == null) {   
            jButton1 = new JButton("浏览");   
            jButton1.setBounds(new Rectangle(285, 60, 35, 30));   
            jButton1.addActionListener(new java.awt.event.ActionListener() {   
                public void actionPerformed(java.awt.event.ActionEvent e) {   
                    jSplitFileChooser.setMultiSelectionEnabled(false);   
                    jSplitFileChooser.setFileSelectionMode(1);   
                    int state=jSplitFileChooser.showOpenDialog(null);   
                    if(state==0){   
                        cunMuLu=jSplitFileChooser.getSelectedFile();   
                        splitSaveDir.setText(cunMuLu.getAbsolutePath());   
                    }   
                }   
            });   
        }   
        return jButton1;   
    }   
  
    /**  
     * This method initializes jTextField2    
     *    
     * @return javax.swing.JTextField     
     */  
    private JTextField getJTextField2() {   
        if (splitSingleFileSize == null) {   
            splitSingleFileSize = new JTextField(5);   
            splitSingleFileSize.setBounds(new Rectangle(105, 104, 165, 25));   
        }   
        return splitSingleFileSize;   
    }   
  
    /**  
     * This method initializes jButton2   
     *    
     * @return javax.swing.JButton    
     */  
    private JButton getJButton2() {   
        if (jButton2 == null) {   
            jButton2 = new JButton("分割");   
            jButton2.setBounds(new Rectangle(11, 152, 135, 45));   
            jButton2.addActionListener(new java.awt.event.ActionListener() {   
                public void actionPerformed(java.awt.event.ActionEvent e) {   
                	if(splitFilePathTextField.getText().trim().length()<=0) {
                		System.out.println("spite file null");
                		return;
                	}
                	if(splitSaveDir.getText().trim().length()<=0) {
                		System.out.println("split save dir null");
                		return;
                	}
                	if(!splitSingleFileSize.getText().matches("\\d+")) {
                		System.out.println("not numberic");
                		return;
                	}
                    int a=0;   
                    try{   
                        a=Integer.parseInt(splitSingleFileSize.getText());
                        System.out.println("### single file length = " + a);
                        SplitFile splitFile = new SplitFile(splitFilePathTextField.getText().trim(), splitSaveDir.getText().trim(), a * 1024);
                        splitFile.setOnStateChangeListener(changeListener);
                    	startTime = System.currentTimeMillis();
                    	isMerge = false; 
                        new Thread(splitFile).start();
                    }catch(Exception ee){   
                        return;   
                    }   
                    //FengGeHeBing.fenGe(splitFile, cunMuLu, a);   
                }   
            });   
        }   
        return jButton2;   
    }   
  
    /**  
     * This method initializes jButton3   
     *    
     * @return javax.swing.JButton    
     */  
    private JButton getJButton3() {   
        if (jButton3 == null) {   
            jButton3 = new JButton("退出");   
            jButton3.setBounds(new Rectangle(191, 152, 135, 45));   
            jButton3.addActionListener(new java.awt.event.ActionListener() {   
                public void actionPerformed(java.awt.event.ActionEvent e) {   
                    System.exit(0);   
                }   
            });   
        }   
        return jButton3;   
    }   
    
    /**  
     * This method initializes jButton3   
     *    
     * @return javax.swing.JButton    
     */  
    private JLabel getJSpitLabel() {   
        if (splitLable == null) {   
        	splitLable = new JLabel();   
        	splitLable.setBackground(Color.blue);
        	splitLable.setBounds(new Rectangle(80, 192, 185, 45));   
        }   
        return splitLable;   
    }   
    
    private JLabel getJMergeLabel() {   
        if (mergeLable == null) {   
        	mergeLable = new JLabel("111111111111111111111111113");   
        	mergeLable.setBounds(new Rectangle(90, 192, 185, 45));   
        }   
        return mergeLable;   
    }   
  
    /**  
     * This method initializes jButton4   
     *    
     * @return javax.swing.JButton    
     */  
    private JButton getJButton4() {   
        if (jButton4 == null) {   
            jButton4 = new JButton("打开");   
            jButton4.setBounds(new Rectangle(8, 155, 85, 35));   
            jButton4.addActionListener(new java.awt.event.ActionListener() {   
                public void actionPerformed(java.awt.event.ActionEvent e) {   
                    jFileChooser1.setFileSelectionMode(1);   
                    //jFileChooser1.setMultiSelectionEnabled(true);   
                    int state=jFileChooser1.showOpenDialog(null);   
                    String s="";   
                    if(state==0){
                    	jTextArea.setText(jFileChooser1.getSelectedFile().getAbsolutePath());   
                    	//jTextArea.setText("合并目录 " + jFileChooser1.getSelectedFile().getAbsolutePath() + " 下的所有文件"); 
                        /*fl=jFileChooser1.getSelectedFiles();   
                        int[] st=new int[fl.length];   
                        for(int i=0;i<fl.length;i++){   
                            String name=fl[i].getName();   
                            int index=name.indexOf('.');   
                            st[i]=Integer.parseInt(name.substring(0,index));   
                            s=s+fl[i].getName()+"\r\n";   
                            jTextArea.setText(s);   
                        }   
                        Arrays.sort(st);   
                        chuanFile=new File[st.length];   
                        for(int i=0;i<st.length;i++){   
                            chuanFile[i]=new File(fl[i].getParent()+"\\"+(i+1)+".tem");   
                        }   */
                    }   
                }   
            });   
        }   
        return jButton4;   
    }   
  
    /**  
     * This method initializes jButton5   
     *    
     * @return javax.swing.JButton    
     */  
    private JButton getJButton5() {   
        if (jButton5 == null) {   
            jButton5 = new JButton("合并");   
            jButton5.setBounds(new Rectangle(113, 155, 85, 35));   
            jButton5.addActionListener(new java.awt.event.ActionListener() {   
                public void actionPerformed(java.awt.event.ActionEvent e) {   
                	if(jTextArea.getText().trim().length()<=0) {
                		return;
                	}
                    //FengGeHeBing.heBing(chuanFile,cunDir,jComboBox.getSelectedItem().toString());   
                	MergeFile mergeFile = new MergeFile(jTextArea.getText().trim(), cunDir.getAbsolutePath());
                	mergeFile.setOnStateChangeListener(changeListener);
                	startTime = System.currentTimeMillis();
                	isMerge = true;
                	try {
						Runtime.getRuntime().exec("explorer.exe http://www.drovik.com");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
                	//new Thread(mergeFile).start();
                    //jTextArea.setForeground(Color.red);   
                    //jTextArea.setText("合并已完成");   
                }   
            });   
        }   
        return jButton5;   
    }   
  
    /**  
     * @param args  
     */  
    public Menu(){   
        this.getJFrame();   
    }   
    /**  
     * This method initializes jFileChooser   
     *    
     * @return javax.swing.JFileChooser   
     */  
    private JFileChooser getJFileChooser() {   
        if (jSplitFileChooser == null) {   
            jSplitFileChooser = new JFileChooser();   
            jSplitFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);   
            //jFileChooser.setBounds(new Rectangle(5, 204, 500, 326));   
        }   
        return jSplitFileChooser;   
    }   
  
    /**  
     * This method initializes jFileChooser1      
     *    
     * @return javax.swing.JFileChooser   
     */  
    private JFileChooser getJFileChooser1() {   
        if (jFileChooser1 == null) {   
            jFileChooser1 = new JFileChooser();   
            jFileChooser1.setFileSelectionMode(0);   
            //jFileChooser1.setBounds(new Rectangle(5, 193, 500, 326));   
        }   
        return jFileChooser1;   
    }   
  
    /**  
     * This method initializes jScrollPane    
     *    
     * @return javax.swing.JScrollPane    
     */  
    private JScrollPane getJScrollPane() {   
        if (jScrollPane == null) {   
            jScrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);   
            jScrollPane.setBounds(new Rectangle(15, 34, 325, 105));   
            jScrollPane.setViewportView(getJTextArea());   
               
        }   
        return jScrollPane;   
    }   
  
    /**  
     * This method initializes jTextArea      
     *    
     * @return javax.swing.JTextArea      
     */  
    private JTextArea getJTextArea() {   
        if (jTextArea == null) {   
            jTextArea = new JTextArea();   
        }   
        return jTextArea;   
    }   
  
    /**  
     * This method initializes jButton6   
     *    
     * @return javax.swing.JButton    
     */  
    private JButton getJButton6() {   
        if (jButton6 == null) {   
            jButton6 = new JButton();   
            jButton6.setBounds(new Rectangle(227, 155, 85, 35));   
            jButton6.setText("退出");   
            jButton6.addActionListener(new java.awt.event.ActionListener() {   
                public void actionPerformed(java.awt.event.ActionEvent e) {   
                    System.exit(0);   
                }   
            });   
        }   
        return jButton6;   
    }   
  
    /**  
     * This method initializes jComboBox      
     * @return javax.swing.JComboBox      
     */  
    private JComboBox getJComboBox() {   
        if (jComboBox == null) {   
            jComboBox = new JComboBox();   
            jComboBox.setBounds(new Rectangle(276, 5, 69, 18));
            jComboBox.addItem(".rar");
            jComboBox.addItem(".exe");   
            jComboBox.addItem(".pdf");   
            jComboBox.addItem(".rm");   
            jComboBox.addItem(".rmvb");   
            jComboBox.addItem(".avi");   
            jComboBox.setEditable(true);   
        }   
        return jComboBox;   
    }   
  
    /**  
     * This method initializes jTextField3    
     *    
     * @return javax.swing.JTextField     
     */  
    private JTextField getJTextField3() {   
        if (jTextField3 == null) {   
            jTextField3 = new JTextField();   
            jTextField3.setText(cunDir.getAbsolutePath());   
            jTextField3.setBounds(new Rectangle(115, 5, 140, 22));   
        }   
        return jTextField3;   
    }   
  
    /**  
     * This method initializes jButton7   
     *    
     * @return javax.swing.JButton    
     */  
    private JButton getJButton7() {   
        if (jButton7 == null) {   
            jButton7 = new JButton();   
            jButton7.setBounds(new Rectangle(10, 6, 105, 18));   
            jButton7.setText("存放目录：");   
            jButton7.addActionListener(new java.awt.event.ActionListener() {   
                public void actionPerformed(java.awt.event.ActionEvent e) {   
                    jFileChooser1.setFileSelectionMode(1);   
                    jFileChooser1.setMultiSelectionEnabled(false);   
                    int state=jFileChooser1.showOpenDialog(null);   
                    if(state==0){   
                        cunDir=jFileChooser1.getSelectedFile();   
                        jTextField3.setText(cunDir.getAbsolutePath());   
                    }   
                }   
            });   
        }   
        return jButton7;   
    }   
  
    private String getPercent(long value) {
		return df.format((double)value/totalLength);
    }
    private static StringBuilder sFormatBuilder = new StringBuilder();
    private static Formatter sFormatter = new Formatter(sFormatBuilder, Locale.getDefault());
    private static final Object[] sTimeArgs = new Object[5];
    
    public static String makeTimeString(long secs) {
        String durationformat =  secs < 3600 ? "%2$d:%5$02d" : "%1$d:%3$02d:%5$02d";
        sFormatBuilder.setLength(0);
        final Object[] timeArgs = sTimeArgs;
        timeArgs[0] = secs / 3600;
        timeArgs[1] = secs / 60;
        timeArgs[2] = (secs / 60) % 60;
        timeArgs[3] = secs;
        timeArgs[4] = secs % 60;
        return sFormatter.format(durationformat, timeArgs).toString();
    }
    
    public static String formetFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = fileS + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}
    
    public static void main(String[] args) {   
        new Menu();   
    }
    
    

	/*@Override
	public void updateProgress(long value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onComplete() {
		// TODO Auto-generated method stub
		
	}   */
  
}