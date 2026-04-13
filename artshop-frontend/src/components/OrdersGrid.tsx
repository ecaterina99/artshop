import { AgGridReact } from "ag-grid-react";
import { ColDef, ModuleRegistry, AllCommunityModule } from "ag-grid-community";
import "ag-grid-community/styles/ag-grid.css";
import "ag-grid-community/styles/ag-theme-quartz.css";
import { Order } from "../types/Order";

ModuleRegistry.registerModules([AllCommunityModule]);

const columnDefs: ColDef[] = [
    { field: "id", headerName: "Order #", sortable: true },
    { field: "status", headerName: "Status", sortable: true, filter: true },
    {
        headerName: "Items",
        valueGetter: (params) =>
            params.data.orderItems
                .map(item => `Painting #${item.paintingId} ×${item.quantity}`)
                .join(", "),
        sortable: true,
    },
    {
        field: "totalPrice",
        headerName: "Total",
        sortable: true,
        valueFormatter: (params) => `$${params.value.toFixed(2)}`,
    },
];

interface OrdersGridProps {
    orders: Order[];
}

export default function OrdersGrid({ orders }: OrdersGridProps) {
    return (
        <div className="ag-theme-quartz" style={{ height: 400 }}>
            <AgGridReact
                rowData={orders}
                columnDefs={columnDefs}
                pagination={true}
                paginationPageSize={10}
            />
        </div>
    );
}
