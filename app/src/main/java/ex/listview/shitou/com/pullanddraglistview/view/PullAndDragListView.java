package ex.listview.shitou.com.pullanddraglistview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AbsListView;
import android.widget.ListView;

import ex.listview.shitou.com.pullanddraglistview.R;
import ex.listview.shitou.com.pullanddraglistview.view.header.DefaultFooterView;
import ex.listview.shitou.com.pullanddraglistview.view.header.DefaultHeaderView;

/**
 * Created by shitou on 15/6/22.
 */
public class PullAndDragListView extends ListView  implements AbsListView.OnScrollListener {

    public final static String TAG = PullAndDragListView.class.getSimpleName();

    private int firstItemIndex;
    private int lastItemIndex ;
    private int endItemIndex ;

    private boolean isHeaderRecored;
    private int headerStartY;
    private int headerState;
    private boolean isHeaderBack ;

    private boolean isFooterRecored;
    private int footerStartY;
    private int footerState;
    private boolean isFooterBack ;


    private LayoutInflater layoutInflater ;


    public PullAndDragListView(Context context) {
        super(context);
        init(context);
    }

    public PullAndDragListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PullAndDragListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }


    private void init(Context context) {
        layoutInflater = LayoutInflater.from(context);
//        footerView.onPerLoad();
        this.headerView = new DefaultHeaderView(R.layout.listview_header,context);
        this.footerView = new DefaultFooterView(R.layout.listview_footer,context);
        this.setHeaderVisible(true);
        this.setFootViewVisible(true);
        headerView.init();
        footerView.init();
        footerView.getLayoutView().invalidate();
        headerView.getLayoutView().invalidate();
        this.addHeaderView(headerView.getLayoutView(),null,false);
        this.addFooterView(footerView.getLayoutView(),null,false);

        headerState = HeaderView.DONE ;
        footerState = FooterView.DONE;
        setOnScrollListener(this);
    }




    //===========start headerView
    /**
     *  header view
     */
    public static abstract class HeaderView{

        // 释放状态
        public final static int RELEASE_To_REFRESH = 0;

        // 下拉到刷新状态
        public final static int PULL_To_REFRESH = 1;

        // 正在刷新
        public final static int REFRESHING = 2;

        // 刷新完成
        public final static int DONE = 3;

        public final static int RATIO = 3;

        private View layoutView ;

        private int headContentHeight ;

        public HeaderView(int layoutId,Context context){
            LayoutInflater inflater = LayoutInflater.from(context);
            layoutView = inflater.inflate(layoutId,null);

        }

        public void init(){
            initHeadViewSize();
        }

        public abstract void onDone();

        public abstract void onRefreshing();

        public abstract void onPullToRefresh();

        public abstract void onReleaseToRefresh();

        public View getLayoutView(){
            return layoutView ;
        }

        protected void setPullRefreshPadding(int tempY,int startY){
            getLayoutView().setPadding(0, -1 * headContentHeight + (tempY - startY) / HeaderView.RATIO, 0, 0);
        }

        protected void setReleaseRefreshPadding(int tempY,int startY){
            getLayoutView().setPadding(0, (tempY - startY) / HeaderView.RATIO - headContentHeight, 0, 0);
        }

        private void measureView(View child)
        {
            ViewGroup.LayoutParams p = child.getLayoutParams();
            if (p == null)
            {
                p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
            int lpHeight = p.height;
            int childHeightSpec = 0;
            if (lpHeight > 0)
            {
                childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                        MeasureSpec.EXACTLY);
            }
            else
            {
                childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                        MeasureSpec.UNSPECIFIED);
            }
            child.measure(childWidthSpec, childHeightSpec);
        }

        public void initHeadViewSize(){
            measureView(getLayoutView());
            headContentHeight = getLayoutView().getMeasuredHeight();
            // headContentWidth = headView.getMeasuredWidth();
            getLayoutView().setPadding(0, -1 * headContentHeight, 0, 0);
        }

        public int getHeadContentHeight(){
            return  headContentHeight;
        }
    }

    private boolean headerVisible ;
    private HeaderView headerView ;

    public boolean isHeaderVisible(){
        return this.headerVisible;
    }

    public void setHeaderVisible(boolean headerVisible){
        this.headerVisible = headerVisible ;
    }

    public HeaderView getHeaderView() {
        return headerView;
    }

    public void setHeaderView(HeaderView headerView) {
        this.headerView = headerView;
        this.addHeaderView(headerView.getLayoutView());
        headerView.init();
    }
    // =========end headerView


    public void setStateWithShow(int headerState) {
        this.headerState = headerState;
        changeHeaderViewByState();
    }
    /**
     * 根据状�?改变下拉刷新的显�?
     */
    private void changeHeaderViewByState() {
        switch (headerState)
        {
            case HeaderView.RELEASE_To_REFRESH:
                headerView.onReleaseToRefresh();
                break;
            case HeaderView.PULL_To_REFRESH:
                headerView.onPullToRefresh();
                break;
            case HeaderView.REFRESHING:
                headerView.onRefreshing();
                break;
            case HeaderView.DONE:
                headerView.onDone();
            break;
            default:
                break;
        }

        switch (footerState)
        {
            case FooterView.RELEASE_To_REFRESH:
                footerView.onReleaseToRefresh();
                break;
            case FooterView.PULL_To_REFRESH:
                footerView.onPullToRefresh();
                break;
            case FooterView.REFRESHING:
                footerView.onRefreshing();
                break;
            case FooterView.DONE:
                footerView.onDone();
                break;
            default:
                break;
        }
    }
    //===========start headerView
    /**
     *  header view
     */
    //脚视图
    public static abstract class FooterView {

        private int footContentHeight ;
        // 释放状态
        public final static int RELEASE_To_REFRESH = 10;

        // 下拉到刷新状态
        public final static int PULL_To_REFRESH = 11;

        // 正在刷新
        public final static int REFRESHING = 12;

        // 刷新完成
        public final static int DONE = 13;

        public final static int RATIO = 3;

        private View layoutView ;
        public FooterView(int layoutId,Context context){
            LayoutInflater inflater = LayoutInflater.from(context);
            layoutView = inflater.inflate(layoutId,null);
        }

        public void init(){
            initFootViewSize();
        }
        public abstract void onDone();

        public abstract void onRefreshing();

        public abstract void onPullToRefresh();

        public abstract void onReleaseToRefresh();

        public View getLayoutView(){
            return layoutView ;
        }

        protected void setPullRefreshPadding(int tempY,int startY){
            getLayoutView().setPadding(0, 0, 0, -1 * footContentHeight + (startY-tempY) / HeaderView.RATIO);
            Log.w("setPullRefreshPadding",""+(-1 * footContentHeight + (startY-tempY) / HeaderView.RATIO));
        }

        protected void setReleaseRefreshPadding(int tempY,int startY){
            getLayoutView().setPadding(0, 0, 0, (startY-tempY) / HeaderView.RATIO - footContentHeight);
            Log.w("setReleaseRefreshPadding",""+(-1 * footContentHeight + (startY-tempY) / HeaderView.RATIO));
        }

        private void measureView(View child) {
            ViewGroup.LayoutParams p = child.getLayoutParams();
            if (p == null) {
                p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
            int lpHeight = p.height;
            int childHeightSpec = 0;
            if (lpHeight > 0) {
                childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                        MeasureSpec.EXACTLY);
            } else {
                childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                        MeasureSpec.UNSPECIFIED);
            }
            child.measure(childWidthSpec, childHeightSpec);
        }

        public void initFootViewSize(){
            measureView(getLayoutView());
            footContentHeight = getLayoutView().getMeasuredHeight();
            Log.w(TAG,"footContentHeight:"+footContentHeight);
            // headContentWidth = headView.getMeasuredWidth();
            getLayoutView().setPadding(0,0,0,-footContentHeight);
        }

        public int getFootContentHeight(){
            return  footContentHeight;
        }
    }

    private FooterView footerView ;
    private boolean footViewVisible ;

    //

    public FooterView getFooterView() {
        return footerView;
    }

    public void setFooterView(FooterView footerView) {
        this.footerView = footerView;
        this.addFooterView(footerView.getLayoutView());
        this.footerView.init();
    }
    public boolean isFootViewVisible() {
        return footViewVisible;
    }

    public void setFootViewVisible(boolean footViewVisible) {
        this.footViewVisible = footViewVisible;
    }

    public void setFooterStateWithShow(int footerState) {
        this.footerState = footerState;
    }

    public void onScroll(AbsListView arg0, int firstVisibleItem, int visibleCount,int endItem){
        firstItemIndex = firstVisibleItem;
        lastItemIndex = firstItemIndex + visibleCount ;
        endItemIndex = endItem;
        Log.w(firstVisibleItem+":"+visibleCount+":"+endItem,lastItemIndex+":"+endItemIndex);
    }

    public void onScrollStateChanged(AbsListView arg0, int arg1){
        Log.w("onScrollStateChanged",lastItemIndex+":"+endItemIndex);
    }

    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                Log.w(TAG,"action down");
                if (firstItemIndex == 0 && !isHeaderRecored){
                    headerStartY = (int) event.getY();
                }
                isHeaderRecored = false ;
                isHeaderBack = false ;

                if (lastItemIndex == endItemIndex && !isFooterRecored){
                    footerStartY = (int) event.getY();
                }
                isFooterBack = false ;
                isFooterRecored = false ;
                break;
            case MotionEvent.ACTION_UP:
                Log.w(TAG,"action up");
                if (headerState != HeaderView.REFRESHING){
                    if (headerState == HeaderView.PULL_To_REFRESH) {
                        headerState = HeaderView.DONE;
                        changeHeaderViewByState();
                    }
                    if (headerState == HeaderView.RELEASE_To_REFRESH) {
                        headerState = HeaderView.REFRESHING;
                        changeHeaderViewByState();
                   //                        onRefresh();
                    }
                }
                isHeaderRecored = false;
                isHeaderBack = false;

                if(footerState != FooterView.REFRESHING){
                    if (footerState == FooterView.PULL_To_REFRESH) {
                        footerState = FooterView.DONE;
                    }
                    if (footerState == FooterView.RELEASE_To_REFRESH) {
                        footerState = FooterView.REFRESHING;
                    }
                }
                isFooterRecored = false ;
                isFooterBack = false ;

                break;
            case MotionEvent.ACTION_MOVE:
                Log.w(TAG,"action move");
                int tempY = (int) event.getY();
                headerTouchMoveEvent(tempY);
                footerTouchMoveEvent(tempY);
                break;

            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    public void footerTouchMoveEvent(int tempY){
        Log.w("footerTouchMoveEvent",tempY+"=="+footerStartY);
        if(tempY > footerStartY && endItemIndex == lastItemIndex){
            isFooterRecored = false ;
            Log.w("footerTouchMoveEvent",tempY+"=========");
        }
        if (!isFooterRecored && endItemIndex == lastItemIndex) {
            isFooterRecored = true;
            footerStartY = tempY;
        }

        if (footerState != FooterView.REFRESHING && isFooterRecored) {
            if (footerState == FooterView.RELEASE_To_REFRESH)
            {
                Log.w("footerTouchMoveEvent",tempY+"==RELEASE_To_REFRESH");
                if (((-tempY + footerStartY) / FooterView.RATIO < footerView.getFootContentHeight())
                        && (-tempY + footerStartY) > 0) {
                    footerState = FooterView.PULL_To_REFRESH;
                    changeHeaderViewByState();
                }
                else if (-tempY + footerStartY <= 0) {
                    footerState = FooterView.DONE;
                    changeHeaderViewByState();
                }
            }
            if (footerState == FooterView.PULL_To_REFRESH)
            {   Log.w("footerTouchMoveEvent",tempY+"==PULL_To_REFRESH");
                if ((-tempY + footerStartY) / FooterView.RATIO >= footerView.getFootContentHeight())
                {
                    footerState = FooterView.RELEASE_To_REFRESH;
                    isFooterBack = true;
                    changeHeaderViewByState();
                }
                else if (- tempY + footerStartY <= 0)
                {
                    footerState = FooterView.DONE;
                    changeHeaderViewByState();
                }
            }
            if (footerState == FooterView.DONE)
            {Log.w("footerTouchMoveEvent",tempY+"==DONE");
                if (-tempY + footerStartY > 0)
                {
                    footerState = FooterView.PULL_To_REFRESH;
                    changeHeaderViewByState();
                }
            }
            if (footerState == FooterView.PULL_To_REFRESH)
            {Log.w("footerTouchMoveEvent",tempY+"PULL_To_REFRESH");
                footerView.setPullRefreshPadding(tempY, footerStartY);
            }
            if (footerState == FooterView.RELEASE_To_REFRESH)
            {Log.w("footerTouchMoveEvent",tempY+"RELEASE_To_REFRESH");
                footerView.setReleaseRefreshPadding(tempY,footerStartY);
            }
        }
    }

    public void headerTouchMoveEvent(int tempY){
        if (!isHeaderRecored && firstItemIndex == 0) {
            isHeaderRecored = true;
            headerStartY = tempY;
        }
        if (headerState != HeaderView.REFRESHING && isHeaderRecored) {
            if (headerState == HeaderView.RELEASE_To_REFRESH)
            {
                setSelection(0);
                if (((tempY - headerStartY) / HeaderView.RATIO < headerView.getHeadContentHeight())
                        && (tempY - headerStartY) > 0) {
                    headerState = HeaderView.PULL_To_REFRESH;
                    changeHeaderViewByState();
                }
                else if (tempY - headerStartY <= 0) {
                    headerState = HeaderView.DONE;
                    changeHeaderViewByState();
                }
            }
            if (headerState == HeaderView.PULL_To_REFRESH)
            {
                setSelection(0);
                if ((tempY - headerStartY) / HeaderView.RATIO >= headerView.getHeadContentHeight())
                {
                    headerState = HeaderView.RELEASE_To_REFRESH;
                    isHeaderBack = true;
                    changeHeaderViewByState();
                }
                else if (tempY - headerStartY <= 0)
                {
                    headerState = HeaderView.DONE;
                    changeHeaderViewByState();
                }
            }
            if (headerState == HeaderView.DONE)
            {
                if (tempY - headerStartY > 0)
                {
                    headerState = HeaderView.PULL_To_REFRESH;
                    changeHeaderViewByState();
                }
            }
            if (headerState == HeaderView.PULL_To_REFRESH)
            {
                headerView.setPullRefreshPadding(tempY,headerStartY);
            }
            if (headerState == HeaderView.RELEASE_To_REFRESH)
            {
                headerView.setReleaseRefreshPadding(tempY,headerStartY);
            }
        }
    }

}
