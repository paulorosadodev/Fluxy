import { ReactNode, useEffect, useState } from "react";
import { DashboardsWrapper, DashboardWrapper, DashboardRow } from "./styles";
import { fetchAveragePrice, fetchCategoriesCount, fetchLowStockProducts, fetchProductsCount, fetchProductsCountByCategory, fetchProductsTotalStock, fetchTotalPrice} from "../../services/endpoints/product/dashboard";
import { fetchEmployeesCount, fetchTotalSalaries, fetchEmployeeCountByShift, fetchEmployeeCountByRole } from "../../services/endpoints/employee/dashboard";
import { fetchSuppliersCount } from "../../services/endpoints/supplier/dashboard";
import { fetchTotalDeliveries, fetchTotalDeliveryCostByMonth, fetchDeliveryMonthAverageCost, fetchDeliveriesByMonth, fetchTotalCost, fetchTotalAverageCost } from "../../services/endpoints/supply/dashboard";
import { fetchTotalPurchases } from "../../services/endpoints/purchase/dashboard";
import { Card } from "../DashboardContents/Card";
import { Package, Notebook, Tag, CurrencyCircleDollar, CurrencyDollarSimple, Users, UserCircle, Buildings, User, Money, Truck, CaretCircleDoubleDown, ShoppingCart} from "phosphor-react";
import { ProductsByCategoryChart } from "../DashboardContents/ProductsByCategoryChart";
import { EmployeesByShiftChart } from "../DashboardContents/EmployeesByShiftChart";
import { EmployeesByRoleChart } from "../DashboardContents/EmployeesByRoleChart";
import { TopTierProducts } from "../DashboardContents/TopTierProducts";
import { TopTierClients } from "../DashboardContents/TopTierClients";
import { TopTierEmployees } from "../DashboardContents/TopTierEmployees";
import { TopTierSuppliers } from "../DashboardContents/TopTierSuppliers";
import { TopTierDeliveries } from "../DashboardContents/TopTierDeliveries";
import { TopTierPurchases } from "../DashboardContents/TopTierPurchases";
import { LowStockProducts } from "../DashboardContents/LowStockProducts";
import { ProductPriceHistory } from "../DashboardContents/ProductPriceHistory";
import { DeliveriesByMonth } from "../DashboardContents/DeliveriesByMonth";
import { PurchasesByMonth } from "../DashboardContents/PurchasesByMonth";
import { fetchTotalClientsByCity, fetchTotalClients, fetchTotalPhysicalClients, fetchTotalJuridicalClients } from "../../services/endpoints/customer/dashboard";
import { ClientsByCityChart } from "../DashboardContents/ClientsByCityChart";
import { PaymentTypesChart } from "../DashboardContents/PaymentTypesChart";
import { MonthlyRevenue } from "../DashboardContents/MonthlyRevenue";

type DashboardProps = {
    dataDashboards: string[][];
    graphs?: boolean
};

type DashboardRenderer = {
    fetch?: () => Promise<any>;
    render: (data?: any, delay?: number) => ReactNode;
};

