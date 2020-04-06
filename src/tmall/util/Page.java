package tmall.util;

public class Page {
    private int start;  //这一页的第一个数据 是总数据的第多少个（从0开始）
    private int count;  //每页多少个数据
    private int total;  //总共有多少数据

    public Page(int start,int count){
        this.start = start;
        this.count = count;
    }

    public boolean isHasPrevious(){
        if (0 == start) return false;
        return true;
    }

    public boolean isHasNext(){
        if (start == getLast()) return false;
        else return true;
    }

    public int getTotalPage(){
        int totalPage;
        if (0 == total % count) totalPage = total / count;
        else totalPage = total / count + 1;
        if (0 == totalPage) totalPage = 1;    //说明没有数据，那么总页数也要是1
        return totalPage;
    }
//计算最后一页的第一个数据是第几个数据(从0开始)
    public int getLast(){
        int last;
        //// 假设总数是50，是能够被5整除的，那么最后一页的开始就是45.     45 46 47 48 49
        if (0 == total % count) last = total - count;
        else last = count * (total / count);
        last = last < 0 ? 0 : last;  // 当total 小于 count时，last会负，此时最后一页也是从第0个数据开始
        return last;

    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
