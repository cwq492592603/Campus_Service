package informationsystem.com.campusservice;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisteredSuccessActivity extends Activity {

    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_success);

        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
