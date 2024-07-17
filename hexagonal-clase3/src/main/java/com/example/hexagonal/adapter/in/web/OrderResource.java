package com.example.hexagonal.adapter.in.web;

import com.example.hexagonal.application.ports.in.OrderUseCase;
import com.example.hexagonal.domain.model.Order;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/ordersV2")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    private final OrderUseCase orderUseCase;

    @Inject
    public OrderResource(OrderUseCase orderUseCase) {
        this.orderUseCase = orderUseCase;
    }

    @POST
    public Response createOrder(Order order) {
        Order createdOrder = orderUseCase.createOrder(order);
        return Response.status(Response.Status.CREATED).entity(createdOrder).build();
    }

    @GET
    @Path("/{id}")
    public Response getOrder(@PathParam("id") Long id) {
        Optional<Order> order = orderUseCase.findById(id);
        return order.map(value -> Response.ok(value).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    public Response getAllOrders() {
        List<Order> orders = orderUseCase.findAll();
        return Response.ok(orders).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOrder(@PathParam("id") Long id) {
        orderUseCase.deleteById(id);
        return Response.noContent().build();
    }
}

