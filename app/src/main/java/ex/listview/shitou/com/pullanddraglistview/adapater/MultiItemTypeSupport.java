package ex.listview.shitou.com.pullanddraglistview.adapater;

/**
 * Created by shitou on 15/6/10.
 */
public interface MultiItemTypeSupport<T> {
    /**
     * 获得根据当前实体Type不同，获得实体所对应的布局
     * @param position
     * @param t
     * @return
     */
    int getLayoutId(int position, T t);

    /**
     * 实体共有多少种Type
     * @return
     */
    int getViewTypeCount();

    /**
     * 获得当前T的类型
     * @param postion
     * @param t
     * @return
     */
    int getItemViewType(int postion, T t);
}
