package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import pages.AddEmployeePage;
import pages.DashBoardPage;
import utils.CommonMethods;
import utils.Constants;
import utils.ExcelReading;
import utils.GlobalVariables;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.*;

public class AddEmployeeSteps extends CommonMethods {
    @When("user clicks on PIM option")
    public void user_clicks_on_pim_option() {
        DashBoardPage dash = new DashBoardPage();
        click(dash.PIM);
    }
    @When("user clicks on Add employee button")
    public void user_clicks_on_add_employee_button() {
        DashBoardPage dash = new DashBoardPage();
        click(dash.addEmployee);
    }
    @When("user enters firstname middlename and lastname")
    public void user_enters_first_name_middle_name_and_lastname() {
        AddEmployeePage add=new AddEmployeePage();
        sendText(add.firstName, "Yuliya555");
        sendText(add.middleName, "Mulia");
        sendText(add.lastName, "Klukach2003");


    }

    @When("user enters first name {string} middle name {string} and last name {string}")
    public void user_enters_first_name_middle_name_and_lastname(String firstName, String middleName, String lastName) {
        AddEmployeePage add=new AddEmployeePage();
        sendText(add.firstName, firstName);
        sendText(add.middleName, middleName);
        sendText(add.lastName, lastName);
        GlobalVariables.FirstName=firstName;
        GlobalVariables.MiddleName=middleName;
        GlobalVariables.LastName=lastName;

    }

    @When("user enters {string} {string} and {string} in the application")
    public void user_enters_and_in_the_application(String FirstName, String MiddleName, String LastName) {
        AddEmployeePage add = new AddEmployeePage();
        sendText(add.firstName, FirstName);
        sendText(add.middleName, MiddleName);
        sendText(add.lastName, LastName);

    }

    @When("user clicks on save button option")
    public void user_clicks_on_save_button_option() {
        AddEmployeePage add=new AddEmployeePage();
        click(add.saveBtn);
    }
    @Then("employee added successfully")
    public void employee_added_successfully() {
        System.out.println("Employee added successfully");
    }

    @When("add multiple employees and verify they are added successfully")
    public void add_multiple_employees_and_verify_they_are_added_successfully(DataTable employees) throws InterruptedException {
        List<Map<String, String>> employeeNames=employees.asMaps();
        for(Map<String, String> employeename:employeeNames) {
            String firstnamevalue=employeename.get("FirstName");
            String middlenamevalue=employeename.get("MiddleName");
            String lastnamevalue=employeename.get("LastName");

            System.out.println(firstnamevalue+" "+middlenamevalue+" "+lastnamevalue);

            AddEmployeePage addEmployeePage=new AddEmployeePage();
            sendText(addEmployeePage.firstName, firstnamevalue);
            sendText(addEmployeePage.middleName, middlenamevalue);
            sendText(addEmployeePage.lastName, lastnamevalue);
            click(addEmployeePage.saveBtn);

            //assertion take it as HW
            Thread.sleep(5000);
            DashBoardPage dash=new DashBoardPage();
            click(dash.addEmployee);
            Thread.sleep(3000);

        }
    }

    @When("user adds multiple employees from excel file from {string} sheet and verify they are added")
    public void user_adds_multiple_employees_from_excel_file_from_sheet_and_verify_they_are_added(String sheetname) throws InterruptedException {
        List<Map<String, String>> newemployees= ExcelReading.excelIntoListMap(Constants.TESTDATA_FILEPATH, sheetname);
        DashBoardPage dashBoardPage=new DashBoardPage();
        AddEmployeePage addEmployeePage=new AddEmployeePage();
        Iterator <Map<String,String>> it=newemployees.iterator();
        while(it.hasNext()){
            Map<String ,String> mapNewEmpl=it.next();
            sendText(addEmployeePage.firstName,mapNewEmpl.get("FirstName"));
            sendText(addEmployeePage.middleName,mapNewEmpl.get("MiddleName"));
            sendText(addEmployeePage.lastName,mapNewEmpl.get("LastName"));
            click(addEmployeePage.saveBtn);
            Thread.sleep(1000);
        }
    }
    @When("capture the employeeId")
    public void capture_the_employee_id() {
        AddEmployeePage addEmployeePage=new AddEmployeePage();
        GlobalVariables.empID=addEmployeePage.emplpyeeID.getAttribute("value");
    }

    @Then("verify the date from frontend and backend")
    public void verify_the_date_from_frontend_and_backend() {
        System.out.println("backend");
        System.out.println("DBFirstName"+GlobalVariables.dbFirstName);
        System.out.println("DBMiddleName"+GlobalVariables.dbMiddleName);
        System.out.println("DBLastName"+GlobalVariables.dbLastName);
        System.out.println("frontend");
        System.out.println("FirstName"+GlobalVariables.FirstName);
        System.out.println("MiddleName"+GlobalVariables.MiddleName);
        System.out.println("LastName"+GlobalVariables.LastName);
        Assert.assertEquals(GlobalVariables.FirstName,GlobalVariables.dbFirstName);
        Assert.assertEquals(GlobalVariables.MiddleName,GlobalVariables.dbMiddleName);
        Assert.assertEquals(GlobalVariables.LastName,GlobalVariables.dbLastName);

            }

}
