package org.example.Dao;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.Models.Employee;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class EmployeeDao {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Employee> showAll() {

        return jdbcTemplate.query("SELECT users.id, users.name, users.login, users.password, users.birthday_date," +
                " users.work_date, users.inn, users.salary, contacts.mobile_number, contacts.e_mail," +
                " (current_date-birthday_date)/365 AS age FROM users INNER JOIN contacts ON users.id = contacts.id" +
                " ORDER BY id ASC;", new BeanPropertyRowMapper<>(Employee.class));

    }

    public Employee preparingForUpdateEmployee(int id) {

        return jdbcTemplate.query("SELECT users.id, users.name, users.login, users.password, users.birthday_date," +
                "                users.work_date, users.inn, users.salary, contacts.mobile_number, contacts.e_mail" +
                "                FROM users INNER JOIN contacts ON users.id = contacts.id WHERE users.id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Employee.class)).stream().findAny().orElse(null);
    }

    public void update(int id, Employee updateEmployee) {

        jdbcTemplate.update("UPDATE users SET name = ?, login = ?, password = ?, birthday_date = ?," +
                        "work_date = ?, inn = ?, salary = ? WHERE id = ?", updateEmployee.getName(),
                updateEmployee.getLogin(), updateEmployee.getPassword(), updateEmployee.getBirthday_date(),
                updateEmployee.getWork_date(), updateEmployee.getInn(), updateEmployee.getSalary(), id);

        jdbcTemplate.update("UPDATE contacts SET mobile_number = ?, e_mail = ? WHERE id = ?",
                updateEmployee.getMobile_number(), updateEmployee.getE_mail(), id);
    }

    public void delete(int id) {

        jdbcTemplate.update("DELETE FROM contacts WHERE id=?", id);
        jdbcTemplate.update("DELETE FROM users WHERE id=?", id);
    }

    public List<Employee> filterNumberWorkDays(Employee employee) {

        return jdbcTemplate.query("SELECT * FROM (SELECT users.id, users.name, users.login, users.password," +
                        " users.birthday_date, users.work_date, users.inn, users.salary, contacts.mobile_number," +
                        " contacts.e_mail,(current_date-birthday_date)/365 AS age, current_date - work_date AS total" +
                        " FROM users INNER JOIN contacts ON users.id = contacts.id) AS TWD WHERE total <'" + employee.getTotal() + "'",
                new AllEmployeeMapper());
    }

    public void createNewEmployee(Employee newEmployee) {
        jdbcTemplate.update("INSERT INTO users (name, login, password, birthday_date, work_date, inn, salary)" +
                        " VALUES (?,?,?,?,?,?,?)", newEmployee.getName(), newEmployee.getLogin(), newEmployee.getPassword(),
                newEmployee.getBirthday_date(), newEmployee.getWork_date(), newEmployee.getInn(), newEmployee.getSalary());

        jdbcTemplate.update("INSERT INTO contacts (id, mobile_number, e_mail) VALUES ((SELECT  MAX(id) FROM users)," +
                "?,?)", newEmployee.getMobile_number(), newEmployee.getE_mail());
    }

    public void exportSheetToExcel() {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(" Employee Info ");
        XSSFRow row;
        Cell cell;

        int rowNumHead = 0;

        row = sheet.createRow(rowNumHead);

        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("ФИО");
        row.createCell(2).setCellValue("Логин");
        row.createCell(3).setCellValue("Пароль");
        row.createCell(4).setCellValue("Дата рождения");
        row.createCell(5).setCellValue("Дата приема на работу");
        row.createCell(6).setCellValue("ИНН");
        row.createCell(7).setCellValue("Заработная плата");
        row.createCell(8).setCellValue("Номер телефона");
        row.createCell(9).setCellValue("e_mail");
        row.createCell(10).setCellValue("Возраст (полных лет)");

        List<Employee> employeeList = jdbcTemplate.query("SELECT users.id, users.name, users.login, users.password, users.birthday_date, " +
                "users.work_date, users.inn, users.salary, contacts.mobile_number, " +
                "contacts.e_mail, + (current_date-birthday_date)/365 AS age FROM users INNER JOIN contacts" +
                " ON users.id = contacts.id ORDER BY id ASC", new BeanPropertyRowMapper<>(Employee.class));

        int rowNum = rowNumHead + 1;

        for (Employee employee : employeeList) {
            rowNum++;
            row = sheet.createRow(rowNum);

            cell = row.createCell(0);
            cell.setCellValue(employee.getId());

            cell = row.createCell(1);
            cell.setCellValue(employee.getName());

            cell = row.createCell(2);
            cell.setCellValue(employee.getLogin());

            cell = row.createCell(3);
            cell.setCellValue(employee.getPassword());

            cell = row.createCell(4);
            cell.setCellValue(employee.getBirthday_date());

            cell = row.createCell(5);
            cell.setCellValue(employee.getWork_date());

            cell = row.createCell(6);
            cell.setCellValue(employee.getInn());

            cell = row.createCell(7);
            cell.setCellValue(employee.getSalary());

            cell = row.createCell(8);
            cell.setCellValue(employee.getMobile_number());

            cell = row.createCell(9);
            cell.setCellValue(employee.getE_mail());

            cell = row.createCell(10);
            cell.setCellValue(employee.getAge());
        }

        JButton jButton = new JButton();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showSaveDialog(jButton);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (fileChooser.getSelectedFile() == null) {
        } else {
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(new File(fileChooser.getSelectedFile().getAbsolutePath() + ".xlsx"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            try {
                workbook.write(out);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



