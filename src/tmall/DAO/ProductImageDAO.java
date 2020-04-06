package tmall.DAO;

import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductImageDAO {
    public int getTotal(int pid) {
        String sql = "select count(*) from productimage where pid =" + pid;
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

    public void add(ProductImage bean) {
        String sql = "insert into productimage values(null,?,?)";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, bean.getProduct().getId());
            preparedStatement.setString(2, bean.getType());
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
        String sql = "delete from productimage where id =" + id;
        try (Connection connection = DBUtil.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(ProductImage bean) {
//        String sql = "update productimage set name = ? where id = ?";
//        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setString(1, bean.getName());
//            preparedStatement.setInt(2, bean.getId());
//            preparedStatement.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    public List<ProductImage> list(Product product, String type) {
        return list(product,type ,0, getTotal(product.getId()));
    }

    public List<ProductImage> list(Product product, String type,int start, int count) {
        List<ProductImage> beans = new ArrayList<>();
        String sql = "select * from productimage where pid =? and type =? order by id desc limit ?,? ";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, product.getId());
            preparedStatement.setString(2, type);
            preparedStatement.setInt(3, start);
            preparedStatement.setInt(4, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ProductImage bean = new ProductImage();
                bean.setId(resultSet.getInt(1));
                bean.setProduct(product);
                bean.setType(resultSet.getString(3));
                beans.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }
}
