package ex.listview.shitou.com.pullanddraglistview.view;

/**
 * Created by shitou on 15/6/11.
 */
public class Bean {
    private int id ;

    public Bean(int id, int type, String text) {
        this.id = id;
        this.type = type;
        this.text = text;
    }

    private int type ;
    private String text ;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
