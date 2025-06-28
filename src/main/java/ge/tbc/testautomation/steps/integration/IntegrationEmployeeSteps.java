package ge.tbc.testautomation.steps.integration;

import asd.newproject.second.*;
import ge.tbc.testautomation.db.models.Employee;
import ge.tbc.testautomation.util.SoapServiceSender;
import ge.tbc.testautomation.util.Unmarshall;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.time.LocalDate;

import static ge.tbc.testautomation.config.DataBaseConfig.dbMapper;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.xml.HasXPath.hasXPath;

public class IntegrationEmployeeSteps {
    ObjectFactory objectFactory = new ObjectFactory();

    @Step("Create employee info: {0}, id: {1}, department: {2}, address: {3}")
    public EmployeeInfo createEmployee(String firstName, long id, String dep, String address) {
        EmployeeInfo employeeInfo = objectFactory.createEmployeeInfo();
        employeeInfo.withEmployeeId(id)
                .withName(firstName)
                .withDepartment(dep)
                .withAddress(address);

        return employeeInfo;
    }

    @Step("Create AddEmployeeRequest with EmployeeInfo")
    public AddEmployeeRequest createAddEmployeeRequest(EmployeeInfo employeeInfo) {
        AddEmployeeRequest addEmployeeRequest = objectFactory.createAddEmployeeRequest();
        addEmployeeRequest.setEmployeeInfo(employeeInfo);
        return addEmployeeRequest;
    }

    @Step("Send SOAP service request to URL: {0} with action: {1}")
    public Response sendService(String url, String action, String body) {
        return SoapServiceSender.send(url, action, body)
                .then().log().all().extract().response();
    }

    @Step("Create GetEmployeeByIdRequest for employeeId: {0}")
    public GetEmployeeByIdRequest createGetEmployeeByIdReq(long employeeId) {
        GetEmployeeByIdRequest getEmployeeByIdRequest = objectFactory.createGetEmployeeByIdRequest();
        getEmployeeByIdRequest.setEmployeeId(employeeId);
        return getEmployeeByIdRequest;
    }

    @Step("Validate employee name is {1}")
    public IntegrationEmployeeSteps validateEmplName(GetEmployeeByIdResponse emp, String emplName) {
        assertThat(emp.getEmployeeInfo().getName(), equalTo(emplName));
        return this;
    }

    @Step("Validate employee id is {1}")
    public IntegrationEmployeeSteps validateEmplId(GetEmployeeByIdResponse emp, long id) {
        assertThat(emp.getEmployeeInfo().getEmployeeId(), equalTo(id));
        return this;
    }

    @Step("Validate employee salary is {1}")
    public IntegrationEmployeeSteps validateEmplSalary(GetEmployeeByIdResponse emp, Long salary) {
        assertThat(emp.getEmployeeInfo().getSalary(), equalTo(salary));
        return this;
    }

    @Step("Validate employee address is {1}")
    public IntegrationEmployeeSteps validateEmplAddress(GetEmployeeByIdResponse emp, String address) {
        assertThat(emp.getEmployeeInfo().getAddress(), equalTo(address));
        return this;
    }

    @Step("Validate employee department is {1}")
    public IntegrationEmployeeSteps validateEmplDep(GetEmployeeByIdResponse emp, String dep) {
        assertThat(emp.getEmployeeInfo().getDepartment(), equalTo(dep));
        return this;
    }

    @Step("Validate employee email is {1}")
    public IntegrationEmployeeSteps validateEmplMail(GetEmployeeByIdResponse emp, String email) {
        assertThat(emp.getEmployeeInfo().getEmail(), equalTo(email));
        return this;
    }

    @Step("Validate employee phone is {1}")
    public IntegrationEmployeeSteps validateEmplPhone(GetEmployeeByIdResponse emp, String phone) {
        assertThat(emp.getEmployeeInfo().getPhone(), equalTo(phone));
        return this;
    }

    @Step("Create UpdateEmployeeRequest with EmployeeInfo")
    public UpdateEmployeeRequest createUpdateEmployeeRequest(EmployeeInfo employeeInfo) {
        UpdateEmployeeRequest updateEmployeeRequest = objectFactory.createUpdateEmployeeRequest();
        updateEmployeeRequest.setEmployeeInfo(employeeInfo);
        return updateEmployeeRequest;
    }

