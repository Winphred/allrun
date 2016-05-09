package com.winphred.allrun;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import android.app.Application;
import android.content.res.XmlResourceParser;
import android.util.Log;

public class TApplication extends Application {
	/**
	 * 在程序启动时执行一次。进程结束时，tapplicaton才会结束 作用：如果这个对象每个activity都会用到
	 */
	public static XMPPConnection xmppConnection;
	public static String host, serviceName;
	private int port;

	@Override
	public void onCreate() {
		// application,activity不能删除，
		super.onCreate();
		try {
			//天天在真机上测试程序
			//1，真机找到设置-->开发者选项-->usb调试
			//2, 装手机助手软件，连上手机，手机助手软件联网在你的笔记本上装驱动
			//3,  debug as -->debug configuration-->target 选第一项
			//4,在手机的浏览器中访问http://ip:9090 如果看不到登录界面，说明网不通
			readConfig();
			connectChatServer();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void readConfig() throws Exception {

		XmlResourceParser xmlResourceParser = this.getResources().getXml(
				R.xml.config);
		int eventType = xmlResourceParser.getEventType();

		while (eventType != XmlResourceParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlResourceParser.START_TAG:
				String tagName = xmlResourceParser.getName();
				if ("host".equals(tagName)) {
					host = xmlResourceParser.nextText();
				}
				if ("serviceName".equals(tagName)) {
					serviceName = xmlResourceParser.nextText();
				}
				if ("port".equals(tagName)) {
					port = Integer.parseInt(xmlResourceParser.nextText());
				}
				break;

			}
			eventType = xmlResourceParser.next();
		}
	}

	private void connectChatServer() {
		new Thread() {
			public void run() {
				try {
					// openfrie有两个端口号
					// 1，浏览器 http://ip:9090 创建用户
					// 2,聊天客户端 spark,allrun socket(5222)
					ConnectionConfiguration config = new ConnectionConfiguration(
							"124.207.192.18", 5222, "tarena.com");
					xmppConnection = new XMPPConnection(config);
					xmppConnection.connect();
					Log.i("connectChatServer",
							"连接服务器结果" + xmppConnection.isConnected());
				} catch (Exception e) {
					// TODO: handle exception
				}
			};
		}.start();

	}
}
