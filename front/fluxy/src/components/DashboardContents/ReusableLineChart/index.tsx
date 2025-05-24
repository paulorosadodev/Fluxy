import { LineChart, Line, XAxis, YAxis, Tooltip, CartesianGrid } from "recharts";
import { 
    ChartWrapper, 
    Title, 
    HeaderContainer, 
    LoadingOverlay, 
    StyledResponsiveContainer,
    ContainerWithSpinner, 
    SelectContainer, 
    SelectDropdown, 
    Spinner 
} from "./styles";
import { formatDate } from "../../../utils";
import { useEffect, useState } from "react";

interface ChartDataPoint {
    value: number;
    date: string;
    [key: string]: any;
}

interface SelectOption {
    value: string;
    label: string;
}

interface ReusableLineChartProps {
    title: string;
    height?: number;
    fetchData: (selectedValue: string) => Promise<ChartDataPoint[]>;
    options: SelectOption[];
    valueKey?: string;
    dateKey?: string;
    lineColor?: string;
    valueFormatter?: (value: number) => string;
    dateFormatter?: (date: string) => string;
}

export function ReusableLineChart({ 
    title,
    height = 320, 
    fetchData,
    options,
    valueKey = "value",
    dateKey = "date",
    lineColor = "#8884d8",
    valueFormatter = (value: number) => `${value}`,
    dateFormatter = formatDate
}: ReusableLineChartProps) {
    const [selectedValue, setSelectedValue] = useState<string>("");
    const [chartData, setChartData] = useState<ChartDataPoint[]>([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        if (options.length > 0 && !selectedValue) {
            setSelectedValue(options[0].value);
        }
    }, [options, selectedValue]);

    useEffect(() => {
        if (!selectedValue) return;
        
        setLoading(true);
        fetchData(selectedValue)
            .then((data) => {
                setChartData(data);
                setLoading(false);
            })
            .catch((error) => {
                console.error("Erro ao buscar dados:", error);
                setLoading(false);
            });
    }, [selectedValue, fetchData]);

    const handleSelectChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        e.preventDefault();
        setSelectedValue(e.target.value);
        e.currentTarget.blur();
    };

    const selectInput = (
        <SelectContainer>
            <SelectDropdown
                id="chart-select"
                value={selectedValue}
                onChange={handleSelectChange}
            >
                {options.map(option => (
                    <option key={option.value} value={option.value}>{option.label}</option>
                ))}
            </SelectDropdown>
        </SelectContainer>
    );

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
                    {selectInput}
                </HeaderContainer>
                <StyledResponsiveContainer width="100%" height={height} $loading={loading}>
                    <LineChart 
                        data={chartData} 
                        margin={{ top: 20, right: 0, left: 0, bottom: 20 }}
                    >
                        <CartesianGrid strokeDasharray="3 3" />
                        <XAxis 
                            dataKey={dateKey}  
                            tickFormatter={dateFormatter}  
                            height={50}
                            tick={{ fontSize: 12 }}
                            padding={{ left: 10, right: 10 }}
                        />
                        <YAxis 
                            dataKey={valueKey} 
                            tickFormatter={valueFormatter}
                            width={70}
                            tick={{ fontSize: 12 }}
                            domain={["auto", "dataMax + 5"]}
                            allowDataOverflow={false}
                        />
                        <Tooltip 
                            formatter={(value: number) => [valueFormatter(value), title]} 
                            labelFormatter={label => `Data: ${dateFormatter(label)}`} 
                        />
                        <Line 
                            type="monotone" 
                            dataKey={valueKey} 
                            stroke={lineColor} 
                            strokeWidth={2} 
                            dot={{ r: 3 }} 
                            activeDot={{ r: 6 }} 
                        />
                    </LineChart>
                </StyledResponsiveContainer>
            </ChartWrapper>
        </ContainerWithSpinner>
    );
}
