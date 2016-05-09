package com.winphred.allrun.biz;

import java.util.HashMap;

import org.jivesoftware.smack.AccountManager;

import com.winphred.allrun.TApplication;
import com.winphred.allrun.entity.UserEntity;
import com.winphred.allrun.util.Const;

import android.app.IntentService;
import android.content.Intent;

public class RegisterBiz extends IntentService {
	// 无参的
	public RegisterBiz() {
		super("RegisterBiz");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		int status = Const.STATUS_OK;
		try {
			int threadId = (int) Thread.currentThread().getId();
			// 接收信息
			UserEntity userEntity = (UserEntity) intent
					.getSerializableExtra(Const.KEY_DATA);
			// 注册
			AccountManager accountManager = TApplication.xmppConnection
					.getAccountManager();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("name", userEntity.getName());
			accountManager.createAccount(userEntity.getUsername(),
					userEntity.getPassword(), map);

		} catch (Exception e) {
			status = Const.STATUS_REGISTER_FAILURE;
		} finally {
			// 发广播
			Intent intent2 = new Intent(Const.ACTION_REGISTER);
			intent2.putExtra(Const.KEY_DATA, status);
			sendBroadcast(intent2);
		}

	}

}
