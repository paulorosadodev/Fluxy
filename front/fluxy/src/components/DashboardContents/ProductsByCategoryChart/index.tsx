import { PieChart, Pie, Cell, Tooltip, ResponsiveContainer } from "recharts";
import { ChartWrapper, ColorBox, InfoWrapper, MotionCategoryItem, MotionCategoryList, ProductsByCategoryChartWrapper, Title } from "./styles";
import { useEffect, useState } from "react";

type DataItem = {
    categoryCode: string;
    productCount: number;
};

type ProductsByCategoryChartProps = {
    data: DataItem[];
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

export const ProductsByCategoryChart = ({ data }: ProductsByCategoryChartProps) => {
    const [ready, setReady] = useState(false);

    useEffect(() => {
        const timer = setTimeout(() => {
            setReady(true);
        }, 100); 
        return () => clearTimeout(timer);
    }, []);

    return (
        <ProductsByCategoryChartWrapper>
            <InfoWrapper>
                <Title>Produtos por Categoria</Title>
                <MotionCategoryList variants={listVariants} initial="hidden" animate="visible">
                    {data.map((item, index) => (
                        <MotionCategoryItem key={item.categoryCode} variants={itemVariants}>
                            <ColorBox color={COLORS[index % COLORS.length]} />
                            {item.categoryCode}
                        </MotionCategoryItem>
                    ))}
                </MotionCategoryList>
            </InfoWrapper>
            <ChartWrapper>
                {ready && 
                    <ResponsiveContainer width="100%" height="100%">
                        <PieChart>
                            <Pie
                                data={data}
                                dataKey="productCount"
                                nameKey="categoryCode"
                                cx="50%"
                                cy="50%"
                                outerRadius={100}
                                startAngle={0}
                                endAngle={360}
                                isAnimationActive={true}
                                animationBegin={0}
                                animationDuration={1000}
                                animationEasing="ease-in-out"
                            >
                                {data.map((_, index) => (
                                    <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                                ))}
                            </Pie>
                            <Tooltip />
                        </PieChart>
                    </ResponsiveContainer>
                }
            </ChartWrapper>
        </ProductsByCategoryChartWrapper>
    );
};
