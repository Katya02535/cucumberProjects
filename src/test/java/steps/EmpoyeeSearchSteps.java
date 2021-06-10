package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.DashBoardPage;
import pages.EmployeeListPage;
import pages.LoginPage;
import utils.CommonMethods;
import utils.ConfigReader;

public class EmpoyeeSearchSteps extends CommonMethods {

    @Given("user navigates to hrms")
    public void user_navigates_to_hrms() {
        setUp();
    }

    @Given("user is logged in with valid admin credentials")
    public void user_is_logged_in_with_valid_admin_credentials() {
        LoginPage loginPage=new LoginPage();
        sendText(loginPage.usernamebox, ConfigReader.getPropertyValue("username"));
        sendText(loginPage.passwordbox, ConfigReader.getPropertyValue("password"));
        click(loginPage.loginBtn);
    }

    @Given("user navigates to employee list page")
    public void user_navigates_to_employee_list_page() {
        DashBoardPage dashBoardPage=new DashBoardPage();
        click(dashBoardPage.PIM);
        click(dashBoardPage.employeeListOption);

    }

    @When("user enters valid employee id")
    public void user_enters_valid_employee_id() {
        EmployeeListPage elp=new EmployeeListPage();
        sendText(elp.idEmployee,"15518");
    }

    @When("clicks on search button")
    public void clicks_on_search_button() {
        EmployeeListPage elp=new EmployeeListPage();
        click(elp.searchbutton);
    }

    @When("user enters valid employee name")
    public void user_enters_valid_employee_name() {
        EmployeeListPage elp=new EmployeeListPage();
        sendText(elp.employeeNameField,"sofiia");
    }


    @Then("user sees employee information is displayed")
    public void user_sees_employee_information_is_displayed() {
        System.out.println("Employee name is dispalyed");
        tearDown();
    }


}
