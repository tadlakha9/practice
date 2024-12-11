package com.example.practice.client;

import com.example.practice.commons.RetrofitClient;
import com.example.practice.dto.CustomerDto;
import reactor.core.publisher.Mono;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

import static com.example.practice.client.constants.ServiceNamesConstants.CUSTOMER_URL_TEST;
import static com.example.practice.client.constants.ServiceNamesConstants.CUSTOMER_VALUE;


@RetrofitClient(value = CUSTOMER_VALUE, url = CUSTOMER_URL_TEST)
public interface CustomerClient {

    @GET("api/customer")
    Mono<List<CustomerDto>> findAssessmentsByOwner(@Query("owner") String owner);

}
