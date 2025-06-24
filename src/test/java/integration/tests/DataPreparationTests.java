package integration.tests;

import com.github.javafaker.Faker;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.db.models.User;
import ge.tbc.testautomation.steps.integration.DataPreparationSteps;
import ge.tbc.testautomation.steps.integration.IntegrationFlowSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import local.spring.model.AuthenticationResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.config.DataBaseConfig.dbMapper;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class DataPreparationTests {
    private final DataPreparationSteps dataPreparationSteps= new DataPreparationSteps();
    private final IntegrationFlowSteps steps = new IntegrationFlowSteps();
    private final Faker faker = new Faker();
    private final String mail = faker.internet().emailAddress();
    private Long id;
    private String JWTToken;
    private String refreshToken;

    @BeforeClass
    public void init(){
        steps.setup();
    }

    @Description("insert employee in database and register")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void insertAndRegister(){
        dataPreparationSteps.insertEmployee(mail,204L);
        AuthenticationResponse authenticationResponse = steps.registerUserValidEmail(mail, Constants.STRONG_PASS);
        id = authenticationResponse.getId();
        JWTToken = authenticationResponse.getAccessToken();
        refreshToken = authenticationResponse.getRefreshToken();
        steps.validateProtectedMessage(JWTToken);

        User selected = dbMapper().selectUserByMail(mail);

        assertThat(selected,notNullValue());
        assertThat(selected.getEmail(),equalTo(mail));


    }
}
