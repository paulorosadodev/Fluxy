import { ReusableBarChart } from "../ReusableBarChart";
import { fetchMostBoughtProducts } from "../../../services/endpoints/product/dashboard";

interface MostBoughtProduct {
    id: number;
    name: string;
    totalBought: number;
}

export function MostBoughtProductsChart() {
    const adaptedFetchData = async () => {
        const data = await fetchMostBoughtProducts();
        return data.map((product: MostBoughtProduct) => ({
            name: product.name,
            totalComprado: product.totalBought,
            id: product.id
        }));
    };

    const valueFormatter = (value: number) => {
        return `${value}`;
    };

    return (
        <ReusableBarChart
            title="Produtos Mais Comprados"
            fetchData={adaptedFetchData}
            nameKey="name"
            valueKey="totalComprado"
            valueFormatter={valueFormatter}
            maxBars={10}
        />
    );
}
