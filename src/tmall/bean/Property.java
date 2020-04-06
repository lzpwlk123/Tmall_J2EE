package tmall.bean;

public class Property {
    private int id;
//    private int cid;   在数据库中，属性与属性值是通过cid关联。
    private Category category;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
