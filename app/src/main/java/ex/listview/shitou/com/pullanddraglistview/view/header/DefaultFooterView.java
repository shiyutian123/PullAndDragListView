package ex.listview.shitou.com.pullanddraglistview.view.header;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ex.listview.shitou.com.pullanddraglistview.R;
import ex.listview.shitou.com.pullanddraglistview.view.PullAndDragListView;

/**
 * Created by shitou on 15/6/24.
 */
public class DefaultFooterView extends PullAndDragListView.FooterView {

    private RelativeLayout footLoadMoreView ;
    private TextView footLoadMoreTv ;
    private LinearLayout footLoadingLayout ;

    public DefaultFooterView(int layoutId, Context context) {
        super(layoutId, context);
        footLoadMoreTv = (TextView)getLayoutView().findViewById(R.id.load_more_tv);
    }

    @Override
    public void onDone() {
        footLoadMoreTv.setText("加载完成");
    }

    @Override
    public void onRefreshing() {
        footLoadMoreTv.setText("刷新中");
    }

    @Override
    public void onPullToRefresh() {
        footLoadMoreTv.setText("上拉加载更多");
    }

    @Override
    public void onReleaseToRefresh() {
        footLoadMoreTv.setText("释放加载");
    }


}
