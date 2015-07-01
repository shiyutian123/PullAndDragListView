package ex.listview.shitou.com.pullanddraglistview.adapater;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by shitou on 15/6/8.
 */
public class AdapterViewBound {
    //储存所有视图的数组 key:value型 key是R.id.viewId value是View
    private final SparseArray<View> views ;

    //listview里面的position
    private int position ;
    //listview显示的布局
    private int layoutId ;
    //context显示的activity
    private Context context ;
    //listview显示的布局的view
    private View layoutView ;
    //存储最后一次从layoutView里面取得的值
    Object associatedObject;

    /**
     * 根据选中值的view,初始化AdapterViewBound
     * @param context
     * @param parent
     * @param layoutId
     * @param position
     */
    protected AdapterViewBound(Context context, ViewGroup parent,
                               int layoutId, int position){
        this.context = context ;
        this.layoutId = layoutId ;
        this.position = position ;
        this.views = new SparseArray<View>();

        this.layoutView = LayoutInflater.from(context).inflate(layoutId,parent,false);
        layoutView.setTag(this);
    }

    /**
     * 获得当前选中行的,并初始化当前选中行的值
     * @param context
     * @param layoutView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static AdapterViewBound getInstance(Context context, View layoutView,
                                                ViewGroup parent, int layoutId, int position){

        if(layoutView == null){
            return new AdapterViewBound(context,parent,layoutId,position);
        }

        AdapterViewBound adapterViewBound = (AdapterViewBound)layoutView.getTag();
        if(adapterViewBound.layoutId != layoutId){
            return new AdapterViewBound(context,parent,layoutId,position);
        }

        adapterViewBound.position = position ;
        return adapterViewBound;
    }

    /**
     * 获得默认的行，默认行为-1
     * @param context
     * @param layoutView
     * @param parent
     * @param layoutId
     * @return
     */
    public static AdapterViewBound getInstance(Context context, View layoutView,
                                               ViewGroup parent, int layoutId){
        return getInstance(context,layoutView,parent,layoutId,-1);
    }

