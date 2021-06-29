package org.example.Controllers;

import org.example.Dao.EmployeeDao;
import org.example.Models.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeDao employeeDao;

    public EmployeeController(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @GetMapping()
    public String showAllStaff(Model model){
        model.addAttribute("staff", employeeDao.showAll());
        return "/ShowAll";
    }

    @GetMapping("/root")
    public String rootPage(Model model, Employee employee){
        model.addAttribute("filterEmployee", employee);
        return "/RootPage";
    }

    @PostMapping("/filter")
    public String filter(@ModelAttribute("filter") Employee employee, Model model){
        model.addAttribute("filter", employeeDao.filterNumberWorkDays(employee));
        return "/FilterPage";
    }

    @GetMapping("/{id}/update")
    public String preparingForUpdate(@PathVariable("id") int id, Model model){
        model.addAttribute("oneOfStaff", employeeDao.preparingForUpdateEmployee(id));
        return "/ShowOne";
    }

    @GetMapping("{id}/delete")
    public String preparingForDelete(@PathVariable("id")int id, Model model){
        model.addAttribute("employeeId",id);
        return "/PreparingToDelete";
    }

    @PatchMapping("/{id}")
    public String updateEmployee(@PathVariable("id") int id, @ModelAttribute("employee") Employee employee){
        employeeDao.update(id, employee);
        return "redirect:/employee";
    }

    @DeleteMapping("/{id}")
    public String PreparingForDelete(@PathVariable("id") int id){
        employeeDao.delete(id);
        return "redirect:/employee";
    }

    @GetMapping("/new")
    public String newEmployeePage(@ModelAttribute("newEmployee")Employee employee){
        return "/CreateNewEmployee";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("newEmployee") Employee newEmployee){
        employeeDao.createNewEmployee(newEmployee);
        return "redirect:/employee";
    }

    @GetMapping("/exportSheetExcel")
    public String export(){
        employeeDao.exportSheetToExcel();
        return "redirect:/employee";
    }
}
