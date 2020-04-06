package tmall.bean;

import java.util.List;

/*
products：属于该类的产品
productsByRow：提供这个属性，用于在首页竖状导航的分类名称右边显示产品列表。
 */
public class Category {
    private int id;
    private String name;
    private List<Product> products;
    private List<List<Product>> productsByRow;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<List<Product>> getProductsByRow() {
        return productsByRow;
    }

    public void setProductsByRow(List<List<Product>> productsByRow) {
        this.productsByRow = productsByRow;
    }

    @Override
    public String toString(){
        return "Category[name = '" + name + "']";
    }
}
