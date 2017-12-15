package com.dp.common.utils.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.dp.common.Constant;

public class MD5Util {
	public static String md5(String userName,String password){
		ByteSource credentialsSalt = ByteSource.Util.bytes(userName); 
		SimpleHash pw = new SimpleHash(Constant.HASH_ALGORITHM_NAME, password, credentialsSalt, Constant.HASH_ITERATIONS);
		return pw.toString();
	}
}
