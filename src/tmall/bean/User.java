package tmall.bean;

public class User {
    private int id;
    private String name;
    private String password;

    /*
    返回匿名，用于评论功能
     */
    public String getAnonymousName(){
        if (null == name) return null;
        if (1 == name.length()) return "*";
        char[] chars = name.toCharArray();
        for (int i = 0; i < chars.length - 1; i++){
            chars[i] = '*';
        }
        return chars.toString();
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
