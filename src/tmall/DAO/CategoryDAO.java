package tmall.DAO;

import tmall.bean.Category;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    public int getTotal() {
        String sql = "select count(*) from category";
        try (Connection connection = DBUtil.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(sql);
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void add(Category bean) {
        String sql = "insert into category values(null,?)";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, bean.getName());
            preparedStatement.execute();
//            ResultSet resultSet = preparedStatement.getResultSet(); 错
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                bean.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "delete from category where id =" + id;
        try (Connection connection = DBUtil.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Category bean) {
        String sql = "update category set name = ? where id = ?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, bean.getName());
            preparedStatement.setInt(2, bean.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Category> list() {
        return list(0, getTotal());
    }

    public List<Category> list(int start, int count) {
        List<Category> beans = new ArrayList<>();
        String sql = "select * from category order by id desc  limit ?,?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Category bean = new Category();
                bean.setId(resultSet.getInt(1));
                bean.setName(resultSet.getString(2));
                beans.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }

    public Category get(int id){
        String sql = "select * from category where id =" + id;
        Category bean = new Category();
        try(Connection connection = DBUtil.getConnection(); Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()){
                bean.setId(resultSet.getInt("id"));
                bean.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }


    public static void main(String[] args) {
        System.out.println(new CategoryDAO().getTotal());
    }
}