package ex.listview.shitou.com.pullanddraglistview.adapater;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import ex.listview.shitou.com.pullanddraglistview.R;
import ex.listview.shitou.com.pullanddraglistview.util.CommonViewUtil;

/**
 * Created by shitou on 15/6/5.
 */
public abstract class SimpleBaseAdapter<T,H extends AdapterViewBound> extends BaseAdapter implements Filterable{

    protected static final String TAG = SimpleBaseAdapter.class.getSimpleName();

    protected List<T> data;
    protected final Context context ;
    protected int layoutResId;

    //1312d01
    public static final int TOP_PROGRESS = 0x0;

    /**
     * 多种布局格式的解决方式
     */
    protected boolean isSupportMultiItem ;
    protected MultiItemTypeSupport<T> multiItemTypeSupport ;

    /**
     * 设置顶部的进度条是否显示
     */
    protected boolean showIndeterminateTopProcess;

    public SimpleBaseAdapter(Context context,int layoutResId){
        this(context,layoutResId,null);
    }

    public SimpleBaseAdapter(Context context,int layoutResId,List<T> data){
        super();
        this.context = context ;
        this.layoutResId = layoutResId;
        if(data == null){
            this.data = new ArrayList<T>();
        }else{
            this.data = data;
        }
    }

    public SimpleBaseAdapter(Context context,List<T> data,MultiItemTypeSupport multiItemTypeSupport){
        super();
        this.context = context ;
        if(data == null){
            this.data = new ArrayList<T>();
        }else{
            this.data = data;
        }
    }

    @Override
    public int getCount() {
        int extra = 0 ;
        if(showIndeterminateTopProcess){
            extra++;
        }
        return data == null  ? extra : data.size() + extra;
    }

    @Override
    public T getItem(int position) {
        if(data == null){
            return null ;
        }
        if(showIndeterminateTopProcess){
            if(position == 0){
                return null ;
            }
            if(position <= data.size()){
                return data.get(position-1);
            }else{
                return null ;
            }
        }
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getDataCount(){
        return data == null ? 0 : data.size();
    }

    @Override
    public int getItemViewType(int position){
        if(showIndeterminateTopProcess && position == 0){
            return TOP_PROGRESS;
        }
        if(multiItemTypeSupport != null){
            return multiItemTypeSupport.getItemViewType(position,getItem(position));
        }
        return 0;
    }

    public int getViewTypeCount(){
        int count = 1 ;
        if(showIndeterminateTopProcess){
            count ++ ;
        }
        if(multiItemTypeSupport != null){
            count += multiItemTypeSupport.getViewTypeCount();
        }
        return count ;
    }
    /**
     * adapter获得当前的position
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //置顶视图
        if(showIndeterminateTopProcess && getItemViewType(position) == TOP_PROGRESS){
            return generateProgressView(position,convertView,parent);
        }
        final H bound = getAdapterViewBound(position,convertView,parent);
        bound.getPosition();
        T item = getItem(position);
        bound.setAssociatedObject(item);
        convert(bound,item);
        return bound.getView() ;
    }

    private View generateProgressView(int position,View convertView,ViewGroup parent){
        if(convertView == null){
            FrameLayout container = new FrameLayout(context);
            container.setForegroundGravity(Gravity.CENTER);
            CommonViewUtil commonViewUtil = CommonViewUtil.getInstance(context);
            ProgressBar progressBar = (ProgressBar)commonViewUtil.findViewByIdFromLayout(R.layout.small_process_bar,R.id.freshProgressBar);
            container.addView(progressBar);
            convertView = container;
        }
        return  convertView;
    }

    protected abstract void convert(H bound, T item);

    protected abstract H getAdapterViewBound(int position, View convertView,ViewGroup parent);


    /**
     * 设置置顶的进度条是否显示
     * @param display
     */
    public void showIndeterminateProgress(boolean display){
        if (display == showIndeterminateTopProcess)
            return;
        showIndeterminateTopProcess = display;
        showIndeterminateTopProcess = false ;
        notifyDataSetChanged();
    }

    /**
     * 数据变动自动刷新
     * @param elem
     */
    public void add(T elem){
        data.add(elem);
        notifyDataSetChanged();
    }

    public void addAll(List<T> elem){
        if(elem != null){
            data.addAll(elem);
        }
        notifyDataSetChanged();
    }

    public void set(T oldElem, T newElem){
        set(data.indexOf(oldElem), newElem);
    }

    public void set(int index, T elem){
        data.set(index, elem);
        notifyDataSetChanged();
    }

    public void remove(T elem){
        data.remove(elem);
        notifyDataSetChanged();
    }

    public void remove(int index){
        data.remove(index);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> elem){
        data.clear();
        if(elem!=null) {
            data.addAll(elem);
        }
        notifyDataSetChanged();
    }

    public boolean contains(T elem){
        return data.contains(elem);
    }

    public void clear(){
        data.clear();
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

}
