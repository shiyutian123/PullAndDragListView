package ex.listview.shitou.com.pullanddraglistview.adapater;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 简单的Adapter实现,支持多类型布局
 * @param <T> adapter中需要存入的实体对象
 */
public abstract class SimpleImplAdapter<T> extends SimpleBaseAdapter<T,AdapterViewBound> {
    public SimpleImplAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    public SimpleImplAdapter(Context context, int layoutResId ,List<T> data){
        super(context,layoutResId,data);
    }

    public SimpleImplAdapter(Context context,List<T> data,MultiItemTypeSupport<T> multiItemTypeSupport){
        super(context,data,multiItemTypeSupport);
        if(multiItemTypeSupport != null){
            isSupportMultiItem = true ;
            this.multiItemTypeSupport = multiItemTypeSupport;
        }
    }

    @Override
    protected AdapterViewBound getAdapterViewBound(int position, View convertView, ViewGroup parent) {
       if(isSupportMultiItem && multiItemTypeSupport != null){
           return AdapterViewBound.getInstance(context,convertView,parent,multiItemTypeSupport.getLayoutId(position,getItem(position)),position);
       }
       return AdapterViewBound.getInstance(context,convertView,parent,layoutResId,position);
    }
}
