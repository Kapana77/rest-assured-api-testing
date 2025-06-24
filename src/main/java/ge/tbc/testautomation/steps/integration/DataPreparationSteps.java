package ge.tbc.testautomation.steps.integration;

import ge.tbc.testautomation.db.models.Employee;

import java.time.LocalDate;

import static ge.tbc.testautomation.config.DataBaseConfig.dbMapper;

public class DataPreparationSteps {


    public DataPreparationSteps insertEmployee(String mail,Long id) {
        Employee employee = new Employee(LocalDate.parse("2004-05-15"),99999L, id,
                "address","dep",mail,"somename","9999999");
        dbMapper().insertEmployee(employee);
        return this;
    }


}
