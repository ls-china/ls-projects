package com.ls.downloadtxt.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.ls.downloadtxt.App;
import com.ls.downloadtxt.R;
import com.ls.downloadtxt.db.dao.ConfigDAO;
import com.ls.downloadtxt.db.entity.Config;
import com.ls.downloadtxt.util.FrameUtil;
import com.ls.downloadtxt.util.ToastUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_setting)
public class SettingActivity extends AppCompatActivity {

    private ConfigDAO configDAO;
    private Config config;

    @ViewInject(R.id.toggleButton)
    private ToggleButton tb;
    @ViewInject(R.id.downloadPath)
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        findViewById(R.id.btn_left).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_right).setVisibility(View.GONE);
        ((TextView) findViewById(android.R.id.title)).setText(R.string.setting);

        configDAO = App.getInstace(this).getConfigDAO();
        config = configDAO.get(this);

        tb.setChecked(config.isUseMobileNet());
        tv.setText(config.getDownloadPath());
    }

    @Event(value = {R.id.toggleButton}, type = CompoundButton.OnCheckedChangeListener.class)
    private void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        config.setUseMobileNet(isChecked);
    }

    @Event(value = {R.id.downloadPath, R.id.btn_left})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                finish();
                break;
            case R.id.downloadPath:
                FrameUtil.startActivityForResult(this, 1001, FileExploreActivity.class);
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001) {
            if (resultCode == Activity.RESULT_OK) {
                String path = data.getStringExtra("PATH");
                config.setDownloadPath(path);
                tv.setText(config.getDownloadPath());
            }
        }
    }

    @Override
    protected void onDestroy() {
        boolean success = configDAO.save(this, config);
        ToastUtil.Toast(this, success ? "设置保存成功。" : "设置保存失败。");
        super.onDestroy();
    }
}
