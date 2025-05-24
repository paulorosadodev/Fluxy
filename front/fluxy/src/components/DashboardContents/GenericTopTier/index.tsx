import { useEffect, useState } from "react";
import { SortInput, SortSelect, TopTierWrapper, MotionListWrapper, MotionListItem } from "./styles";
import { CurrencyCircleDollar } from "phosphor-react";

type TopTierItem = {
    name: string;
    value: string | number;
    [key: string]: any;
};

type TopTierConfig = {
    title: {
        high: string;
        low: string;
    };
    sortOptions: {
        high: { value: string; label: string };
        low: { value: string; label: string };
    };
    icon?: React.ComponentType<any>;
    valueFormatter?: (value: string | number) => string;
    valueKey?: string;
    nameKey?: string;
};

type TopTierProps = {
    fetchHighData: () => Promise<TopTierItem[]>;
    fetchLowData: () => Promise<TopTierItem[]>;
    config: TopTierConfig;
    dependencies?: any[];
};

const listVariants = {
    hidden: { opacity: 0 },
    visible: {
        opacity: 1,
        transition: {
            staggerChildren: 0.2,
        },
    },
};

const itemVariants = {
    hidden: { opacity: 0, x: -10 },
    visible: { opacity: 1, x: 0 },
};

export const GenericTopTier = ({ 
    fetchHighData, 
    fetchLowData, 
    config, 
    dependencies = [] 
}: TopTierProps) => {
    const [sortField, setSortField] = useState<string>(config.sortOptions.high.value);
    const [highData, setHighData] = useState<TopTierItem[]>();
    const [lowData, setLowData] = useState<TopTierItem[]>();

    const Icon = config.icon || CurrencyCircleDollar;
    const valueKey = config.valueKey || "value";
    const nameKey = config.nameKey || "name";
    const valueFormatter = config.valueFormatter || ((value: string | number) => String(value));

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [highResult, lowResult] = await Promise.all([
                    fetchHighData(),
                    fetchLowData()
                ]);
                setHighData(highResult);
                setLowData(lowResult);
            } catch (error) {
                console.error("Erro ao buscar dados do TopTier:", error);
            }
        };

        fetchData();
    }, dependencies);

    const currentData = sortField === config.sortOptions.high.value ? highData : lowData;
    const currentTitle = sortField === config.sortOptions.high.value ? config.title.high : config.title.low;

    return (
        currentData ? (
            <TopTierWrapper>
                <h2>{currentTitle}</h2>
                <SortInput>
                    <SortSelect value={sortField} onChange={(e: any) => setSortField(e.target.value)}>
                        <option value={config.sortOptions.high.value}>
                            {config.sortOptions.high.label}
                        </option>
                        <option value={config.sortOptions.low.value}>
                            {config.sortOptions.low.label}
                        </option>
                    </SortSelect>
                </SortInput>
                <MotionListWrapper
                    variants={listVariants}
                    initial="hidden"
                    animate="visible"
                >
                    {currentData.map((item, index) => (
                        <MotionListItem key={index} variants={itemVariants}>
                            <span>
                                <Icon 
                                    size={20} 
                                    weight="bold" 
                                    className={`arrow ${sortField === config.sortOptions.high.value ? "up" : "down"}`} 
                                />
                                <strong>{valueFormatter(item[valueKey])}</strong>
                            </span>
                            <span>
                                {item[nameKey]}
                            </span>
                        </MotionListItem>
                    ))}
                </MotionListWrapper>
            </TopTierWrapper>
        ) : (
            <></>
        )
    );
};
