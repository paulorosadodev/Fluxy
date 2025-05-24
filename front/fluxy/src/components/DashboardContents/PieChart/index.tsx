import { PieChart, Pie, Cell, Tooltip, ResponsiveContainer } from "recharts";
import { ChartWrapper, ColorBox, InfoWrapper, MotionCategoryItem, MotionCategoryList, PieChartWrapper, Title } from "./styles";
import { useEffect, useState } from "react";

type DataItem = {
    [key: string]: any;
};

type PieChartProps<T extends DataItem> = {
    data?: T[];
    title: string;
    dataKey: string;
    nameKey: string;
    labelFormatter?: (value: any) => string;
};

const listVariants = {
    hidden: { opacity: 0 },
    visible: {
        opacity: 1,
        transition: {
            staggerChildren: 0.07,
        },
    },
};

const itemVariants = {
    hidden: { opacity: 0, x: -10 },
    visible: { opacity: 1, x: 0 },
};

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

export const ReusablePieChart = <T extends DataItem>({ 
    data = [], 
    title, 
    dataKey, 
    nameKey,
    labelFormatter 
}: PieChartProps<T>) => {
    const [ready, setReady] = useState(false);

    useEffect(() => {
        const timer = setTimeout(() => {
            setReady(true);
        }, 100); 
        return () => clearTimeout(timer);
    });

    const formatLabel = (value: any) => {
        return labelFormatter ? labelFormatter(value) : value;
    };

    const processDataForChart = (rawData: T[]): T[] => {
        if (!rawData || rawData.length <= 12) {
            return rawData;
        }

        const sortedData = [...rawData].sort((a, b) => {
            const valueA = Number(a[dataKey]) || 0;
            const valueB = Number(b[dataKey]) || 0;
            return valueB - valueA;
        });


        const topItems = sortedData.slice(0, 11);
        
        const remainingItems = sortedData.slice(11);
        const othersSum = remainingItems.reduce((sum, item) => {
            return sum + (Number(item[dataKey]) || 0);
        }, 0);

        if (remainingItems.length > 0) {
            const othersItem = {
                [nameKey]: "Outros",
                [dataKey]: othersSum
            } as T;
            
            return [...topItems, othersItem];
        }

        return topItems;
    };

    const processedData = processDataForChart(data);

    return (
        processedData ? (
            <PieChartWrapper>
                <InfoWrapper>
                    <Title>{title}</Title>
                    <MotionCategoryList variants={listVariants} initial="hidden" animate="visible">
                        {processedData && processedData.map((item, index) => (
                            <MotionCategoryItem key={item[nameKey]} variants={itemVariants}>
                                <ColorBox color={COLORS[index % COLORS.length]} />
                                {formatLabel(item[nameKey])}: {item[dataKey]}
                            </MotionCategoryItem>
                        ))}
                    </MotionCategoryList>
                </InfoWrapper>
                <ChartWrapper>
                    {ready && 
                    <ResponsiveContainer width="100%" height="100%">
                        <PieChart>
                            <Pie
                                data={processedData}
                                dataKey={dataKey}
                                nameKey={nameKey}
                                cx="50%"
                                cy="50%"
                                outerRadius={100}
                                startAngle={0}
                                endAngle={360}
                                isAnimationActive={true}
                                animationBegin={0}
                                animationDuration={1000}
                                animationEasing="ease-in-out"
                                stroke={processedData.length === 1 ? "none" : "#fff"}
                            >
                                {processedData && processedData.map((_, index) => (
                                    <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                                ))}
                            </Pie>
                            <Tooltip />
                        </PieChart>
                    </ResponsiveContainer>
                    }
                </ChartWrapper>
            </PieChartWrapper>
        ) : (
            <></>
        )
    );
};
