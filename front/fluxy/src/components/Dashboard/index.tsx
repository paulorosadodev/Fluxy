import { ReactNode, useEffect, useState } from "react";
import { DashboardsWrapper, DashboardWrapper, DashboardRow } from "./styles";
import { fetchAveragePrice, fetchCategoriesCount, fetchProductsCount, fetchProductsCountByCategory, fetchProductsTotalStock, fetchTotalPrice} from "../../services/endpoints/product/dashboard";
import { Card } from "../DashboardContents/Card";
import { Package, Notebook, Tag, CurrencyCircleDollar, CurrencyDollarSimple} from "phosphor-react";
import { ProductsByCategoryChart } from "../DashboardContents/ProductsByCategoryChart";
import { TopTier } from "../DashboardContents/TopTier";

type DashboardProps = {
    dataDashboards: string[][];
    graphs?: boolean
};

type DashboardRenderer = {
    fetch?: () => Promise<any>;
    render: (data?: any) => ReactNode;
};

const dashboardsController: Record<string, DashboardRenderer> = {
    productsTotalStock: {
        fetch: fetchProductsTotalStock,
        render: (data) => (
            <Card icon={Package} data={data} text="produtos em estoque" />
        ),
    },
    productsTotalCount: {
        fetch: fetchProductsCount,
        render: (data) => (
            <Card icon={Notebook} data={data} text="produtos cadastrados" />
        ),
    },
    productsCategoriesCount: {
        fetch: fetchCategoriesCount,
        render: (data) => (
            <Card icon={Tag} data={data} text="categorias cadastradas" />
        ),
    },
    productsAveragePrice: {
        fetch: fetchAveragePrice,
        render: (data) => (
            <Card icon={CurrencyCircleDollar} data={data} text="preço médio dos produtos" type="price" />
        ),
    },
    productsTotalPrice: {
        fetch: fetchTotalPrice,
        render: (data) => (
            <Card icon={CurrencyDollarSimple} data={data} text="valor total do estoque" type="price" />
        ),
    },
    productsCountByCategory: {
        fetch: fetchProductsCountByCategory,
        render: (data) => (
            <ProductsByCategoryChart data={data} />
        ),
    },
    topTierProducts: {
        render: () => (
            <TopTier />
        ),
    },
};

export const Dashboard = ({ dataDashboards, graphs }: DashboardProps) => {
    const [dashboards, setDashboards] = useState<ReactNode[][]>([]);

    const fetchData = async () => {
        const loaded: ReactNode[][] = [];

        for (const row of dataDashboards) {
            const rowComponents: ReactNode[] = [];

            for (const key of row) {
                const controller = dashboardsController[key];
                if (!controller) continue;

                try {
                    let component;
                    if (controller.fetch) {
                        const data = await controller.fetch();
                        component = controller.render(data);
                    } else {
                        component = controller.render();
                    }
                    rowComponents.push(component);
                } catch (error: any) {
                    console.error(`Erro ao buscar ${key}:`, error);
                    const fallbackData = 0;
                    const component = controller.render(fallbackData);
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
