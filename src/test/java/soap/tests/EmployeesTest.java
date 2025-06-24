package soap.tests;

import asd.newproject.second.*;
import ge.tbc.testautomation.steps.soap.EmployeesSteps;
import ge.tbc.testautomation.util.Marshall;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.Constants.*;

@Feature("Employes API Tests")
public class EmployeesTest {
    private final EmployeesSteps employeesSteps = new EmployeesSteps();

    @Description("test addEmployee request and validate correct message is returned")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void addEmployeeTest() {
        EmployeeInfo employeeInfo = employeesSteps.createEmployee(NAME, TESTID, DEP, RAND_ADDR);
        AddEmployeeRequest addEmployeeRequest = employeesSteps.createAddEmployeeRequest(employeeInfo);

        String body = Marshall.marshallSoapRequest(addEmployeeRequest);

        Response response = employeesSteps.sendService(LOCAL_URL, ADD_ACT, body);

        employeesSteps.validateStatusCode200(response)
                .validateMessage(response, ADDED_MESSAGE);
    }

    @Description("get employee by id  request and validate correct message is and fields returned")
    @Severity(SeverityLevel.CRITICAL)
    @Test(dependsOnMethods = "addEmployeeTest")
    public void getEmployeeByIdTest() {
        GetEmployeeByIdRequest req = employeesSteps.createGetEmployeeByIdReq(TESTID);
        String body = Marshall.marshallSoapRequest(req);

        Response resp = employeesSteps.sendServiceWithNS(LOCAL_URL, GETBYID_ACT, body);


        employeesSteps.validateStatusCode200(resp).validateId(resp, TESTID)
                .validateName(resp, NAME)
                .validateDepartment(resp, DEP)
                .validateAddress(resp, RAND_ADDR);
    }

    @Description("update employee and validate fields was updated")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void updateEmployeeTest() {
        EmployeeInfo employeeInfo = employeesSteps.createEmployee(UPDATED_NAME, NEW_TESTID, UPDATED_DEP, UPDATED_ADDRESS);
        UpdateEmployeeRequest req = employeesSteps.createUpdateEmployeeRequest(employeeInfo);
        String body = Marshall.marshallSoapRequest(req);
        Response resp = employeesSteps.sendServiceWithNS(LOCAL_URL, UPDATE_ACT, body);


        GetEmployeeByIdRequest getEmployeeByIdRequest = employeesSteps.createGetEmployeeByIdReq(NEW_TESTID);
        String getBodyForIdRequest = Marshall.marshallSoapRequest(getEmployeeByIdRequest);
        Response updatedResponse = employeesSteps.sendServiceWithNS(LOCAL_URL, GETBYID_ACT, getBodyForIdRequest);

        employeesSteps.validateMessage(resp, UPDATED_MESSAGE) //validate message
                .validateId(updatedResponse, NEW_TESTID)               //validate getEmployeeById details are updated correctly.
                .validateName(updatedResponse, UPDATED_NAME)
                .validateDepartment(updatedResponse, UPDATED_DEP)
                .validateAddress(updatedResponse, UPDATED_ADDRESS);

    }

    @Description("delete employee and validate it was deleted")
    @Severity(SeverityLevel.CRITICAL)
    @Test(dependsOnMethods = "addEmployeeTest", priority = 4)
    public void deleteEmployeeTest() {
        DeleteEmployeeRequest req = employeesSteps.createDeleteEmployeeRequest(TESTID);
        String body = Marshall.marshallSoapRequest(req);

        Response resp = employeesSteps.sendServiceWithNS(LOCAL_URL, DELETE_ACT, body);

        GetEmployeeByIdRequest getEmployeeByIdRequest = employeesSteps.createGetEmployeeByIdReq(TESTID);
        String getBodyForIdRequest = Marshall.marshallSoapRequest(getEmployeeByIdRequest);
        Response errorResponse = employeesSteps.sendServiceWithNS(LOCAL_URL, GETBYID_ACT, getBodyForIdRequest);

        System.out.println(errorResponse.asString());

        employeesSteps.validateMessage(resp, DELETED_MESSAGE)
                .validateStatusCode500(errorResponse)
                .validateErrorMessage(errorResponse);

    }

}
