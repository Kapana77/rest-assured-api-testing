package ge.tbc.testautomation.db.mapper;

import ge.tbc.testautomation.db.models.Employee;
import ge.tbc.testautomation.db.models.User;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;

@Mapper
public interface CompanyMapper {
    @Insert("""
        INSERT INTO employee (
            birth_date, salary, employee_id, address, department, email, name, phone
        ) VALUES (
            #{birthDate}, #{salary}, #{employeeId}, #{address}, #{department}, #{email}, #{name}, #{phone}
        )
    """)
    @Options(useGeneratedKeys = true, keyProperty = "employeeId")
    void insertEmployee(Employee employee);

    @Select("SELECT employee_id as employeeId, * FROM employee WHERE employee_id = #{id}")
    Employee selectById(Long id);

    @Update("UPDATE employee SET name = #{name}, email = #{email}, address = #{address}, department = #{department} phone = #{phone}, salary = #{salary}, birth_date = #{birthDate} WHERE employee_id = #{employeeId}")
    void updateEmployee(@Param("employeeId") Long employeeId, @Param("name") String name, @Param("email") String email,
                                @Param("address") String address, @Param("department") String department, @Param("phone") String phone,
                                @Param("salary") Long salary, @Param("birthDate") LocalDate birthDate);

    @Select("SELECT COUNT(*) FROM employee")
    int count();

    @Select("SELECT id, firstname AS firstName, lastname AS lastName, email, password_hash AS passwordHash, role, active FROM users WHERE id = #{id}")
    User selectUserById(Long id);
    @Select("SELECT id, firstname AS firstName, lastname AS lastName, email, password_hash AS passwordHash, role, active FROM users WHERE email = #{mail}")
    User selectUserByMail(String mail);



}
