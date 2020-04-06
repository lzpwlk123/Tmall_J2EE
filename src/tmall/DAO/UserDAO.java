package tmall.DAO;

import tmall.bean.User;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/*
除开CRUD之外，UserDAO需要支持一些特殊的业务：
1.注册时，判断用户名是否已经存在。

2.登录时，根据提供的用户名和密码进行判断
 */
public class UserDAO {
    public int getTotal() {
        String sql = "select count(*) from user";
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

    public void add(User bean) {
        String sql = "insert into user values(null,?,?)";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, bean.getName());
            preparedStatement.setString(2,bean.getPassword());
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
        String sql = "delete from user where id =" + id;
        try (Connection connection = DBUtil.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(User bean) {
        String sql = "update user set name = ?, password = ? where id = ?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, bean.getName());
            preparedStatement.setString(2, bean.getPassword());
            preparedStatement.setInt(3, bean.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> list() {
        return list(0, getTotal());
    }

    public List<User> list(int start, int count) {
        List<User> beans = new ArrayList<>();
        String sql = "select * from user order by id desc  limit ?,?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User bean = new User();
                bean.setId(resultSet.getInt(1));
                bean.setName(resultSet.getString(2));
                bean.setPassword(resultSet.getString(3));
                beans.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }
    public boolean isExist(String name) {
        User user = get(name);
        return user!=null;

    }

    public User get(String name) {
        User bean = null;

        String sql = "select * from User where name = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs =ps.executeQuery();

            if (rs.next()) {
                bean = new User();
                int id = rs.getInt("id");
                bean.setName(name);
                String password = rs.getString("password");
                bean.setPassword(password);
                bean.setId(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public User get(String name, String password) {
        User bean = null;

        String sql = "select * from User where name = ? and password=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, password);
            ResultSet rs =ps.executeQuery();

            if (rs.next()) {
                bean = new User();
                bean.setName(name);
                bean.setPassword(password);
                bean.setId(rs.getInt("id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }
}
