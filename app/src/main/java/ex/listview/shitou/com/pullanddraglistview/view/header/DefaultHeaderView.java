package ex.listview.shitou.com.pullanddraglistview.view.header;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import ex.listview.shitou.com.pullanddraglistview.R;
import ex.listview.shitou.com.pullanddraglistview.view.PullAndDragListView;

/**
 * Created by shitou on 15/6/22.
 */
public class DefaultHeaderView extends PullAndDragListView.HeaderView{
    public DefaultHeaderView(int layoutId,Context context) {
        super(layoutId,context);
        initView();
    }

    private boolean isBack ;


    private LinearLayout headView;

    private TextView tipsTextview;

    private TextView lastUpdatedTextView;

    private ImageView arrowImageView;

    private ProgressBar progressBar;

    private void initView(){
        this.headView = (LinearLayout)getLayoutView();
        this.tipsTextview = (TextView)headView.findViewById(R.id.head_tipsTextView);
        this.lastUpdatedTextView = (TextView)headView.findViewById(R.id.head_lastUpdatedTextView);
        this.arrowImageView = (ImageView)headView.findViewById(R.id.head_arrowImageView);
        this.progressBar = (ProgressBar)headView.findViewById(R.id.head_progressBar);
    }


    @Override
    public void onDone() {
         headView.setPadding(0, -1 * super.getHeadContentHeight(), 0, 0);
         progressBar.setVisibility(View.GONE);
         arrowImageView.clearAnimation();
         arrowImageView.setImageResource(R.drawable.ic_launcher);
         tipsTextview.setText("加载完成");
         lastUpdatedTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefreshing() {
        headView.setPadding(0, 0, 0, 0);
        progressBar.setVisibility(View.VISIBLE);
        arrowImageView.clearAnimation();
        arrowImageView.setVisibility(View.GONE);
        tipsTextview.setText("加载中...");
        lastUpdatedTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPullToRefresh() {
        Animation reverseAnimation = new RotateAnimation(-180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        reverseAnimation.setInterpolator(new LinearInterpolator());
        reverseAnimation.setDuration(200);
        reverseAnimation.setFillAfter(true);

        progressBar.setVisibility(View.GONE);
        tipsTextview.setVisibility(View.VISIBLE);
        lastUpdatedTextView.setVisibility(View.VISIBLE);
        arrowImageView.clearAnimation();
        arrowImageView.setVisibility(View.VISIBLE);
        if (isBack)
        {
            isBack = false;
            arrowImageView.clearAnimation();
            arrowImageView.startAnimation(reverseAnimation);
            tipsTextview.setText("下拉刷新");
        }
        else
        {
            tipsTextview.setText("下拉刷新");
        }
    }

    @Override
    public void onReleaseToRefresh() {
        Animation animation = new RotateAnimation(0, -180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(250);
        animation.setFillAfter(true);

        arrowImageView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        tipsTextview.setVisibility(View.VISIBLE);
        lastUpdatedTextView.setVisibility(View.VISIBLE);
        arrowImageView.clearAnimation();
        arrowImageView.startAnimation(animation);
        tipsTextview.setText("释放刷新");
    }
}
