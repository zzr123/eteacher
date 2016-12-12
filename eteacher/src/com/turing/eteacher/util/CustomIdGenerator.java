package com.turing.eteacher.util;

import java.io.Serializable;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.type.Type;

/**
 * 自定义主键生成策略
 * @author caojian
 *
 */
public class CustomIdGenerator implements IdentifierGenerator, Configurable {

	@Override
	public void configure(Type arg0, Properties arg1, Dialect arg2)
			throws MappingException {
		
	}

	@Override
	public Serializable generate(SessionImplementor arg0, Object arg1)
			throws HibernateException {
		return generateShortUuid().toString();
	}

	private static String[] chars = new String[] { "a", "b", "c", "d", "e",
		"f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
		"s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4",
		"5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
		"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
		"V", "W", "X", "Y", "Z" };
	
	public static String generateShortUuid() {
		return generateShortUuid(1000+new Random().nextInt(8999)+"");
	}
	
	private static String generateShortUuid(String nodeName) {

		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		uuid = HexString.encode(nodeName.substring(0, 4)) + uuid;
		for (int i = 0; i < 10; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();
	}
	
	public static void main(String[] args) {
		for (int i = 0;i< 20;i++){
			String uuid1 = CustomIdGenerator.generateShortUuid();
			System.out.println(uuid1);
		}
		System.out.println(""+DateUtil.isOverlap2(DateUtil.getCurrentDateStr(DateUtil.YYYYMMDDHHMM), 
				DateUtil.getCurrentDateStr(DateUtil.YYYYMMDDHHMM),
				"2016-12-11", 
				"2016-12-30"));
//		String uuid2 = null;
//		int i = 0;
//		System.out.println(uuid1);
//		do {
//			uuid2 = CustomIdGenerator.generateShortUuid();
//			i++;
//			System.out.println(i + "           :" + uuid2);
//		} while (!uuid2.equals(uuid1));
	}
}
