package tmall.DAO;

import tmall.bean.Property;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/*
Property的查 操作 都要基于 cid
 */
public class PropertyDAO {
    public int getTotal(int cid) {
        String sql = "select count(*) from property where cid = " + cid;
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

    public void add(Property bean) {
        String sql = "insert into property values(null,?,?)";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, bean.getCategory().getId());
            preparedStatement.setString(2, bean.getName());
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
        String sql = "delete from property where id =" + id;
        try (Connection connection = DBUtil.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Property bean) {
        String sql = "update property set name = ? , set cid = ? where id = ?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, bean.getName());
            preparedStatement.setInt(2, bean.getCategory().getId());
            preparedStatement.setInt(3, bean.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Property> list(int cid) {
        return list(cid,0, getTotal(cid));
    }

    public List<Property> list(int cid, int start, int count) {
        List<Property> beans = new ArrayList<>();
        String sql = "select * from property where cid = ? order by id desc limit ?,?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, cid);
            preparedStatement.setInt(2, start);
            preparedStatement.setInt(3, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Property bean = new Property();
                bean.setId(resultSet.getInt(1));
                bean.setName(resultSet.getString(2));
                beans.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }
}
