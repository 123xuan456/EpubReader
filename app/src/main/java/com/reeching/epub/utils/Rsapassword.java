package com.reeching.epub.utils;

import com.koolearn.android.util.LogUtils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

import javax.crypto.Cipher;

public class Rsapassword {
//	public static void main(String[] args) {
//		String ras_key_url="F:/Work/RSAKey.xml";//加密文件存放的位置
//		saveRSAKey(ras_key_url);
//		String srcFileName="F://Work//170950.epub";//
//		String destFileName="F://Work//170950_encryption.epub";
////		encryptFile(srcFileName, destFileName, ras_key_url);jia
//
//		srcFileName=destFileName;
//		destFileName="F://Work//170950_Decrypt.epub";
//
//		decryptFile(srcFileName, destFileName, ras_key_url);
//	}
//	/**
//	 *
//	 * @param ras_key_url    加密文件存放的位置
//	 * @param epubFileMenu   epub文件存放目录文件夹
//	 * @param bookName		 epub文件名称
//	 * @return
//	 */
//
	public static String rsaepubFile(String packName, String proFilePath, String epubName) {
		//String message_epub=prop.getProperty("message_epub");
		String epubFileMenu = proFilePath;//epub文件存放目录文件夹
		String epubFilePass=null;
		String ras_key_url=packName+"/RSAKey.xml";//加密文件存放的位置
		File f=new File(ras_key_url);
		boolean flag=false;
		if(!f.exists()){
			flag=saveRSAKey(ras_key_url);
		}else{
			flag=true;
		}
			if(flag){
				String srcFileName=epubFileMenu+"/"+epubName+".epub";
			String destFileName=epubFileMenu+"/"+epubName+"_Decrypt.epub";
			flag=encryptFile(srcFileName, destFileName, ras_key_url);
			if(flag){
				epubFilePass="/"+epubName+"_Decrypt.epub";
			}
		}
		return epubFilePass;

	}

	/**
	 * 文件解密
	 *
	 * 文件file进行加密并保存目标文件destFile中
	 * @param srcFileName
	 *已加密的文件如c:/加密后文件.txt
	 * @param destFileName
	 *解密后存放的文件名如c:/test/解密后文件.txt
	 */

	public static boolean decryptFile(String srcFileName,String destFileName,String ras_key_url) {
		boolean flag=true;
		OutputStream outputWriter = null;
		FileInputStream inputReader = null;
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding",
			new BouncyCastleProvider());
			byte[] buf = new byte[128];
			int bufl;
			cipher.init(Cipher.DECRYPT_MODE, getKeyPair(ras_key_url).getPrivate());
			outputWriter = new FileOutputStream(destFileName);
			inputReader = new FileInputStream(srcFileName);
			while ((bufl = inputReader.read(buf)) != -1) {
				byte[] encText = null;
				byte[] newArr = null;
				if (buf.length == bufl) {
					newArr = buf;
				} else {
					newArr = new byte[bufl];
					for (int i = 0; i < bufl; i++) {
						newArr[i] = (byte) buf[i];
					}
				}
				encText = cipher.doFinal(newArr);
				outputWriter.write(encText);
			}
			outputWriter.flush();
		} catch (Exception e) {
			try {
				if (outputWriter != null) {
					outputWriter.close();
				}
				if (inputReader != null) {
					inputReader.close();
				}
			} catch (Exception e1) {
			}
			flag=false;
			e.printStackTrace();
		} finally {
			try {
				if (outputWriter != null) {
					outputWriter.close();
				}
				if (inputReader != null) {
					inputReader.close();
				}
			} catch (Exception e) {
			}
		}

		return flag;
	}

	/**
	 * 文件采用RSA算法加密文件
	 *
	 * 文件file进行加密并保存目标文件destFile中
	 * @param srcFileName
	 *要加密的文件如c:/test/srcFile.txt
	 * @param destFileName
	 *加密后存放的文件名如c:/加密后文件.txt
	 * @param ras_key_url  加密key的位置
	 */

	public static boolean encryptFile(String srcFileName, String destFileName,String ras_key_url){
		boolean flag=true;
	    OutputStream outputWriter = null;
	    FileInputStream inputReader = null;
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding",new org.bouncycastle.jce.provider.BouncyCastleProvider());
			byte[] buf = new byte[100];
			int bufl;
			cipher.init(Cipher.ENCRYPT_MODE, getKeyPair(ras_key_url).getPublic());
			outputWriter = new FileOutputStream(destFileName);
			inputReader = new FileInputStream(srcFileName);
			while ((bufl = inputReader.read(buf)) != -1) {
				byte[] encText = null;
				byte[] newArr = null;
				if (buf.length == bufl) {
					newArr = buf;
				} else {
					newArr = new byte[bufl];
					for (int i = 0; i < bufl; i++) {
						newArr[i] = (byte) buf[i];
					}
				}
				encText = cipher.doFinal(newArr);
				outputWriter.write(encText);
			}
			outputWriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (outputWriter != null) {
					outputWriter.close();
				}
				if (inputReader != null) {
					inputReader.close();
				}
			} catch (Exception e1) {
			}
			flag=false;
		} finally {
			try {
				if (outputWriter != null) {
					outputWriter.close();
				}
				if (inputReader != null) {
					inputReader.close();
				}
			} catch (Exception e) {
			}
		}
		return flag;
	}

	/***
	 * 把成生的一对密钥保存到RSAKey.xml文件中
	 */
	public static boolean saveRSAKey(String ras_key_url) {
		boolean flag=true;
		ObjectOutputStream oos=null;
		FileOutputStream fos=null;
		try {
			SecureRandom sr = new SecureRandom();
			KeyPairGenerator kg = KeyPairGenerator.getInstance("RSA",new org.bouncycastle.jce.provider.BouncyCastleProvider());
		    //注意密钥大小最好为1024,否则解密会有乱码情况
			kg.initialize(1024, sr);
			fos = new FileOutputStream(ras_key_url);
			oos = new ObjectOutputStream(fos);
			//生成密钥
			oos.writeObject(kg.generateKeyPair());
			oos.close();
			fos.close();
		} catch (Exception e) {
			try {
				if(null!=oos){
					oos.close();
				}
				if(null!=fos){
					fos.close();
				}
			} catch (IOException e1) {
			}
			flag=false;
		}
		return flag;
	}

	/**
	 * 获得RSA加密的密钥。
	 * @return   KeyPair返回对称密钥
	 */
	public static KeyPair getKeyPair(String ras_key_url){
		//产生新密钥对
		KeyPair kp=null;
//			InputStream is = FileUtils.class.getClassLoader().getResourceAsStream(fileName);
		FileInputStream fiskey=null;
		ObjectInputStream oos=null;
		try {
			fiskey = new FileInputStream(ras_key_url);
			oos = new ObjectInputStream(fiskey);
			kp = (KeyPair) oos.readObject();
			oos.close();
		} catch (ClassNotFoundException e) {

			try{
				if(null!=oos){
					oos.close();
				}
				if(null!=fiskey){
					fiskey.close();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (IOException e) {
			try{
				if(null!=oos){
					oos.close();
				}
				if(null!=fiskey){
					fiskey.close();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
        LogUtils.i(kp+"");
		return kp;
	}
}
