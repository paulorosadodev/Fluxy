import { ReusablePieChart } from "../PieChart";

type DataItem = {
    cidade: string;
    totalClientes: number;
};

type ClientsByCityChartProps = {
    data?: DataItem[];
};

export const ClientsByCityChart = ({ data = [] }: ClientsByCityChartProps) => {
    return (
        <ReusablePieChart
            data={data}
            title="Clientes por Cidade"
            dataKey="totalClientes"
            nameKey="cidade"
        />
    );
};
