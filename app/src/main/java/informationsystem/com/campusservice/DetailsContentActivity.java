package informationsystem.com.campusservice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

public class DetailsContentActivity extends Activity {

    public static void actionStart(Context context, String title, String content){
        Intent intent = new Intent(context,DetailsContentActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("content",content);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.details_frag);

        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");
        DetailsContentFragment detailsContentFragment = (DetailsContentFragment) getFragmentManager()
                .findFragmentById(R.id.details_content_fragment);
//        DetailsContentFragment detailsContentFragment = new DetailsContentFragment();
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
//        beginTransaction.add(R.id.details_content_fragment,detailsContentFragment);
//        beginTransaction.commit();
        if(detailsContentFragment==null){
            Toast.makeText(this,"Fragment为空",Toast.LENGTH_SHORT).show();
        }else if(title == null|| content == null) {
            Toast.makeText(this, "数据为空", Toast.LENGTH_SHORT).show();
        }else{
            detailsContentFragment.refresh(title,content);
        }

    }

}
