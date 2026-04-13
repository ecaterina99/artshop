import type { Meta, StoryObj } from "@storybook/react";
import PaintingCard from "./PaintingCard";
import "../App.css";

const meta: Meta<typeof PaintingCard> = {
    title: "Components/PaintingCard",
    component: PaintingCard,
    decorators: [
        (Story) => (
            <div style={{ maxWidth: 320 }}>
                <Story />
            </div>
        ),
    ],
};
export default meta;

type Story = StoryObj<typeof PaintingCard>;

export const Modern: Story = {
    args: {
        painting: {
            id: 1,
            name: "Sunset Over Mountains",
            description: "A vibrant modern painting of a sunset",
            length: 80,
            high: 60,
            style: "MODERN",
            price: 5000,
            medium: "Oil on canvas",
            img: "https://placehold.co/400x300/e67e22/fff?text=Sunset",
        },
    },
};

export const Impressionist: Story = {
    args: {
        painting: {
            id: 2,
            name: "Water Lilies",
            description: "Impressionist water lilies painting",
            length: 120,
            high: 90,
            style: "IMPRESSIONIST",
            price: 12000,
            medium: "Oil on canvas",
            img: "https://placehold.co/400x300/2ecc71/fff?text=Lilies",
        },
    },
};

export const Minimalist: Story = {
    args: {
        painting: {
            id: 3,
            name: "Black Circle",
            description: "A minimalist composition",
            length: 50,
            high: 50,
            style: "MINIMALIST",
            price: 3500,
            medium: "Acrylic on canvas",
            img: "https://placehold.co/400x300/2c3e50/fff?text=Circle",
        },
    },
};
