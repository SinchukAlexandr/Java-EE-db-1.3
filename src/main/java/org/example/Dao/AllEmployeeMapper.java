package org.example.Dao;

import org.example.Models.Employee;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AllEmployeeMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
        Employee employee = new Employee();

        employee.setId(resultSet.getInt("id"));
        employee.setName(resultSet.getString("name"));
        employee.setLogin(resultSet.getString("login"));
        employee.setPassword(resultSet.getString("password"));
        employee.setWork_date(resultSet.getDate("work_date"));
        employee.setInn(resultSet.getInt("inn"));
        employee.setSalary(resultSet.getInt("salary"));
        employee.setBirthday_date(resultSet.getDate("birthday_date"));
        employee.setMobile_number(resultSet.getString("mobile_number"));
        employee.setE_mail(resultSet.getString("e_mail"));
        employee.setAge(resultSet.getString("age"));
        employee.setTotal(resultSet.getInt("total"));
        return employee;
    }
}
