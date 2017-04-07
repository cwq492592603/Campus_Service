package informationsystem.com.campusservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SetSelfIntroductionActivity extends Activity implements TextWatcher {

    private EditText editText;
    private TextView finish;
    private ImageView back;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setselfintro);

        editText = (EditText) findViewById(R.id.selfintro);
        finish = (TextView) findViewById(R.id.finish);
        back = (ImageView) findViewById(R.id.go_back);

        //为个人简介输入框初始为之前所保存的内容
        bundle = getIntent().getExtras();
        editText.setText(bundle.getString("self_introduction"));

        editText.addTextChangedListener(this);
        finish.setEnabled(false);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(editText.getText().toString().isEmpty()){
            finish.setEnabled(false);
        }else{
            finish.setEnabled(true);
            finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String str = editText.getText().toString();
                    Intent intent = getIntent();
                    intent.putExtra("self_introduction",str);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
        }

    }
}