const dashboardsController: Record<string, DashboardRenderer> = {
    productsTotalStock: {
        fetch: fetchProductsTotalStock,
        render: (data, delay = 0) => (
            <Card icon={Package} data={data} text={data !== 1 ? "produtos em estoque" : "produto em estoque"} delay={delay} />
        ),
    },
    productsTotalCount: {
        fetch: fetchProductsCount,
        render: (data, delay = 0) => (
            <Card icon={Notebook} data={data} text={data !== 1 ? "produtos cadastrados" : "produto cadastrado"} delay={delay} />
        ),
    },
    productsCategoriesCount: {
        fetch: fetchCategoriesCount,
        render: (data, delay = 0) => (
            <Card icon={Tag} data={data} text={data !== 1 ? "categorias cadastradas" : "categoria cadastrada"} delay={delay} />
        ),
    },
    productsAveragePrice: {
        fetch: fetchAveragePrice,
        render: (data, delay = 0) => (
            <Card icon={CurrencyCircleDollar} data={data} text="preço médio dos produtos" type="price" delay={delay} />
        ),
    },
    productsTotalPrice: {
        fetch: fetchTotalPrice,
        render: (data, delay = 0) => (
            <Card icon={CurrencyDollarSimple} data={data} text="valor total do estoque" type="price" delay={delay} />
        ),
    },
    productsCountByCategory: {
        fetch: fetchProductsCountByCategory,
        render: (data) => (
            <ProductsByCategoryChart data={data} />
        ),
    },
    employeesCountByShift: {
        fetch: fetchEmployeeCountByShift,
        render: (data) => (
            <EmployeesByShiftChart data={data} />
        ),
    },
    employeesCountByRole: {
        fetch: fetchEmployeeCountByRole,
        render: (data) => (
            <EmployeesByRoleChart data={data} />
        ),
    },
    topTierProducts: {
        render: () => (
            <TopTierProducts />
        ),
    },
    topTierClients: {
        render: () => (
            <TopTierClients />
        ),
    },
    topTierEmployees: {
        render: () => (
            <TopTierEmployees />
        ),
    },
    topTierSuppliers: {
        render: () => (
            <TopTierSuppliers />
        ),
    },
    topTierDeliveries: {
        render: () => (
            <TopTierDeliveries />
        ),
    },
    topTierPurchases: {
        render: () => (
            <TopTierPurchases />
        ),
    },
    deliveriesTotalCount: {
        fetch: fetchTotalDeliveries,
        render: (data, delay = 0) => (
            <Card icon={CaretCircleDoubleDown} data={data} text={data !== 1 ? "entregas realizadas" : "entrega realizada"} delay={delay} />
        ),
    },
    deliveriesMonthlyCost: {
        fetch: async () => {
            const currentDate = new Date();
            const currentMonth = currentDate.getMonth() + 1; 
            const currentYear = currentDate.getFullYear();
            return await fetchTotalDeliveryCostByMonth(currentMonth, currentYear);
        },
        render: (data, delay = 0) => (
            <Card icon={CurrencyDollarSimple} data={data} text="custo este mês" type="price" delay={delay} />
        ),
    },
    deliveriesMonthlyAverageCost: {
        fetch: async () => {
            const currentDate = new Date();
            const currentMonth = currentDate.getMonth() + 1; 
            const currentYear = currentDate.getFullYear();
            return await fetchDeliveryMonthAverageCost(currentMonth, currentYear);
        },
        render: (data, delay = 0) => (
            <Card icon={CurrencyCircleDollar} data={data} text="custo médio este mês" type="price" delay={delay} />
        ),
    },
    deliveriesMonthlyCount: {
        fetch: async () => {
            const currentDate = new Date();
            const currentMonth = currentDate.getMonth() + 1; 
            const currentYear = currentDate.getFullYear();
            return await fetchDeliveriesByMonth(currentMonth, currentYear);
        },
        render: (data, delay = 0) => (
            <Card icon={CaretCircleDoubleDown} data={data} text={data !== 1 ? "entregas este mês" : "entrega este mês"} delay={delay} />
        ),
    },
    deliveriesTotalCost: {
        fetch: fetchTotalCost,
        render: (data, delay = 0) => (
            <Card icon={CurrencyDollarSimple} data={data} text="custo total" type="price" delay={delay} />
        ),
    },
    deliveriesTotalAverageCost: {
        fetch: fetchTotalAverageCost,
        render: (data, delay = 0) => (
            <Card icon={CurrencyCircleDollar} data={data} text="custo médio total" type="price" delay={delay} />
        ),
    },
    suppliersTotalCount: {
        fetch: fetchSuppliersCount,
        render: (data, delay = 0) => (
            <Card icon={Truck} data={data} text={data !== 1 ? "fornecedores cadastrados" : "fornecedor cadastrado"} delay={delay} />
        ),
    },
    lowStockProducts: {
        fetch: fetchLowStockProducts,
        render: (data) => (
            <LowStockProducts data={data} />
        ),
    },
    productPriceHistory: {
        render: () => (
            <ProductPriceHistory />
        ),
    },
    deliveriesByMonth: {
        render: () => (
            <DeliveriesByMonth />
        ),
    },
    purchasesByMonth: {
        render: () => (
            <PurchasesByMonth />
        ),
    },
    clientsByCity: {
        fetch: fetchTotalClientsByCity,
        render: (data) => (
            <ClientsByCityChart data={data} />
        ),
    },
    clientsTotalCount: {
        fetch: fetchTotalClients,
        render: (data, delay = 0) => (
            <Card icon={Users} data={data} text={data !== 1 ? "clientes cadastrados" : "cliente cadastrado"} delay={delay} />
        ),
    },
    clientsPhysicalCount: {
        fetch: fetchTotalPhysicalClients,
        render: (data, delay = 0) => (
            <Card icon={UserCircle} data={data} text={data !== 1 ? "clientes físicos" : "cliente físico"} delay={delay} />
        ),
    },
    clientsJuridicalCount: {
        fetch: fetchTotalJuridicalClients,
        render: (data, delay = 0) => (
            <Card icon={Buildings} data={data} text={data !== 1 ? "clientes jurídicos" : "cliente jurídico"} delay={delay} />
        ),
    },
    employeesTotalCount: {
        fetch: fetchEmployeesCount,
        render: (data, delay = 0) => (
            <Card icon={User} data={data} text={data !== 1 ? "funcionários cadastrados" : "funcionário cadastrado"} delay={delay} />
        ),
    },
    employeesTotalSalaries: {
        fetch: fetchTotalSalaries,
        render: (data, delay = 0) => (
            <Card icon={Money} data={data} text="folha salarial mensal" type="price" delay={delay} />
        ),
    },
    purchasesTotalCount: {
        fetch: fetchTotalPurchases,
        render: (data, delay = 0) => (
            <Card icon={ShoppingCart} data={data} text={data !== 1 ? "compras realizadas" : "compra realizada"} delay={delay} />
        ),
    },
    paymentTypesChart: {
        render: () => (
            <PaymentTypesChart />
        ),
    },
    monthlyRevenue: {
        render: (_, delay = 0) => (
            <MonthlyRevenue delay={delay} />
        ),
    },
};

