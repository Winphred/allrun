package com.winphred.allrun;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import android.app.Application;
import android.content.res.XmlResourceParser;
import android.util.Log;

public class TApplication extends Application {
	/**
	 * �ڳ�������ʱִ��һ�Ρ����̽���ʱ��tapplicaton�Ż���� ���ã�����������ÿ��activity�����õ�
	 */
	public static XMPPConnection xmppConnection;
	public static String host, serviceName;
	private int port;

	@Override
	public void onCreate() {
		// application,activity����ɾ����
		super.onCreate();
		try {
			//����������ϲ��Գ���
			//1������ҵ�����-->������ѡ��-->usb����
			//2, װ�ֻ���������������ֻ����ֻ����������������ıʼǱ���װ����
			//3,  debug as -->debug configuration-->target ѡ��һ��
			//4,���ֻ���������з���http://ip:9090 �����������¼���棬˵������ͨ
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
					// openfrie�������˿ں�
					// 1������� http://ip:9090 �����û�
					// 2,����ͻ��� spark,allrun socket(5222)
					ConnectionConfiguration config = new ConnectionConfiguration(
							"124.207.192.18", 5222, "tarena.com");
					xmppConnection = new XMPPConnection(config);
					xmppConnection.connect();
					Log.i("connectChatServer",
							"���ӷ��������" + xmppConnection.isConnected());
				} catch (Exception e) {
					// TODO: handle exception
				}
			};
		}.start();

	}
}
