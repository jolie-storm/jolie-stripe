package joliex.stripe;

import jolie.runtime.Value;
import junit.framework.TestCase;

public class StripeServiceTest extends TestCase {

    public void testCreateCustomer() {

        StripeService stripeService = new StripeService();
        Value request = Value.create();
        request.getFirstChild("apiKey").setValue("sk_test_51J7oQXGGZ7AGZizXdZ6xYcaUrfet7WfqGmls1HnGPMypQOqaZZcl83M0wjYvdaHFhmyLGdBtsBSOCv2ddTZRT4Tf00m60HZVzV");
        request.getFirstChild("email").setValue("bmaschio77@gmail.com");
        request.getFirstChild("description").setValue("fg4576575435363456");
        stripeService.createCustomer(request);

    }

    public void testCreateSubscription() {
        StripeService stripeService = new StripeService();
        Value request = Value.create();
        request.getFirstChild("apiKey").setValue("sk_test_51J7oQXGGZ7AGZizXdZ6xYcaUrfet7WfqGmls1HnGPMypQOqaZZcl83M0wjYvdaHFhmyLGdBtsBSOCv2ddTZRT4Tf00m60HZVzV");
        request.getFirstChild("customerId").setValue("cus_JsMLeB1ljtHeSd");
        request.getFirstChild("priceId").setValue("price_1J7ocpGGZ7AGZizXSdn9NaJJ");
        Value  response = stripeService.createSubscription(request);
        System.out.println(response.getFirstChild("clientSecret").strValue());


    }

    public void testListCustomers() {
        StripeService stripeService = new StripeService();
        Value request = Value.create();
        request.getFirstChild("apiKey").setValue("sk_test_51J7oQXGGZ7AGZizXdZ6xYcaUrfet7WfqGmls1HnGPMypQOqaZZcl83M0wjYvdaHFhmyLGdBtsBSOCv2ddTZRT4Tf00m60HZVzV");
        request.getFirstChild("email").setValue("bmaschio77@gmail.com");

        Value  response = stripeService.listCustomers(request);
        System.out.println(response.getChildren("customers").get(0).getFirstChild("id").strValue());
    }
}