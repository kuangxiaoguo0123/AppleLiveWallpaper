package com.kxg.livewallpaper.ui;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.kxg.livewallpaper.R;
import com.kxg.livewallpaper.base.BaseActivity;
import com.kxg.livewallpaper.constant.Constant;
import com.kxg.livewallpaper.model.UpdateSignatureInfo;
import com.kxg.livewallpaper.util.SpUtil;

import butterknife.BindView;
import de.greenrobot.event.EventBus;

/**
 * Created by kuangxiaoguo on 2018/4/6.
 */

public class SignatureActivity extends BaseActivity {

    private static final String SIGNATURE_TEXT_TAG = "signature_text_tag";
    @BindView(R.id.edit_text)
    EditText mEditText;

    public static void startActivity(Context context, String text) {
        Intent intent = new Intent(context, SignatureActivity.class)
                .putExtra(SIGNATURE_TEXT_TAG, text);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_signature;
    }

    @Override
    protected void initToolbar() {
        mToolbar.setNavigationIcon(R.drawable.back_white_image);
        mToolbar.setTitle(R.string.signature);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.signature_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_save) {
            SpUtil.putString(this, Constant.SIGNATURE_DATA, mEditText.getText().toString().trim());
            EventBus.getDefault().post(new UpdateSignatureInfo());
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void configViews() {

    }

    @Override
    protected void initData() {
        String text = getIntent().getStringExtra(SIGNATURE_TEXT_TAG);
        mEditText.setText(text);
    }
}
