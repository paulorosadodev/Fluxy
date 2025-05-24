import { ReusablePieChart } from "../PieChart";

type DataItem = {
    categoryCode: string;
    productCount: number;
};

type ProductsByCategoryChartProps = {
    data?: DataItem[];
};

export const ProductsByCategoryChart = ({ data = [] }: ProductsByCategoryChartProps) => {
    return (
        <ReusablePieChart<DataItem>
            data={data}
            title="Produtos por Categoria"
            dataKey="productCount"
            nameKey="categoryCode"
        />
    );
};