export const Dashboard = ({ dataDashboards, graphs }: DashboardProps) => {
    const [dashboards, setDashboards] = useState<ReactNode[][]>([]);

    const fetchData = async () => {
        const loaded: ReactNode[][] = [];
        let cardIndex = 0; 
        const DELAY_BETWEEN_CARDS = 100; 

        for (const row of dataDashboards) {
            const rowComponents: ReactNode[] = [];

            for (const key of row) {
                const controller = dashboardsController[key];
                if (!controller) continue;

                try {
                    let component;
                    
                    const isCard = [
                        "productsTotalStock", 
                        "productsTotalCount", 
                        "productsCategoriesCount", 
                        "productsAveragePrice", 
                        "productsTotalPrice",
                        "clientsTotalCount",
                        "clientsPhysicalCount", 
                        "clientsJuridicalCount",
                        "employeesTotalCount",
                        "employeesTotalSalaries",
                        "suppliersTotalCount",
                        "deliveriesTotalCount",
                        "deliveriesMonthlyCost",
                        "deliveriesMonthlyAverageCost",
                        "deliveriesMonthlyCount",
                        "deliveriesTotalCost",
                        "deliveriesTotalAverageCost",
                        "purchasesTotalCount",
                        "monthlyRevenue"
                    ].includes(key);
                    const delay = isCard ? cardIndex * DELAY_BETWEEN_CARDS : 0;
                    
                    if (controller.fetch) {
                        const data = await controller.fetch();
                        component = isCard ? controller.render(data, delay) : controller.render(data);
                    } else {
                        component = isCard ? controller.render(undefined, delay) : controller.render();
                    }
                    
                    if (isCard) cardIndex++; 
                    rowComponents.push(component);
                } catch (error: any) {
                    console.error(`Erro ao buscar ${key}:`, error);
                    const fallbackData = 0;
                    const isCard = [
                        "productsTotalStock", 
                        "productsTotalCount", 
                        "productsCategoriesCount", 
                        "productsAveragePrice", 
                        "productsTotalPrice",
                        "clientsTotalCount",
                        "clientsPhysicalCount", 
                        "clientsJuridicalCount",
                        "employeesTotalCount",
                        "employeesTotalSalaries",
                        "suppliersTotalCount",
                        "deliveriesTotalCount",
                        "deliveriesMonthlyCost",
                        "deliveriesMonthlyAverageCost",
                        "deliveriesMonthlyCount",
                        "deliveriesTotalCost",
                        "deliveriesTotalAverageCost",
                        "purchasesTotalCount",
                        "monthlyRevenue"
                    ].includes(key);
                    const delay = isCard ? cardIndex * DELAY_BETWEEN_CARDS : 0;
                    const component = isCard ? controller.render(fallbackData, delay) : controller.render(fallbackData);
                    if (isCard) cardIndex++; 
                    rowComponents.push(component);
                }
            }

            loaded.push(rowComponents);
        }

        setDashboards(loaded);
    };

    useEffect(() => {
        fetchData();

        const interval = setInterval(() => {
            setDashboards((prev) => {
                if (prev.length === 0) {
                    fetchData();
                }
                return prev;
            });
        }, 2000);

        return () => clearInterval(interval);
    }, [dataDashboards]);

    return (
        <DashboardsWrapper className={`dashboard ${graphs ? "graphs" : ""}`}>
            {dashboards.map((row, rowIndex) => (
                <DashboardRow key={rowIndex}>
                    {row.map((component, colIndex) => (
                        <DashboardWrapper key={colIndex}>
                            {component}
                        </DashboardWrapper>
                    ))}
                </DashboardRow>
            ))}
        </DashboardsWrapper>
    );
};
