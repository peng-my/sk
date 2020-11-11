package org.jckj.modules.business.test.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Title 订单号生成
 * @Description
 *  订单号组成： 时间戳(yyMMddHHmmssSSS) + 机器ID + 机构ID + 序号</br>
 *  时间戳  15位</br>
 *  机器ID 2位</br>
 *  序号 4位</br>
 *  机构ID 4位
 *  订单号总长度25位，
 * @author liuzhimin
 *
 */
public class OrderUtils {

	/** 序列 */
	private final long sequenceBits = 12L;

	// 最近的时间戳
	private long lastTimestamp=0;

	// 0，并发控制
	private long sequence = 0L;

	private String machineId;

	private String organizerId;

	/** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
	private final long sequenceMask = -1L ^ (-1L << sequenceBits);

	public OrderUtils(int machineId,int organizerId) {
		this.machineId = leftPad(machineId,2);
		this.organizerId = leftPad(organizerId,4);
	}

	/**
	 * 生成订单号
	 */
	public synchronized String nextId(){
		Date now=new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmssSSS");
		String time= formatter.format(now);
		long timestamp = now.getTime();

		//如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
		if (timestamp < lastTimestamp) {
			throw new RuntimeException(
				String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
		}

		//如果是同一时间生成的，则进行毫秒内序列
		if (lastTimestamp == timestamp) {
			sequence = (sequence + 1) & sequenceMask;
			//毫秒内序列溢出
			if (sequence == 0) {
				//阻塞到下一个毫秒,获得新的时间戳
				now = tilNextMillis(lastTimestamp);
				time= formatter.format(now);
			}
		}
		//时间戳改变，毫秒内序列重置
		else {
			sequence = 0L;
		}

		//上次生成ID的时间截
		lastTimestamp = now.getTime();

		String seq = leftPad(sequence, 4);
		StringBuilder sb=new StringBuilder(time).append(machineId).append(organizerId).append(seq);
		return sb.toString();
	}

	/**
	 * 补码
	 * @param i
	 * @param n
	 * @return
	 */
	private String leftPad(long i,int n){
		String s = String.valueOf(i);
		StringBuilder sb=new StringBuilder();
		int c= n - s.length();
		c=c<0?0:c;
		for (int t=0;t<c;t++){
			sb.append("0");
		}
		return sb.append(s).toString();
	}

	/**
	 * 阻塞到下一个毫秒，直到获得新的时间戳
	 * @param lastTimestamp 上次生成ID的时间截
	 * @return 当前时间戳
	 */
	protected Date tilNextMillis(long lastTimestamp) {
		Date now=new Date();
		long timestamp = now.getTime();
		while (timestamp <= lastTimestamp) {
			now = new Date();
			timestamp = now.getTime();
		}
		return now;
	}


}
