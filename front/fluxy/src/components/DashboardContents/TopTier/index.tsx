import { useEffect, useState } from "react";
import { SortInput, SortSelect, TopTierWrapper, MotionListWrapper, MotionListItem } from "./styles";
import { fetchLeastExpensiveProducts, fetchMostExpensiveProducts } from "../../../services/endpoints/product/dashboard";
import { formatMoney } from "../../../utils";
import { Tag, CurrencyCircleDollar } from "phosphor-react";

type Product = {
    name: string;
    price: string;
}

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

export const TopTier = () => {

    const [sortField, setSortField] = useState<string>("most-expensive");
    const [mostExpensive, setMostExpensive] = useState<Product[]>();
    const [leastExpensive, setLeastExpensive] = useState<Product[]>();

    useEffect(() => {
        const fetchData = async () => {
            const mostExpensiveData =  await fetchMostExpensiveProducts();
            const leastExpensiveData =  await fetchLeastExpensiveProducts();
            setMostExpensive(mostExpensiveData);
            setLeastExpensive(leastExpensiveData);
        };

        fetchData();

    }, []);

    return (
        <TopTierWrapper>
            {
                sortField === "most-expensive"  ? (
                    <h2>Maiores preços</h2>
                ) : (
                    <h2>Menores preços</h2>
                )
            }
            <SortInput>
                <SortSelect value={String(sortField)} onChange={(e) => setSortField(e.target.value)}>
                    <option value="most-expensive">Maior preço</option>
                    <option value="least-expensive">Menor preço</option>
                </SortSelect>
            </SortInput>
            <MotionListWrapper
                variants={listVariants}
                initial="hidden"
                animate="visible"
            >
                {sortField === "most-expensive" && mostExpensive ? (
                    mostExpensive.map((product, index) => (
                        <MotionListItem key={index} variants={itemVariants}>
                            <span>
                                <CurrencyCircleDollar size={20} weight="bold" className="arrow up" />
                                <strong>{formatMoney(Number(product.price))}</strong>
                            </span>
                            <span>
                                {product.name}
                            </span>
                        </MotionListItem>
                    ))
                ) : (
                    leastExpensive?.map((product, index) => (
                        <MotionListItem key={index} variants={itemVariants}>
                            <span>
                                <CurrencyCircleDollar size={20} weight="bold" className="arrow down" />
                                <strong>{formatMoney(Number(product.price))}</strong>
                            </span>
                            <span>
                                {product.name}
                            </span>
                        </MotionListItem>
                    ))
                )}
            </MotionListWrapper>


        </TopTierWrapper>
    );

};