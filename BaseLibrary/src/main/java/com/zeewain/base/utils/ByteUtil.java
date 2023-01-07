package com.zeewain.base.utils;

import java.io.ByteArrayOutputStream;

public class ByteUtil {
	/** 
     * @功能: BCD码转为10进制串(阿拉伯数据) 
     * @参数: BCD码 
     * @结果: 10进制串 
     */  
    public static String bcd2Str(byte[] bytes) {  
        StringBuffer temp = new StringBuffer(bytes.length * 2);  
        for (int i = 0; i < bytes.length; i++) {  
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));  
            temp.append((byte) (bytes[i] & 0x0f));  
        }  
        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp  
                .toString().substring(1) : temp.toString();  
    }  
  
    /** 
     * @功能: 10进制串转为BCD码 
     * @参数: 10进制串 
     * @结果: BCD码 
     */  
    public static byte[] str2Bcd(String asc) {  
        int len = asc.length();  
        int mod = len % 2;  
        if (mod != 0) {  
            asc = "0" + asc;  
            len = asc.length();  
        }  
        byte abt[] = new byte[len];  
        if (len >= 2) {  
            len = len / 2;  
        }  
        byte bbt[] = new byte[len];  
        abt = asc.getBytes();  
        int j, k;  
        for (int p = 0; p < asc.length() / 2; p++) {  
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {  
                j = abt[2 * p] - '0';  
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {  
                j = abt[2 * p] - 'a' + 0x0a;  
            } else {  
                j = abt[2 * p] - 'A' + 0x0a;  
            }  
            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {  
                k = abt[2 * p + 1] - '0';  
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {  
                k = abt[2 * p + 1] - 'a' + 0x0a;  
            } else {  
                k = abt[2 * p + 1] - 'A' + 0x0a;  
            }  
            int a = (j << 4) + k;  
            byte b = (byte) a;  
            bbt[p] = b;  
        }  
        return bbt;  
    }  
    
    /*
    * Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。  
    * @param src byte[] data  
    * @return  hex string  
    */     
   public static String bytesToHexString(byte[] src){  
       StringBuilder stringBuilder = new StringBuilder("");  
       if (src == null || src.length <= 0) {  
           return null;  
       }  
       for (int i = 0; i < src.length; i++) {  
           int v = src[i] & 0xFF;  
           String hv = Integer.toHexString(v);  
           if (hv.length() < 2) {  
               stringBuilder.append(0);  
           }  
           stringBuilder.append(hv);  
       }  
       return stringBuilder.toString();  
   } 
   
   public static byte[] hexStringToBytes(String str) {
       if(str == null || str.trim().equals("")) {
           return new byte[0];
       }

       byte[] bytes = new byte[str.length() / 2];
       for(int i = 0; i < str.length() / 2; i++) {
           String subStr = str.substring(i * 2, i * 2 + 2);
           bytes[i] = (byte) Integer.parseInt(subStr, 16);
       }

       return bytes;
   }
   
   /** 
    * 注释：short到字节数组的转换！ 
    * 
    * @return
    */ 
   public static byte[] shortToByte(short number) { 
       int temp = number; 
       byte[] b = new byte[2]; 
       for (int i = 0; i < b.length; i++) { 
           b[i] = new Integer(temp & 0xff).byteValue();// 将最低位保存在最低位 
           temp = temp >> 8; // 向右移8位 
       } 
       return b; 
   } 

   /** 
    * 注释：字节数组到short的转换！ 
    * 
    * @param b 
    * @return 
    */ 
   public static short byteToShort(byte[] b) { 
       short s = 0; 
       short s0 = (short) (b[0] & 0xff);// 最低位 
       short s1 = (short) (b[1] & 0xff); 
       s1 <<= 8; 
       s = (short) (s0 | s1); 
       return s; 
   }
   
   
   public static int byteToInt(byte[] b) {  
	    return   b[3] & 0xFF |  
	            (b[2] & 0xFF) << 8 |  
	            (b[1] & 0xFF) << 16 |  
	            (b[0] & 0xFF) << 24;  
	}  
   
	public static int bytes4ToInt(byte[] b) {  
		return	b[3] & 0xFF |  
				(b[2] & 0xFF) << 8 |  
				(b[1] & 0xFF) << 16 |  
				(b[0] & 0xFF) << 24;  
	}
	
	public static int bytes2ToInt(byte[] b) {  
		return	((b[0] << 8) & 0xff00) | (b[1] & 0xff);  
	}
	
	public static int bytes1ToInt(byte[] b) {  
		return	b[0] & 0xff;  
	}
	  
	public static byte[] intToByte(int a) {  
	    return new byte[] {  
	        (byte) ((a >> 24) & 0xFF),  
	        (byte) ((a >> 16) & 0xFF),     
	        (byte) ((a >> 8) & 0xFF),     
	        (byte) (a & 0xFF)  
	    };  
	} 
	
	public static byte[] intToTwoByte(int a) {  
	    return new byte[] {  
	        (byte)((a & 0xFF00)>>8),
			(byte)(a & 0x00FF)
	    };  
	}
	
	public static byte[] intToOneByte(int a) {  
	    return new byte[] {  
			(byte)(a & 0x00ff)
	    };  
	}
   
	/** 
	 *  
	 * @param data1 
	 * @param data2 
	 * @return data1 与 data2拼接的结果 
	*/  
	public static byte[] addBytes(byte data1, byte[] data2) {  
	   byte[] data3 = new byte[1 + data2.length];  
	   System.arraycopy(data1, 0, data3, 0, 1);  
	   System.arraycopy(data2, 0, data3, 1, data2.length);  
	   return data3;  
	}
	
	/** 
	 *  
	 * @param data1 
	 * @param data2 
	 * @return data1 与 data2拼接的结果 
	*/  
	public static byte[] addBytes(byte[] data1, byte data2) {  
	   byte[] data3 = new byte[data1.length + 1];  
	   System.arraycopy(data1, 0, data3, 0, data1.length);  
	   System.arraycopy(data2, 0, data3, data1.length, 1);  
	   return data3;  
	}
	
	/** 
	 *  
	 * @param data1 
	 * @param data2 
	 * @return data1 与 data2拼接的结果 
	*/  
	public static byte[] addBytes(byte[] data1, byte[] data2) {  
	   byte[] data3 = new byte[data1.length + data2.length];  
	   System.arraycopy(data1, 0, data3, 0, data1.length);  
	   System.arraycopy(data2, 0, data3, data1.length, data2.length);  
	   return data3;  
	}
	
	/** 
	 *  
	 * @param data1 
	 * @param data2 
	 * @param data3 
	 * @return data1,data2,data3拼接的结果 
	*/  
	public static byte[] addBytes(byte[] data1, byte[] data2, byte[] data3) {  
	   byte[] data = new byte[data1.length + data2.length + data3.length];  
	   System.arraycopy(data1, 0, data, 0, data1.length);  
	   System.arraycopy(data2, 0, data, data1.length, data2.length); 
	   System.arraycopy(data3, 0, data, data1.length + data2.length, data3.length);
	   return data;  
	}
	
	/**
	 * 拼接多个byte[]
	 * @param datas
	 * @return
	 */
	public static byte[] jointBytes(byte[]... datas)
	{
		try{
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			for (byte[] data : datas) {  
				bos.write(data);
			} 
			byte[] result = bos.toByteArray();
			bos.close();
			return result;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
