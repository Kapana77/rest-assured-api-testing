package ge.tbc.testautomation.steps.soap;

import asd.newproject.second.*;
import ge.tbc.testautomation.util.SoapServiceSender;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.xml.HasXPath.hasXPath;

public class EmployeesSteps {
    ObjectFactory objectFactory = new ObjectFactory();

    public EmployeeInfo createEmployee(String firstName, long id, String dep, String address) {
        EmployeeInfo employeeInfo = objectFactory.createEmployeeInfo();
        employeeInfo.setName(firstName);
        employeeInfo.setEmployeeId(id);
        employeeInfo.setDepartment(dep);
        employeeInfo.setAddress(address);

        return employeeInfo;
    }

    public AddEmployeeRequest createAddEmployeeRequest(EmployeeInfo employeeInfo) {
        AddEmployeeRequest addEmployeeRequest = objectFactory.createAddEmployeeRequest();
        addEmployeeRequest.setEmployeeInfo(employeeInfo);
        return addEmployeeRequest;
    }

    public Response sendService(String url, String action, String body) {
        return SoapServiceSender.send(url, action, body)
                .then().log().all().statusCode(200).extract().response();
    }

    public EmployeesSteps validateMessage(Response response, String message) {
        response.then()
                .body(hasXPath("//*[local-name()='message']", equalTo(message)));
        return this;
    }

    public GetEmployeeByIdRequest createGetEmployeeByIdReq(long employeeId) {
        GetEmployeeByIdRequest getEmployeeByIdRequest = objectFactory.createGetEmployeeByIdRequest();
        getEmployeeByIdRequest.setEmployeeId(employeeId);
        return getEmployeeByIdRequest;
    }

    public Response sendServiceWithNS(String url, String action, String body) {
        return SoapServiceSender.sendWithNameSpaces(url, action, body)
                .then().log().all().extract().response();

    }

    public EmployeesSteps validateId(Response response, long id) {
        response.then()
                .body(hasXPath("//*[local-name()='employeeId']/text()", equalTo(String.valueOf(id))));
        return this;

    }

    public EmployeesSteps validateName(Response response, String name) {
        response.then()
                .body(hasXPath("//*[local-name()='name']/text()", equalTo(name)));
        return this;

    }

    public EmployeesSteps validateDepartment(Response response, String department) {
        response.then()
                .body(hasXPath("//*[local-name()='department']/text()", equalTo(department)));
        return this;
    }

    public EmployeesSteps validateAddress(Response response, String address) {
        response.then()
                .body(hasXPath("//*[local-name()='address']/text()", equalTo(address)));
        return this;

    }

    public UpdateEmployeeRequest createUpdateEmployeeRequest(EmployeeInfo employeeInfo) {
        UpdateEmployeeRequest updateEmployeeRequest = objectFactory.createUpdateEmployeeRequest();
        updateEmployeeRequest.setEmployeeInfo(employeeInfo);
        return updateEmployeeRequest;
    }

    public DeleteEmployeeRequest createDeleteEmployeeRequest(long employeeId) {
        DeleteEmployeeRequest deleteEmployeeRequest = objectFactory.createDeleteEmployeeRequest();
        deleteEmployeeRequest.setEmployeeId(employeeId);
        return deleteEmployeeRequest;

    }

    public EmployeesSteps validateStatusCode200(Response response) {
        response.then().assertThat().statusCode(200);
        return this;
    }

    public EmployeesSteps validateStatusCode500(Response response) {
        response.then().assertThat().statusCode(500);
        return this;
    }

    public EmployeesSteps validateErrorMessage(Response response) {
        response.then()
                .body(hasXPath("//*[local-name()='Fault']/*[local-name()='faultstring']", equalTo("Source must not be null")));
        return this;

    }


}
