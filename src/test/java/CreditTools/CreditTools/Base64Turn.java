package CreditTools.CreditTools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import sun.misc.BASE64Encoder;

public class Base64Turn {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String pdfToBase64 = PDFToBase64(new File("0212国投（宁夏）互联网小额贷款有限公司贷款合同（清洁版）.pdf"));
		System.out.println(pdfToBase64);
	}
	/**
     * Description: 将pdf文件转换为Base64编码
     * @param  要转的的pdf文件
     */
    public static String PDFToBase64(File file) {
        BASE64Encoder encoder = new BASE64Encoder();
        FileInputStream fin =null;
        BufferedInputStream bin =null;
        ByteArrayOutputStream baos = null;
        BufferedOutputStream bout =null;
        String a  = null;
        try {
            fin = new FileInputStream(file);
            bin = new BufferedInputStream(fin);
            baos = new ByteArrayOutputStream();
            bout = new BufferedOutputStream(baos);
            byte[] buffer = new byte[1024];
            int len = bin.read(buffer);
            while(len != -1){
                bout.write(buffer, 0, len);
                len = bin.read(buffer);
            }
            //刷新此输出流并强制写出所有缓冲的输出字节
            bout.flush();
            byte[] bytes = baos.toByteArray();
            a =  encoder.encodeBuffer(bytes).trim();  
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                fin.close();
                bin.close();
                bout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return a;
    }


}
