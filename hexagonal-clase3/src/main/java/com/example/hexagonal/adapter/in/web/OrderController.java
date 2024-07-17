package com.example.hexagonal.adapter.in.web;

import com.example.hexagonal.application.service.OrderService;
import com.example.hexagonal.domain.model.Order;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderController {

    OrderService orderService;

    @Inject
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @POST
    public Response createOrder(Order order) {
        Order createdOrder = orderService.createOrder(order);
        return Response.status(Response.Status.CREATED).entity(createdOrder).build();
    }

    @GET
    @Path("/{id}")
    public Response getOrder(@PathParam("id") Long id) {
        Optional<Order> order = orderService.findById(id);
        return order.map(value -> Response.ok(value).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    public Response getAllOrders() {
        List<Order> orders = orderService.findAll();
        return Response.ok(orders).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOrder(@PathParam("id") Long id) {
        orderService.deleteById(id);
        return Response.noContent().build();
    }
}
