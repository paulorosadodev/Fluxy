import { ReusableBarChart } from "../ReusableBarChart";
import { fetchMostPurchasedCategories } from "../../../services/endpoints/product/dashboard";

interface MostPurchasedCategoriesData {
    nome: string;
    totalComprado: number;
}

export function MostPurchasedCategoriesChart() {
    const handleFetchData = async (): Promise<MostPurchasedCategoriesData[]> => {
        return await fetchMostPurchasedCategories();
    };

    return (
        <ReusableBarChart
            title="Categorias Mais Compradas"
            fetchData={handleFetchData}
            nameKey="nome"
            valueKey="totalComprado"
            valueFormatter={(value: number) => value.toString()}
            maxBars={12}
        />
    );
}
