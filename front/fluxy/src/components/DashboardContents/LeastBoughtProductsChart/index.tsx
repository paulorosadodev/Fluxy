import { ReusableBarChart } from "../ReusableBarChart";
import { fetchLeastBoughtProducts } from "../../../services/endpoints/product/dashboard";

interface LeastBoughtProduct {
    id: number;
    name: string;
    totalBought: number;
}

export function LeastBoughtProductsChart() {
    const adaptedFetchData = async () => {
        const data = await fetchLeastBoughtProducts();
        return data.map((product: LeastBoughtProduct) => ({
            nome: product.name,
            totalComprado: product.totalBought,
            id: product.id
        }));
    };

    const valueFormatter = (value: number) => {
        return `${value}`;
    };

    return (
        <ReusableBarChart
            title="Produtos Menos Comprados"
            fetchData={adaptedFetchData}
            nameKey="nome"
            valueKey="totalComprado"
            valueFormatter={valueFormatter}
            maxBars={10}
        />
    );
}
