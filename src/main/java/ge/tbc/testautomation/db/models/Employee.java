package ge.tbc.testautomation.db.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private LocalDate birthDate;
    private Long salary;
    private Long employeeId;
    private String address;
    private String department;
    private String email;
    private String name;
    private String phone;


}