    /**
     * 将viewId对应的view加入到views中
     * @param viewId
     * @param <T> view的子类
     * @return
     */
    protected <T extends View> T retrieveView(int viewId){
        View view = views.get(viewId);
        if (view == null)
        {
            view = layoutView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置文本的值
     * @param id
     * @param value
     * @return
     */
    public AdapterViewBound setText(int id ,CharSequence value){
        TextView textView = (TextView)retrieveView(id);
        textView.setText(value);
        return this ;
    }

    /**
     * 设置图片
     * @param id
     * @param imgRes
     * @return
     */
    public AdapterViewBound setImageResource(int id , int imgRes){
        ImageView imageView = (ImageView) retrieveView(id);
        imageView.setImageResource(imgRes);
        return this ;
    }

    public AdapterViewBound setImageResource(int id , Bitmap imgRes){
        ImageView imageView = (ImageView) retrieveView(id);
        imageView.setImageBitmap(imgRes);
        return this ;
    }

    public AdapterViewBound setImageResource(int id , Drawable drawable){
        ImageView imageView = (ImageView) retrieveView(id);
        imageView.setImageDrawable(drawable);
        return this ;
    }

    /**
     * 设置背景图片
     * @param viewId
     * @param drawable
     * @return
     */
    public AdapterViewBound setBackgroundRes(int viewId, Drawable drawable){
        View view = retrieveView(viewId);
        view.setBackground(drawable);
        return this;
    }

    /**
     * 设置背景图片
     * @param viewId
     * @param backgroundRes
     * @return
     */
    public AdapterViewBound setBackgroundRes(int viewId, int backgroundRes){
        View view = retrieveView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    /**
     * 设置背景颜色
     * @param viewId
     * @param color
     * @return
     */
    public AdapterViewBound setBackgroundColor(int viewId, int color){
        View view = retrieveView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public AdapterViewBound setBackgroundColorRes(int viewId, int backgroundColorRes){
        View view = retrieveView(viewId);
        view.setBackgroundColor(context.getResources().getColor(backgroundColorRes));
        return this;
    }

    /**
     * 设置文本颜色
     * @param viewId
     * @param textColor
     * @return
     */
    public AdapterViewBound setTextColor(int viewId, int textColor){
        TextView view = retrieveView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    /**
     * 设置文本颜色
     * @param viewId
     * @param textColorRes
     * @return
     */
    public AdapterViewBound setTextColorRes(int viewId, int textColorRes){
        TextView view = retrieveView(viewId);
        view.setTextColor(context.getResources().getColor(textColorRes));
        return this;
    }

    /**
     * 设置组件透明度
     * @param viewId
     * @param value
     * @return
     */
    public AdapterViewBound setAlpha(int viewId, float value){
        /**
         * 版本号
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            retrieveView(viewId).setAlpha(value);
        } else
        {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            retrieveView(viewId).startAnimation(alpha);
        }
        return this;
    }

    /**
     * 设置view的可见状态
     * @param viewId
     * @param visible
     * @return
     */
    public AdapterViewBound setVisible(int viewId, boolean visible){
        View view = retrieveView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 设置文本中链接
     * @param viewId
     * @return
     */
    public AdapterViewBound linkify(int viewId){
        TextView view = retrieveView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    /**
     * 字体设置 系统默认字体 “sans”, “serif”, “monospace"
     * @param viewId
     * @param typeface
     * @return
     *
    在Android中可以引入其他字体，首先要将字体文件保存在assets/fonts/目录下
    Android:text="Hello,World"
    Android:textSize="20sp" />

    java程序中引入其他字体关键代码
    TextView textView =(TextView)findViewById(R.id.custom);  　　
    Typeface typeFace =Typeface.createFromAsset(getAssets(),"fonts/HandmadeTypewriter.ttf");
    textView.setTypeface(typeFace);
    */
    public AdapterViewBound setTypeface(int viewId, Typeface typeface){
        TextView view = retrieveView(viewId);
        view.setTypeface(typeface);
        view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        return this;
    }

    /**
     * 设置progress状态
     * @param viewId
     * @param progress
     * @return
     */
    public AdapterViewBound setProgress(int viewId, int progress){
        ProgressBar view = retrieveView(viewId);
        view.setProgress(progress);
        return this;
    }

    public AdapterViewBound setProgress(int viewId, int progress, int max){
        ProgressBar view = retrieveView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public AdapterViewBound setMax(int viewId, int max){
        ProgressBar view = retrieveView(viewId);
        view.setMax(max);
        return this;
    }

    /**
     * 设置选择五角星打分组件值
     * @param viewId
     * @param rating
     * @return
     */
    public AdapterViewBound setRating(int viewId, float rating){
        RatingBar view = retrieveView(viewId);
        view.setRating(rating);
        return this;
    }

    public AdapterViewBound setRating(int viewId, float rating, int max){
        RatingBar view = retrieveView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    /**
     * 设置组件的tag
     * @param viewId
     * @param tag
     * @return
     */
    public AdapterViewBound setTag(int viewId, Object tag){
        View view = retrieveView(viewId);
        view.setTag(tag);
        return this;
    }

    public AdapterViewBound setTag(int viewId, int key, Object tag){
        View view = retrieveView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public AdapterViewBound setChecked(int viewId, boolean checked){
        Checkable view = (Checkable) retrieveView(viewId);
        view.setChecked(checked);
        return this;
    }

    /**
     * 设置adapter
     * @param viewId
     * @param adapter
     * @return
     */
    public AdapterViewBound setAdapter(int viewId, Adapter adapter){
        AdapterView view = retrieveView(viewId);
        view.setAdapter(adapter);
        return this;
    }

    /**
     * 设置监听
     * @param viewId
     * @param listener
     * @return
     */
    public AdapterViewBound setOnClickListener(int viewId, View.OnClickListener listener){
        View view = retrieveView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    /**
     * 设置touch时间
     * @param viewId
     * @param listener
     * @return
     */
    public AdapterViewBound setOnTouchListener(int viewId, View.OnTouchListener listener){
        View view = retrieveView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public AdapterViewBound addView(int viewId){
        View view = views.get(viewId);
        if (view == null){
            view = layoutView.findViewById(viewId);
            views.put(viewId, view);
        }
        return this ;
    }

    public AdapterViewBound addView(View view){
        if (view == null){
            views.put(view.getId(), view);
        }
        return this ;
    }

    public AdapterViewBound removeView(int viewId){
        View view = views.get(viewId);
        if (view != null){
            views.remove(viewId);
        }
        return this ;
    }

    public AdapterViewBound removeView(View view){
        if (view != null){
            views.remove(view.getId());
        }
        return this ;
    }


    public AdapterViewBound setOnLongClickListener(int viewId,View.OnLongClickListener listener){
        View view = retrieveView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    public View getView(){
        return this.layoutView;
    }

    public int getPosition(){
        if (position == -1)
            throw new IllegalStateException(
                    "Use AdapterViewBound constructor "
                            + "with position if you need to retrieve the position.");
        return position;
    }

    public Object getAssociatedObject(){
        return associatedObject;
    }

    public void setAssociatedObject(Object associatedObject){
        this.associatedObject = associatedObject;
    }

    public <T extends View> T getView(int viewId){
        return retrieveView(viewId);
    }
}
