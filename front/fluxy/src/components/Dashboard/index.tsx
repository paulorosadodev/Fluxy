import { ReactNode, useEffect, useState } from "react";
import { DashboardsWrapper, DashboardWrapper, DashboardRow } from "./styles";
import { fetchAveragePrice, fetchCategoriesCount, fetchLowStockProducts, fetchProductsCount, fetchProductsCountByCategory, fetchProductsTotalStock, fetchTotalPrice} from "../../services/endpoints/product/dashboard";
import { Card } from "../DashboardContents/Card";
import { Package, Notebook, Tag, CurrencyCircleDollar, CurrencyDollarSimple} from "phosphor-react";
import { ProductsByCategoryChart } from "../DashboardContents/ProductsByCategoryChart";
import { TopTier } from "../DashboardContents/TopTier";
import { LowStockProducts } from "../DashboardContents/LowStockProducts";
import { ProductPriceHistoryWithSelect } from "../DashboardContents/ProductPriceHistory/ProductPriceHistoryWithSelect";

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
            <Card icon={Package} data={data} text={data > 1 ? "produtos em estoque" : "produto em estoque"} delay={delay} />
        ),
    },
    productsTotalCount: {
        fetch: fetchProductsCount,
        render: (data, delay = 0) => (
            <Card icon={Notebook} data={data} text={data > 1 ? "produtos cadastrados" : "produto cadastrado"} delay={delay} />
        ),
    },
    productsCategoriesCount: {
        fetch: fetchCategoriesCount,
        render: (data, delay = 0) => (
            <Card icon={Tag} data={data} text={data > 1 ? "categorias cadastradas" : "categoria cadastrada"} delay={delay} />
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
    topTierProducts: {
        render: () => (
            <TopTier />
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
            <ProductPriceHistoryWithSelect />
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
                    
                    const isCard = ["productsTotalStock", "productsTotalCount", "productsCategoriesCount", "productsAveragePrice", "productsTotalPrice"].includes(key);
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
                    const isCard = ["productsTotalStock", "productsTotalCount", "productsCategoriesCount", "productsAveragePrice", "productsTotalPrice"].includes(key);
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
