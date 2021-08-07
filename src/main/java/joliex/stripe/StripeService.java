package joliex.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerCollection;
import com.stripe.model.Subscription;
import com.stripe.param.SubscriptionCreateParams;
import jolie.runtime.CanUseJars;
import jolie.runtime.JavaService;
import jolie.runtime.Value;
import jolie.runtime.embedding.RequestResponse;

import java.util.Arrays;
import java.util.HashMap;

public class StripeService extends JavaService {

    @CanUseJars( {
            "stripe-java-20.64.0.jar"
    } )

    @RequestResponse
    public Value createCustomer(Value request) {

        Value response = Value.create();
        try {
            Stripe.apiKey = request.getFirstChild("apiKey").strValue();
            HashMap<String, Object> params = new HashMap<>();
            params.put("email", request.getFirstChild("email").strValue());
            params.put("description", request.getFirstChild("description").strValue());
            Customer customer = Customer.create(params);
            response.getFirstChild("customerId").setValue(customer.getId());
        } catch (StripeException e) {
            e.printStackTrace();
        }

        return response;
    }


    @RequestResponse

    public Value listCustomers (Value request){
        Value response = Value.create();
        try {
            Stripe.apiKey = request.getFirstChild("apiKey").strValue();
            HashMap<String, Object> params = new HashMap<>();
            params.put("email", request.getFirstChild("email").strValue());

            CustomerCollection customers = Customer.list(params);
            customers.getData().forEach(
                    customer-> {
                        Value customerValue = Value.create();
                        customerValue.getFirstChild("id").setValue(customer.getId());
                        response.getChildren("customers").add(customerValue);
                    }
            );
        } catch (StripeException e) {
            e.printStackTrace();
        }

        return response;
    }



    @RequestResponse

    public Value createSubscription (Value request){
        Value response = Value.create();
        try {
            Stripe.apiKey = request.getFirstChild("apiKey").strValue();
            HashMap<String, Object> params = new HashMap<>();
            params.put("customer", request.getFirstChild("customerId").strValue());
            params.put("price", request.getFirstChild("priceId").strValue());
            SubscriptionCreateParams subscriptionCreateParams = SubscriptionCreateParams.builder()
                                                                .setCustomer(request.getFirstChild("customerId").strValue())
                                                                .addItem(SubscriptionCreateParams.Item.builder().setPrice (request.getFirstChild("priceId").strValue()).build())
                                                                .setPaymentBehavior(SubscriptionCreateParams.PaymentBehavior.DEFAULT_INCOMPLETE)
                                                                .addAllExpand(Arrays.asList("latest_invoice.payment_intent"))
                                                                .build();
            Subscription subscription = Subscription.create(subscriptionCreateParams);
            response.getFirstChild("clientSecret").setValue(subscription.getLatestInvoiceObject().getPaymentIntentObject().getClientSecret());
        } catch (StripeException e) {
            e.printStackTrace();
        }
        return response;
    }

}
