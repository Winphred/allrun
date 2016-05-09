package com.winphred.allrun.activity;

import com.winphred.allrun.R;
import com.winphred.allrun.biz.RegisterBiz;
import com.winphred.allrun.entity.UserEntity;
import com.winphred.allrun.util.Const;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	EditText etUsername, etPassword, etConfirmPassword, etName;
	Button tvSubmit;

	MyReceiver myReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		try {
			setContentView(R.layout.activity_register);
			setupView();
			addListener();

			myReceiver = new MyReceiver();
			this.registerReceiver(myReceiver, new IntentFilter(
					Const.ACTION_REGISTER));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try {
			this.unregisterReceiver(myReceiver);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void addListener() {
		tvSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					String username = etUsername.getText().toString();
					String password = etPassword.getText().toString();
					String name = etName.getText().toString();
					// 检查学员完成

					// 调IntentService
					Intent intent = new Intent(RegisterActivity.this,
							RegisterBiz.class);
					// 带数据
					UserEntity userEntity = new UserEntity();
					userEntity.setUsername(username);
					userEntity.setPassword(password);
					userEntity.setName(name);

					intent.putExtra(Const.KEY_DATA, userEntity);

					RegisterActivity.this.startService(intent);

				} catch (Exception e) {
				}
			}
		});
	}

	private void setupView() {
		// TODO Auto-generated method stub
		etUsername = (EditText) findViewById(R.id.et_register_username);
		etPassword = (EditText) findViewById(R.id.et_register_password);
		etConfirmPassword = (EditText) findViewById(R.id.et_register_confirm_password);
		etName = (EditText) findViewById(R.id.et_register_name);

		tvSubmit = (Button) findViewById(R.id.btn_register_submit);
	}

	class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int status=intent.getIntExtra(Const.KEY_DATA, -1);
			if (status==Const.STATUS_OK)
			{
			Toast.makeText(context, "注册成功",Toast.LENGTH_LONG).show();
			}else
			{
			Toast.makeText(context, "注册失败", Toast.LENGTH_LONG).show();

			}
		}

	}
}
