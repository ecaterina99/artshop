import type { Meta, StoryObj } from "@storybook/react";
import OrdersGrid from "./OrdersGrid";
import "../App.css";

const meta: Meta<typeof OrdersGrid> = {
    title: "Components/OrdersGrid",
    component: OrdersGrid,
};
export default meta;

type Story = StoryObj<typeof OrdersGrid>;

export const WithOrders: Story = {
    args: {
        orders: [
            {
                id: 1,
                userId: 1,
                status: "CREATED",
                totalPrice: 5000,
                orderItems: [
                    { id: 1, orderId: 1, paintingId: 3, quantity: 2 },
                ],
            },
            {
                id: 2,
                userId: 1,
                status: "PAID",
                totalPrice: 8500,
                orderItems: [
                    { id: 2, orderId: 2, paintingId: 1, quantity: 1 },
                    { id: 3, orderId: 2, paintingId: 5, quantity: 1 },
                ],
            },
            {
                id: 3,
                userId: 1,
                status: "SHIPPED",
                totalPrice: 3500,
                orderItems: [
                    { id: 4, orderId: 3, paintingId: 7, quantity: 1 },
                ],
            },
            {
                id: 4,
                userId: 1,
                status: "DELIVERED",
                totalPrice: 12000,
                orderItems: [
                    { id: 5, orderId: 4, paintingId: 2, quantity: 3 },
                    { id: 6, orderId: 4, paintingId: 9, quantity: 1 },
                ],
            },
        ],
    },
};

export const SingleOrder: Story = {
    args: {
        orders: [
            {
                id: 1,
                userId: 1,
                status: "CREATED",
                totalPrice: 5000,
                orderItems: [
                    { id: 1, orderId: 1, paintingId: 3, quantity: 1 },
                ],
            },
        ],
    },
};
