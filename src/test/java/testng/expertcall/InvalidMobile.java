package testng.expertcall;

import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.DataChunk;
import factory.Page;
import provider.Property;
import provider.Provider;
import util.step.Step;

public class InvalidMobile extends Page {
	@SuppressWarnings("static-access")
	@BeforeClass
	public void setData() {
		Provider.file("expertcall").sheet("invalidMobile");
	}

	@BeforeMethod
	public void setDetails(ITestResult result) {
		Details.bridge(result).description("Book an expert call with invalid mobile number")
				.name("Book Expert Call Mobile Number").device(Property.browser())
				.category("Negative", "Unit", "ExpertCall", "Input");
	}

	@Test(dataProvider = "data", dataProviderClass = Provider.class)
	public void expertCallWithInvalidData(DataChunk chunk) {
		Step steps = new Step(SS, chunk);
		steps.add((data) -> {
			view("/bricks/advertise-with-us");
		}, "User is on Advertisement Page");
		steps.add((data) -> {
			expertCallPage.selectCountryCode(data[0]);
		}, "User selects ^ country code", "c_code");
		steps.add((data) -> {
			expertCallPage.enterPhoneNumber(data[0]);
		}, "Enters ^ mobile number", "mob_num");
		steps.add((data) -> {
			expertCallPage.clickOnSubmitBtn();
		}, "User clicks on Get A Callback button");
		steps.add((data) -> {
			expertCallPage.verifyError(data[0]);
		}, "Error message ^ is displayed for Mobile", "err_msg");
		steps.exec();
	}
}
