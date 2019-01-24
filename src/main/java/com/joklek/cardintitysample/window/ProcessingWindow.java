package com.joklek.cardintitysample.window;

import com.cardinity.model.CardinityError;
import com.cardinity.model.Payment;
import com.cardinity.model.Result;
import com.joklek.cardintitysample.payments.ShopDriver;
import com.joklek.cardintitysample.user.CardInfo;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        /** Request was valid but payment requires additional authentication. */
        if (result.isValid() && result.getItem().getStatus() == Payment.Status.PENDING) {
            UUID paymentId = result.getItem().getId();
            String acsURL = result.getItem().getAuthorizationInformation().getUrl();
            String paReq = result.getItem().getAuthorizationInformation().getData();
            // redirect customer to ACS server for 3D Secure authentication

            if(paReq.equals("3d-fail")) {
                System.out.println("Your card provider has declined your payment");
                return;
            }
            else if (!paReq.equals("3d-pass")) {
                System.out.println("Something went wrong, try again");
                return;
            }

            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                HttpPost httppost = new HttpPost(acsURL);

                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("PaReq", paReq));
                params.add(new BasicNameValuePair("TermUrl", paymentProcessor.getCallbackURL()));
                params.add(new BasicNameValuePair("MD", paymentId.toString()));
                httppost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

                String authorizeData = getAuthorizeData(httpclient, httppost);

                result = paymentProcessor.finalizePayment(paymentId, authorizeData);
            } catch (IOException e) {
                System.out.println("Error with connection, try again later");
            }
        }


        /** Request was valid and payment was approved. */
        if (result.isValid() && result.getItem().getStatus() == Payment.Status.APPROVED) {
            UUID paymentId = result.getItem().getId();
            System.out.println(String.format("Congrats, the payment with id %s has been made successfully", paymentId));
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

    private String getAuthorizeData(CloseableHttpClient httpclient, HttpPost httppost) throws IOException {
        HttpResponse response = httpclient.execute(httppost);
        String authorizeData = "";

        // This is a very ugly way to do this nad it'll break if the page changes
        String responseString = new BasicResponseHandler().handleResponse(response);
        Pattern p = Pattern.compile("name=\"PaRes\" value=\".*\"");
        Matcher m = p.matcher(responseString);
        if(m.find()) {
            authorizeData = m.group(0).split("\\s")[1].split("\"")[1];
        }
        return authorizeData;
    }
}
