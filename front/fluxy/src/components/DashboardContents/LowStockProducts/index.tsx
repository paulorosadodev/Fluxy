import { WarningCircle, CheckCircle } from "phosphor-react";
import { useEffect, useState } from "react";
import { LowStockWrapper, Title, MotionList, MotionListItem, ProductName, ProductInfo, AlertIcon, MessageWrapper } from "./styles";
import { Product } from "../../../@types";

type LowStockProductsProps = {
    data: Product[];
};

const listVariants = {
    hidden: { opacity: 0 },
    visible: {
        opacity: 1,
        transition: {
            staggerChildren: 0.08,
        },
    },
};

const itemVariants = {
    hidden: { opacity: 0, x: -15 },
    visible: { opacity: 1, x: 0 },
};

function getStockClass(stockQuantity: number) {
    if (stockQuantity === 0) return "stock-zero";
    if (stockQuantity >= 1 && stockQuantity <= 5) return "stock-low";
    if (stockQuantity >= 6 && stockQuantity <= 10) return "stock-medium";
    return "";
}

export const LowStockProducts = ({ data = []}: LowStockProductsProps) => {
    const [ready, setReady] = useState(false);

    useEffect(() => {
        const timer = setTimeout(() => setReady(true), 100);
        return () => clearTimeout(timer);
    }, []);

    return (
        data ? (
            <LowStockWrapper>
                <Title>Produtos com Baixo Estoque</Title>
                {ready && (
                    data.length > 0 ? (
                        <MotionList variants={listVariants} initial="hidden" animate="visible">
                            {data.map((product, index) => (
                                <MotionListItem key={index} variants={itemVariants} className={getStockClass(product.stockQuantity)}>
                                    <AlertIcon>
                                        <WarningCircle id="warning-circle" className={getStockClass(product.stockQuantity)} size={24} />
                                    </AlertIcon>
                                    <ProductInfo>
                                        <ProductName>{product.name}</ProductName>
                                        <span>{product.stockQuantity} unidades</span>
                                    </ProductInfo>
                                </MotionListItem>
                            ))}
                        </MotionList>
                    ) : (
                        <MessageWrapper>
                            <CheckCircle id="svg" />
                            <p>Nenhum produto com estoque baixo</p>
                        </MessageWrapper>
                    )
                )}
            </LowStockWrapper>
        ) : (
            <></>
        )
    );
};
