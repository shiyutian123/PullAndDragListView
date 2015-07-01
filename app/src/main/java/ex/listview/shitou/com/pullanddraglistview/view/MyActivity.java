package ex.listview.shitou.com.pullanddraglistview.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import ex.listview.shitou.com.pullanddraglistview.R;
import ex.listview.shitou.com.pullanddraglistview.adapater.AdapterViewBound;
import ex.listview.shitou.com.pullanddraglistview.adapater.MultiItemTypeSupport;
import ex.listview.shitou.com.pullanddraglistview.adapater.SimpleImplAdapter;

public class MyActivity extends Activity {

    public static final String TAG = MyActivity.class.getSimpleName();
    private PullAndDragListView listView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        listView = (PullAndDragListView)this.findViewById(R.id.listview);
        MultiItemTypeSupport<Bean> multiItemTypeSupport = new MultiItemTypeSupport<Bean>() {
            @Override
            public int getLayoutId(int position, Bean bean) {
                if(bean.getType() == 1){
                    return R.layout.layout_left;
                }else{
                    return R.layout.layout_right;
                }
            }

            @Override
            public int getViewTypeCount() {
                return 2;
            }

            @Override
            public int getItemViewType(int postion, Bean bean) {
                return bean.getType();
            }
        };

        Bean bean1 = new Bean(1,1,"第一左");
        Bean bean2 = new Bean(2,2,"第二右");
        Bean bean3 = new Bean(3,2,"第三右");
        Bean bean4 = new Bean(4,1,"第四左");
        Bean bean5 = new Bean(1,1,"第一左");
        Bean bean6 = new Bean(1,1,"第一左");
        Bean bean7 = new Bean(1,1,"第一左");
        Bean bean8 = new Bean(1,1,"第一左");
        Bean bean9 = new Bean(1,1,"第一左");
        Bean bean10 = new Bean(1,1,"第一左");
        Bean bean11= new Bean(1,1,"第一左");
        Bean bean12= new Bean(1,1,"第一左");
        Bean bean13 = new Bean(1,1,"第一左");
        Bean bean14 = new Bean(1,1,"第一左");
        Bean bean15 = new Bean(1,1,"第一左");
        Bean bean16 = new Bean(1,1,"第一左");
        Bean bean17 = new Bean(1,1,"第一左");
        Bean bean18 = new Bean(1,1,"第一左");
        Bean bean19 = new Bean(1,1,"第一左");
        Bean bean20 = new Bean(1,1,"第一左");
        Bean bean21 = new Bean(1,1,"第一左");
        Bean bean22 = new Bean(1,1,"第一左");


        List<Bean> data = new ArrayList<Bean>();
        data.add(bean1);
        data.add(bean2);
        data.add(bean3);
        data.add(bean4);
        data.add(bean5);
        data.add(bean6);
        data.add(bean7);
        data.add(bean8);
        data.add(bean9);
        data.add(bean10);
        data.add(bean11);
        data.add(bean12);
        data.add(bean13);
        data.add(bean14);
        data.add(bean15);
        data.add(bean16);
        data.add(bean17);
        data.add(bean18);
        data.add(bean19);
        data.add(bean20);
        SimpleImplAdapter<Bean> simpleImplAdapter = new SimpleImplAdapter<Bean>(this,data,multiItemTypeSupport) {
            @Override
            protected void convert(final AdapterViewBound bound, Bean item) {
                if(getItemViewType(bound.getPosition()) == 1){
                    bound.setText(R.id.letf_text,item.getText());
                    bound.setOnClickListener(R.id.left_layout,new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.w(TAG,"THIS IS LAYOUT CLICK===="+bound.getPosition());
                        }
                    });
                    bound.setText(R.id.left_button,"按钮");
                    bound.setOnClickListener(R.id.left_button,new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.w(TAG,"THIS IS Button CLICK........."+bound.getPosition());
                        }
                    });
                }else {
                    bound.setText(R.id.rigth_text,item.getText());
                }
            }
        };
        simpleImplAdapter.showIndeterminateProgress(true);
        listView.setAdapter(simpleImplAdapter);

        Button button = (Button)findViewById(R.id.deleteFirst);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleImplAdapter<Bean> adapter = (SimpleImplAdapter<Bean>)listView.getAdapter();
                Log.w(TAG,adapter.getDataCount()+"");
                if(adapter.getDataCount() > 0)
                    adapter.remove(0);
                adapter.showIndeterminateProgress(false);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
