import { Dashboard } from "../../../components/Dashboard";

export default function MainDashboard() {

    return (
        <div id="main">
            <h1>Dashboard</h1>
            <Dashboard 
                dataDashboards={[
                    ["productsTotalCount", "suppliersTotalCount", "deliveriesTotalCount", "employeesTotalCount", "clientsTotalCount", "purchasesTotalCount"],
                    ["monthlyRevenue", "monthlyExpenses", "monthlyProfit"],
                    ["financialLineChart"],
                    ["lowStockProducts"]
                ]}
                isMainDashboard={true}
            />
        </div>
    );
}
