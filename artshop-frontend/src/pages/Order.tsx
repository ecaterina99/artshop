import {fetchMyOrders} from "../api/api";
import {useAuth} from "react-oidc-context";
import {useApi} from "../hooks/useApi";
import { AgGridReact } from "ag-grid-react";
import { ColDef } from "ag-grid-community";
import "ag-grid-community/styles/ag-grid.css";        // grid structure CSS
import "ag-grid-community/styles/ag-theme-quartz.css"; // visual theme
  import { ModuleRegistry, AllCommunityModule } from 'ag-grid-community';


export default function MyOrders() {
 ModuleRegistry.registerModules([ AllCommunityModule ]);
    const auth = useAuth();
    const token = auth.user?.access_token;

    const{data: orders, error, loading} = useApi(() => {
        if (!token) return Promise.resolve([]);
        return fetchMyOrders(token);
    }, [token])

    if (loading) return <p className="loading">Loading orders...</p>;
    if (error) return <p className="error">{error}</p>;

    if (!orders || orders.length === 0) {
        return (
            <div className="orders-page">
                <h1>My Orders</h1>
                <div className="empty-state">
                    <h2>No orders yet</h2>
                    <p>Your completed orders will appear here.</p>
                </div>
            </div>
        );
    }

 const columnDefs: ColDef[] = [
      { field: "id", headerName: "Order #", sortable: true },
      { field: "status", headerName: "Status", sortable: true, filter: true },
      {
          headerName: "Items",
          // valueGetter = custom function that computes a display value,instead of reading a simple field, it reads the nested array
          valueGetter: (params) =>
              params.data.orderItems
                  .map(item => `Painting #${item.paintingId} ×${item.quantity}`)
                  .join(", "),
          // "Painting #1 ×2, Painting #5 ×1"
          sortable: true,
      },
      {
          field: "totalPrice",
          headerName: "Total",
          sortable: true,
          // valueFormatter = changes how the value LOOKS (not the data itself)
          valueFormatter: (params) => `$${params.value.toFixed(2)}`,
          // 150 → "$150.00"
      },
  ];


    return (
        <div className="orders-page">
            <h1>My Orders</h1>
            <div className="ag-theme-quartz" style={{ height: 400 }}>
                  <AgGridReact
                      rowData={orders}           // ← your orders array
                      columnDefs={columnDefs}    // ← column config from above
                      pagination={true}          // ← adds page controls at bottom
                      paginationPageSize={10}    // ← 10 rows per page
                  />
              </div>
        </div>
    );
}
