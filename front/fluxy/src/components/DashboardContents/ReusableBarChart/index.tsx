import { BarChart, Bar, XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer, Cell } from "recharts";
import { 
    ChartWrapper, 
    Title, 
    HeaderContainer, 
    LoadingOverlay, 
    ContainerWithSpinner, 
    Spinner 
} from "../ReusableLineChart/styles";
import { useEffect, useState } from "react";

const COLORS = [
    "#EE6C4D",
    "#F38D68",
    "#FFBC9A",
    "#662C91",
    "#8B5FBF",
    "#A49FD4",
    "#17A398",
    "#40C0B0",
    "#9CE3DC",
    "#33312E",
    "#5A5563",
    "#CAC4CE",
];

interface ChartDataPoint {
    nome: string;
    totalComprado: number;
    [key: string]: any;
}

interface ReusableBarChartProps {
    title: string;
    height?: number;
    fetchData: () => Promise<ChartDataPoint[]>;
    nameKey?: string;
    valueKey?: string;
    valueFormatter?: (value: number) => string;
    maxBars?: number;
}

export function ReusableBarChart({ 
    title,
    height = 320, 
    fetchData,
    nameKey = "nome",
    valueKey = "totalComprado",
    valueFormatter = (value: number) => `${value}`,
    maxBars = 12
}: ReusableBarChartProps) {
    const [chartData, setChartData] = useState<ChartDataPoint[]>([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        setLoading(true);
        fetchData()
            .then((data) => {
                const sortedData = [...data].sort((a, b) => b[valueKey] - a[valueKey]);
                const limitedData = sortedData.slice(0, maxBars);
                setChartData(limitedData);
                setLoading(false);
            })
            .catch((error) => {
                console.error("Erro ao buscar dados:", error);
                setLoading(false);
            });
    }, [fetchData, valueKey, maxBars]);

    return (
        <ContainerWithSpinner $loading={loading}>
            {loading && (
                <LoadingOverlay>
                    <Spinner />
                    <span>Carregando...</span>
                </LoadingOverlay>
            )}
            <ChartWrapper>
                <HeaderContainer>
                    <Title>{title}</Title>
                </HeaderContainer>
                <ResponsiveContainer width="100%" height={height}>
                    <BarChart 
                        data={chartData} 
                        margin={{ top: 20, right: 30, left: -20, bottom: 20 }}
                    >
                        <CartesianGrid strokeDasharray="3 3" />
                        <XAxis 
                            dataKey={nameKey}
                            tick={{ fontSize: 10 }}
                            angle={-45}
                            textAnchor="end"
                            height={80}
                            interval={0}
                        />
                        <YAxis 
                            tickFormatter={valueFormatter}
                            width={70}
                            tick={{ fontSize: 12 }}
                        />
                        <Tooltip 
                            content={({ active, payload, label }) => {
                                if (active && payload && payload.length) {
                                    const dataIndex = chartData.findIndex(item => item[nameKey] === label);
                                    const color = COLORS[dataIndex % COLORS.length];
                                    const value = payload[0].value;
                                    
                                    return (
                                        <div style={{
                                            backgroundColor: "white",
                                            padding: "10px",
                                            border: "1px solid #ccc",
                                            borderRadius: "4px",
                                            boxShadow: "0 2px 4px rgba(0,0,0,0.1)"
                                        }}>
                                            <p style={{ 
                                                margin: "0 0 5px 0", 
                                                fontWeight: "bold",
                                                color: color 
                                            }}>
                                                {label}
                                            </p>
                                            <p style={{ 
                                                margin: 0, 
                                                color: "#666" 
                                            }}>
                                                Compras: {valueFormatter(value as number)}
                                            </p>
                                        </div>
                                    );
                                }
                                return null;
                            }}
                        />
                        <Bar 
                            dataKey={valueKey} 
                            radius={[4, 4, 0, 0]}
                        >
                            {chartData.map((_, index) => (
                                <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                            ))}
                        </Bar>
                    </BarChart>
                </ResponsiveContainer>
            </ChartWrapper>
        </ContainerWithSpinner>
    );
}
