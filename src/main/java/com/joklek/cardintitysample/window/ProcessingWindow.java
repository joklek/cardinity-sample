package com.joklek.cardintitysample.window;

import com.cardinity.model.CardinityError;
import com.cardinity.model.Payment;
import com.cardinity.model.Result;
import com.joklek.cardintitysample.ShopDriver;
import com.joklek.cardintitysample.user.CardInfo;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("squid:S106")
public class ProcessingWindow extends AbstractWindow {

    @Autowired
    private ShopDriver paymentProcessor;

    public ProcessingWindow() {
        super();
    }

    @Override
    public String getTitle() {
        return "Your cart";
    }

    @Override
    public void render(Map<String, Object> args) {
        CardInfo cardInfo = (CardInfo) args.get("card");
        BigDecimal price = (BigDecimal) args.get("price");
        UUID cartId = (UUID) args.get("cartId");
        Result<Payment> result = paymentProcessor.makePayment(cardInfo, price, cartId);

        /** Request was valid and payment was approved. */
        if (result.isValid() && result.getItem().getStatus() == Payment.Status.APPROVED) {
            UUID paymentId = result.getItem().getId();
            System.out.println(String.format("Congrats, the payment with id %s has been made successfully", paymentId));
        }
        /** Request was valid but payment requires additional authentication. */
        else if (result.isValid() && result.getItem().getStatus() == Payment.Status.PENDING) {
            UUID paymentId = result.getItem().getId();
            String acsURL = result.getItem().getAuthorizationInformation().getUrl();
            String paReq = result.getItem().getAuthorizationInformation().getData();

            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                HttpPost httppost = new HttpPost(acsURL);
                httppost.addHeader("PaReq", paReq);
                httppost.addHeader("TermUrl", paymentProcessor.getCallbackURL());
                httppost.addHeader("MD", paymentId.toString());
                // redirect customer to ACS server for 3D Secure authentication

                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
            } catch (IOException e) {
                System.out.println("Error with connection, try again later");
            }
        }
        /** Request was valid but payment was declined. */
        else if (result.isValid()) {
            String declineReason = result.getItem().getError();
            // proceed with declined payment flow
            System.out.println(String.format("Your payment was declined with the reason %s", declineReason));
        }
        /** Request was invalid.
         *   Possible reasons: wrong credentials, unsupported currency, suspended account, etc.
         */
        else {
            CardinityError error = result.getCardinityError();
            // log error details for debugging
            // proceed with error handling flow
            System.out.println("An unindentified error has been encountered, please try again");
        }
    }
}
