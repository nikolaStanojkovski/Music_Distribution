package com.musicdistribution.streamingservice.domain.model.response.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

/**
 * Object used for data transfer from the
 * back-end to the front-end for a transaction.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private String orderId;
    private URI approvalLink;

    /**
     * Method used for building a order response object.
     *
     * @param orderId      - the ID of the order which was created.
     * @param approvalLink - the approval link of the transaction to be made.
     * @return the created response object.
     */
    public static OrderResponse from(String orderId, URI approvalLink) {
        OrderResponse order = new OrderResponse();
        order.setOrderId(orderId);
        order.setApprovalLink(approvalLink);

        return order;
    }
}
