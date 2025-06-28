package integration.tests;

import asd.newproject.second.*;
import ge.tbc.testautomation.db.models.Employee;
import ge.tbc.testautomation.steps.integration.IntegrationEmployeeSteps;
import ge.tbc.testautomation.util.Marshall;
import ge.tbc.testautomation.util.Unmarshall;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static ge.tbc.testautomation.config.DataBaseConfig.dbMapper;
import static ge.tbc.testautomation.data.Constants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SoapValidationTests {
    private IntegrationEmployeeSteps employeeSteps = new IntegrationEmployeeSteps();
    @Description("validate insertion of employeee")
    @Severity(SeverityLevel.CRITICAL)
    @Test()
    public void testInsertEmployee() {
        Employee employee = new Employee(LocalDate.parse("2004-05-15"),99999L, 140L,
                UPDATED_ADDRESS,DEP,RAND_MAIL,NAME,RAND_PHONE);
        dbMapper().insertEmployee(employee);

        GetEmployeeByIdRequest req = employeeSteps.createGetEmployeeByIdReq(140);
        String body = Marshall.marshallSoapRequest(req);

        System.out.println(body);

        Response resp = employeeSteps.sendService(LOCAL_URL, GETBYID_ACT, body);

        GetEmployeeByIdResponse employeeByIdResponse = Unmarshall.unmarshallResponse(resp.body().asString(), GetEmployeeByIdResponse.class);

        employeeSteps.validateEmplName(employeeByIdResponse,NAME)
                .validateEmplMail(employeeByIdResponse,RAND_MAIL)
                .validateEmplAddress(employeeByIdResponse,UPDATED_ADDRESS)
                .validateEmplPhone(employeeByIdResponse,RAND_PHONE)
                .validateEmplDep(employeeByIdResponse,DEP)
                .validateEmplId(employeeByIdResponse,140);
    }
    @Description("test employee update request")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 2)
    public void testUpdateEmployee() {
        EmployeeInfo employeeInfo = employeeSteps.createEmployee(UPDATED_NAME, 140, UPDATED_DEP, UPDATED_ADDRESS);
        UpdateEmployeeRequest req = employeeSteps.createUpdateEmployeeRequest(employeeInfo);
        String body = Marshall.marshallSoapRequest(req);
        Response resp = employeeSteps.sendService(LOCAL_URL, UPDATE_ACT, body);
        UpdateEmployeeResponse employeeResponse = employeeSteps.getUpdateEmployeeResponse(resp);
        employeeSteps.validateUpdateEmployeeResponseStatus(employeeResponse)
                .validateUpdateEmployeeResponseMessage(employeeResponse);

        Employee updatedEmployee = employeeSteps.returnEmployeeFromDB(140);

        employeeSteps
                .validateEmployeeId(updatedEmployee,140)
                .validateEmployeeName(updatedEmployee,UPDATED_NAME)
                .validateEmployeeDep(updatedEmployee,UPDATED_DEP)
                .validateEmployeeAdress(updatedEmployee,UPDATED_ADDRESS);

    }
    @Description("update employee from DataBase")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 2)
    public void testUpdateEmployeeDB() {

        employeeSteps.updateEmployeeFromDB(140L,UPDATED_NAME,UPDATED_MAIL,UPDATED_ADDRESS,UPDATED_DEP,
                UPDATED_PHONE,UPDATED_SALARY,LocalDate.now().minusYears(20));

        GetEmployeeByIdRequest req = employeeSteps.createGetEmployeeByIdReq(140);
        String body = Marshall.marshallSoapRequest(req);

        Response resp = employeeSteps.sendService(LOCAL_URL, GETBYID_ACT, body);

        GetEmployeeByIdResponse updatedEmployee = Unmarshall.unmarshallResponse(resp.body().asString(), GetEmployeeByIdResponse.class);

        employeeSteps.validateEmplName(updatedEmployee,UPDATED_NAME)
                .validateEmplMail(updatedEmployee,UPDATED_MAIL)
                .validateEmplAddress(updatedEmployee,UPDATED_ADDRESS)
                .validateEmplPhone(updatedEmployee,UPDATED_PHONE)
                .validateEmplDep(updatedEmployee,UPDATED_DEP)
                .validateEmplId(updatedEmployee,140);

    }
    @Description("test deleting emloyee request")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 3)
    public void testDeleteEmployee() {
        int initialCount = dbMapper().count();

        DeleteEmployeeRequest req = employeeSteps.createDeleteEmployeeRequest(140L);
        String body = Marshall.marshallSoapRequest(req);

        Response resp = employeeSteps.sendService(LOCAL_URL, DELETE_ACT, body);

        DeleteEmployeeResponse employeeResponse = employeeSteps.getDeleteEmployeeResponse(resp);

        employeeSteps.validateDeleteSuccessfull(employeeResponse);


        GetEmployeeByIdRequest getEmployeeByIdRequest = employeeSteps.createGetEmployeeByIdReq(140L);
        String getBodyForIdRequest = Marshall.marshallSoapRequest(getEmployeeByIdRequest);
        Response errorResponse = employeeSteps.sendService(LOCAL_URL, GETBYID_ACT, getBodyForIdRequest);


        employeeSteps.validateStatusCode500(errorResponse)
                .validateErrorMessage(errorResponse);

        assertThat(dbMapper().count(), equalTo(initialCount -1));

    }




}
