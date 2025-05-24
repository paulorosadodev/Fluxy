import { LineChart, Line, XAxis, YAxis, Tooltip, CartesianGrid } from "recharts";
import { ChartWrapper, Title, HeaderContainer, LoadingOverlay, StyledResponsiveContainer } from "./styles";
import { formatDate } from "../../../utils";
import { ReactNode } from "react";

interface PriceHistoryPoint {
    price: number;
    date: string;
}

interface PriceHistoryChartProps {
    data: PriceHistoryPoint[];
    height?: number;
    selectInput?: ReactNode;
    loading?: boolean;
}

export function ProductPriceHistory({ data, height = 320, selectInput, loading = false }: PriceHistoryChartProps) {
    return (
        <ChartWrapper>
            <HeaderContainer>
                <Title>Histórico de Preço</Title>
                {selectInput}
            </HeaderContainer>
            {loading && (
                <LoadingOverlay>
                    Carregando...
                </LoadingOverlay>
            )}
            <StyledResponsiveContainer width="100%" height={height} $loading={loading}>
                <LineChart 
                    data={data} 
                    margin={{ top: 20, right: 0, left: 0, bottom: 20 }}
                >
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis 
                        dataKey="date" 
                        tickFormatter={date => formatDate(date)} 
                        height={50}
                        tick={{ fontSize: 12 }}
                        padding={{ left: 10, right: 10 }}
                    />
                    <YAxis 
                        dataKey="price" 
                        tickFormatter={price => `R$ ${price.toFixed(2)}`}
                        width={70}
                        tick={{ fontSize: 12 }}
                        domain={["auto", "dataMax + 5"]}
                        allowDataOverflow={false}
                    />
                    <Tooltip 
                        formatter={(value: number) => [`R$ ${value.toFixed(2)}`, "Preço"]} 
                        labelFormatter={label => `Data: ${formatDate(label)}`} 
                    />
                    <Line 
                        type="monotone" 
                        dataKey="price" 
                        stroke="#8884d8" 
                        strokeWidth={2} 
                        dot={{ r: 3 }} 
                        activeDot={{ r: 6 }} 
                    />
                </LineChart>
            </StyledResponsiveContainer>
        </ChartWrapper>
    );
}