    @Step("Unmarshall UpdateEmployeeResponse from response")
    public UpdateEmployeeResponse getUpdateEmployeeResponse(Response response) {
        return Unmarshall.unmarshallResponse(response.body().asString(), UpdateEmployeeResponse.class);
    }

    @Step("Validate UpdateEmployeeResponse status is SUCCESS")
    public IntegrationEmployeeSteps validateUpdateEmployeeResponseStatus(UpdateEmployeeResponse updateEmployeeResponse) {
        assertThat(updateEmployeeResponse.getServiceStatus().getStatus(), equalTo("SUCCESS"));
        return this;
    }

    @Step("Validate UpdateEmployeeResponse message is 'Content Updated Successfully'")
    public IntegrationEmployeeSteps validateUpdateEmployeeResponseMessage(UpdateEmployeeResponse updateEmployeeResponse) {
        assertThat(updateEmployeeResponse.getServiceStatus().getMessage(), equalTo("Content Updated Successfully"));
        return this;
    }

    @Step("Retrieve Employee from DB by id: {0}")
    public Employee returnEmployeeFromDB(long id) {
        return dbMapper().selectById(id);
    }

    @Step("Validate Employee name is {1}")
    public IntegrationEmployeeSteps validateEmployeeName(Employee employee, String name) {
        assertThat(employee.getName(), equalTo(name));
        return this;
    }

    @Step("Validate Employee address is {1}")
    public IntegrationEmployeeSteps validateEmployeeAdress(Employee employee, String adress) {
        assertThat(employee.getAddress(), equalTo(adress));
        return this;
    }

    @Step("Validate Employee id is {1}")
    public IntegrationEmployeeSteps validateEmployeeId(Employee employee, long id) {
        assertThat(employee.getEmployeeId(), equalTo(id));
        return this;
    }

    @Step("Validate Employee department is {1}")
    public IntegrationEmployeeSteps validateEmployeeDep(Employee employee, String dep) {
        assertThat(employee.getDepartment(), equalTo(dep));
        return this;
    }

    @Step("Validate Employee phone number is {1}")
    public IntegrationEmployeeSteps validateEmployeeNum(Employee employee, String num) {
        assertThat(employee.getPhone(), equalTo(num));
        return this;
    }

    @Step("Update Employee in DB with id: {0}")
    public IntegrationEmployeeSteps updateEmployeeFromDB(Long id, String name, String email, String address, String department, String phone,
                                                         Long salary, LocalDate birthDate) {
        dbMapper().updateEmployee(id, name, email, address, department, phone, salary, birthDate);
        return this;
    }

    @Step("Create DeleteEmployeeRequest for employeeId: {0}")
    public DeleteEmployeeRequest createDeleteEmployeeRequest(long employeeId) {
        DeleteEmployeeRequest deleteEmployeeRequest = objectFactory.createDeleteEmployeeRequest();
        deleteEmployeeRequest.setEmployeeId(employeeId);
        return deleteEmployeeRequest;
    }

    @Step("Unmarshall DeleteEmployeeResponse from response")
    public DeleteEmployeeResponse getDeleteEmployeeResponse(Response response) {
        return Unmarshall.unmarshallResponse(response.body().asString(), DeleteEmployeeResponse.class);
    }

    @Step("Validate DeleteEmployeeResponse status is SUCCESS")
    public IntegrationEmployeeSteps validateDeleteSuccessfull(DeleteEmployeeResponse deleteEmployeeResponse) {
        assertThat(deleteEmployeeResponse.getServiceStatus().getStatus(), equalTo("SUCCESS"));
        return this;
    }

    @Step("Validate status code is 500")
    public IntegrationEmployeeSteps validateStatusCode500(Response response) {
        response.then().assertThat().statusCode(500);
        return this;
    }

    @Step("Validate error message contains 'Source must not be null'")
    public IntegrationEmployeeSteps validateErrorMessage(Response response) {
        response.then()
                .body(hasXPath("//*[local-name()='Fault']/*[local-name()='faultstring']", equalTo("Source must not be null")));
        return this;
    }
}
