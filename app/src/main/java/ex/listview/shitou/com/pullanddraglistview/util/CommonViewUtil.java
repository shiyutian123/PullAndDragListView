package ex.listview.shitou.com.pullanddraglistview.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by shitou on 15/6/10.
 */
public class CommonViewUtil {
    private Context mContext ;
    private CommonViewUtil(){};

    public static CommonViewUtil getInstance(Context context){
        CommonViewUtil commonViewUtil = new CommonViewUtil();
        commonViewUtil.mContext = context;
        return commonViewUtil;
    }

    public View findViewByIdFromLayout(int layoutId,int id){
        if(mContext == null){
            return null ;
        }
        LayoutInflater layoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutView = layoutInflater.inflate(layoutId,null,false);
        View view = null;
        if(layoutView != null && layoutView instanceof ViewGroup){
            view = layoutView.findViewById(id);
            ViewGroup viewGroup = (ViewGroup)layoutView;
            viewGroup.removeAllViews();
        }
        return view ;
    }


}
